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
package mobi.shad.s3lib.gfx.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import mobi.shad.s3lib.gfx.util.GradientUtil;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3Math;

import java.util.ArrayList;

/**
 * @author Jarek
 */
public class TopBorder extends AbstractEffect{

	private Texture texture = new Texture(1, 1, Pixmap.Format.RGBA4444);
	private int sizeTop = 30;
	private int sizeBorder = 1;
	private Color colorTop = Color.BLACK;
	private int colorBorder = 0;
	private float borderSpeed = 1;
	private float borderAngle = 0;
	private float stepLocal = 0;
	private float angle = 0;
	private Color color;
	private Pixmap pixmap;
	private float borderMultiply = 0;
	private boolean disableChange;

	/**
	 *
	 */
	public TopBorder(){
		init();
	}

	/**
	 * @param random
	 */
	public TopBorder(boolean random){
		this();
	}

	/**
	 *
	 */
	@Override
	public final void init(){

		if (pixmap == null){
			pixmap = new Pixmap(S3Constans.proceduralTextureSize, 1, Pixmap.Format.RGBA8888);
			pixmap.setColor(1, 1, 1, 1);
			pixmap.fill();
			texture = new Texture(pixmap);
		}
	}

	/**
	 *
	 */
	@Override
	public void start(){
	}

	/**
	 *
	 */
	@Override
	public void stop(){
	}

	/**
	 *
	 */
	@Override
	public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){

		if (isPause){
			return;
		}

		float pos = effectTime * borderSpeed * 2;
		for (int i = 0; i < S3Constans.proceduralTextureSize; ++i){
			stepLocal = i * borderMultiply;
			angle = (pos + stepLocal) * S3Math.DIV_PI2_360;
			color = GradientUtil.getColorPallete(0.5f + S3Math.fastSin(angle) * 0.5f, colorBorder);
			pixmap.drawPixel(i, 0, Color.rgba8888(color.r, color.g, color.b, color.a));
		}
		texture.draw(pixmap, 0, 0);
	}

	/**
	 *
	 */
	@Override
	public void preRender(){
	}

	/**
	 *
	 * @param batch
	 * @param parentAlpha
	 */
	@Override
	public void render(Batch batch, float parentAlpha){
		final float startX = parentActor.getX();
		final float startY = parentActor.getY();
		final float width = parentActor.getWidth();
		final float height = parentActor.getHeight();
		final float endX = startX + width;
		final float endY = startY + height;
		final float widthDiv2 = parentActor.getWidth() / 2;
		final float heightDiv2 = parentActor.getHeight() / 2;
		final float centerX = startX + widthDiv2;
		final float centerY = startY + heightDiv2;

		S3Gfx.drawBackground(colorTop, startX, endY - sizeTop, endX, endY);
		S3Gfx.drawBackground(texture, startX, endY - sizeTop + sizeBorder, endX, endY - sizeTop);

		S3Gfx.drawBackground(colorTop, startX, startY, endX, startY + sizeTop);
		S3Gfx.drawBackground(texture, startX, startY + sizeTop - sizeBorder, endX, startY + sizeTop);
	}

	/**
	 *
	 */
	@Override
	public void postRender(){
	}

	/**
	 *
	 * @param guiDef
	 */
	@Override
	public void getGuiDefinition(ArrayList<String[]> guiDef){

		guiDef.add(new String[]{"sizeTop", "sizeTop", "SPINNER_INT", "1.0"});
		guiDef.add(new String[]{"sizeBorder", "sizeBorder", "SPINNER_INT", "1.0"});
		guiDef.add(new String[]{"borderSpeed", "borderSpeed", "SPINNER", "1.0"});
		guiDef.add(new String[]{"borderMultiply", "borderMultiply", "SPINNER", "1.0"});

		guiDef.add(new String[]{"colorBorder", "colorBorder", "SPINNER_INT", "1"});

		guiDef.add(new String[]{"colorTop", "colorTop", "COLOR", "000000ff"});
	}

	/**
	 *
	 * @param values
	 */
	@Override
	public void getValues(ArrayMap<String, String> values){

		values.put("sizeTop", String.valueOf(sizeTop));
		values.put("sizeBorder", String.valueOf(sizeBorder));
		values.put("borderSpeed", String.valueOf(borderSpeed));
		values.put("borderMultiply", String.valueOf(borderMultiply));

		values.put("colorBorder", String.valueOf(colorBorder));

		values.put("colorTop", colorTop.toString());
	}

	/**
	 *
	 * @param changeKey
	 * @param values
	 */
	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		sizeTop = Integer.parseInt(values.get("sizeTop"));
		sizeBorder = Integer.parseInt(values.get("sizeBorder"));
		borderSpeed = Float.parseFloat(values.get("borderSpeed"));
		borderMultiply = Float.parseFloat(values.get("borderMultiply"));

		colorBorder = Integer.parseInt(values.get("colorBorder"));
		if (colorBorder < 0){
			colorBorder = 0;
		}

		colorTop = Color.valueOf(values.get("colorTop"));
	}

	/**
	 *
	 * @param json
	 * @param jsonData
	 */
	@Override
	public void read(Json json, JsonValue jsonData){

		sizeTop = Integer.parseInt(jsonData.getString("sizeTop"));
		sizeBorder = Integer.parseInt(jsonData.getString("sizeBorder"));
		borderSpeed = Float.parseFloat(jsonData.getString("borderSpeed"));
		borderMultiply = Float.parseFloat(jsonData.getString("borderMultiply"));

		colorBorder = Integer.parseInt(jsonData.getString("colorBorder"));
		if (colorBorder < 0){
			colorBorder = 0;
		}

		colorTop = Color.valueOf(jsonData.getString("colorTop"));
	}

	/**
	 *
	 * @param json
	 * @param objectWrite
	 */
	@Override
	public void write(Json json, Object objectWrite){
		json.writeValue("sizeTop", sizeTop);
		json.writeValue("sizeBorder", sizeBorder);
		json.writeValue("borderSpeed", borderSpeed);
		json.writeValue("borderMultiply", borderMultiply);

		json.writeValue("colorBorder", colorBorder);

		json.writeValue("colorTop", colorTop.toString());
	}
}
