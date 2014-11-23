/*******************************************************************************
 * Copyright 2013
 *
 * Jaroslaw Czub
 * http://shad.mobi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ******************************************************************************/
package mobi.shad.s3lib.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.files.FileHandle;
import mobi.shad.s3lib.main.S3File;
import mobi.shad.s3lib.main.S3Log;

/**
 * AudioAnalyzer - playing ogg or mp3 file and spectrum analyzing.
 * Can detect impact rate for kick or foot
 */
public class AudioAnalyzer{
	private static final String TAG = "MusicPlayer";

	private String audioFileName = "";
	private Mpg123Decoder decoder;
	private AudioDevice device;
	private KissFFT fft;
	private boolean playing = false;
	private float playTime = 0.0f;
	private int bufferSize = 2048;
	private short[] samples = new short[bufferSize];
	private float[] spectrum = new float[bufferSize];
	private float[] maxValues = new float[bufferSize];
	private float[] topValues = new float[bufferSize];
	private float[] top2Values = new float[bufferSize];
	private float[] top3Values = new float[bufferSize];
	private float[] barValues = new float[bufferSize];
	private int spectrumBars = 128;
	private float fallingSpeed = 1;
	private Thread playbackThread;
	private PlayerRunnable playerRunnable;
	private int footStart = 0;
	private int footBars = 40;
	private float footDetect = 0.50f;
	private float footThreshold = 0.90f;
	private float maxFoot = 0;
	private float footSize = 0;
	private float avgFoot = 0;
	private boolean isFoot = false;
	private boolean footActive = false;
	private int kickStart = 35;
	private int kickBars = 40;
	private float kickDetect = 0.50f;
	private float kickThreshold = 0.70f;
	private float maxKick = 0;
	private float kickSize = 0;
	private float avgKick = 0;
	private boolean isKick = false;
	private boolean kickActive = false;
	private boolean canPlay = false;

	public class PlayerRunnable implements Runnable{

		float average = 0;

		@Override
		public void run(){
			int readSamples = 0;
			int nb = 0;

			if (canPlay == false){
				return;
			}

			try {
				while (playing && (readSamples = decoder.readSamples(samples, 0, samples.length)) > 0){
					fft.spectrum(samples, spectrum);
					device.writeSamples(samples, 0, readSamples);

					for (int i = 0; i < spectrumBars; i++){
						nb = (samples.length / spectrumBars) / 2;
						average = avg(i, nb);
						if (average > maxValues[i]){
							maxValues[i] = average;
						}

						if (average > topValues[i]){
							topValues[i] = average;
						}
						if (average > top2Values[i]){
							top2Values[i] = average;
						}
						if (average > top3Values[i]){
							top3Values[i] = average;
						}

						maxValues[i] -= fallingSpeed * 0.1;
						topValues[i] -= fallingSpeed;
						top2Values[i] -= fallingSpeed / 2;
						top3Values[i] -= fallingSpeed / 3;
						barValues[i] = average;
					}

					//
					// Detect foot
					//
					footSize = 0;
					avgFoot = 0;
					maxFoot = 0;
					for (int i = footStart; i < footBars; i++){
						avgFoot += (topValues[i] + top2Values[i] + top3Values[i]) / 3;
						maxFoot += maxValues[i];
					}
					if (avgFoot > maxFoot * footDetect){
						footSize = avgFoot / maxFoot;
						if (footSize > footThreshold){
							if (footActive == false){
								isFoot = true;
								footActive = true;
							}
						} else {
							footActive = false;
						}
					}


					//
					// Detect kick
					//
					kickSize = 0;
					avgKick = 0;
					maxKick = 0;
					for (int i = kickStart; i < kickBars; i++){
						avgKick += (topValues[i] + top2Values[i] + top3Values[i]) / 3;
						maxKick += maxValues[i];
					}
					if (avgKick > maxKick * kickDetect){
						kickSize = avgKick / maxKick;
						if (kickSize > kickThreshold){
							if (kickActive == false){
								isKick = true;
								kickActive = true;
							}
						} else {
							kickActive = false;
						}
					}

					Thread.yield();
				}
			} catch (Exception ex){
				S3Log.error(TAG, "Player exception", ex);
			}
			if (playing == false){
				for (int i = 0; i < spectrumBars; i++){
					maxValues[i] = 0;
					topValues[i] = 0;
					top2Values[i] = 0;
					top3Values[i] = 0;
					barValues[i] = 0;
				}
			}
		}

