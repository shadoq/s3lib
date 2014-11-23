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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import mobi.shad.s3lib.gfx.pixmap.procedural.ProceduralInterface;
import mobi.shad.s3lib.gfx.util.GradientUtil;

public class GradientMap implements ProceduralInterface, FilterPixmapInterface{

	private static int numGradients = 48;
	private static int numGradientsStep = 256;
	private static int[][] cacheColorGradients;

	static{
		cacheColorGradients = new int[numGradients + 1][numGradientsStep + 1];
		float step = 1.0f / (numGradientsStep + 1);
		for (int i = 0; i <= numGradients; i++){

			for (float j = 0; j <= 1.0f; j = j + step){
				int num = (int) (j * numGradientsStep);
				Color tmpColor = GradientUtil.getColorPalleteUncache(j, i);
				cacheColorGradients[i][num] =
				((int) (tmpColor.r * 255) << 24) | ((int) (tmpColor.g * 255) << 16) | ((int) (tmpColor.b * 255) << 8) | ((int) (tmpColor.a * 255));
			}
		}

	}

	/**
	 * @param pixmap
	 * @param colorBase
	 * @param colorPercentRed
	 * @param colorPercentGreen
	 * @param colorPercentBlue
	 * @param brithness
	 * @param contrast
	 * @param saturation
	 * @param alpha
	 */
	public static void generate(final Pixmap pixmap, int mode){

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		int index = 0;

		if (mode > numGradients){
			mode = 0;
		}

		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){

				int rgb = pixmap.getPixel(x, y);
				int r = (rgb & 0xff000000) >>> 24;
				int g = (rgb & 0x00ff0000) >>> 16;
				int b = (rgb & 0x0000ff00) >>> 8;

				index = r + g + b >> 2;

				if (index > numGradientsStep){
					index = numGradientsStep - 1;
				}

				//
				// SetColor
				//
				pixmap.drawPixel(x, y, cacheColorGradients[mode][index]);
			}
		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 0);
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void filter(Pixmap pixmap){
		generate(pixmap, 0);
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void random(final Pixmap pixmap){
		generate(pixmap, (int) (Math.random() * 46));
	}
}
