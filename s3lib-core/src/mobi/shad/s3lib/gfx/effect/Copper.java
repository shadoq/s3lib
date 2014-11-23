/*******************************************************************************
 * Copyright 2013
 *
 * Jaroslaw Czub
 * http://shad.mobi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 ******************************************************************************/
package mobi.shad.s3lib.gfx.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.sun.deploy.util.StringUtils;
import mobi.shad.s3lib.gfx.util.GradientUtil;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Math;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Drawing a copper bar on calculated path
 */
public class Copper extends AbstractEffect{

	//
	// Resource
	//
	private int count = 10;
	private int mode = 0;
	private float copperAmplitude = 1f;
	private float copperMultipler = 2.0f;
	private float copperSpeed = 20.0f;
	private float copperStep = 10.0f;
	private float copperAmplitude2 = 1f;
	private float copperMultipler2 = 2.0f;
	private float copperSpeed2 = 20.0f;
	private float copperStep2 = 10.0f;
	private Float[] positionBuffer;
	private Float[] coordsBuffer;
	private int texturePerRow = 2;
	private int texturePerColumn = 32;
	private int textureSizeX = S3Constans.proceduralTextureSizeHight;
	private int textureSizeY = S3Constans.proceduralTextureSizeHight;
	private Pixmap pixmap;
	private Texture copperTexture;
	private Color[] copperCollorsInistde;
	private Color[] copperCollorsOutside;
	private Color[] copperCollorsInistde2;
	private Color[] copperCollorsOutside2;
	private Color colorOutSide = Color.BLACK;
	private Color colorInSide = Color.WHITE;
	private Color colorInSide2 = Color.WHITE;
	private Color colorOutSide2 = Color.BLACK;
	private float positionY = 0;
	private float copperSize = 1;

	/**
	 *
	 */
	public Copper(){
		init();
	}

	/**
	 * Initialize class
	 */
	@Override
	public final void init(){

		Color[] copperCollorsInistdeLoc = new Color[count + 10];
		Color[] copperCollorsOutsideLoc = new Color[count + 10];
		Color[] copperCollorsInistde2Loc = new Color[count + 10];
		Color[] copperCollorsOutside2Loc = new Color[count + 10];
		for (int i = 0; i < count + 10; ++i){
			copperCollorsInistdeLoc[i] = new Color(colorOutSide);
			copperCollorsOutsideLoc[i] = new Color(colorInSide);
			copperCollorsInistde2Loc[i] = new Color(colorInSide2);
			copperCollorsOutside2Loc[i] = new Color(colorOutSide2);
		}

		copperCollorsOutside = copperCollorsInistdeLoc;
		copperCollorsInistde = copperCollorsOutsideLoc;
		copperCollorsInistde2 = copperCollorsInistde2Loc;
		copperCollorsOutside2 = copperCollorsOutside2Loc;

		if (pixmap == null){
			pixmap = new Pixmap(textureSizeX, textureSizeY, Format.RGBA8888);
		}
		if (copperTexture == null){
			copperTexture = new Texture(pixmap);
			copperTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		}
		positionBuffer = new Float[count * 4];
		coordsBuffer = new Float[count * 4];

		int v = 0;
		for (int i = 0; i < count; ++i){
			int offset = i * 4;
			calculateBobsTexture2D(texturePerRow, texturePerColumn, i, coordsBuffer, offset);
		}

		//
		// Generate texture
		//
		for (int i = 0; i < count; ++i){
			calculateCopperBarTexture2D(copperCollorsOutside[i], copperCollorsInistde[i], copperCollorsInistde2[i],
										copperCollorsOutside2[i], texturePerRow, texturePerColumn, i, pixmap,
										textureSizeX, textureSizeY);
		}
		copperTexture.draw(pixmap, 0, 0);
	}

	@Override
	public void start(){
		init();
	}

	/**
	 *
	 */
	@Override
	public void stop(){
	}

