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
package mobi.shad.s3lib.gfx.pixmap.filter;

import com.badlogic.gdx.graphics.Pixmap;
import mobi.shad.s3lib.gfx.pixmap.procedural.ProceduralInterface;

import java.util.Random;

/**
 * Add grey noise to the pixmap
 */
public class NoiseGrey implements ProceduralInterface, FilterPixmapInterface{

	private static Random randomNumberGenerator = new Random();

	public static void setSeed(long seed){
		randomNumberGenerator.setSeed(seed);
	}

	/**
	 * @param pixmap
	 * @param range
	 */
	public static void generate(final Pixmap pixmap, int range){

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();
		int start = -(range / 2);

		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				int rgb = pixmap.getPixel(x, y);
				int r = (rgb & 0xff000000) >>> 24;
				int g = (rgb & 0x00ff0000) >>> 16;
				int b = (rgb & 0x0000ff00) >>> 8;
				int a = (rgb & 0x000000ff);

				//
				// Add grey noise
				//
				int grey = (int) (start + randomNumberGenerator.nextFloat() * range);
				r = r + grey;
				g = g + grey;
				b = b + grey;

				//
				// Clamp
				//
				r = r > 255 ? 255 : r;
				g = g > 255 ? 255 : g;
				b = b > 255 ? 255 : b;

				r = r < 0 ? 0 : r;
				g = g < 0 ? 0 : g;
				b = b < 0 ? 0 : b;

				pixmap.drawPixel(x, y, ((int) r << 24) | ((int) g << 16) | ((int) b << 8) | a);

			}
		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 64);
	}

	@Override
	public void filter(Pixmap pixmap){
		generate(pixmap, 64);
	}

	@Override
	public void random(final Pixmap pixmap){
		generate(pixmap, (int) (32.0f + Math.random() * 32));
	}
}