		private float avg(int pos, int nb){
			int sum = 0;
			for (int i = 0; i < nb; i++){
				sum += spectrum[pos + i];
			}

			return (float) (sum / nb);
		}
	}

	public AudioAnalyzer(String fileName){
		load(fileName);
	}

	public final void load(String fileName){
		audioFileName = fileName;

		fft = new KissFFT(bufferSize);
		for (int i = 0; i < bufferSize; i++){
			samples[i] = 0;
			spectrum[i] = 0;
			maxValues[i] = 0;
			topValues[i] = 0;
			top2Values[i] = 0;
			top3Values[i] = 0;
			barValues[i] = 0;
		}

		if (!fileName.equalsIgnoreCase("") && fileName.endsWith(".mp3")){
			FileHandle fileHandle = S3File.getFileHandle(audioFileName);
			if (fileHandle.exists()){
				try {
					FileHandle copyFileHeader = S3File.getFileHandle(audioFileName + "/tmp/" + fileHandle.name(), false, true);
					fileHandle.copyTo(copyFileHeader);

					decoder = new Mpg123Decoder(copyFileHeader);
					S3Log.log(TAG,
							  "Play file: " + fileName + " length: " + decoder.getLength() + " position: " + decoder.getPosition() + " rate: " + decoder.getRate() + " channels: " + decoder
							  .getChannels());
					device = Gdx.audio.newAudioDevice(decoder.getRate(), decoder.getChannels() == 1 ? true : false);
					playerRunnable = new PlayerRunnable();
					playbackThread = new Thread(playerRunnable);
					playbackThread.setDaemon(true);
					canPlay = true;
				} catch (Exception ex){
					S3Log.error(TAG, "Error load mp3 ...", ex);
				}
			} else {
				canPlay = false;
			}
		}
	}

	public void dispose(){
		playing = false;
		canPlay = false;
		if (device != null){
			device.dispose();
		}
		if (decoder != null){
			decoder.dispose();
		}
	}

	public void startPlay(){
		if (canPlay){
			decoder.setPosition(0);
			playbackThread.start();
			playing = true;
		}
	}

	public void stopPlay(){
		playing = false;
	}

	public int getSpectrumBars(){
		return spectrumBars;
	}

	public float[] getSpectrum(){
		return spectrum;
	}

	public float[] getMaxValues(){
		return maxValues;
	}

	public float[] getTopValues(){
		return topValues;
	}

	public float[] getTop2Values(){
		return top2Values;
	}

	public float[] getTop3Values(){
		return top3Values;
	}

	public float[] barValues(){
		return barValues;
	}

	//
	// Foot Detect
	//
	public float getFootDetect(){
		return footDetect;
	}

	public void setFootDetect(float footDetect){
		this.footDetect = footDetect;
	}

	public float getFootThreshold(){
		return footThreshold;
	}

	public void setFootThreshold(float footThreshold){
		this.footThreshold = footThreshold;
	}

	public int getFootStart(){
		return footStart;
	}

	public void setFootStart(int footStart){
		this.footStart = footStart;
	}

	public int getFootBars(){
		return footBars;
	}

	public void setFootBars(int footBars){
		this.footBars = footBars;
	}

	public float getFootSize(){
		return footSize;
	}

	public boolean isFoot(){
		boolean tmpFoot = isFoot;
		isFoot = false;
		return tmpFoot;
	}

	//
	// Kick Detect
	//
	public float getKickDetect(){
		return kickDetect;
	}

	public void setKickDetect(float kickDetect){
		this.kickDetect = kickDetect;
	}

	public float getKickThreshold(){
		return kickThreshold;
	}

	public void setKickThreshold(float kickThreshold){
		this.kickThreshold = kickThreshold;
	}

	public int getKickStart(){
		return kickStart;
	}

	public void setKickStart(int kickStart){
		this.kickStart = kickStart;
	}

	public int getKickBars(){
		return kickBars;
	}

	public void setKickBars(int kickBars){
		this.kickBars = kickBars;
	}

	public float getKickSize(){
		return kickSize;
	}

	public boolean isKick(){
		boolean tmpKick = isKick;
		isKick = false;
		return tmpKick;
	}
}
