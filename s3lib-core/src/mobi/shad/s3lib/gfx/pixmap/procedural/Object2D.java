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

import java.util.Random;

/**
 * @author Jarek
 */
public class Object2D implements ProceduralInterface{

	static int[] objectsPosistionX = new int[1];
	static int[] objectsPosistionY = new int[1];
	static int[] objectsExpand = new int[1];
	static int[] objectsFade = new int[1];
	static float[][] objectsColor = new float[1][3];
	static Random rnd = new Random();

	/**
	 * @param pixmap
	 * @param xCenter
	 * @param yCenter
	 * @param xSize
	 * @param ySize
	 * @param maxIterations
	 */
	public static void generate(final Pixmap pixmap,
								int mode, int count, int size,
								int growth, int randomColor, int red, int green,
								int blue){

		S3Log.log("Object2D", "mode: " + mode + " count: " + count + " size: " + size);

		int width = pixmap.getWidth();
		int height = pixmap.getHeight();


		if (objectsPosistionX.length != count){
			objectsPosistionX = new int[count];
			objectsPosistionY = new int[count];
			objectsExpand = new int[count];
			objectsFade = new int[count];
			objectsColor = new float[count][3];

			for (int i = 0; i < count; i++){
				objectsPosistionX[i] = rnd.nextInt(width);
				objectsPosistionY[i] = rnd.nextInt(height);
				objectsExpand[i] = rnd.nextInt(growth);
				objectsFade[i] = 0;

				if (randomColor > 0){
					objectsColor[i][0] = rnd.nextInt(256);
					objectsColor[i][1] = rnd.nextInt(256);
					objectsColor[i][2] = rnd.nextInt(256);
				} else {
					objectsColor[i][0] = red;
					objectsColor[i][1] = green;
					objectsColor[i][2] = blue;
				}
			}
		}

		for (int i = 0; i < count; i++){
			int r = (int) (objectsColor[i][0] * (1.0F - objectsFade[i] / growth));
			int g = (int) (objectsColor[i][1] * (1.0F - objectsFade[i] / growth));
			int b = (int) (objectsColor[i][2] * (1.0F - objectsFade[i] / growth));

			int diameter = size + objectsExpand[i];

			if (objectsExpand[i] < growth){


				pixmap.setColor(((int) r << 24) | ((int) g << 16) | ((int) b << 8) | 255);

				int offset = diameter / 2;

				switch (mode){
					default:
						pixmap.drawCircle(objectsPosistionX[i] - offset, objectsPosistionY[i] - offset, diameter);
						break;
					case 1:
						pixmap.fillCircle(objectsPosistionX[i] - offset, objectsPosistionY[i] - offset, diameter);
						break;
					case 2:
						pixmap.drawRectangle(objectsPosistionX[i] - offset, objectsPosistionY[i] - offset, diameter, diameter);
						break;
					case 3:
						pixmap.fillRectangle(objectsPosistionX[i] - offset, objectsPosistionY[i] - offset, diameter, diameter);
						break;
					case 4:
						pixmap.drawCircle(objectsPosistionX[i] - offset, objectsPosistionY[i] - offset, diameter);
						pixmap.drawCircle(objectsPosistionX[i] - offset + diameter / 4 - diameter / 8, objectsPosistionY[i] - offset + diameter / 3,
										  diameter / 4);
						pixmap.drawCircle(objectsPosistionX[i] - offset + (int) (diameter * 0.75F) - diameter / 8, objectsPosistionY[i] - offset + diameter / 3,
										  diameter / 4);
						pixmap.drawCircle(objectsPosistionX[i] - offset + diameter / 4, objectsPosistionY[i] - offset + diameter / 3, diameter / 8);
						pixmap.drawCircle(objectsPosistionX[i] - offset + (int) (diameter * 0.75F), objectsPosistionY[i] - offset + diameter / 3, diameter / 8);
						break;
				}
			}

			objectsExpand[i] += 2;
			objectsFade[i] += 2;
			if (objectsFade[i] >= growth){
				objectsFade[i] = growth;
			}

			if ((objectsExpand[i] < growth) || (rnd.nextInt(100) >= 10)){
				continue;
			}
			objectsPosistionX[i] = rnd.nextInt(width);
			objectsPosistionY[i] = rnd.nextInt(height);
			objectsExpand[i] = 0;
			objectsFade[i] = 0;
		}
	}

	/**
	 * @param pixmap
	 */
	@Override
	public void generate(final Pixmap pixmap){
		generate(pixmap, 0, 5, 10, 10, 1, 255, 255, 0);
	}

	@Override
	public void random(Pixmap pixmap){
		generate(pixmap, (int) (Math.random() * 4), (int) (5 + Math.random() * 5), (int) (5 + Math.random() * 5), (int) (5 + Math.random() * 5), 1, 255, 255,
				 0);
	}
}
