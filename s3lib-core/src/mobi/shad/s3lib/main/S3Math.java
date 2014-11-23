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

package mobi.shad.s3lib.main;

import java.util.Random;

/**
 * Some math function and fast array implementation of sin/cos function
 */
public class S3Math{

	public static final float PI = (float) Math.PI;
	public static final float PI2 = (float) Math.PI * 2;
	public static final float DIV_PI2_360 = (float) (1.0f / (360.0f / PI2));
	public static final float PI1_2 = (float) Math.PI * 0.5f;
	public static final float PI1_4 = (float) Math.PI * 0.25f;
	public static final int cacheSize = 8192;
	public static final float factor = (float) cacheSize / PI2;
	public static float[] sinCache;
	public static float[] cosCache;
	private static int angleIndeX1;
	private static int angleIndeY1;
	private static int angleIndeX2;
	private static int angleIndeY2;
	private static int angleIndeX3;
	private static int angleIndeY3;

	private static Random random = new Random();

	private S3Math(){

	}

	public static void init(){

		sinCache = new float[cacheSize + 256];
		cosCache = new float[cacheSize + 256];
		for (int i = 0; i < cacheSize; i++){
			double angle = ((double) i / (double) cacheSize) * PI2;
			sinCache[i] = (float) Math.sin(angle);
			cosCache[i] = (float) Math.cos(angle);
		}
	}

	/**
	 * @param i
	 * @return
	 */
	public static float fastSin(float i){

		int index = (int) (i * factor) % cacheSize;
		return (index < 0) ? sinCache[index + cacheSize] : sinCache[index];
	}

	/**
	 * @param i
	 * @return
	 */
	public static float fastCos(float i){

		int index = (int) (i * factor) % cacheSize;
		return (index < 0) ? cosCache[index + cacheSize] : cosCache[index];
	}


	/**
	 * Returns whether x is a power of two or not.
	 */
	/**
	 * @param x
	 * @return
	 */
	public static boolean isPOT(int x){
		return x != 0 && (x & (x - 1)) == 0;
	}

	/**
	 * Returns the smallest power of two that is greater than or equal to x.
	 *
	 * @param x
	 * @return
	 */
	public static int ceilPOT(int x){
		if (isPOT(x) || x == 0){
			return x;
		}

		long mask = (long) 1 << 31;
		while ((x & mask) == 0 && mask > 1){
			mask >>= 1;
		}
		return (int) mask << 1;
	}

	/**
	 * Returns the largest power of two that is smaller than or equal to x.
	 *
	 * @param x
	 * @return
	 */
	public static int floorPOT(int x){
		if (isPOT(x) || x == 0){
			return x;
		}

		long mask = (long) 1 << 31;
		while ((x & mask) == 0 && mask > 1){
			mask >>= 1;
		}
		return (int) mask;
	}

	/**
	 * Returns a random float number != invalid in range [0, factor[.
	 *
	 * @param factor
	 * @param invalid
	 * @return
	 */
	public static float randomize(float factor, float invalid){
		float r;
		do{
			r = (float) (Math.random() * factor);
		} while (r == invalid);
		return r;
	}

	/**
	 * @param factor
	 * @return
	 */
	public static float random(float factor){
		return (float) (random.nextDouble() * factor);
	}

	/**
	 * @param factor
	 * @return
	 */
	public static int random(int factor){
		return (int) (random.nextDouble() * factor);
	}

	/**
	 * @return
	 */
	public static int randomUnsignetInt(){
		return (int) (random.nextInt() % 32768);
	}


	/**
	 * @param t
	 * @return
	 */
	public static final float smoothCurve(float t){
		return (float) (t * t * (3. - 2. * t));
	}

	/**
	 * @param t
	 * @param a
	 * @param b
	 * @return
	 */
	public static final float lerp(float t, float a, float b){
		return a + t * (b - a);
	}


