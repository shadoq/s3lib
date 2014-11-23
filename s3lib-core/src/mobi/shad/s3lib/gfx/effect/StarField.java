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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3Math;
import mobi.shad.s3lib.main.S3Screen;

import java.util.ArrayList;

/**
 * @author Jarek
 */
public class StarField extends AbstractEffect{

	private float[] spriteCoords;
	private float[] starSpeed;
	private float screenCenterX = 0.5f;
	private float screenCenterY = 0.5f;
	private int count = 400;
	private float speed = 1;
	private boolean disableChange;

	/**
	 *
	 */
	public StarField(){
		init();
	}

	/**
	 * @param random
	 */
	public StarField(boolean random){
		init();
	}

	/**
	 *
	 */
	@Override
	public final void init(){

		spriteCoords = new float[count * 2 + 2];
		starSpeed = new float[count + 2];

		for (int i = 0; i < count; i++){
			spriteCoords[i * 2] = (float) Math.round(S3Math.randomize(S3Screen.width, S3Screen.centerX));
			spriteCoords[i * 2 + 1] = (float) Math.round(S3Math.randomize(S3Screen.height, S3Screen.centerY));
			starSpeed[i] = S3Math.randomize(1, 0);
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

		final float startCenterX = startX + width * screenCenterX;
		final float startCenterY = startY + height * screenCenterY;

		float x, y, factor;

		for (int i = 0, idx = 0; i < count; ++i, idx = idx + 2){

			factor = starSpeed[i] * S3.osDeltaTime * speed;

			x = spriteCoords[idx];
			y = spriteCoords[idx + 1];

			if (x < startX || y < startY || x > endX || y > endY){
				x = (float) Math.round(startX + S3Math.randomize(width, startCenterX));
				y = (float) Math.round(startY + S3Math.randomize(height, startCenterY));
			}

			x = x + (x - startCenterX) * factor;
			y = y + (y - startCenterY) * factor;

			spriteCoords[idx] = x;
			spriteCoords[idx + 1] = y;
		}
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
		for (int i = 0, idx = 0; i < count; ++i, idx = idx + 2){
			//			batch.setColor(parentActor.getColor().mul(starSpeed[i]));
			batch.setColor(starSpeed[i], starSpeed[i], starSpeed[i], 1f);
			batch.draw(S3Gfx.whiteTexture, spriteCoords[idx], spriteCoords[idx + 1], 1, 1);
		}
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

		guiDef.add(new String[]{"count", "count", "SPINNER_INT", "100"});

		guiDef.add(new String[]{"speed", "speed", "SPINNER", "1.0"});
		guiDef.add(new String[]{"centerX", "centerX", "SPINNER", "0.5"});
		guiDef.add(new String[]{"centerY", "centerY", "SPINNER", "0.5"});
	}

	/**
	 *
	 * @param values
	 */
	@Override
	public void getValues(ArrayMap<String, String> values){
		values.put("count", String.valueOf(count));

		values.put("speed", String.valueOf(speed));
		values.put("centerX", String.valueOf(screenCenterX));
		values.put("centerY", String.valueOf(screenCenterY));
	}

	/**
	 *
	 * @param changeKey
	 * @param values
	 */
	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){
		count = Integer.parseInt(values.get("count"));

		if (count > 100000){
			count = 100000;
		}
		if (count < 100){
			count = 100;
		}

		if (screenCenterX < -3.0){
			screenCenterX = -3.0f;
		}
		if (screenCenterX > 3.0){
			screenCenterX = 3.0f;
		}
		if (screenCenterY < -3.0){
			screenCenterY = -3.0f;
		}
		if (screenCenterY > 3.0){
			screenCenterY = 3.0f;
		}

		speed = Float.parseFloat(values.get("speed"));
		screenCenterX = Float.parseFloat(values.get("centerX"));
		screenCenterY = Float.parseFloat(values.get("centerY"));

		if (changeKey.equals("count")){
			init();
		}
	}

	/**
	 *
	 * @param json
	 * @param jsonData
	 */
	@Override
	public void read(Json json, JsonValue jsonData){
		count = Integer.parseInt(jsonData.getString("count"));

		speed = Float.parseFloat(jsonData.getString("speed"));
		screenCenterX = Float.parseFloat(jsonData.getString("centerX"));
		screenCenterY = Float.parseFloat(jsonData.getString("centerY"));

		if (count > 100000){
			count = 100000;
		}
		if (count < 100){
			count = 100;
		}

		if (screenCenterX < -3.0){
			screenCenterX = -3.0f;
		}
		if (screenCenterX > 3.0){
			screenCenterX = 3.0f;
		}
		if (screenCenterY < -3.0){
			screenCenterY = -3.0f;
		}
		if (screenCenterY > 3.0){
			screenCenterY = 3.0f;
		}

		init();
	}

	/**
	 *
	 * @param json
	 * @param objectWrite
	 */
	@Override
	public void write(Json json, Object objectWrite){
		json.writeValue("count", count);

		json.writeValue("speed", speed);
		json.writeValue("centerX", screenCenterX);
		json.writeValue("centerY", screenCenterY);
	}
}
