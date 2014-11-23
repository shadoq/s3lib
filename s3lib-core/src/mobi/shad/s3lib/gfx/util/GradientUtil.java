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
package mobi.shad.s3lib.gfx.util;

import com.badlogic.gdx.graphics.Color;

/**
 * @author Jarek
 */
public class GradientUtil{

	private static int numGradients = 48;
	private static int numGradientsStep = 256;
	private static Color[][] cacheColorGradients;

	static{
		cacheColorGradients = new Color[numGradients + 1][numGradientsStep + 1];
		float step = 1.0f / (numGradientsStep + 1);
		for (int i = 0; i <= numGradients; i++){

			for (float j = 0; j <= 1.0f; j = j + step){
				int num = (int) (j * numGradientsStep);
				cacheColorGradients[i][num] = getColorPalleteUncache(j, i);
			}
		}
	}

	/**
	 * Generuje paletę kolorów na podsawie wartości indeksu koloru
	 *
	 * @param index - indeks koloru od 0-255
	 * @param mode  - tryb
	 * @return
	 */
	public static Color getColorPallete(float index, int mode){

		if (index < 0){
			index = 0.0f;
		} else if (index > 1){
			index = 1.0f;
		}

		if (mode < 0){
			mode = 0;
		} else if (mode > numGradients){
			mode = numGradients;
		}

		int num = (int) (index * numGradientsStep);
		if (num >= numGradientsStep){
			num = numGradientsStep - 1;
		}
		if (mode > numGradients){
			mode = 0;
		}
		return cacheColorGradients[mode][num];
	}

	/**
	 * @param index
	 * @param mode
	 * @return
	 */
	public static Color getColorPalleteInt(int index, int mode){

		if (index < 0){
			index = 0;
		} else if (index > numGradientsStep){
			index = numGradientsStep - 1;
		}

		if (mode > numGradients){
			mode = 0;
		}
		return cacheColorGradients[mode][index];
	}

