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
package mobi.shad.s3lib.gfx.pixmap.procedural;

import com.badlogic.gdx.graphics.Pixmap;
import mobi.shad.s3lib.main.S3Log;

import java.util.Random;

/**
 * @author Jarek
 */
public class Fire implements ProceduralInterface{

	static int[] sectorSpeed = new int[256];
	static int[] lineZero = new int[256];
	static int[] fireLuminate = new int[256 * 256];
	static int[][] colorPalette = new int[100][3];
	static int index = 0;
	static Random rand = new Random();

	static{
		for (int i = 0; i < 50; i++){
			colorPalette[(i + 0)] = new int[3];
			colorPalette[(i + 0)][0] = 245;
			colorPalette[(i + 0)][1] = i * 4;
			colorPalette[(i + 0)][2] = i * 2;
		}
		for (int i = 0; i < 25; i++){
			colorPalette[(i + 50)] = new int[3];
			colorPalette[(i + 50)][0] = 245 - 5 * i;
			colorPalette[(i + 50)][1] = 196 - 7 * i;
			colorPalette[(i + 50)][2] = 98 - 4 * i;
		}
		for (int i = 0; i < 25; i++){
			colorPalette[(i + 75)] = new int[3];
			colorPalette[(i + 75)][0] = 0;
			colorPalette[(i + 75)][1] = 0;
			colorPalette[(i + 75)][2] = 0;
		}

	}

	/**
	 * @param pixmap
	 * @param xCenter
	 * @param yCenter
	 * @param xSize
	 * @param ySize
	 * @param maxIterations
	 */
	public static void generate(final Pixmap pixmap, int stepWidth, int randFraction, int maxSpeed, int maxStart){

		S3Log.info("Fire", "stepWidth: " + stepWidth + " randFraction: " + randFraction + " maxSpeed: " + maxSpeed + " maxStart: " + maxStart);

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		if (width > height){
			if (width > sectorSpeed.length){
				sectorSpeed = new int[width];
				lineZero = new int[width];
				fireLuminate = new int[width * height];
				for (int i = 0; i < width; i++){
					sectorSpeed[i] = (maxSpeed - rand.nextInt(2 * maxSpeed));
					lineZero[i] = 0;
				}
			}
		} else {
			if (height > sectorSpeed.length){
				sectorSpeed = new int[height];
				lineZero = new int[height];
				fireLuminate = new int[width * height];
				for (int i = 0; i < height; i++){
					sectorSpeed[i] = (maxSpeed - rand.nextInt(2 * maxSpeed));
					lineZero[i] = 0;
				}
			}
		}
		int max = colorPalette.length - 1;

		for (int x = 0; x < width; x++){
			lineZero[x] = (lineZero[x] + sectorSpeed[x] - rand.nextInt(25));

			if (lineZero[x] < 0){
				lineZero[x] = 0;
				sectorSpeed[x] = (maxSpeed / 2 + rand.nextInt(maxSpeed) / 2);
			}

			if (lineZero[x] > maxStart){
				lineZero[x] = maxStart;
				sectorSpeed[x] = (0 - maxSpeed / 2 - rand.nextInt(maxSpeed) / 2);
			}

			if (rand.nextInt(100) >= 20){
				continue;
			}
			sectorSpeed[x] = (int) (sectorSpeed[x] * rand.nextFloat());
		}

		//
		// Draw box pixel
		//
		int temp = (lineZero[0] + lineZero[1]) / 2 + stepWidth + rand.nextInt(randFraction);
		if (temp > max){
			temp = max;
		}
		if (temp < 0){
			temp = 0;
		}
		pixmap.drawPixel(0, height - 1, ((int) colorPalette[temp][0] << 24) | ((int) colorPalette[temp][1] << 16) | ((int) colorPalette[temp][2] << 8) | 255);

		temp = (lineZero[(width - 1)] + lineZero[(width - 2)]) / 2 + stepWidth + rand.nextInt(randFraction);
		if (temp > max){
			temp = max;
		}
		if (temp < 0){
			temp = 0;
		}
		pixmap.drawPixel(width - 1, height - 1,
						 ((int) colorPalette[temp][0] << 24) | ((int) colorPalette[temp][1] << 16) | ((int) colorPalette[temp][2] << 8) | 255);

		for (int i = 1; i < width - 1; i++){
			index = (width * (height - 1) + i);
			fireLuminate[index] = ((lineZero[(i - 1)] + lineZero[i] + lineZero[(i + 1)]) / 3 + stepWidth + rand.nextInt((int) randFraction));
			if (fireLuminate[index] > max){
				fireLuminate[index] = max;
			}
			temp = fireLuminate[index];
			pixmap.drawPixel(i, height - 1,
							 ((int) colorPalette[temp][0] << 24) | ((int) colorPalette[temp][1] << 16) | ((int) colorPalette[temp][2] << 8) | 255);
		}

		for (int y = 1; y < width; y++){
			index = (height - 1 - y);

			//
			// Image first X column
			//
			int index_a = index * width;
			int index_c = (index + 1) * width;
			int index_d = (index + 1) * width + 1;

			fireLuminate[index_a] = ((fireLuminate[index_c] + fireLuminate[index_d]) / 2 + stepWidth + rand.nextInt((int) randFraction));
			if (fireLuminate[index_a] > max){
				fireLuminate[index_a] = max;
			}
			temp = fireLuminate[index_a];
			pixmap.drawPixel(0, index, ((int) colorPalette[temp][0] << 24) | ((int) colorPalette[temp][1] << 16) | ((int) colorPalette[temp][2] << 8) | 255);

			//
			// Image last X column
			//
			index_a = index * width + (width - 1);
			index_c = (index + 1) * width + (width - 1);
			index_d = (index + 1) * width + (width - 2);

			fireLuminate[index_a] = ((fireLuminate[index_c] + fireLuminate[index_d]) / 2 + stepWidth + rand.nextInt((int) randFraction));
			if (fireLuminate[index_a] > max){
				fireLuminate[index_a] = max;
			}
			temp = fireLuminate[index_a];
			pixmap.drawPixel(width - 1, index,
							 ((int) colorPalette[temp][0] << 24) | ((int) colorPalette[temp][1] << 16) | ((int) colorPalette[temp][2] << 8) | 255);
		}

		//
		// Draw Fire pixel
		//
		for (int x = 1; x < width - 1; x++){
			for (int y = 1; y < height; y++){
				index = (height - 1 - y);

				int index_a = index * width + x;
				int index_b = (index + 1) * width + (x - 1);
				int index_c = (index + 1) * width + x;
				int index_d = (index + 1) * width + (x + 1);

				fireLuminate[index_a] =
				((fireLuminate[index_b] + fireLuminate[index_c] + fireLuminate[index_d]) / 3 + stepWidth + rand.nextInt((int) randFraction));
				if (fireLuminate[index_a] > max){
					fireLuminate[index_a] = max;
				}
				temp = fireLuminate[index_a];
				pixmap.drawPixel(x, index,
								 ((int) colorPalette[temp][0] << 24) | ((int) colorPalette[temp][1] << 16) | ((int) colorPalette[temp][2] << 8) | 255);
			}
		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 10, 20, 20, 20);
	}

	@Override
	public void random(Pixmap pixmap){
		generate(pixmap, (int) (Math.random() * 10), 1 + (int) (Math.random() * 20), 1 + (int) (Math.random() * 20), 1 + (int) (Math.random() * 20));
	}
}
