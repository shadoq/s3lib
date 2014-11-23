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
public class MetaBalls implements ProceduralInterface{

	static int[][] mb_vx = new int[1][32];
	static int[][] mb_vy = new int[1][16];
	static int[] mb_px = new int[1];
	static int[] mb_py = new int[1];
	static int[] mb_dx = new int[1];
	static int[] mb_dy = new int[1];
	static Random rnd = new Random();
	static int mb_counter = 0;

	/**
	 * @param pixmap
	 * @param xCenter
	 * @param yCenter
	 * @param xSize
	 * @param ySize
	 * @param maxIterations
	 */
	public static void generate(final Pixmap pixmap, int rStart, int gStart, int bStart,
								int dia, int number, int speed,
								int randomColor, int randomTime){

		S3Log.log("MetaBalls",
				  "rStart: " + rStart + " gStart: " + gStart + " bStart: " + bStart
				  + "dia: " + dia + " number: " + number + " speed: " + speed
				  + "randomColor: " + randomColor + " randomTime: " + randomTime);

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		if (mb_px.length != number){

			mb_px = new int[number];
			mb_py = new int[number];
			mb_dx = new int[number];
			mb_dy = new int[number];

			mb_vx = new int[number][width];
			mb_vy = new int[number][height];

			for (int i = 0; i < number; i++){
				mb_px[i] = (int) (rnd.nextDouble() * width);
				mb_py[i] = (int) (rnd.nextDouble() * height);

				if (rnd.nextDouble() < 0.5D){
					mb_dx[i] = -1;
				} else {
					mb_dx[i] = 1;
				}
				if (rnd.nextDouble() < 0.5D){
					mb_dy[i] = -1;
				} else {
					mb_dy[i] = 1;
				}
			}

		}

		if (randomColor > 0){
			if (mb_counter > randomTime){
				rStart = rnd.nextInt(256);
				gStart = rnd.nextInt(256);
				bStart = rnd.nextInt(256);

				mb_counter = 0;
			} else {
				mb_counter += 1;
			}
		}

		for (int i = 0; i < number; i++){
			mb_px[i] += speed * mb_dx[i];
			mb_py[i] += speed * mb_dy[i];

			if (mb_px[i] < 0){
				mb_dx[i] = 1;
			}
			if (mb_px[i] > width){
				mb_dx[i] = -1;
			}
			if (mb_py[i] < 0){
				mb_dy[i] = 1;
			}
			if (mb_py[i] > height){
				mb_dy[i] = -1;
			}

			for (int x = 0; x < width; x++){
				mb_vx[i][x] = ((mb_px[i] - x) * (mb_px[i] - x));
			}

			for (int y = 0; y < height; y++){
				mb_vy[i][y] = ((mb_py[i] - y) * (mb_py[i] - y));
			}
		}

		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				int r = 0;
				int g = 0;
				int b = 0;

				for (int i = 0; i < number; i++){
					double distance = mb_vx[i][x] + mb_vy[i][y] + 1;

					r += (int) (dia / distance * rStart);
					g += (int) (dia / distance * gStart);
					b += (int) (dia / distance * bStart);
				}

				//
				// Clamp
				//
				r = (r < 255) ? r : 255;
				r = (r > 0) ? r : 0;
				g = (g < 255) ? g : 255;
				g = (g > 0) ? g : 0;
				b = (b < 255) ? b : 255;
				b = (b > 0) ? b : 0;

				//
				// Draw pixel
				//
				pixmap.drawPixel(x, y, ((int) r << 24) | ((int) g << 16) | ((int) b << 8) | 255);
			}
		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		//		generate(pixmap, 0.25f, 0.25f, 1);
	}

	@Override
	public void random(Pixmap pixmap){
		//		generate(pixmap, (float) Math.random(), (float) Math.random(), 8);
		generate(pixmap, 255, 255, 255, (int) (20 + Math.random() * 40), 4, (int) (2 + Math.random() * 10), 0, 10);
	}
}