	/**
	 * Generuje paletę kolorów na podsawie wartości indeksu koloru
	 *
	 * @param index - indeks koloru od 0-255
	 * @param mode  - tryb
	 * @return
	 */
	public static Color getColorPalleteUncache(float index, int mode){

		if (index < 0){
			index = 0.0f;
		} else if (index > 1){
			index = 1.0f;
		}
		Color color = new Color(Color.WHITE);

		switch (mode){
			default:
				//
				// Hue
				//
				Color.rgba8888ToColor(color, ColorUtil.HSBtoRGBA(index, 1.0f, 1.0f));
				break;
			case 1:
				//
				// Fire
				//
				Color.rgba8888ToColor(color, ColorUtil.HSBtoRGBA(index * 0.26f, 1.0f, (index < 0.5f) ? index * 2 : 1.0f));
				break;
			case 2:
				//
				// Ice
				//
				Color.rgba8888ToColor(color, ColorUtil.HSBtoRGBA(index * 0.26f + 0.55f, 1.0f, (index < 0.5f) ? index * 2 : 1.0f));
				break;
			case 3:
				//
				// Ice
				//
				Color.rgba8888ToColor(color, ColorUtil.HSBtoRGBA(index * 0.15f + 0.0f, 1.0f, (index < 0.51f) ? index * 2 : 1.0f));
				break;
			case 4:
				color = linearGradientColor(new Color(1.0f, 1.0f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 1.0f, 1.0f), new Color(1.0f, 0.0f, 1.0f, 1.0f), index);
				break;
			case 5:
				color = linearGradientColor(new Color(1.0f, 1.0f, 0.5f, 1.0f), new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 1.0f, 1.0f), index);
				break;
			case 6:
				color = linearGradientColor(new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(0.0f, 0.5f, 1.0f, 1.0f), new Color(1.0f, 0.0f, 0.0f, 1.0f), index);
				break;
			case 7:
				color = linearGradientColor(new Color(0.0f, 0.0f, 1.0f, 1.0f), new Color(0.0f, 1.0f, 0.0f, 1.0f), new Color(1.0f, 0.0f, 0.0f, 1.0f), index);
				break;
			case 8:
				color = linearGradientColor(new Color(1.0f, 1.0f, 0.0f, 1.0f), new Color(1.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f),
											new Color(1.0f, 0.5f, 0.0f, 1.0f), index);
				break;
			case 9:
				color.r = index;
				color.g = 0;
				color.b = 0;
				break;
			case 10:
				color.r = 0;
				color.g = index;
				color.b = 0;
				break;
			case 11:
				color.r = 0;
				color.g = 0;
				color.b = index;
				break;
			case 12:
				color.r = index;
				color.g = index;
				color.b = 0;
				break;
			case 13:
				color.r = 0;
				color.g = 1 - index;
				color.b = index;
				break;
			case 14:
				color.r = index;
				color.g = index;
				color.b = 1 - index;
				break;
			case 15:
				color.r = 1 - index;
				color.g = index * 0.5f;
				color.b = index;
				break;
			case 16:
				color.r = 1 - index;
				color.g = 1 - index;
				color.b = index;
				break;

			case 17:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.5f, 0.5f, 0.5f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 18:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f, 1.0f), index);
				break;
			case 19:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.25f, 0.25f, 0.25f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;


			case 20:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 21:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 1.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 22:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 23:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 24:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 0.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 25:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;

			case 26:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 27:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.5f, 1.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 28:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.5f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 29:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 0.3f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 30:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.4f, 0.7f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 31:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.5f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;


			case 32:
				color = linearGradientColor(new Color(0.2f, 0.6f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(0.6f, 0.4f, 0.0f, 1.0f),
											new Color(1.0f, 1.0f, 1.0f, 1.0f), index);
				break;
			case 33:
				color = linearGradientColor(new Color(0.0f, 0.0f, 0.5f, 1.0f), new Color(1.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 0.0f, 1.0f), index);
				break;
			case 34:
				color = linearGradientColor(new Color(0.7f, 0.4f, 0.2f, 1.0f), new Color(1.0f, 0.8f, 0.6f, 1.0f), new Color(0.5f, 0.2f, 0.1f, 1.0f),
											new Color(1.0f, 0.8f, 0.6f, 1.0f), index);
				break;
			case 35:
				color = linearGradientColor(new Color(1.0f, 0.7f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.4f, 0.7f, 1.0f), index);
				break;
			case 36:
				color = linearGradientColor(new Color(1.0f, 0.9f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.5f, 0.1f, 0.5f, 1.0f), index);
				break;
			case 37:
				color = linearGradientColor(new Color(1.0f, 0.2f, 0.2f, 1.0f), new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.7f, 0.6f, 1.0f), index);
				break;
			case 38:
				color = linearGradientColor(new Color(1.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 0.0f, 1.0f), index);
				break;
			case 39:
				color = linearGradientColor(new Color(1.0f, 0.7f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.8f, 0.4f, 1.0f), index);
				break;
			case 40:
				color = linearGradientColor(new Color(1.0f, 0.0f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(0.5f, 0.0f, 1.0f, 1.0f), index);
				break;
			case 41:
				color = linearGradientColor(new Color(0.0f, 0.4f, 1.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 0.4f, 0.4f, 1.0f), index);
				break;
			case 42:
				color = linearGradientColor(new Color(1.0f, 0.0f, 1.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 0.0f, 1.0f), index);
				break;

		}
		return color;
	}

	/**
	 * @param colorTop
	 * @param colorBottom
	 * @param ratio
	 * @return
	 */
	public static Color linearGradientColor(Color colorTop, Color colorBottom, float ratio){
		Color color = new Color();
		color.r = (float) (colorBottom.r * ratio + colorTop.r * (1 - ratio));
		color.g = (float) (colorBottom.g * ratio + colorTop.g * (1 - ratio));
		color.b = (float) (colorBottom.b * ratio + colorTop.b * (1 - ratio));
		color.a = 1.0f;
		color.clamp();
		return color;
	}

	/**
	 * @param colorTop
	 * @param colorInside
	 * @param colorBottom
	 * @param ratio
	 * @return
	 */
	public static Color linearGradientColor(Color colorTop, Color colorInside, Color colorBottom, float ratio){
		Color color;
		if (ratio < 0.5f){
			color = linearGradientColor(colorTop, colorInside, ratio * 2f);
		} else {
			color = linearGradientColor(colorInside, colorBottom, ((ratio - 0.5f) * 2f));
		}
		return color;
	}

	/**
	 * @param colorTop
	 * @param colorInside1
	 * @param colorInside2
	 * @param colorBottom
	 * @param ratio
	 * @return
	 */
	public static Color linearGradientColor(Color colorTop, Color colorInside1, Color colorInside2, Color colorBottom, float ratio){
		Color color;
		if (ratio < 0.33f){
			color = linearGradientColor(colorTop, colorInside1, ratio * 3f);
		} else if (ratio > 0.32f && ratio < 0.66f){
			color = linearGradientColor(colorInside1, colorInside2, ((ratio - 0.33f) * 3f));
		} else {
			color = linearGradientColor(colorInside2, colorBottom, ((ratio - 0.66f) * 3f));
		}
		return color;
	}

	/**
	 * @param colorTop
	 * @param colorInside1
	 * @param colorInside2
	 * @param colorInside3
	 * @param colorBottom
	 * @param ratio
	 * @return
	 */
	public static Color linearGradientColor(Color colorTop, Color colorInside1, Color colorInside2, Color colorInside3, Color colorBottom, float ratio){
		Color color;
		if (ratio < 0.25f){
			color = linearGradientColor(colorTop, colorInside1, ratio * 4f);
		} else if (ratio > 0.24f && ratio < 0.50f){
			color = linearGradientColor(colorInside1, colorInside2, ((ratio - 0.25f) * 4f));
		} else if (ratio > 0.49f && ratio < 0.75f){
			color = linearGradientColor(colorInside1, colorInside2, ((ratio - 0.50f) * 4f));
		} else {
			color = linearGradientColor(colorInside2, colorBottom, ((ratio - 0.75f) * 4f));
		}
		return color;
	}
}