	/**
	 * @param angle
	 * @param multipleX
	 * @param multipleY
	 * @param amplitudeX
	 * @param amplitudeY
	 * @return
	 */
	public static float matchSinCosArrayX(float angle, float multipleX, float multipleY, float amplitudeX, float amplitudeY){
		int angleIndex = (int) (angle * factor * multipleX) % cacheSize;
		return ((float) (((angleIndex < 0) ? cosCache[angleIndex + cacheSize] : cosCache[angleIndex])) * amplitudeX);
	}

	/**
	 * @param angle
	 * @param multipleX
	 * @param multipleY
	 * @param amplitudeX
	 * @param amplitudeY
	 * @return
	 */
	public static float matchSinCosArrayY(float angle, float multipleX, float multipleY, float amplitudeX, float amplitudeY){
		int angleIndex = (int) (angle * factor * multipleY) % cacheSize;
		return ((float) (((angleIndex < 0) ? sinCache[angleIndex + cacheSize] : sinCache[angleIndex])) * amplitudeY);
	}

	/**
	 * @param angle
	 * @param multipleX
	 * @param multipleY
	 * @param amplitudeX
	 * @param amplitudeY
	 * @param angle2
	 * @param multipleX2
	 * @param multipleY2
	 * @param amplitudeX2
	 * @param amplitudeY2
	 * @param angle3
	 * @param multipleX3
	 * @param multipleY3
	 * @param amplitudeX3
	 * @param amplitudeY3
	 * @return
	 */
	public static float matchSinCosArrayX(
	float angle, float multipleX, float multipleY, float amplitudeX, float amplitudeY,
	float angle2, float multipleX2, float multipleY2, float amplitudeX2, float amplitudeY2,
	float angle3, float multipleX3, float multipleY3, float amplitudeX3, float amplitudeY3){
		int angleIndex = (int) (angle * factor * multipleX) % cacheSize;
		int angleIndex2 = (int) (angle2 * factor * multipleX2) % cacheSize;
		int angleIndex3 = (int) (angle3 * factor * multipleX3) % cacheSize;
		return ((((angleIndex < 0) ? cosCache[angleIndex + cacheSize] : cosCache[angleIndex])) * amplitudeX)
		+ ((((angleIndex2 < 0) ? cosCache[angleIndex2 + cacheSize] : cosCache[angleIndex2])) * amplitudeX2)
		+ ((((angleIndex3 < 0) ? cosCache[angleIndex3 + cacheSize] : cosCache[angleIndex3])) * amplitudeX3);
	}

	/**
	 * @param angle
	 * @param multipleX
	 * @param multipleY
	 * @param amplitudeX
	 * @param amplitudeY
	 * @param angle2
	 * @param multipleX2
	 * @param multipleY2
	 * @param amplitudeX2
	 * @param amplitudeY2
	 * @param angle3
	 * @param multipleX3
	 * @param multipleY3
	 * @param amplitudeX3
	 * @param amplitudeY3
	 * @return
	 */
	public static float matchSinCosArrayY(
	float angle, float multipleX, float multipleY, float amplitudeX, float amplitudeY,
	float angle2, float multipleX2, float multipleY2, float amplitudeX2, float amplitudeY2,
	float angle3, float multipleX3, float multipleY3, float amplitudeX3, float amplitudeY3){
		int angleIndex = (int) (angle * factor * multipleY) % cacheSize;
		int angleIndex2 = (int) (angle2 * factor * multipleY2) % cacheSize;
		int angleIndex3 = (int) (angle3 * factor * multipleY3) % cacheSize;
		return ((((angleIndex < 0) ? sinCache[angleIndex + cacheSize] : sinCache[angleIndex])) * amplitudeY)
		+ ((((angleIndex2 < 0) ? sinCache[angleIndex2 + cacheSize] : sinCache[angleIndex2])) * amplitudeY2)
		+ ((((angleIndex3 < 0) ? sinCache[angleIndex3 + cacheSize] : sinCache[angleIndex3])) * amplitudeY3);
	}

