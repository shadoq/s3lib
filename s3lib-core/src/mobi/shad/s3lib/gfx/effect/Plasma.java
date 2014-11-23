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
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import mobi.shad.s3lib.gfx.util.GradientUtil;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Math;
import mobi.shad.s3lib.main.S3Screen;

import java.util.ArrayList;

/**
 * Draw texture with procedural generate 4 layer plasma
 */
public class Plasma extends AbstractEffect{

	//
	// Layer 1
	//
	private boolean layer1 = true;
	private float startX1 = 0;
	private float startY1 = 0;
	private float speedX1 = 2.3f;
	private float speedY1 = 1.2f;
	private float divX1 = 8;
	private float divY1 = 17;
	private int speedX1Mode = 0;
	private int speedY1Mode = 0;
	//
	// Layer 2
	//
	private boolean layer2 = true;
	private float startX2 = 0;
	private float startY2 = 0;
	private float speedX2 = 0.4f;
	private float speedY2 = 1.5f;
	private float divX2 = 10;
	private float divY2 = 12;
	private int speedX2Mode = 0;
	private int speedY2Mode = 0;
	//
	// Layer 3
	//
	private boolean layer3 = true;
	private float startX3 = 0;
	private float startY3 = 0;
	private float speedX3 = 0.3f;
	private float speedY3 = 1.1f;
	private float divX3 = 4;
	private float divY3 = 8;
	private int speedX3Mode = 0;
	private int speedY3Mode = 0;
	//
	// Layer 4
	//
	private boolean layer4 = true;
	private float startX4 = 3;
	private float startY4 = 0;
	private float speedX4 = 1.1f;
	private float speedY4 = 0.8f;
	private float divX4 = 9;
	private float divY4 = 6;
	private int speedX4Mode = 0;
	private int speedY4Mode = 0;
	//
	// Color
	//
	private int countLayer = 0;
	private float stepLayer = 0;
	private int colorMode = 9;
	private String colorModename = "";
	private Color col;
	private float color = 0;
	private double xDistance;
	private double yDistance;
	private double distanceToTargetPoint;
	private double xDistance2;
	private double yDistance2;
	private double distanceToTargetPoint2;
	private int sizeX = S3Constans.proceduralTextureSizeLow;
	private int sizeY = S3Constans.proceduralTextureSizeLow;
	private Texture texture;
	private Pixmap pixmap;

	/**
	 *
	 */
	public Plasma(){
		init();
	}

