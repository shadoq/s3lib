/*******************************************************************************
 * Copyright 2012
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
public class ColorUtil{

	/**
	 * @param color
	 * @return
	 */
	public static int ColortoRGBA(Color color){

		return ((int) (color.r * 255) << 24) | ((int) (color.g * 255) << 16) | ((int) (color.b * 255) << 8) | (int) (color.a * 255);
	}

	/**
	 * @param colorStr
	 * @return
	 */
	public static Color HexToColor(String colorStr){

		//		S3Log.log("HexToColor", "Color In: " + colorStr);

		if (colorStr.length() == 6){
			colorStr = "ff" + colorStr;
		} else if (colorStr.length() == 7){
			colorStr = "f" + colorStr;
		}
		float aL = 1.0f;
		try {
			aL = (((float) Integer.valueOf(colorStr.substring(6, 8), 16)) / 255);
		} catch (Exception e){
		}

		float rL = 1.0f;
		try {
			rL = (((float) Integer.valueOf(colorStr.substring(0, 2), 16)) / 255);
		} catch (Exception e){
		}
		float gL = 1.0f;
		try {
			gL = (((float) Integer.valueOf(colorStr.substring(2, 4), 16)) / 255);
		} catch (Exception e){
		}
		float bL = 1.0f;
		try {
			bL = (((float) Integer.valueOf(colorStr.substring(4, 6), 16)) / 255);
		} catch (Exception e){
		}

		Color color = new Color(rL, gL, bL, aL);
		//		S3Log.log("HexToColor", " Color out: " + color.toString());
		return color;
	}

	/**
	 * Konwertuje kolor HSB do RGBA
	 *
	 * @param hue
	 * @param saturation
	 * @param brightness
	 * @return
	 */
	public static int HSBtoRGBA(float hue, float saturation, float brightness){

		if (hue > 1.0f){
			hue = 1.0f;
		}
		if (saturation > 1.0f){
			saturation = 1.0f;
		}
		if (brightness > 1.0f){
			brightness = 1.0f;
		}
		if (hue < 0.0f){
			hue = 0.0f;
		}
		if (saturation < 0.0f){
			saturation = 0.0f;
		}
		if (brightness < 0.0f){
			brightness = 0.0f;
		}
		int r = 0, g = 0, b = 0;
		int a = 255;
		if (saturation == 0){
			r = g = b = (int) (brightness * 255.0f + 0.5f);
		} else {
			float h = (hue - (float) Math.floor(hue)) * 6.0f;
			float f = h - (float) java.lang.Math.floor(h);
			float p = brightness * (1.0f - saturation);
			float q = brightness * (1.0f - saturation * f);
			float t = brightness * (1.0f - (saturation * (1.0f - f)));
			switch ((int) h){
				case 0:
					r = (int) (brightness * 255.0f + 0.5f);
					g = (int) (t * 255.0f + 0.5f);
					b = (int) (p * 255.0f + 0.5f);
					break;
				case 1:
					r = (int) (q * 255.0f + 0.5f);
					g = (int) (brightness * 255.0f + 0.5f);
					b = (int) (p * 255.0f + 0.5f);
					break;
				case 2:
					r = (int) (p * 255.0f + 0.5f);
					g = (int) (brightness * 255.0f + 0.5f);
					b = (int) (t * 255.0f + 0.5f);
					break;
				case 3:
					r = (int) (p * 255.0f + 0.5f);
					g = (int) (q * 255.0f + 0.5f);
					b = (int) (brightness * 255.0f + 0.5f);
					break;
				case 4:
					r = (int) (t * 255.0f + 0.5f);
					g = (int) (p * 255.0f + 0.5f);
					b = (int) (brightness * 255.0f + 0.5f);
					break;
				case 5:
					r = (int) (brightness * 255.0f + 0.5f);
					g = (int) (p * 255.0f + 0.5f);
					b = (int) (q * 255.0f + 0.5f);
					break;
			}
		}
		return ((int) (r) << 24) | ((int) (g) << 16) | ((int) (b) << 8) | (int) (a);
	}

	/**
	 * Konwertuje wartości RGB do HSB
	 *
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public static float[] RGBtoHSB(int r, int g, int b){
		float hue, saturation, brightness;
		float[] hsbvals = new float[3];

		int cmax = (r > g) ? r : g;
		if (b > cmax){
			cmax = b;
		}
		int cmin = (r < g) ? r : g;
		if (b < cmin){
			cmin = b;
		}

		brightness = ((float) cmax) / 255.0f;
		if (cmax != 0){
			saturation = ((float) (cmax - cmin)) / ((float) cmax);
		} else {
			saturation = 0;
		}
		if (saturation == 0){
			hue = 0;
		} else {
			float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
			float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
			float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
			if (r == cmax){
				hue = bluec - greenc;
			} else if (g == cmax){
				hue = 2.0f + redc - bluec;
			} else {
				hue = 4.0f + greenc - redc;
			}
			hue = hue / 6.0f;
			if (hue < 0){
				hue = hue + 1.0f;
			}
		}
		hsbvals[0] = hue;
		hsbvals[1] = saturation;
		hsbvals[2] = brightness;
		return hsbvals;
	}

	/**
	 * Konwertuje HSB do objektu Color
	 *
	 * @param hue
	 * @param saturation
	 * @param brightness
	 * @return
	 */
	public static Color HSBToColor(float hue, float saturation, float brightness){
		Color col = new Color();
		Color.rgb888ToColor(col, HSBtoRGBA(hue, saturation, brightness));
		return col;
	}

	/**
	 * @return
	 */
	public static Color randomColor(){
		return new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1.0F);
	}
}
