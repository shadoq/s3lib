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

public class Alpha implements ProceduralInterface, FilterPixmapInterface{

	/**
	 * @param pixmap
	 * @param amplify - The bulge value
	 */
	public static void generate(final Pixmap pixmap, int amplify){

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){

				int rgb = pixmap.getPixel(x, y);
				int r = (rgb & 0xff000000) >>> 24;
				int g = (rgb & 0x00ff0000) >>> 16;
				int b = (rgb & 0x0000ff00) >>> 8;
				int a = (rgb & 0x000000ff);

				a = (a + (r + g + b)) >> 1;
				r = a;
				g = a;
				b = a;

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

	/**
	 * @param pixmap
	 */
	@Override
	public void filter(Pixmap pixmap){
		generate(pixmap, 64);
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void random(final Pixmap pixmap){
		generate(pixmap, (int) (32.0f + Math.random() * 64));
	}
}
