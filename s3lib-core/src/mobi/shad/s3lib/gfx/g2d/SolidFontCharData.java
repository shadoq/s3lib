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

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Jarek
 */
public class SolidFontCharData{

	public char ch = ' ';
	public int code = 0;
	public float x = 0;
	public float y = 0;
	public int width = 0;
	public int height = 0;
	public float endX = 0;
	public float endY = 0;
	public float centerX = 0;
	public float centerY = 0;
	public int startWidth = 0;
	public int startHeight = 0;
	public float u0 = 0f;
	public float v0 = 0f;
	public float u1 = 1f;
	public float v1 = 1f;
	public TextureRegion region = null;

	/**
	 * @return
	 */
	@Override
	public String toString(){
		return "CharData{" + "ch=" + ch + ", code=" + code + ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", endX=" + endX + ", endY=" + endY + ", centerX=" + centerX + ", centerY=" + centerY + ", u0=" + u0 + ", v0=" + v0 + ", u1=" + u1 + ", v1=" + v1 + ", region=" + region + '}';
	}

	/**
	 *
	 */
	public void update(){
		startWidth = width;
		startHeight = height;
		endX = (x + width);
		endY = (y + height);
		centerX = (x + width / 2);
		centerY = (y + height / 2);
	}
}
