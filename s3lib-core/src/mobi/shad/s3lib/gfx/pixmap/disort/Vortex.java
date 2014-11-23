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
package mobi.shad.s3lib.gfx.pixmap.disort;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import mobi.shad.s3lib.gfx.pixmap.filter.FilterPixmapInterface;
import mobi.shad.s3lib.gfx.pixmap.procedural.ProceduralInterface;
import mobi.shad.s3lib.main.S3Math;

public class Vortex implements ProceduralInterface, FilterPixmapInterface{

	/**
	 *
	 * @param pixmap
	 * @param centerX
	 * @param centerY
	 * @param rayX
	 * @param rayY
	 * @param twist
	 */
	public static void generate(final Pixmap pixmap, float centerX, float centerY, float rayX, float rayY, float twist){

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		//
		// Process operator
		//
		int dwCenterX = (int) (centerX * width);
		int dwCenterY = (int) (centerY * height);
		int dwRadiusX = (int) (rayX * width);
		int dwRadiusY = (int) (rayY * height);

		float f1_RadiusX = 1.0f / (float) dwRadiusX;
		float f1_RadiusY = 1.0f / (float) dwRadiusY;
		float radians = twist * S3Math.PI2;

		Pixmap dstPixmap = new Pixmap(width, height, pixmap.getFormat());
		dstPixmap.setColor(Color.BLACK);
		dstPixmap.fill();

		for (int y = 0; y < height; y++){

			for (int x = 0; x < width; x++){

				//
				// Calculate distance
				//
				float dx = (float) (x - dwCenterX) * f1_RadiusX;
				float dy = (float) (y - dwCenterY) * f1_RadiusY;
				float d = (float) Math.sqrt(dx * dx + dy * dy);

				//
				// If distance more radius, olny copy pixels
				//
				if (d > 1.0){
					int rgb = pixmap.getPixel(x, y);
					dstPixmap.drawPixel(x, y, rgb);
				} else {

					d = (float) S3Math.fastCos(d * S3Math.PI1_2 - S3Math.PI1_2);
					d = 1.0f - d;

					//
					// Rotate around middle
					//
					float nx = x - dwCenterX;
					float ny = y - dwCenterY;

					float rad = radians * d;
					float bx = nx;
					nx = bx * S3Math.fastCos(rad) - nx * S3Math.fastSin(rad) + dwCenterX;
					ny = bx * S3Math.fastSin(rad) + ny * S3Math.fastCos(rad) + dwCenterY;

					if (nx >= width){
						nx = nx - width;
					}
					if (ny >= height){
						ny = ny - height;
					}
					if (nx < 0){
						nx = width + nx;
					}
					if (ny < 0){
						ny = height + ny;
					}

					//
					// Bilinear sample nearest 4 pixels at rotated pos
					//
					int ix, iy;
					ix = (int) nx;
					iy = (int) ny;

					float fracX = nx - ix;
					float fracY = ny - iy;

					float ul = (1.0f - fracX) * (1.0f - fracY);
					float ll = (1.0f - fracX) * fracY;
					float ur = fracX * (1.0f - fracY);
					float lr = fracX * fracY;

					int wrapx = (ix + 1);
					int wrapy = (iy + 1);

					int rgb = pixmap.getPixel(ix, iy);
					int r = (rgb & 0xff000000) >>> 24;
					int g = (rgb & 0x00ff0000) >>> 16;
					int b = (rgb & 0x0000ff00) >>> 8;

					rgb = pixmap.getPixel(wrapx, iy);
					int r2 = (rgb & 0xff000000) >>> 24;
					int g2 = (rgb & 0x00ff0000) >>> 16;
					int b2 = (rgb & 0x0000ff00) >>> 8;

					rgb = pixmap.getPixel(ix, wrapy);
					int r3 = (rgb & 0xff000000) >>> 24;
					int g3 = (rgb & 0x00ff0000) >>> 16;
					int b3 = (rgb & 0x0000ff00) >>> 8;

					rgb = pixmap.getPixel(wrapx, wrapy);
					int r4 = (rgb & 0xff000000) >>> 24;
					int g4 = (rgb & 0x00ff0000) >>> 16;
					int b4 = (rgb & 0x0000ff00) >>> 8;

					r = (int) (r * ul + r2 * ur + r3 * ll + r4 * lr);
					g = (int) (g * ul + g2 * ur + g3 * ll + g4 * lr);
					b = (int) (b * ul + b2 * ur + b3 * ll + b4 * lr);

					//
					// Clamp
					//
					r = (r < 255) ? r : 255;
					r = (r > 0) ? r : 0;
					g = (g < 255) ? g : 255;
					g = (g > 0) ? g : 0;
					b = (b < 255) ? b : 255;
					b = (b > 0) ? b : 0;

					dstPixmap.drawPixel(x, y, ((int) r << 24) | ((int) g << 16) | ((int) b << 8) | 255);
				}
			}
		}
		pixmap.drawPixmap(dstPixmap, 0, 0);
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 0.5f, 0.5f, 0, 1f, 1f);
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void filter(Pixmap pixmap){
		generate(pixmap, 0.5f, 0.5f, 0, 1f, 1f);
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void random(final Pixmap pixmap){
		//		generate(pixmap, (float) Math.random(), (float) Math.random(), (float) (Math.random()), (float) Math.random(), (float) Math.random());
		generate(pixmap, 0.5f, 0.5f, 3.5f, 3.5f, 0.5f);
	}
}
