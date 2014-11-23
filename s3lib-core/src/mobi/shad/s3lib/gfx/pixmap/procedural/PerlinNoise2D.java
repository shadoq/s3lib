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
import mobi.shad.s3lib.gfx.math.PerlinNoise;

/**
 *
 * @author Jarek
 */
public class PerlinNoise2D implements ProceduralInterface
{

	/**
	 *
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 1.0f, 1.0f, 1);
	}

	@Override
	public void random(Pixmap pixmap){
		generate(pixmap, (float) Math.random(), (float) Math.random(), 8);
	}

	/**
	 *
	 * @param pixmap
	 * @param xCenter
	 * @param yCenter
	 * @param xSize
	 * @param ySize
	 * @param maxIterations
	 */
	public static void generate(final Pixmap pixmap, float amplitude, float frequency, int octaves){

		S3Log.log("PerlinNoise3D", "amplitude: " + amplitude + " frequency: " + frequency + " octaves: " + octaves);
		
		int width=pixmap.getWidth();
		int height=pixmap.getHeight();

		int grey=0;
		for (int y=0; y < height; y++){
			for (int x=0; x < width; x++){
				grey=PerlinNoise.perlinNoise(x, y, amplitude, frequency, octaves);
				pixmap.drawPixel(x, y, ((int) grey << 24) | ((int) grey << 16) | ((int) grey << 8) | 255);
			}
		}
	}
}
