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
package mobi.shad.s3lib.gfx.g2d;

import com.badlogic.gdx.math.Interpolation;
import mobi.shad.s3lib.main.S3Math;

/**
 * @author Jarek
 */
public class SpriteAnim{

	private float[] posistion;
	private float[] size;
	private float[] copyPosistion;
	private float[] copySize;
	private int posistionLenght;
	private float tempX;
	private float tempY;
	private float tempX2;
	private float tempY2;
	private float tempWidth;
	private float tempHeight;
	private float tempCenterX;
	private float tempCenterY;

	/**
	 * @param position
	 * @param size
	 */
	public SpriteAnim(float[] position, float[] size){
		this.posistion = position;
		copyPosistion = new float[position.length];
		posistionLenght = copyPosistion.length;

		this.size = size;
		this.copySize = new float[size.length];

		System.arraycopy(position, 0, copyPosistion, 0, position.length);
		System.arraycopy(size, 0, copySize, 0, size.length);
	}

	/**
	 * @param time
	 * @param start
	 * @param end
	 * @param interpolation
	 */
	public void size(float time, float start, float end, Interpolation interpolation){
		time = S3Math.fastCos(time);
		float range = interpolation.apply(start, end, time) / 2;

		for (int idx = 0; idx < posistionLenght; idx = idx + 2){

			//
			// Getting position value
			//
			tempX = copyPosistion[idx];
			tempY = copyPosistion[idx + 1];

			tempWidth = copySize[idx];
			tempHeight = copySize[idx + 1];

			tempCenterX = (tempX + tempWidth / 2);
			tempCenterY = (tempY + tempHeight / 2);

			//
			// Calculate new value
			//
			tempX = tempCenterX - range;
			tempX2 = tempCenterX + range;

			tempY = tempCenterY - range;
			tempY2 = tempCenterY + range;

			tempWidth = tempX2 - tempX;
			tempHeight = tempY2 - tempY;

			//
			// Setting new value on oryginal vertext array
			//
			posistion[idx] = tempX;
			posistion[idx + 1] = tempY;

			size[idx] = tempWidth;
			size[idx + 1] = tempHeight;
		}
	}

	/**
	 * @param time
	 * @param step
	 * @param start
	 * @param end
	 * @param interpolation
	 */
	public void wobblingSize(float time, float step, float start, float end, Interpolation interpolation){

		float range = 0;
		float fxTime = time;
		for (int idx = 0; idx < posistionLenght; idx = idx + 2){

			fxTime = fxTime + step;
			time = S3Math.fastCos(fxTime);
			range = interpolation.apply(start, end, time) / 2;

			//
			// Getting position value
			//
			tempX = copyPosistion[idx];
			tempY = copyPosistion[idx + 1];

			tempWidth = copySize[idx];
			tempHeight = copySize[idx + 1];

			tempCenterX = (tempX + tempWidth / 2);
			tempCenterY = (tempY + tempHeight / 2);

			//
			// Calculate new value
			//
			tempX = tempCenterX - range;
			tempX2 = tempCenterX + range;

			tempY = tempCenterY - range;
			tempY2 = tempCenterY + range;

			tempWidth = tempX2 - tempX;
			tempHeight = tempY2 - tempY;

			//
			// Setting new value on oryginal vertext array
			//
			posistion[idx] = tempX;
			posistion[idx + 1] = tempY;

			size[idx] = tempWidth;
			size[idx + 1] = tempHeight;
		}
	}


	/**
	 * @param timeX
	 * @param timeY
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param interpolation
	 */
	public void position(float timeX, float timeY, float startX, float startY, float endX, float endY, Interpolation interpolation){

		timeX = S3Math.fastCos(timeX);
		timeY = S3Math.fastSin(timeY);
		float rangeX = interpolation.apply(startX, endX, timeX);
		float rangeY = interpolation.apply(startY, endY, timeY);

		for (int idx = 0; idx < posistionLenght; idx = idx + 2){

			//
			// Getting position value
			//
			tempX = copyPosistion[idx];
			tempY = copyPosistion[idx + 1];

			tempWidth = copySize[idx];
			tempHeight = copySize[idx + 1];

			tempCenterX = (tempX + tempWidth / 2);
			tempCenterY = (tempY + tempHeight / 2);

			//
			// Calculate new value
			//
			tempX = tempCenterX + rangeX;
			tempY = tempCenterY + rangeY;

			//
			// Setting new value on oryginal vertext array
			//
			posistion[idx] = tempX;
			posistion[idx + 1] = tempY;

			size[idx] = tempWidth;
			size[idx + 1] = tempHeight;
		}
	}