	/**
	 * Initialize class
	 */
	@Override
	public final void init(){

		countLayer = 0;
		if (layer1){
			countLayer++;
		}
		if (layer2){
			countLayer++;
		}
		if (layer3){
			countLayer++;
		}
		if (layer4){
			countLayer++;
		}
		if (countLayer > 0){
			stepLayer = 1.0f / (float) countLayer;
		}
		if (pixmap == null){
			pixmap = new Pixmap(sizeX, sizeY, Format.RGBA8888);
		}
		if (texture == null){
			texture = new Texture(pixmap);
			texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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
	 * Animate plasma texture
	 *
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 * @param isPause
	 */
	@Override
	public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){

		if (isPause){
			return;
		}

		for (int y = 0; y < sizeY; y++){
			for (int x = 0; x < sizeX; x++){

				color = 0.0f;

				if (layer1){
					//
					// Layer 1
					//
					color = (S3Math.fastSin((float) x / divX1 + startX1 + (float) y / divY1 + startY1) + 1.0f) * 0.5f;
				}

				if (layer2){
					//
					// Layer 2
					//
					color =
					color + (S3Math.fastSin((float) x / divX2 + startX2 - (float) y / divY2 + startY2) + 1.0f) * 0.5f;
				}
				if (layer3){

					//
					// Layer 3
					//
					xDistance = x - S3Screen.centerX * (S3Math.fastCos(startX3 / divY3) + 1.0f / 2);
					yDistance = y - S3Screen.centerY * (S3Math.fastSin(startY3 / divY3) + 1.0f / 2);
					distanceToTargetPoint = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
					color = color + (S3Math.fastSin((float) distanceToTargetPoint / divX3 + startX3) + 1.0f) * 0.5f;
				}
				if (layer4){
					//
					// Layer 4
					//
					xDistance2 = x - S3Screen.centerX * (S3Math.fastCos(startX4 / divY4) + 1.0f / 2);
					yDistance2 = y - S3Screen.centerY * (S3Math.fastSin(startY4 / divY4) + 1.0f / 2);
					distanceToTargetPoint2 = Math.sqrt((xDistance2 * xDistance2) + (yDistance2 * yDistance2));
					color = color + (S3Math.fastSin((float) distanceToTargetPoint2 / divX4 + startX4) + 1.0f) * 0.5f;
				}
				color = color * stepLayer;

				col = GradientUtil.getColorPallete(color, colorMode);
				pixmap.drawPixel(x, y, Color.rgba8888(col.r, col.g, col.b, col.a));
			}
		}

		texture.draw(pixmap, 0, 0);


		//
		// Layer 1 Speed
		//
		if (startX1 > sizeX){
			speedX1Mode = 1;
		} else if (startX1 < 0){
			speedX1Mode = 0;
		}
		if (speedX1Mode == 0){
			startX1 = startX1 + S3.graphics.getDeltaTime() * speedX1;
		} else {
			startX1 = startX1 - S3.graphics.getDeltaTime() * speedX1;
		}

		if (startY1 > sizeY){
			speedY1Mode = 1;
		} else if (startY1 < 0){
			speedY1Mode = 0;
		}
		if (speedY1Mode == 0){
			startY1 = startY1 + S3.graphics.getDeltaTime() * speedY1;
		} else {
			startY1 = startY1 + S3.graphics.getDeltaTime() * speedY1;
		}


		//
		// Layer 2 Speed
		//
		if (startX2 > sizeX){
			speedX2Mode = 1;
		} else if (startX2 < 0){
			speedX2Mode = 0;
		}
		if (speedX2Mode == 0){
			startX2 = startX2 + S3.graphics.getDeltaTime() * speedX2;
		} else {
			startX2 = startX2 - S3.graphics.getDeltaTime() * speedX2;
		}

		if (startY2 > sizeY){
			speedY2Mode = 1;
		} else if (startY2 < 0){
			speedY2Mode = 0;
		}
		if (speedY2Mode == 0){
			startY2 = startY2 + S3.graphics.getDeltaTime() * speedY2;
		} else {
			startY2 = startY2 + S3.graphics.getDeltaTime() * speedY2;
		}


		//
		// Layer 3 Speed
		//
		if (startX3 > sizeX){
			speedX3Mode = 1;
		} else if (startX3 < 0){
			speedX3Mode = 0;
		}
		if (speedX3Mode == 0){
			startX3 = startX3 + S3.graphics.getDeltaTime() * speedX3;
		} else {
			startX3 = startX3 - S3.graphics.getDeltaTime() * speedX3;
		}

		if (startY3 > sizeY){
			speedY3Mode = 1;
		} else if (startY3 < 0){
			speedY3Mode = 0;
		}
		if (speedY3Mode == 0){
			startY3 = startY3 + S3.graphics.getDeltaTime() * speedY3;
		} else {
			startY3 = startY3 + S3.graphics.getDeltaTime() * speedY3;
		}


		//
		// Layer 3 Speed
		//
		if (startX4 > sizeX){
			speedX4Mode = 1;
		} else if (startX4 < 0){
			speedX4Mode = 0;
		}
		if (speedX4Mode == 0){
			startX4 = startX4 + S3.graphics.getDeltaTime() * speedX4;
		} else {
			startX4 = startX4 - S3.graphics.getDeltaTime() * speedX4;
		}

		if (startY4 > sizeY){
			speedY4Mode = 1;
		} else if (startY4 < 0){
			speedY4Mode = 0;
		}
		if (speedY4Mode == 0){
			startY4 = startY4 + S3.graphics.getDeltaTime() * speedY4;
		} else {
			startY4 = startY4 + S3.graphics.getDeltaTime() * speedY4;
		}

	}

	/**
	 *
	 */
	@Override
	public void preRender(){
	}

	/**
	 * Drawing plasma texture
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

		batch.draw(texture, startX, startY, width, height);
	}

	/**
	 *
	 */
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

		guiDef.add(new String[]{"colorMode", "colorMode", "SPINNER_INT", "0"});

		guiDef.add(new String[]{"layer1", "layer1", "CHECKBOX", "true"});
		guiDef.add(new String[]{"speedX1", "speedX1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"speedY1", "speedY1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"divX1", "divX1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"divY1", "divY1", "SPINNER", "1.0"});

		guiDef.add(new String[]{"layer2", "layer2", "CHECKBOX", "true"});
		guiDef.add(new String[]{"speedX2", "speedX2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"speedY2", "speedY2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"divX2", "divX2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"divY2", "divY2", "SPINNER", "1.0"});

		guiDef.add(new String[]{"layer3", "layer3", "CHECKBOX", "true"});
		guiDef.add(new String[]{"speedX3", "speedX3", "SPINNER", "1.0"});
		guiDef.add(new String[]{"speedY3", "speedY3", "SPINNER", "1.0"});
		guiDef.add(new String[]{"divX3", "divX3", "SPINNER", "1.0"});
		guiDef.add(new String[]{"divY3", "divY3", "SPINNER", "1.0"});

		guiDef.add(new String[]{"layer4", "layer4", "CHECKBOX", "true"});
		guiDef.add(new String[]{"speedX4", "speedX4", "SPINNER", "1.0"});
		guiDef.add(new String[]{"speedY4", "speedY4", "SPINNER", "1.0"});
		guiDef.add(new String[]{"divX4", "divX4", "SPINNER", "1.0"});
		guiDef.add(new String[]{"divY4", "divY4", "SPINNER", "1.0"});
	}

	/**
	 * Create map of class value
	 *
	 * @param values
	 */
	@Override
	public void getValues(ArrayMap<String, String> values){

		values.put("colorMode", String.valueOf(colorMode));

		values.put("layer1", String.valueOf(layer1));
		values.put("speedX1", String.valueOf(speedX1));
		values.put("speedY1", String.valueOf(speedY1));
		values.put("divX1", String.valueOf(divX1));
		values.put("divY1", String.valueOf(divY1));

		values.put("layer2", String.valueOf(layer2));
		values.put("speedX2", String.valueOf(speedX2));
		values.put("speedY2", String.valueOf(speedY2));
		values.put("divX2", String.valueOf(divX2));
		values.put("divY2", String.valueOf(divY2));

		values.put("layer3", String.valueOf(layer3));
		values.put("speedX3", String.valueOf(speedX3));
		values.put("speedY3", String.valueOf(speedY3));
		values.put("divX3", String.valueOf(divX3));
		values.put("divY3", String.valueOf(divY3));

		values.put("layer4", String.valueOf(layer4));
		values.put("speedX4", String.valueOf(speedX4));
		values.put("speedY4", String.valueOf(speedY4));
		values.put("divX4", String.valueOf(divX4));
		values.put("divY4", String.valueOf(divY4));

	}

	/**
	 * Update effect value, calling on GUI value are changing
	 *
	 * @param changeKey
	 * @param values
	 */
	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		colorMode = Integer.parseInt(values.get("colorMode"));
		if (colorMode > 49){
			colorMode = 49;
		}
		if (colorMode < 0){
			colorMode = 0;
		}

		layer1 = Boolean.parseBoolean(values.get("layer1"));
		speedX1 = Float.parseFloat(values.get("speedX1"));
		speedY1 = Float.parseFloat(values.get("speedY1"));
		divX1 = Float.parseFloat(values.get("divX1"));
		divY1 = Float.parseFloat(values.get("divY1"));

		layer2 = Boolean.parseBoolean(values.get("layer2"));
		speedX2 = Float.parseFloat(values.get("speedX2"));
		speedY2 = Float.parseFloat(values.get("speedY2"));
		divX2 = Float.parseFloat(values.get("divX2"));
		divY2 = Float.parseFloat(values.get("divY2"));

		layer3 = Boolean.parseBoolean(values.get("layer3"));
		speedX3 = Float.parseFloat(values.get("speedX3"));
		speedY3 = Float.parseFloat(values.get("speedY3"));
		divX3 = Float.parseFloat(values.get("divX3"));
		divY3 = Float.parseFloat(values.get("divY3"));

		layer4 = Boolean.parseBoolean(values.get("layer4"));
		speedX4 = Float.parseFloat(values.get("speedX4"));
		speedY4 = Float.parseFloat(values.get("speedY4"));
		divX4 = Float.parseFloat(values.get("divX4"));
		divY4 = Float.parseFloat(values.get("divY4"));

		init();
	}

	/**
	 * Read effect values from Json
	 *
	 * @param json
	 * @param jsonData
	 */
	@Override
	public void read(Json json, JsonValue jsonData){

		colorMode = Integer.parseInt(jsonData.getString("colorMode"));
		if (colorMode > 49){
			colorMode = 49;
		}
		if (colorMode < 0){
			colorMode = 0;
		}

		layer1 = Boolean.parseBoolean(jsonData.getString("layer1"));
		speedX1 = Float.parseFloat(jsonData.getString("speedX1"));
		speedY1 = Float.parseFloat(jsonData.getString("speedY1"));
		divX1 = Float.parseFloat(jsonData.getString("divX1"));
		divY1 = Float.parseFloat(jsonData.getString("divY1"));

		layer2 = Boolean.parseBoolean(jsonData.getString("layer2"));
		speedX2 = Float.parseFloat(jsonData.getString("speedX2"));
		speedY2 = Float.parseFloat(jsonData.getString("speedY2"));
		divX2 = Float.parseFloat(jsonData.getString("divX2"));
		divY2 = Float.parseFloat(jsonData.getString("divY2"));

		layer3 = Boolean.parseBoolean(jsonData.getString("layer3"));
		speedX3 = Float.parseFloat(jsonData.getString("speedX3"));
		speedY3 = Float.parseFloat(jsonData.getString("speedY3"));
		divX3 = Float.parseFloat(jsonData.getString("divX3"));
		divY3 = Float.parseFloat(jsonData.getString("divY3"));

		layer4 = Boolean.parseBoolean(jsonData.getString("layer4"));
		speedX4 = Float.parseFloat(jsonData.getString("speedX4"));
		speedY4 = Float.parseFloat(jsonData.getString("speedY4"));
		divX4 = Float.parseFloat(jsonData.getString("divX4"));
		divY4 = Float.parseFloat(jsonData.getString("divY4"));
		init();
	}

	/**
	 * Write efeect values to Json
	 *
	 * @param json
	 * @param objectWrite
	 */
	@Override
	public void write(Json json, Object objectWrite){

		json.writeValue("colorMode", colorMode);

		json.writeValue("layer1", layer1);
		json.writeValue("speedX1", speedX1);
		json.writeValue("speedY1", speedY1);
		json.writeValue("divX1", divX1);
		json.writeValue("divY1", divY1);

		json.writeValue("layer2", layer2);
		json.writeValue("speedX2", speedX2);
		json.writeValue("speedY2", speedY2);
		json.writeValue("divX2", divX2);
		json.writeValue("divY2", divY2);

		json.writeValue("layer3", layer3);
		json.writeValue("speedX3", speedX3);
		json.writeValue("speedY3", speedY3);
		json.writeValue("divX3", divX3);
		json.writeValue("divY3", divY3);

		json.writeValue("layer4", layer4);
		json.writeValue("speedX4", speedX4);
		json.writeValue("speedY4", speedY4);
		json.writeValue("divX4", divX4);
		json.writeValue("divY4", divY4);
	}
}
