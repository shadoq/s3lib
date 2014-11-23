/*******************************************************************************
 * Copyright 2014
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
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import mobi.shad.s3lib.main.S3File;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.constans.MusicPlayType;

/**
 * MusicPlayer - class to management audio file and playing
 */
public class MusicPlayer{

	private static final String TAG = "MusicPlayer";

	protected static Music music;
	private static MusicPlayer instance;
	protected String musicFileName = null;
	protected boolean musicIsInit = false;

	/**
	 * Get instance class
	 * @return
	 */
	public static MusicPlayer getInstance(){
		if (instance == null){
			instance = new MusicPlayer();
		}
		return instance;
	}

	/**
	 * Play audio file
	 * @param musicFileName
	 * @param playType
	 */
	public void play(String musicFileName, MusicPlayType playType){

		S3Log.info(TAG, "Play music: " + musicFileName + " type: " + playType);

		if (!musicFileName.contains("music/")){
			musicFileName = "music/" + musicFileName;
		}
		this.musicFileName = musicFileName;
		if (music != null){
			try {
				if (music.isPlaying()){
					music.stop();
				}
				music = null;
				musicIsInit = false;
			} catch (Exception e){
				S3Log.error(TAG, "Error stop play...", e);
			}
		}

		FileHandle fileHandle = S3File.getFileHandle(this.musicFileName);
		if (fileHandle.isDirectory()){
			S3Log.log(TAG, "File music: " + this.musicFileName+"  is directory !");
			return;
		}
		if (!fileHandle.exists()){
			S3Log.log(TAG, "File music: " + this.musicFileName+" not exists !");
			return;
		}
		try {
			S3Log.log(TAG, "Load music: " + this.musicFileName);
			music = Gdx.audio.newMusic(S3File.getFileHandle(this.musicFileName));
			if (music == null){
				return;
			}
			music.play();
			musicIsInit = true;
			if (playType == MusicPlayType.Once){
				music.setLooping(false);
			} else {
				music.setLooping(true);
			}
		} catch (Exception ex){
			S3Log.error(TAG, "Load music exception: " + this.musicFileName, ex);
		}
	}

	/**
	 * Stop playing audio file
	 */
	public void stop(){
		if (music != null){
			try {
				S3Log.info(TAG, "Stop play: " + musicFileName);
				if (music.isPlaying()){
					music.stop();
				}
				music = null;
				musicIsInit = false;
			} catch (Exception e){
				S3Log.error(TAG, "Error stop play...", e);
			}
		}
	}
}
