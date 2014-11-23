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

import java.nio.*;

/**
 * Generowanie natywnych buforów NIO
 *
 * @author Jarek
 */
public class BufferUtil{

	/**
	 * @param capacity
	 * @return
	 */
	public static ByteBuffer nativeByteBuffer(int capacity){
		ByteBuffer buffer = ByteBuffer.allocateDirect(capacity);
		buffer.order(ByteOrder.nativeOrder());
		return buffer;
	}

	/**
	 * @param array
	 * @return
	 */
	public static ByteBuffer nativeByteBuffer(byte[] array){
		ByteBuffer buffer = nativeByteBuffer(array.length);
		buffer.put(array).position(0);
		return buffer;
	}

	/**
	 * @param capacity
	 * @return
	 */
	public static ShortBuffer nativeShortBuffer(int capacity){
		return nativeByteBuffer(capacity * Short.SIZE >> 3).asShortBuffer();
	}

	/**
	 * @param array
	 * @return
	 */
	public static ShortBuffer nativeShortBuffer(short[] array){
		ShortBuffer buffer = nativeShortBuffer(array.length);
		buffer.put(array).position(0);
		return buffer;
	}

	/**
	 * @param capacity
	 * @return
	 */
	public static IntBuffer nativeIntBuffer(int capacity){
		return nativeByteBuffer(capacity * Integer.SIZE >> 3).asIntBuffer();
	}

	/**
	 * @param array
	 * @return
	 */
	public static IntBuffer nativeIntBuffer(int[] array){
		IntBuffer buffer = nativeIntBuffer(array.length);
		buffer.put(array).position(0);
		return buffer;
	}

	/**
	 * @param capacity
	 * @return
	 */
	public static FloatBuffer nativeFloatBuffer(int capacity){
		return nativeByteBuffer(capacity * Float.SIZE >> 3).asFloatBuffer();
	}

	/**
	 * @param array
	 * @return
	 */
	public static FloatBuffer nativeFloatBuffer(float[] array){
		FloatBuffer buffer = nativeFloatBuffer(array.length);
		buffer.put(array).position(0);
		return buffer;
	}

	/**
	 * @param buffer
	 * @return
	 */
	public static String floatBufferToString(FloatBuffer buffer){
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < buffer.capacity(); i++){
			out.append(i).append("=").append(buffer.get(i)).append(" ");
		}
		return out.toString();
	}

	/**
	 * @param buffer
	 * @return
	 */
	public static String shordBufferToString(ShortBuffer buffer){
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < buffer.capacity(); i++){
			out.append(i).append("=").append(buffer.get(i)).append(" ");
		}
		return out.toString();
	}

	/**
	 * @param buffer
	 * @return
	 */
	public static String byteBufferToString(ByteBuffer buffer){
		StringBuilder out = new StringBuilder();
		int j = 32;
		for (int i = 0; i < buffer.capacity(); i++){
			j++;
			if (j > 7){
				j = 0;
				out.append("\n[").append(i).append("] ");
			}
			out.append(buffer.get(i)).append(" ");
		}
		return out.toString();
	}

}
