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

/**
 * @author Jarek
 */
public class Mandelbrot5 implements ProceduralInterface{

	/**
	 * @param pixmap
	 * @param xCenter
	 * @param yCenter
	 * @param xSize
	 * @param ySize
	 * @param maxIterations
	 */
	public static void generate(final Pixmap pixmap, double xCenter, double yCenter, double xSize, double ySize, int maxIterations){

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		double xStart = xCenter - xSize;
		double yStart = yCenter - ySize;

		double xStep = (xSize * 2f) / width;
		double yStep = (ySize * 2f) / height;

		double px = 0, py = 0;
		double zx = 0.0, zy = 0.0, zx2 = 0.0, zy2 = 0.0;
		int value = 0;
		float grey = 0;

		py = yStart;
		for (int y = 0; y < height; y++){
			px = xStart;
			for (int x = 0; x < width; x++){

				zx = 0;
				zy = 0;
				value = 0;

				for (int m = 0; m < maxIterations; m++){
					zx2 = zx;
					zx = zx * zx - zy * zy + px;
					zy = 2 * zx2 * zy + py;
					if ((zx * zx + zy * zy) > 8){
						break;
					}
					value++;
				}
				grey = ((maxIterations - value) * (255 / maxIterations));
				pixmap.drawPixel(x, y, ((int) grey << 24) | ((int) grey << 16) | ((int) grey << 8) | 255);
				px += xStep;
			}
			py += yStep;
		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 0.0d, 0.0d, 2.0d, 2.0d, 64);
	}

	@Override
	public void random(final Pixmap pixmap){
		generate(pixmap, -0.5f + Math.random() * 1, -0.5f + Math.random() * 1, 1.0f + Math.random() * 1, 1.0f + Math.random() * 1, 64);
	}
}
