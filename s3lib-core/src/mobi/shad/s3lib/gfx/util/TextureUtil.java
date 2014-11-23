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

package mobi.shad.s3lib.gfx.util;

import mobi.shad.s3lib.main.S3Math;

import java.nio.FloatBuffer;

/**
 * @author Jarek
 */
public class TextureUtil{

	/**
	 * @param centerX
	 * @param centerY
	 * @param width
	 * @param height
	 * @param vertexCoords
	 * @param offset
	 */
	public static void calculateVertex2DCenterCoords(float centerX, float centerY, float width, float height, FloatBuffer vertexCoords, int offset){
		width = width / 2;
		height = height / 2;
		float cxmw = (float) ((centerX - width));
		float cxpw = (float) ((centerX + width));
		float cymh = (float) ((centerY - height));
		float cyph = (float) ((centerY + height));
		vertexCoords.position(offset);
		vertexCoords.put(cxmw);
		vertexCoords.put(cymh);
		vertexCoords.put(cxmw);
		vertexCoords.put(cyph);
		vertexCoords.put(cxpw);
		vertexCoords.put(cymh);
		vertexCoords.put(cxpw);
		vertexCoords.put(cyph);
		vertexCoords.position(0);
	}

	/**
	 * @param topX
	 * @param topY
	 * @param width
	 * @param height
	 * @param vertexCoords
	 * @param offset
	 */
	public static void calculateVertex2DTopCoords(float topX, float topY, float width, float height, FloatBuffer vertexCoords, int offset){
		float cxmw = (float) ((topX));
		float cxpw = (float) ((topX + width));
		float cymh = (float) ((topY));
		float cyph = (float) ((topY + height));
		vertexCoords.position(offset);
		vertexCoords.put(cxmw);
		vertexCoords.put(cymh);
		vertexCoords.put(cxmw);
		vertexCoords.put(cyph);
		vertexCoords.put(cxpw);
		vertexCoords.put(cymh);
		vertexCoords.put(cxpw);
		vertexCoords.put(cyph);
		vertexCoords.position(0);
	}

	/**
	 * @param centerX
	 * @param centerY
	 * @param width
	 * @param height
	 * @param vertexCoords
	 * @param offset
	 */
	public static void calculateVertex2DEfectCoords(float centerX, float centerY, float width, float height, FloatBuffer vertexCoords, int offset, float angleX,
													float angleY, float scaleX, float scaleY){
		width = width / 2;
		height = height / 2;
		float vertexMinX = (float) ((centerX - width - S3Math.fastCos(angleX) * scaleX));
		float vertexMaxX = (float) ((centerX + width + S3Math.fastCos(angleX) * scaleX));
		float veretxMinY = (float) ((centerY - height - S3Math.fastSin(angleY) * scaleY));
		float vertexMaxY = (float) ((centerY + height + S3Math.fastSin(angleY) * scaleY));
		vertexCoords.position(offset);
		vertexCoords.put(vertexMinX);
		vertexCoords.put(veretxMinY);
		vertexCoords.put(vertexMinX);
		vertexCoords.put(vertexMaxY);
		vertexCoords.put(vertexMaxX);
		vertexCoords.put(veretxMinY);
		vertexCoords.put(vertexMaxX);
		vertexCoords.put(vertexMaxY);
		vertexCoords.position(0);
	}

	/**
	 * @param texturePreRow
	 * @param texturePerColumn
	 * @param index
	 * @param textureCoords
	 * @param offset
	 */
	public static void calculateTexture2DCoords(int texturePreRow, int texturePerColumn, int index, FloatBuffer textureCoords, int offset){
		float xStep = 1.0F / texturePreRow;
		float yStep = 1.0F / texturePerColumn;
		float x = index % texturePreRow;
		float y = index / texturePreRow;
		float xMin = x * xStep;
		float xMax = xMin + (xStep);
		float yMin = y * yStep;
		float yMax = yMin + (yStep);
		textureCoords.position(offset);
		textureCoords.put(xMin);
		textureCoords.put(yMin);
		textureCoords.put(xMin);
		textureCoords.put(yMax);
		textureCoords.put(xMax);
		textureCoords.put(yMin);
		textureCoords.put(xMax);
		textureCoords.put(yMax);
		textureCoords.position(0);
	}

}
