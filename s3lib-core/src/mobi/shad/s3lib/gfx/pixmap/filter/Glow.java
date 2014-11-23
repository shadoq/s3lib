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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import mobi.shad.s3lib.gfx.pixmap.procedural.ProceduralInterface;

/**
 * Add glow light to the pixmap
 */
public class Glow implements ProceduralInterface, FilterPixmapInterface{

	/**
	 * @param pixmap
	 * @param color
	 * @param centerX
	 * @param centerY
	 * @param rayX
	 * @param rayY
	 * @param gamma
	 * @param alpha
	 */
	public static void generate(final Pixmap pixmap, Color color, float centerX, float centerY, float rayX, float rayY, float gamma, float alpha){

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		if (DEBUG){
			Gdx.app.log("Gradient::generate()",
						"pixmap: " + pixmap + " color: " + color + " centerX: " + centerX + " centerY: " + centerY + " rayX: " + rayX + " rayY: " + rayY + " gamma: " + gamma + " alpha: " + alpha);
		}

		int pxCenterX = (int) (centerX * width);
		int pxCenterY = (int) (centerY * height);
		int pxRadiusX = (int) (rayX * width);
		int pxRadiusY = (int) (rayY * height);

		float red = color.r;
		float green = color.g;
		float blue = color.b;

		float radiusX = 1.0f / (float) pxRadiusX;
		float radiusY = 1.0f / (float) pxRadiusY;

		for (int y = 0; y < height; y++){

			float dy = (float) (y - pxCenterY) * radiusY;
			float dy2 = dy * dy;

			for (int x = 0; x < width; x++){

				//
				// Calculate distance at center
				//
				float dx = (float) (x - pxCenterX) * radiusX;
				float d = (float) Math.sqrt(dx * dx + dy2);
				if (d > 1.0f){
					d = 1.0f;
				}
				d = 1.0f - d;

				//
				// Draw glow pixel
				//
				int rgb = pixmap.getPixel(x, y);
				int r = (rgb & 0xff000000) >>> 24;
				int g = (rgb & 0x00ff0000) >>> 16;
				int b = (rgb & 0x0000ff00) >>> 8;
				int a = (rgb & 0x000000ff);

				r = (int) (r + ((gamma * d * red) * alpha));
				g = (int) (g + ((gamma * d * green) * alpha));
				b = (int) (b + ((gamma * d * blue) * alpha));

				//
				// Clamp
				//
				r = r > 255 ? 255 : r;
				g = g > 255 ? 255 : g;
				b = b > 255 ? 255 : b;

				pixmap.drawPixel(x, y, ((int) r << 24) | ((int) g << 16) | ((int) b << 8) | a);
			}
		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, Color.WHITE, 0.5f, 0.5f, 0.25f, 0.25f, 10, 10);
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void filter(Pixmap pixmap){
		generate(pixmap, Color.WHITE, 0.5f, 0.5f, 0.25f, 0.25f, 10, 10);
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void random(Pixmap pixmap){
		generate(pixmap, Color.WHITE, (float) Math.random(), (float) Math.random(), (float) Math.random() * .5f, (float) Math.random() * .5f, 10, 10);
	}
}