	/**
	 * Calculate bar position on path
	 *
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 */
	@Override
	public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){

		if (isPause){
			return;
		}

		final float posX = parentActor.getX();
		final float posY = parentActor.getY();
		final float width = parentActor.getWidth();
		final float height = parentActor.getHeight();
		final float widthDiv2 = parentActor.getWidth() / 2;
		final float heightDiv2 = parentActor.getHeight() / 2;
		final float centerX = posX + widthDiv2;
		final float centerY = posY + heightDiv2;

		float pos = effectTime * copperSpeed;
		float pos2 = effectTime * copperSpeed2;

		float copperWidth = height * copperSize / 100;
		float copperWidth2 = copperWidth / 2;
		int i, offset, step, step2;
		for (
		i = 0, offset = 0, step = 0, step2 = 0; i < count; ++i, offset = offset + 4, step = (int) (step + copperStep),
		step2 = (int) (step2 + copperStep2)
		){

			float angle = (pos + step) / 360 * S3Math.PI2;
			float angle2 = (pos2 + step2) / 360 * S3Math.PI2;
			float y = (int) ((Math.sin(angle * copperMultipler) * copperAmplitude * heightDiv2) + (Math.sin(
			angle2 * copperMultipler2) * copperAmplitude2 * heightDiv2) + centerY - copperWidth2);

			positionBuffer[offset] = posX;
			positionBuffer[offset + 1] = y;
			positionBuffer[offset + 2] = width;
			positionBuffer[offset + 3] = copperWidth;
		}

	}

	/**
	 * Calculate bar UV on texture bitmap
	 *
	 * @param texPerRow
	 * @param texPerColumn
	 * @param index
	 * @param textCoord
	 * @param offset
	 */
	private void calculateBobsTexture2D(int texPerRow, int texPerColumn, int index, Float[] textCoord, int offset){

		float xStep = 1.0f / texPerRow;
		float yStep = 1.0f / texPerColumn;
		float x = index % texPerRow;
		float y = index / texPerRow;
		float xMin = (x * xStep);
		float xMax = xMin + (xStep);
		float yMin = (y * yStep);
		float yMax = yMin + (yStep);

		textCoord[offset] = xMin;
		textCoord[offset + 1] = yMin;
		textCoord[offset + 2] = xMax;
		textCoord[offset + 3] = yMax;
	}

	/**
	 * Generate color bar on the bitmap
	 *
	 * @param colorOutside
	 * @param colorInside
	 * @param texPerRow
	 * @param tex_per_column
	 * @param index
	 * @param localPixmap
	 * @param localSizeX
	 * @param localSizeY
	 */
	private void calculateCopperBarTexture2D(Color colorOutside, Color colorInside, Color colorInside2,
											 Color colorOutside2, int texPerRow, int tex_per_column, int index,
											 Pixmap localPixmap, int localSizeX, int localSizeY){

		float xStep = (float) localSizeX / texPerRow;
		float yStep = (float) localSizeY / tex_per_column;
		float x = index % texPerRow;
		float y = index / texPerRow;
		float xMin = (x * xStep);
		float xMax = xMin + (xStep);
		float yMin = (y * yStep);
		float yMax = yMin + (yStep);

		Color color = Color.BLACK;
		for (int py = (int) yMin; py <= (int) yMax; py++){
			float procent = (py - yMin) / yStep;
			switch (mode){
				default:
					color = GradientUtil.linearGradientColor(colorOutside, colorInside, colorOutside, procent);
					break;
				case 1:
					color = GradientUtil.linearGradientColor(colorInside, colorOutside, procent);
					break;
				case 2:
					color = GradientUtil.linearGradientColor(colorOutside, colorInside, colorOutside2, procent);
					break;
				case 3:
					color =
					GradientUtil.linearGradientColor(colorOutside, colorInside, colorInside2, colorOutside2, procent);
					break;
				case 4:
					if (index % 4 == 0){
						color = GradientUtil.linearGradientColor(Color.BLACK, colorOutside, Color.BLACK, procent);
					} else if (index % 4 == 1){
						color = GradientUtil.linearGradientColor(Color.BLACK, colorInside, Color.BLACK, procent);
					} else if (index % 4 == 2){
						color = GradientUtil.linearGradientColor(Color.BLACK, colorInside2, Color.BLACK, procent);
					} else {
						color = GradientUtil.linearGradientColor(Color.BLACK, colorOutside2, Color.BLACK, procent);
					}
					break;
				case 5:
					color = GradientUtil.getColorPallete(procent, (int) (colorOutside.a * 42));
					break;
				case 6:
					if (procent < 0.5f){
						color = GradientUtil.getColorPallete(procent * 0.5f, (int) (colorOutside.a * 42));
					} else {
						color = GradientUtil.getColorPallete(1f - procent * 0.5f, (int) (colorOutside.a * 42));
					}
					break;
				case 7:
					if (index % 2 == 0){
						color = GradientUtil.getColorPallete(procent, (int) (colorOutside.a * 42));
					} else if (index % 2 == 1){
						color = GradientUtil.getColorPallete(procent, (int) (colorOutside.a * 42) + 2);
					}
					break;
				case 8:
					if (index % 4 == 0){
						color = GradientUtil.getColorPallete(procent, (int) (colorOutside.a * 42));
					} else if (index % 4 == 1){
						color = GradientUtil.getColorPallete(procent, (int) (colorOutside.a * 42) + 1);
					} else if (index % 4 == 2){
						color = GradientUtil.getColorPallete(procent, (int) (colorOutside.a * 42) + 2);
					} else if (index % 4 == 3){
						color = GradientUtil.getColorPallete(procent, (int) (colorOutside.a * 42) + 3);
					}
					break;


			}
			int pixelColor = Color.rgba8888(color.r, color.g, color.b, color.a);
			for (int px = (int) xMin; px <= (int) xMax; px++){
				localPixmap.drawPixel(px, py, pixelColor);
			}
		}
	}

	@Override
	public void preRender(){
	}

	/**
	 * Drawing bar
	 *
	 * @param batch
	 * @param parentAlpha
	 */
	@Override
	public void render(Batch batch, float parentAlpha){

		int offset, i;
		for (i = 0, offset = 0; i < count; ++i, offset = offset + 4){
			batch.draw(copperTexture, positionBuffer[offset], positionBuffer[offset + 1], positionBuffer[offset + 2],
					   positionBuffer[offset + 3], coordsBuffer[offset], coordsBuffer[offset + 1],
					   coordsBuffer[offset + 2], coordsBuffer[offset + 3]);
		}

	}

	@Override
	public void postRender(){
	}

	/**
	 * Create definition to draw gui from
	 *
	 * @param guiDef
	 */
	@Override
	public void getGuiDefinition(ArrayList<String[]> guiDef){

		guiDef.add(new String[]{"count", "count", "SPINNER_INT", "100"});

		guiDef.add(new String[]{"size", "size", "SPINNER", "1.0"});
		guiDef.add(new String[]{"positionY", "positionY", "SPINNER", "1.0"});

		guiDef.add(new String[]{"speed1", "speed1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"step1", "step1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitude1", "amplitude1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"multiplier1", "multiplier1", "SPINNER", "1.0"});

		guiDef.add(new String[]{"speed2", "speed2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"step2", "step2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitude2", "amplitude2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"multiplier2", "multiplier2", "SPINNER", "1.0"});


		String[] colorModeItems =
		{S3Lang.get("gradient_2a"), S3Lang.get("gradient_2b"), S3Lang.get("gradient_3"), S3Lang.get(
		"gradient_4"), S3Lang.get("4_Black_Color"), S3Lang.get("Alpha_Palette"), S3Lang.get(
		"Alpha_Palette_2"), S3Lang.get("Alpha_Palette_3"), S3Lang.get("Alpha_Palette_4")};
		guiDef.add(new String[]{"colorMode", "colorMode", "LIST", S3Lang.get("gradient_2a"), StringUtils.join(
		Arrays.asList(colorModeItems), ",")});

		guiDef.add(new String[]{"outSideColor", "outSideColor", "COLOR", "000000ff"});
		guiDef.add(new String[]{"inSideColor", "inSideColor", "COLOR", "ffffffff"});
		guiDef.add(new String[]{"inSide2Color", "inSide2Color", "COLOR", "ffffffff"});
		guiDef.add(new String[]{"outSide2Color", "outSide2Color", "COLOR", "000000ff"});
	}

	/**
	 * Create map of class value
	 *
	 * @param values
	 */
	@Override
	public void getValues(ArrayMap<String, String> values){

		values.put("count", String.valueOf(count));
		values.put("size", String.valueOf(copperSize));
		values.put("positionY", String.valueOf(positionY));

		values.put("speed1", String.valueOf(copperSpeed));
		values.put("step1", String.valueOf(copperStep));
		values.put("amplitude1", String.valueOf(copperAmplitude));
		values.put("multiplier1", String.valueOf(copperMultipler));

		values.put("speed2", String.valueOf(copperSpeed2));
		values.put("step2", String.valueOf(copperStep2));
		values.put("amplitude2", String.valueOf(copperAmplitude2));
		values.put("multiplier2", String.valueOf(copperMultipler2));

		values.put("colorMode", String.valueOf(mode));

		values.put("outSideColor", colorOutSide.toString());
		values.put("inSideColor", colorInSide.toString());
		values.put("inSide2Color", colorInSide2.toString());
		values.put("outSide2Color", colorOutSide2.toString());
	}

	/**
	 * Update effect value, calling on GUI value are changing
	 * @param changeKey
	 * @param values
	 */
	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		count = Integer.parseInt(values.get("count"));
		if (count > 100){
			count = 100;
		}
		if (count < 1){
			count = 1;
		}

		copperSize = Float.parseFloat(values.get("size"));
		if (copperSize > 10){
			copperSize = 10f;
		}
		if (copperSize < 0.1){
			copperSize = 0.1f;
		}

		positionY = Float.parseFloat(values.get("positionY"));

		copperSpeed = Float.parseFloat(values.get("speed1"));
		copperStep = Float.parseFloat(values.get("step1"));
		copperAmplitude = Float.parseFloat(values.get("amplitude1"));
		copperMultipler = Float.parseFloat(values.get("multiplier1"));

		copperSpeed2 = Float.parseFloat(values.get("speed2"));
		copperStep2 = Float.parseFloat(values.get("step2"));
		copperAmplitude2 = Float.parseFloat(values.get("amplitude2"));
		copperMultipler2 = Float.parseFloat(values.get("multiplier2"));

		if (values.get("colorMode").equals(S3Lang.get("gradient_2a"))){
			mode = 0;
		} else if (values.get("colorMode").equals(S3Lang.get("gradient_2b"))){
			mode = 1;
		} else if (values.get("colorMode").equals(S3Lang.get("gradient_3"))){
			mode = 2;
		} else if (values.get("colorMode").equals(S3Lang.get("gradient_4"))){
			mode = 3;
		} else if (values.get("colorMode").equals(S3Lang.get("4_Black_Color"))){
			mode = 4;
		} else if (values.get("colorMode").equals(S3Lang.get("Alpha_Palette"))){
			mode = 5;
		} else if (values.get("colorMode").equals(S3Lang.get("Alpha_Palette_2"))){
			mode = 6;
		} else if (values.get("colorMode").equals(S3Lang.get("Alpha_Palette_3"))){
			mode = 7;
		} else if (values.get("colorMode").equals(S3Lang.get("Alpha_Palette_4"))){
			mode = 8;
		}

		colorOutSide = Color.valueOf(values.get("outSideColor"));
		colorInSide = Color.valueOf(values.get("inSideColor"));
		colorInSide2 = Color.valueOf(values.get("inSide2Color"));
		colorOutSide2 = Color.valueOf(values.get("outSide2Color"));
		if (
		changeKey.equalsIgnoreCase("colorMode") ||
		changeKey.equalsIgnoreCase("outSideColor") ||
		changeKey.equalsIgnoreCase("inSideColor") ||
		changeKey.equalsIgnoreCase("inSide2Color") ||
		changeKey.equalsIgnoreCase("outSide2Color")
		){
			init();
		}
	}

	/**
	 * Read effect values from Json
	 *
	 * @param json
	 * @param jsonData
	 */
	@Override
	public void read(Json json, JsonValue jsonData){

		count = Integer.parseInt(jsonData.getString("count"));

		copperSize = Float.parseFloat(jsonData.getString("size"));
		positionY = Float.parseFloat(jsonData.getString("positionY"));

		copperSpeed = Float.parseFloat(jsonData.getString("speed1"));
		copperStep = Float.parseFloat(jsonData.getString("step1"));
		copperAmplitude = Float.parseFloat(jsonData.getString("amplitude1"));
		copperMultipler = Float.parseFloat(jsonData.getString("multiplier1"));

		copperSpeed2 = Float.parseFloat(jsonData.getString("speed2"));
		copperStep2 = Float.parseFloat(jsonData.getString("step2"));
		copperAmplitude2 = Float.parseFloat(jsonData.getString("amplitude2"));
		copperMultipler2 = Float.parseFloat(jsonData.getString("multiplier2"));

		mode = Integer.parseInt(jsonData.getString("colorMode"));

		colorOutSide = Color.valueOf(jsonData.getString("outSideColor"));
		colorInSide = Color.valueOf(jsonData.getString("inSideColor"));
		colorInSide2 = Color.valueOf(jsonData.getString("inSide2Color"));
		colorOutSide2 = Color.valueOf(jsonData.getString("outSide2Color"));

		init();
	}

	/**
	 * Write efeect values to Json
	 * @param json
	 * @param objectWrite
	 */
	@Override
	public void write(Json json, Object objectWrite){
		json.writeValue("count", count);

		json.writeValue("size", copperSize);
		json.writeValue("positionY", positionY);

		json.writeValue("speed1", copperSpeed);
		json.writeValue("step1", copperStep);
		json.writeValue("amplitude1", copperAmplitude);
		json.writeValue("multiplier1", copperMultipler);

		json.writeValue("speed2", copperSpeed2);
		json.writeValue("step2", copperStep2);
		json.writeValue("amplitude2", copperAmplitude2);
		json.writeValue("multiplier2", copperMultipler2);

		json.writeValue("colorMode", mode);
		json.writeValue("outSideColor", colorOutSide.toString());
		json.writeValue("inSideColor", colorInSide.toString());
		json.writeValue("inSide2Color", colorInSide2.toString());
		json.writeValue("outSide2Color", colorOutSide2.toString());
	}

}
