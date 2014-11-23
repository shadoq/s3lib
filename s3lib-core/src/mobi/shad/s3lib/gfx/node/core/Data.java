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
package mobi.shad.s3lib.gfx.node.core;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import mobi.shad.s3lib.gfx.g2d.SolidFont;
import mobi.shad.s3lib.gfx.g3d.shaders.BaseShader;
import mobi.shad.s3lib.gfx.g3d.simpleobject.ObjectMesh;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class Data{

	private static int countId = 0;
	//
	// Name Data
	//
	public String name = "fx_" + countId;
	public int id = countId++;
	public Type type = Type.NONE;
	//
	// S3Screen size data
	//
	public float startX = 0;
	public float startY = 0;
	public float endX = S3Screen.width;
	public float endY = S3Screen.height;
	public float centerX = S3Screen.centerX;
	public float centerY = S3Screen.centerY;
	public float width = S3Screen.width;
	public float height = S3Screen.height;
	//
	// Texture Data
	//
	public Pixmap pixmap = null;
	public Texture texture = null;
	public boolean textureChange = false;
	public Color color = null;
	//
	// Sprite Batch Data
	//
	public float[] spritePosition = null;
	public float[] spriteSize = null;
	public float[] spriteColor = null;
	public boolean spriteBlend = true;
	public float spriteWidth = 16;
	public float spriteHeight = 16;
	public Texture spriteTexture = null;
	//
	// Font Data
	//
	public SolidFont solidFont = null;
	public BitmapFontCache bitmapFont = null;
	public SpriteCache spriteCache = null;
	public int bitmapFontWrite = -1;
	//
	// 3d Object Data
	//
	public int primitiveType = GL20.GL_POINTS;
	public ObjectMesh objectMesh = null;
	public BaseShader shader = null;
	public PerspectiveCamera perspectiveCamera = null;

	public enum Type{

		NONE, EFFECT_2D, EFFECT_3D
	}

	@Override
	public String toString(){
		return "name:" + name + " id:" + id + " type: " + type
		+ "\nstartX: " + startX + " startY: " + startY + " endX: " + endX + " endY: " + endY + " centerX: " + centerX + " centerY: " + centerY + " width: " + width + " height: " + height
		+ "\npixmap: " + pixmap + " texture: " + texture + " textureChange: " + textureChange + " color: " + color
		+ "\nspritePosition: " + spritePosition + " spriteSize: " + spriteSize + " spriteColor: " + spriteColor + " spriteBlend: " + spriteBlend + " spriteWidth: " + spriteWidth + " spriteHeight: " + spriteHeight + " spriteTexture: " + spriteTexture
		+ "\nsolidFont: " + solidFont + " bitmapFont: " + bitmapFont
		+ "\nprimitiveType: " + primitiveType + " objectMesh: " + objectMesh + " shader: " + shader + " perspectiveCamera: " + perspectiveCamera;
	}
}
