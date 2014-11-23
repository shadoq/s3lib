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
package mobi.shad.s3lib.gfx.math;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * @author Jarek
 */
public class Convolve{

	public static float[] BLUR_MATRIX_3x3 = {
	0.111f, 0.111f, 0.111f,
	0.111f, 0.111f, 0.111f,
	0.111f, 0.111f, 0.111f,};
	public static float[] BLUR_MATRIX_5x5 = {
	0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
	0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
	0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
	0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
	0.04f, 0.04f, 0.04f, 0.04f, 0.04f,};
	public static float[] BLUR_MATRIX_CROSS = {
	0.00f, 0.00f, 0.111f, 0.00f, 0.00f,
	0.00f, 0.00f, 0.111f, 0.00f, 0.00f,
	0.111f, 0.111f, 0.111f, 0.111f, 0.111f,
	0.00f, 0.00f, 0.111f, 0.00f, 0.00f,
	0.00f, 0.00f, 0.111f, 0.00f, 0.00f,};
	public static float[] BLUR_MATRIX_STAR = {
	0.111f, 0.00f, 0.00f, 0.00f, 0.111f,
	0.00f, 0.111f, 0.00f, 0.111f, 0.00f,
	0.00f, 0.00f, 0.111f, 0.00f, 0.00f,
	0.00f, 0.111f, 0.00f, 0.111f, 0.00f,
	0.111f, 0.00f, 0.00f, 0.00f, 0.111f,};
	public static float[] BLUR_MATRIX_BLOCK = {
	0.0625f, 0.0625f, 0.0625f, 0.0625f, 0.0625f,
	0.0625f, 0.00f, 0.00f, 0.00f, 0.0625f,
	0.0625f, 0.00f, 0.00f, 0.00f, 0.0625f,
	0.0625f, 0.00f, 0.00f, 0.00f, 0.0625f,
	0.0625f, 0.0625f, 0.0625f, 0.0625f, 0.0625f,};
	public static float[] BLUR_MATRIX_EMPTY = {
	0.00f, 0.00f, 0.00f, 0.00f, 0.00f,
	0.00f, 0.00f, 0.00f, 0.00f, 0.00f,
	0.00f, 0.00f, 0.00f, 0.00f, 0.00f,
	0.00f, 0.00f, 0.00f, 0.00f, 0.00f,
	0.00f, 0.00f, 0.00f, 0.00f, 0.00f,};

	public enum Edge{
		CLAMP_EDGES, WRAP_EDGES, NONE
	}