	/**
	 * @param time
	 * @param stepX
	 * @param stepY
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param interpolation
	 */
	public void wobblingPosition(float time, float stepX, float stepY, float startX, float startY, float endX, float endY, Interpolation interpolation){

		float timeX = time;
		float timeY = time;
		float rangeX = 0;
		float rangeY = 0;
		float impX = 0;
		float impY = 0;

		for (int idx = 0; idx < posistionLenght; idx = idx + 2){

			timeX = timeX + stepX;
			timeY = timeY + stepY;
			impX = S3Math.fastCos(timeX);
			impY = S3Math.fastSin(timeY);
			rangeX = interpolation.apply(startX, endX, impX);
			rangeY = interpolation.apply(startY, endY, impY);

			//
			// Getting position value
			//
			tempX = copyPosistion[idx];
			tempY = copyPosistion[idx + 1];

			tempWidth = copySize[idx];
			tempHeight = copySize[idx + 1];

			tempCenterX = (tempX + tempWidth / 2);
			tempCenterY = (tempY + tempHeight / 2);

			//
			// Calculate new value
			//
			tempX = tempCenterX + rangeX;
			tempY = tempCenterY + rangeY;

			//
			// Setting new value on oryginal vertext array
			//
			posistion[idx] = tempX;
			posistion[idx + 1] = tempY;

			size[idx] = tempWidth;
			size[idx + 1] = tempHeight;
		}
	}

	/**
	 * @param time
	 * @param stepSizeX
	 * @param stepSizeY
	 * @param stepPosX
	 * @param stepPosY
	 * @param startSizeX
	 * @param startSizeY
	 * @param endSizeX
	 * @param endSizeY
	 * @param startPosX
	 * @param startPosY
	 * @param endPosX
	 * @param endPosY
	 * @param interpolationSize
	 * @param interpolationPosistion
	 */
	public void wobblingSizeAndPosition(float time, float stepSizeX, float stepSizeY, float stepPosX, float stepPosY, float startSizeX, float startSizeY,
										float endSizeX, float endSizeY, float startPosX, float startPosY, float endPosX, float endPosY,
										Interpolation interpolationSize, Interpolation interpolationPosistion){

		float deltaTimeSizeX = time;
		float deltaTimeSizeY = time;
		float deltaTimePosX = time;
		float deltaTimePosY = time;
		float impSizeX = 0;
		float impSizeY = 0;
		float impPosX = 0;
		float impPosY = 0;

		for (int idx = 0; idx < posistionLenght; idx = idx + 2){

			deltaTimeSizeX = deltaTimeSizeX + stepSizeX;
			deltaTimeSizeY = deltaTimeSizeY + stepSizeY;
			deltaTimePosX = deltaTimePosX + stepPosX;
			deltaTimePosY = deltaTimePosY + stepPosY;
			impSizeX = interpolationSize.apply(startSizeX, endSizeX, S3Math.fastCos(deltaTimeSizeX)) / 2;
			impSizeY = interpolationSize.apply(startSizeY, endSizeY, S3Math.fastSin(deltaTimeSizeY)) / 2;
			impPosX = interpolationPosistion.apply(startPosX, endPosX, S3Math.fastCos(deltaTimePosX));
			impPosY = interpolationPosistion.apply(startPosY, endPosY, S3Math.fastSin(deltaTimePosY));


			//
			// Getting position value
			//
			tempX = copyPosistion[idx];
			tempY = copyPosistion[idx + 1];

			tempWidth = copySize[idx];
			tempHeight = copySize[idx + 1];

			//
			// Calculate new value
			//
			tempX = tempX + impPosX - impSizeX;
			tempY = tempY + impPosY - impSizeY;

			tempX2 = tempX + tempWidth + impPosX + impSizeX;
			tempY2 = tempY + tempHeight + impPosY + impSizeY;

			tempWidth = tempX2 - tempX;
			tempHeight = tempY2 - tempY;

			//
			// Setting new value on oryginal vertext array
			//
			posistion[idx] = tempX;
			posistion[idx + 1] = tempY;

			size[idx] = tempWidth;
			size[idx + 1] = tempHeight;
		}
	}
}
