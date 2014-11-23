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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class SolidFont{

	protected String fontFileName = "font/3.png";
	protected int x = 0;
	protected int y = 0;
	protected int screenWidth = S3Screen.width;
	protected int screenHeight = S3Screen.height;
	protected int fontWidth = 16;
	protected int fontHeight = 16;
	protected int drawFonts = 0;
	protected String fontText = "";
	protected Texture fontTexture;
	protected String fontCharset = ""
	+ "                "
	+ "                "
	+ " !\"#$%&'()*+,-./"
	+ "0123456789:;<=>?"
	+ "@ABCDEFGHIJKLMNO"
	+ "PQRSTUVWXYZ[\\]^_"
	+ "`abcdefghijklmno"
	+ "pqrstuvwxyz{|}~ "
	+ "            Ś  Ź"
	+ "            ś  ź"
	+ "   Ł Ą         Ż"
	+ "   ł     ą     ż"
	+ "      Ć   Ę     "
	+ " Ń Ó            "
	+ "      ć   ę     "
	+ " ń ó            ";
	protected SpriteBatch spriteBatch;
	protected Array<SolidFontCharData> chars = new Array<SolidFontCharData>();
	protected int textLengh = 0;
	protected SolidFontCharData tmpCh = new SolidFontCharData();

	/**
	 * @param fontFileName
	 * @param fontText
	 */
	public SolidFont(String fontFileName, String fontText){
		this.fontTexture = fontTexture;
		this.fontText = fontText;
		initData();
	}

	/**
	 * @param fontFileName
	 * @param fontText
	 * @param x
	 * @param y
	 * @param screenWidth
	 * @param screenHeight
	 */
	public SolidFont(String fontFileName, String fontText, int x, int y, int screenWidth, int screenHeight){
		this.fontFileName = fontFileName;
		this.fontText = fontText;
		this.x = x;
		this.y = y;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		initData();
	}

	/**
	 * @param fontFileName
	 * @param fontText
	 * @param x
	 * @param y
	 * @param screenWidth
	 * @param screenHeight
	 * @param fontWidth
	 * @param fontHeight
	 */
	public SolidFont(String fontFileName, String fontText, int x, int y, int screenWidth, int screenHeight, int fontWidth, int fontHeight){
		this.fontFileName = fontFileName;
		this.fontText = fontText;
		this.x = x;
		this.y = y;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.fontWidth = fontWidth;
		this.fontHeight = fontHeight;
		initData();
	}

	/**
	 *
	 */
	private void initData(){
		spriteBatch = new SpriteBatch();
		fontTexture = S3ResourceManager.getTexture(fontFileName, S3Constans.textureSolidFontSize);
		calcTexCoords(fontText, fontCharset, fontTexture.getWidth(), fontTexture.getHeight(), 16, 16, x, y, screenWidth, screenHeight, fontWidth, fontHeight);
		drawFonts = textLengh;
	}

	/**
	 * @param text
	 * @param charset
	 * @param textureWidth
	 * @param textureHeight
	 * @param textureCharsPerColumn
	 * @param textureCharsPerRow
	 */
	protected void calcTexCoords(String text, String charset, int textureWidth, int textureHeight, int textureCharsPerColumn, int textureCharsPerRow,
								 int screenX, int screenY, int screenWidth, int screenHeight, int screenCharWidth, int screenCharHeight){

		chars = new Array<SolidFontCharData>();
		text = text.toUpperCase();

		int textureCharWidth = textureWidth / textureCharsPerColumn;
		int textureCharHeight = textureHeight / textureCharsPerRow;

		int screenCharsPerColumn = screenWidth / screenCharWidth;
		int screenCharsPerRow = screenHeight / screenCharHeight;

		int length = text.length();

		if (S3Constans.SCREEN_DEBUG){
			S3Log.log("SolidFont::calcTexCoords",
					  " length: " + length + " textureWidth: " + textureWidth + " textureHeight: " + textureHeight + " textureCharWidth: " + textureCharWidth + " charHeight: " + textureCharHeight,
					  1);
			S3Log.log("SolidFont::calcTexCoords",
					  " screenCharsPerColumn: " + screenCharsPerColumn + " screenCharsPerRow: " + screenCharsPerRow + " screenCharWidth: " + screenCharWidth + " screenCharHeight: " + screenCharHeight,
					  1);
		}
		float x = 0;
		float y = 0;

		for (int i = 0; i < text.length(); ++i){

			//
			// Texture pos
			//
			char cha = text.charAt(i);
			int pos = charset.indexOf(cha);

			pos = pos * textureCharWidth;
			x = pos % textureWidth;
			y = pos / textureWidth * textureCharHeight;
			if (x > textureWidth - textureCharWidth){
				x = 0;
				y = y + textureCharHeight;
			}

			float u0 = (float) x / textureWidth;
			float u1 = ((float) (x + textureCharWidth - 1) / textureWidth);
			float v0 = ((float) y / textureHeight);
			float v1 = ((float) (y + textureCharHeight - 1) / textureHeight);

			//
			// S3Screen pos
			//
			int scrPos = i;
			int scrX = screenX + ((scrPos % screenCharsPerColumn) * screenCharWidth);
			int scrY = screenY + ((screenCharsPerRow - 1 - (scrPos / screenCharsPerColumn)) * screenCharHeight);

			//
			// Add Char Data
			//
			SolidFontCharData ch = new SolidFontCharData();
			ch.ch = text.charAt(i);
			ch.code = cha;
			ch.x = (int) scrX;
			ch.y = (int) scrY;
			ch.width = screenCharWidth;
			ch.height = screenCharHeight;
			ch.u0 = u0;
			ch.v0 = u1;
			ch.u1 = v0;
			ch.v1 = v1;
			ch.region = new TextureRegion(fontTexture, u0, v0, u1, v1);
			ch.update();

			if (S3Constans.NOTICE){
				S3Log.log("", "i=" + i + " -> " + ch.toString(), 3);
			}
			chars.add(ch);
		}
		textLengh = chars.size;
	}

	/**
	 *
	 */
	public void draw(){

		if (drawFonts > textLengh){
			drawFonts = textLengh;
		}
		spriteBatch.begin();
		for (int i = 0; i < drawFonts; ++i){
			tmpCh = chars.get(i);
			spriteBatch.draw(tmpCh.region, tmpCh.x, tmpCh.y, tmpCh.width, tmpCh.height);
		}
		spriteBatch.end();
	}

	/**
	 * @param drawFonts
	 */
	public void setDrawFonts(int drawFonts){
		this.drawFonts = drawFonts;
	}

	/**
	 * @param deltaTime
	 * @param start
	 * @param end
	 * @param interpolation
	 */
	public void animeSize(float deltaTime, float start, float end, Interpolation interpolation){
		deltaTime = S3Math.fastCos(deltaTime);
		float imp = interpolation.apply(start, end, deltaTime) / 2;
		for (int i = 0; i < textLengh; ++i){
			tmpCh = chars.get(i);
			tmpCh.x = (int) (tmpCh.centerX - imp);
			tmpCh.y = (int) (tmpCh.centerY - imp);
			tmpCh.width = (int) (imp * 2);
			tmpCh.height = (int) (imp * 2);
		}
	}

	/**
	 * @param deltaTime
	 * @param step
	 * @param start
	 * @param end
	 * @param interpolation
	 */
	public void animeWobbingSize(float deltaTime, float step, float start, float end, Interpolation interpolation){
		float imp = 0;
		for (int i = 0; i < textLengh; ++i, deltaTime = deltaTime + step){
			imp = interpolation.apply(start, end, S3Math.fastCos(deltaTime)) / 2;
			tmpCh = chars.get(i);
			tmpCh.x = (int) (tmpCh.centerX - imp);
			tmpCh.y = (int) (tmpCh.centerY - imp);
			tmpCh.width = (int) (imp * 2);
			tmpCh.height = (int) (imp * 2);
		}
	}

	/**
	 * @param deltaTime
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param interpolation
	 */
	public void animePosistion(float deltaTime, float startX, float startY, float endX, float endY, Interpolation interpolation){
		float deltaTimeX = S3Math.fastCos(deltaTime);
		float deltaTimeY = S3Math.fastSin(deltaTime);
		float impX = interpolation.apply(startX, endX, deltaTimeX);
		float impY = interpolation.apply(startY, endY, deltaTimeY);
		for (int i = 0; i < textLengh; ++i){
			tmpCh = chars.get(i);
			tmpCh.x = (int) (tmpCh.centerX - impX);
			tmpCh.y = (int) (tmpCh.centerY - impY);
		}
	}

	/**
	 * @param deltaTime
	 * @param stepX
	 * @param stepY
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param interpolation
	 */
	public void animeWobbingPosistion(float deltaTime, float stepX, float stepY, float startX, float startY, float endX, float endY,
									  Interpolation interpolation){
		float deltaTimeX = deltaTime;
		float deltaTimeY = deltaTime;
		float impX = 0;
		float impY = 0;
		for (int i = 0; i < textLengh; ++i){
			deltaTimeX = deltaTimeX + stepX;
			deltaTimeY = deltaTimeY + stepY;
			impX = interpolation.apply(startX, endX, S3Math.fastCos(deltaTimeX));
			impY = interpolation.apply(startY, endY, S3Math.fastSin(deltaTimeY));
			tmpCh = chars.get(i);
			tmpCh.x = (int) (tmpCh.centerX - impX);
			tmpCh.y = (int) (tmpCh.centerY - impY);
		}
	}

	/**
	 * @param deltaTime
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
	 * @param interpolation
	 */
	public void animeWobbingSizeAndPosistion(float deltaTime, float stepSizeX, float stepSizeY, float stepPosX, float stepPosY, float startSizeX,
											 float startSizeY, float endSizeX, float endSizeY, float startPosX, float startPosY, float endPosX, float endPosY,
											 Interpolation interpolation){

		startSizeX = startSizeX + endSizeX / 2;
		startSizeY = startSizeY + endSizeX / 2;

		float deltaTimeSizeX = deltaTime;
		float deltaTimeSizeY = deltaTime;
		float deltaTimePosX = deltaTime;
		float deltaTimePosY = deltaTime;
		float impSizeX = 0;
		float impSizeY = 0;
		float impPosX = 0;
		float impPosY = 0;
		for (int i = 0; i < textLengh; ++i){
			deltaTimeSizeX = deltaTimeSizeX + stepSizeX;
			deltaTimeSizeY = deltaTimeSizeY + stepSizeY;
			deltaTimePosX = deltaTimePosX + stepPosX;
			deltaTimePosY = deltaTimePosY + stepPosY;
			impSizeX = interpolation.apply(startSizeX, endSizeX, S3Math.fastCos(deltaTimeSizeX));
			impSizeY = interpolation.apply(startSizeY, endSizeY, S3Math.fastSin(deltaTimeSizeY));
			impPosX = interpolation.apply(startPosX, endPosY, S3Math.fastCos(deltaTimePosX));
			impPosY = interpolation.apply(startPosX, endPosY, S3Math.fastSin(deltaTimePosY));
			tmpCh = chars.get(i);
			tmpCh.x = (int) (tmpCh.centerX - impPosX);
			tmpCh.y = (int) (tmpCh.centerY - impPosY);
			tmpCh.width = (int) (impSizeX);
			tmpCh.height = (int) (impSizeY);
		}
	}

	/**
	 * @param deltaTime
	 * @param speed
	 */
	public void animeScrollVertical(float deltaTime, float speed){
		speed = speed * deltaTime * 2;
		for (int i = 0; i < textLengh; ++i){
			tmpCh = chars.get(i);
			tmpCh.y += speed;
		}
	}
}
