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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Generate cell procedure texture
 *
 * @author Jaroslaw Czub (http://shad.net.pl)
 */
public class Cell implements ProceduralInterface{

	private static Random randomNumberGenerator = new Random();
	private static int oldDensity;
	private static float[] cellPoint;

	/**
	 * @param seed
	 */
	public static void setSeed(long seed){
		randomNumberGenerator.setSeed(seed);
		oldDensity = 0;
	}

	/**
	 * Main operation process. Generate cell texture procedure
	 *
	 * @param pixmap
	 * @param regularity
	 * @param density
	 * @param color
	 * @param pattern
	 * @param chessboard
	 */
	public static void generate(final Pixmap pixmap, float regularity, int density, Color color, int pattern, int chessboard){

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		int r, g, b = 0;
		int a = 255;
		Vector3 distVect = new Vector3();
		float dist = 0;
		float rand1 = 0;
		float rand2 = 0;

		//
		// Create random cell point, create random cache where necessary
		//
		if (oldDensity != density || cellPoint == null){
			cellPoint = new float[density * density * 2 + 4];
			for (int y = 0; y < density; y++){
				for (int x = 0; x < density; x++){
					rand1 = randomNumberGenerator.nextFloat();
					rand2 = randomNumberGenerator.nextFloat();
					cellPoint[(x + y * density) * 2] = (x + 0.5f + (rand1 - 0.5f) * (1 - regularity)) / density - 1.f / width;
					cellPoint[(x + y * density) * 2 + 1] = (y + 0.5f + (rand2 - 0.5f) * (1 - regularity)) / density - 1.f / height;
				}
			}
		}

		oldDensity = density;

		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){

				float pixelPosX = (float) x / width;
				float pixelPosY = (float) y / height;

				float minDist = 10;
				float nextMinDist = minDist;
				int xo = x * density / width;
				int yo = y * density / height;

				for (int v = -1; v < 2; ++v){
					int vo = ((yo + density + v) % density) * density;
					for (int u = -1; u < 2; ++u){
						float cellPosX = cellPoint[((((xo + density + u)) % density) + vo) * 2];
						float cellPosY = cellPoint[((((xo + density + u)) % density) + vo) * 2 + 1];

						if (u == -1 && x * density < width){
							cellPosX -= 1;
						}
						if (v == -1 && y * density < height){
							cellPosY -= 1;
						}
						if (u == 1 && x * density >= width * (density - 1)){
							cellPosX += 1;
						}
						if (v == 1 && y * density >= height * (density - 1)){
							cellPosY += 1;
						}

						dist = distVect.set(pixelPosX, pixelPosY, 0).dst(cellPosX, cellPosY, 0);

						if (dist < minDist){
							nextMinDist = minDist / 2;
							minDist = dist;
						}
						if (dist < nextMinDist){
							nextMinDist = dist;
						}
					}
				}


				switch (pattern){
					default:
					case 0:
						minDist = (2 * nextMinDist - minDist) * density;
						break;
					case 1:
						minDist = 2 * nextMinDist * density - 1;
						break;
					case 2:
						minDist = 1 - minDist * density;
						break;
				}

				if (minDist < 0){
					minDist = 0;
				}
				if (minDist > 1){
					minDist = 1;
				}

				//
				// Draw pixel
				//
				if (chessboard == 1){
					int cfc = (xo & 1) ^ (yo & 1);
					float coeff = (1 - 2 * cfc) / 2.5f;
					r = (int) ((cfc + coeff * minDist) * color.r * 255);
					g = (int) ((cfc + coeff * minDist) * color.g * 255);
					b = (int) ((cfc + coeff * minDist) * color.b * 255);
				} else {
					r = (int) (minDist * color.r * 255);
					g = (int) (minDist * color.g * 255);
					b = (int) (minDist * color.b * 255);
				}

				pixmap.drawPixel(x, y, ((int) r << 24) | ((int) g << 16) | ((int) b << 8) | a);

			}
		}
	}

	/**
	 * Single operation process
	 *
	 * @param pixmap
	 */
	@Override
	public void generate(Pixmap pixmap){
		generate(pixmap, 128, 64, Color.WHITE, 0, 0);
	}

	/**
	 * Random operation process
	 *
	 * @param pixmap
	 */
	@Override
	public void random(Pixmap pixmap){
		generate(pixmap, (float) Math.random(), 4, Color.WHITE, (int) (Math.random() * 3), (int) (Math.random() * 2));
	}
}