	/**
	 * @param angleX
	 * @param angleY
	 * @param amplitudeX
	 * @param amplitudeY
	 * @param angleX2
	 * @param angleY2
	 * @param amplitudeX2
	 * @param amplitudeY2
	 * @param angleX3
	 * @param angleY3
	 * @param amplitudeX3
	 * @param amplitudeY3
	 * @return
	 */
	public static float matchSinCos3D(
	float angleX, float angleY, float amplitudeX, float amplitudeY,
	float angleX2, float angleY2, float amplitudeX2, float amplitudeY2,
	float angleX3, float angleY3, float amplitudeX3, float amplitudeY3){
		int angleIndexX = (int) (angleX * factor) % cacheSize;
		int angleIndexY = (int) (angleY * factor) % cacheSize;
		int angleIndexX2 = (int) (angleX2 * factor) % cacheSize;
		int angleIndexY2 = (int) (angleY2 * factor) % cacheSize;
		int angleIndexX3 = (int) (angleX3 * factor) % cacheSize;
		int angleIndexY3 = (int) (angleY3 * factor) % cacheSize;
		return ((((angleIndexX < 0) ? sinCache[angleIndexX + cacheSize] : sinCache[angleIndexX])) * amplitudeX)
		+ ((((angleIndexY < 0) ? cosCache[angleIndexY + cacheSize] : cosCache[angleIndexY])) * amplitudeY)
		+ ((((angleIndexX2 < 0) ? sinCache[angleIndexX2 + cacheSize] : sinCache[angleIndexX2])) * amplitudeX2)
		+ ((((angleIndexY2 < 0) ? cosCache[angleIndexY2 + cacheSize] : cosCache[angleIndexY2])) * amplitudeY2)
		+ ((((angleIndexX3 < 0) ? sinCache[angleIndexX3 + cacheSize] : sinCache[angleIndexX3])) * amplitudeX3)
		+ ((((angleIndexY3 < 0) ? cosCache[angleIndexY3 + cacheSize] : cosCache[angleIndexY3])) * amplitudeY3);
	}

	/**
	 * @param angleX1
	 * @param angleY1
	 * @param amplitudeX1
	 * @param amplitudeY1
	 * @param angleX2
	 * @param angleY2
	 * @param amplitudeX2
	 * @param amplitudeY2
	 * @param angleX3
	 * @param angleY3
	 * @param amplitudeX3
	 * @param amplitudeY3
	 * @return
	 */
	public static float newSinCosArrayX(
	float angleX1, float angleY1, float amplitudeX1, float amplitudeY1,
	float angleX2, float angleY2, float amplitudeX2, float amplitudeY2,
	float angleX3, float angleY3, float amplitudeX3, float amplitudeY3){
		angleIndeX1 = (int) (angleX1 * factor) % cacheSize;
		angleIndeY1 = (int) (angleY1 * factor) % cacheSize;
		angleIndeX2 = (int) (angleX2 * factor) % cacheSize;
		angleIndeY2 = (int) (angleY2 * factor) % cacheSize;
		angleIndeX3 = (int) (angleX3 * factor) % cacheSize;
		angleIndeY3 = (int) (angleY3 * factor) % cacheSize;
		return ((((angleIndeX1 < 0) ? cosCache[angleIndeX1 + cacheSize] : cosCache[angleIndeX1])) * amplitudeX1)
		+ ((((angleIndeX2 < 0) ? cosCache[angleIndeX2 + cacheSize] : cosCache[angleIndeX2])) * amplitudeX2)
		+ ((((angleIndeX3 < 0) ? cosCache[angleIndeX3 + cacheSize] : cosCache[angleIndeX3])) * amplitudeX3);
	}

	/**
	 * @param amplitudeY1
	 * @param amplitudeY2
	 * @param amplitudeY3
	 * @return
	 */
	public static float newSinCosArrayY(float amplitudeY1, float amplitudeY2, float amplitudeY3){
		return ((((angleIndeY1 < 0) ? sinCache[angleIndeY1 + cacheSize] : sinCache[angleIndeY1])) * amplitudeY1)
		+ ((((angleIndeY2 < 0) ? sinCache[angleIndeY2 + cacheSize] : sinCache[angleIndeY2])) * amplitudeY2)
		+ ((((angleIndeY3 < 0) ? sinCache[angleIndeY3 + cacheSize] : sinCache[angleIndeY3])) * amplitudeY3);
	}
}
