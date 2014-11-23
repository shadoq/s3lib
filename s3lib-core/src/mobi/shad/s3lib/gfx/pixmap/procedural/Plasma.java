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
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3Math;

/**
 * @author Jarek
 */
public class Plasma implements ProceduralInterface{

	private static int[][] plasmaColor = new int[1537][3];
	private static float plasmaCounter = 0.0F;

	static{
		for (int i = 0; i < 256; i++){
			plasmaColor[(i + 0)][0] = 0;
			plasmaColor[(i + 0)][1] = i;
			plasmaColor[(i + 0)][2] = 0;
		}

		for (int i = 0; i < 256; i++){
			plasmaColor[(i + 256)][0] = (255 - i);
			plasmaColor[(i + 256)][1] = 0;
			plasmaColor[(i + 256)][2] = 0;
		}

		for (int i = 0; i < 256; i++){
			plasmaColor[(i + 512)][0] = 0;
			plasmaColor[(i + 512)][1] = 0;
			plasmaColor[(i + 512)][2] = i;
		}

		for (int i = 0; i < 256; i++){
			plasmaColor[(i + 768)][0] = 0;
			plasmaColor[(i + 768)][1] = (255 - i);
			plasmaColor[(i + 768)][2] = 0;
		}

		for (int i = 0; i < 256; i++){
			plasmaColor[(i + 1024)][0] = i;
			plasmaColor[(i + 1024)][1] = 0;
			plasmaColor[(i + 1024)][2] = 0;
		}

		for (int i = 0; i < 256; i++){
			plasmaColor[(i + 1280)][0] = 0;
			plasmaColor[(i + 1280)][1] = 0;
			plasmaColor[(i + 1280)][2] = (255 - i);
		}
	}

	/**
	 * @param pixmap
	 * @param xCenter
	 * @param yCenter
	 * @param xSize
	 * @param ySize
	 * @param maxIterations
	 */
	public static void generate(final Pixmap pixmap, int plasmaSpeed, int scrollSpeed, int cellSizeX, int cellSizeY){

		if (DEBUG){
			S3Log.trace("PerlinNoise3D", "plasmaSpeed: " + plasmaSpeed + " cellSizeX: " + cellSizeX + " cellSizeY: " + cellSizeY);
		}

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		float calc1 = (float) S3Math.fastSin(plasmaCounter * 0.05f);
		float calc2 = (float) S3Math.fastSin(plasmaCounter * -0.05f);

		plasmaCounter += plasmaSpeed * S3.osDeltaTime / 5f;
		float cellScroll = scrollSpeed / 100f;
		float cellXAdd = cellSizeX / 300f;
		float cellYAdd = cellSizeY / 300f;

		float xc = 10.0f;

		for (int x = 0; x < width; x++){
			xc += cellXAdd;
			float yc = -25.0f;
			int s1 = (int) (768 + 768 * S3Math.fastSin(xc) * calc1);

			for (int y = 0; y < height; y++){
				yc += cellYAdd;

				int s2 = (int) (768 + 768 * S3Math.fastSin(yc) * calc2);
				int s3 = (int) (768 + 768 * S3Math.fastSin((xc + yc + plasmaCounter * cellScroll)));

				int pixel = (int) ((s1 + s2 + s3) / 3);

				int r = plasmaColor[pixel][0];
				int g = plasmaColor[pixel][1];
				int b = plasmaColor[pixel][2];

				//
				// Draw pixel
				//
				pixmap.drawPixel(x, y, ((int) r << 24) | ((int) g << 16) | ((int) b << 8) | 255);
			}
		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 20, 20, 8, 8);
	}

	@Override
	public void random(Pixmap pixmap){
		generate(pixmap, 100, 20, 2, 2);
	}
}
