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

/**
 * @author Jarek
 */
public class Kernel implements Cloneable{
	private int width;
	private int height;
	private int xOrigin;
	private int yOrigin;
	private float data[];

	public Kernel(int width, int height, float data[]){
		this.width = width;
		this.height = height;
		this.xOrigin = (width - 1) >> 1;
		this.yOrigin = (height - 1) >> 1;
		int len = width * height;
		if (data.length < len){
			throw new IllegalArgumentException("Data array too small (is " + data.length + "and should be " + len);
		}
		this.data = new float[len];
		System.arraycopy(data, 0, this.data, 0, len);
	}

	final public int getXOrigin(){
		return xOrigin;
	}

	final public int getYOrigin(){
		return yOrigin;
	}

	final public int getWidth(){
		return width;
	}

	final public int getHeight(){
		return height;
	}

	final public float[] getKernelData(float[] data){
		if (data == null){
			data = new float[this.data.length];
		} else if (data.length < this.data.length){
			throw new IllegalArgumentException("Data array too small  (should be " + this.data.length + " but is " + data.length + " )");
		}
		System.arraycopy(this.data, 0, data, 0, this.data.length);
		return data;
	}
}