	/**
	 *
	 * @param kernel
	 * @param inPixels
	 * @param outPixels
	 * @param width
	 * @param height
	 * @param alpha
	 * @param edgeAction
	 */
	public static void convolveHV(Kernel kernel, Pixmap inPixels, Pixmap outPixels, int width, int height, boolean alpha, Edge edgeAction){

		float[] matrix = kernel.getKernelData(null);
		int rows = kernel.getHeight();
		int cols = kernel.getWidth();
		int rows2 = rows / 2;
		int cols2 = cols / 2;

		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				float r = 0, g = 0, b = 0, a = 0;

				for (int row = -rows2; row <= rows2; row++){
					int iy = y + row;
					if (!(0 <= iy && iy < height)){
						if (edgeAction == Edge.CLAMP_EDGES){
							iy = y;
						} else if (edgeAction == Edge.WRAP_EDGES){
							iy = ((iy + height) % height);
						} else {
							continue;
						}
					}
					int moffset = cols * (row + rows2) + cols2;
					for (int col = -cols2; col <= cols2; col++){
						float f = matrix[moffset + col];

						if (f != 0){
							int ix = x + col;
							if (!(0 <= ix && ix < width)){
								if (edgeAction == Edge.CLAMP_EDGES){
									ix = x;
								} else if (edgeAction == Edge.WRAP_EDGES){
									ix = (x + width) % width;
								} else {
									continue;
								}
							}

							int rgb = inPixels.getPixel(ix, iy);
							a += f * ((rgb >> 24) & 0xff);
							r += f * ((rgb >> 16) & 0xff);
							g += f * ((rgb >> 8) & 0xff);
							b += f * (rgb & 0xff);
						}
					}
				}

				int ia = alpha ? (int) (a + 0.5) : 0xff;
				int ir = (int) (r + 0.5);
				int ig = (int) (g + 0.5);
				int ib = (int) (b + 0.5);

				if (ia < 0){
					ia = 0;
				}
				if (ia > 255){
					ia = 255;
				}

				if (ir < 0){
					ir = 0;
				}
				if (ir > 255){
					ir = 255;
				}

				if (ig < 0){
					ig = 0;
				}
				if (ig > 255){
					ig = 255;
				}

				if (ib < 0){
					ib = 0;
				}
				if (ib > 255){
					ib = 255;
				}

				int color = (ia << 24) | (ir << 16) | (ig << 8) | ib;
				outPixels.drawPixel(x, y, color);
			}
		}
	}

	/**
	 *
	 * @param kernel
	 * @param inPixels
	 * @param outPixels
	 * @param width
	 * @param height
	 * @param alpha
	 * @param edgeAction
	 */
	public static void convolveH(Kernel kernel, Pixmap inPixels, Pixmap outPixels, int width, int height, boolean alpha, Edge edgeAction){

		float[] matrix = kernel.getKernelData(null);
		int cols = kernel.getWidth();
		int cols2 = cols / 2;

		for (int y = 0; y < height; y++){

			for (int x = 0; x < width; x++){
				float r = 0, g = 0, b = 0, a = 0;

				int moffset = cols2;
				for (int col = -cols2; col <= cols2; col++){
					float f = matrix[moffset + col];

					if (f != 0){
						int ix = x + col;
						if (!(0 <= ix && ix < width)){
							if (edgeAction == Edge.CLAMP_EDGES){
								ix = x;
							} else if (edgeAction == Edge.WRAP_EDGES){
								ix = (x + width) % width;
							} else {
								continue;
							}
						}

						f = f * cols;

						int rgb = inPixels.getPixel(ix, y);
						a += f * ((rgb >> 24) & 0xff);
						r += f * ((rgb >> 16) & 0xff);
						g += f * ((rgb >> 8) & 0xff);
						b += f * (rgb & 0xff);
					}
				}

				int ia = alpha ? (int) (a + 0.5) : 0xff;
				int ir = (int) (r + 0.5);
				int ig = (int) (g + 0.5);
				int ib = (int) (b + 0.5);

				if (ia < 0){
					ia = 0;
				}
				if (ia > 255){
					ia = 255;
				}

				if (ir < 0){
					ir = 0;
				}
				if (ir > 255){
					ir = 255;
				}

				if (ig < 0){
					ig = 0;
				}
				if (ig > 255){
					ig = 255;
				}

				if (ib < 0){
					ib = 0;
				}
				if (ib > 255){
					ib = 255;
				}

				int color = (ia << 24) | (ir << 16) | (ig << 8) | ib;
				outPixels.drawPixel(x, y, color);
			}
		}
	}

	/**
	 *
	 * @param kernel
	 * @param inPixels
	 * @param outPixels
	 * @param width
	 * @param height
	 * @param alpha
	 * @param edgeAction
	 */
	public static void convolveV(Kernel kernel, Pixmap inPixels, Pixmap outPixels, int width, int height, boolean alpha, Edge edgeAction){
		int index = 0;
		float[] matrix = kernel.getKernelData(null);
		int rows = kernel.getHeight();
		int rows2 = rows / 2;

		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				float r = 0, g = 0, b = 0, a = 0;

				for (int row = -rows2; row <= rows2; row++){
					int iy = y + row;
					if (iy < 0){
						if (edgeAction == Edge.CLAMP_EDGES){
							iy = 0;
						} else if (edgeAction == Edge.WRAP_EDGES){
							iy = ((y + height) % height);
						} else {
							iy = iy;
						}
					} else if (iy >= height){
						if (edgeAction == Edge.CLAMP_EDGES){
							iy = (height - 1);
						} else if (edgeAction == Edge.WRAP_EDGES){
							iy = ((y + height) % height);
						} else {
							iy = iy;
						}
					} else {
						iy = iy;
					}

					float f = matrix[row + rows2];

					if (f != 0){

						f = f * rows;

						int rgb = inPixels.getPixel(x, iy);
						a += f * ((rgb >> 24) & 0xff);
						r += f * ((rgb >> 16) & 0xff);
						g += f * ((rgb >> 8) & 0xff);
						b += f * (rgb & 0xff);
					}
				}

				int ia = alpha ? (int) (a + 0.5) : 0xff;
				int ir = (int) (r + 0.5);
				int ig = (int) (g + 0.5);
				int ib = (int) (b + 0.5);

				if (ia < 0){
					ia = 0;
				}
				if (ia > 255){
					ia = 255;
				}

				if (ir < 0){
					ir = 0;
				}
				if (ir > 255){
					ir = 255;
				}

				if (ig < 0){
					ig = 0;
				}
				if (ig > 255){
					ig = 255;
				}

				if (ib < 0){
					ib = 0;
				}
				if (ib > 255){
					ib = 255;
				}

				int color = (ia << 24) | (ir << 16) | (ig << 8) | ib;
				outPixels.drawPixel(x, y, color);
			}
		}
	}
}
