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

package mobi.shad.s3lib.gfx.effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import mobi.shad.s3lib.main.S3Math;
import mobi.shad.s3lib.main.S3ResourceManager;

import java.util.ArrayList;

/**
 * Drawing sprite on calculated path in sin/cos function
 */
public class BobsMulti extends AbstractEffect{

	private TextureRegion bobsTexture;
	private String textureFileName = "bobs_c";
	private SpriteBatch spriteBatch;
	private float[] spriteCoords;
	private float speed = 1;
	private float speed2 = 1;
	private float speed3 = 1;
	private int count = 64;
	private int countSteps = 1;
	private float amplitudeAdd = 0;
	private float amplitudeAdd2 = 0;
	private float step = 5;
	private float step2 = 0;
	private float step3 = 0;
	private float multiplierX = 1;
	private float multiplierY = 1;
	private float amplitudeX = 1;
	private float amplitudeY = 1;
	private float multiplierX2 = 0;
	private float multiplierY2 = 0;
	private float amplitudeX2 = 0;
	private float amplitudeY2 = 0;
	private float multiplierX3 = 0;
	private float multiplierY3 = 0;
	private float amplitudeX3 = 0;
	private float amplitudeY3 = 0;
	private float size = 32;
	private float multiplerAdd = 0;
	private float stepAdd = 0;
	private float speedAdd = 0;

	public BobsMulti(){
		init();
	}

	/**
	 * Initialize class
	 */
	@Override
	public final void init(){

		bobsTexture = S3ResourceManager.getTextureRegion(textureFileName);

		if (spriteBatch == null){
			spriteBatch = new SpriteBatch();
		}
		if (spriteCoords == null){
			spriteCoords = new float[1000 * 2 * 25];
		}
	}

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
	 * Calculate sprite position on path
	 */
	@Override
	public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){
		if (isPause){
			return;
		}

		if (spriteCoords == null){
			return;
		}

		final float width = parentActor.getWidth() / 2f;
		final float height = parentActor.getHeight() / 2f;
		final float centerX = parentActor.getX() + width;
		final float centerY = parentActor.getY() + height;

		final float add2Amplitude = amplitudeAdd2 / 1000;
		float add2AmplitudeValue = 0;

		try {
			for (int j = 0; j < countSteps; j++){

				float addAmplitudeSteps = amplitudeAdd * j;
				float addMultiplerSteps = multiplerAdd * j;
				float addStepSteps = stepAdd * j;
				float addSpeedSteps = speedAdd * j;
				int offsetStart = count * j * 2;

				float pos = effectTime * (addSpeedSteps + speed) * 2;
				float pos2 = effectTime * speed2 * 2;
				float pos3 = effectTime * speed3 * 2;

				for (int i = 0, idx = 0; i < count; ++i, idx = idx + 2){

					int stepLocal = (int) (i * step + addStepSteps);
					int step2Local = (int) (i * step2);
					int step3Local = (int) (i * step3);

					float angle = (pos + stepLocal) * S3Math.DIV_PI2_360;
					float angle2 = (pos2 + step2Local) * S3Math.DIV_PI2_360;
					float angle3 = (pos3 + step3Local) * S3Math.DIV_PI2_360;

					add2AmplitudeValue += add2Amplitude;

					spriteCoords[offsetStart + idx] = S3Math.matchSinCosArrayX(
					angle, multiplierX + addMultiplerSteps, multiplierY + addMultiplerSteps,
					(amplitudeX + addAmplitudeSteps + add2AmplitudeValue) * width,
					(amplitudeY + addAmplitudeSteps + add2AmplitudeValue) * height,
					angle2, multiplierX2, multiplierY2, amplitudeX2 * width, amplitudeY2 * height,
					angle3, multiplierX3, multiplierY3, amplitudeX3 * width, amplitudeY3 * height) + centerX;
					spriteCoords[offsetStart + idx + 1] = S3Math.matchSinCosArrayY(
					angle, multiplierX + addMultiplerSteps, multiplierY + addMultiplerSteps,
					(amplitudeX + addAmplitudeSteps + add2AmplitudeValue) * width,
					(amplitudeY + addAmplitudeSteps + add2AmplitudeValue) * height,
					angle2, multiplierX2, multiplierY2, amplitudeX2 * width, amplitudeY2 * height,
					angle3, multiplierX3, multiplierY3, amplitudeX3 * width, amplitudeY3 * height) + centerY;
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void preRender(){
	}

	/**
	 * Drawing sprite
	 * @param batch
	 * @param parentAlpha
	 */
	@Override
	public void render(Batch batch, float parentAlpha){
		if (spriteCoords == null){
			return;
		}

		int countDraw = count * countSteps;
		for (int i = 0, idx = 0; i < countDraw; ++i, idx = idx + 2){
			batch.draw(bobsTexture, spriteCoords[idx], spriteCoords[idx + 1], size, size);
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
	public void getGuiDefinition(final ArrayList<String[]> guiDef){

		guiDef.add(new String[]{"texture", "texture", "TEXTURE_LIST", ""});
		guiDef.add(new String[]{"size", "size", "SPINNER", "1.0"});

		guiDef.add(new String[]{"count", "count", "SPINNER_INT", "100"});

		guiDef.add(new String[]{"countSteps", "countSteps", "SPINNER_INT", "1.0"});
		guiDef.add(new String[]{"amplitudeAdd", "amplitudeAdd", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitudeAdd2", "amplitudeAdd2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"multiplerAdd", "multiplerAdd", "SPINNER", "1.0"});
		guiDef.add(new String[]{"stepAdd", "stepAdd", "SPINNER", "1.0"});
		guiDef.add(new String[]{"speedAdd", "speedAdd", "SPINNER", "1.0"});

		guiDef.add(new String[]{"speed1", "speed1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"step1", "step1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitudeX1", "amplitudeX1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitudeY1", "amplitudeY1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"multiplierX1", "multiplierX1", "SPINNER", "1.0"});
		guiDef.add(new String[]{"multiplierY1", "multiplierY1", "SPINNER", "1.0"});

		guiDef.add(new String[]{"speed2", "speed2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"step2", "step2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitudeX2", "amplitudeX2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitudeY2", "amplitudeY2", "SPINNER", "1.0"});
		guiDef.add(new String[]{"multiplierX2", "multiplierX2", "SPINNER", "0.0"});
		guiDef.add(new String[]{"multiplierY2", "multiplierY2", "SPINNER", "0.0"});

		guiDef.add(new String[]{"speed3", "speed3", "SPINNER", "1.0"});
		guiDef.add(new String[]{"step3", "step3", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitudeX3", "amplitudeX3", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitudeY3", "amplitudeY3", "SPINNER", "1.0"});
		guiDef.add(new String[]{"multiplierX3", "multiplierX3", "SPINNER", "0.0"});
		guiDef.add(new String[]{"multiplierY3", "multiplierY3", "SPINNER", "0.0"});
	}

	/**
	 * Create map of class value
	 *
	 * @param values
	 */
	@Override
	public void getValues(final ArrayMap<String, String> values){

		values.put("texture", textureFileName);

		values.put("size", String.valueOf(size));

		values.put("count", String.valueOf(count));

		values.put("countSteps", String.valueOf(countSteps));
		values.put("amplitudeAdd", String.valueOf(amplitudeAdd));
		values.put("amplitudeAdd2", String.valueOf(amplitudeAdd2));
		values.put("multiplerAdd", String.valueOf(multiplerAdd));
		values.put("stepAdd", String.valueOf(stepAdd));
		values.put("speedAdd", String.valueOf(speedAdd));

		values.put("speed1", String.valueOf(speed));
		values.put("step1", String.valueOf(step));
		values.put("amplitudeX1", String.valueOf(amplitudeX));
		values.put("amplitudeY1", String.valueOf(amplitudeY));
		values.put("multiplierX1", String.valueOf(multiplierX));
		values.put("multiplierY1", String.valueOf(multiplierY));

		values.put("speed2", String.valueOf(speed2));
		values.put("step2", String.valueOf(step2));
		values.put("amplitudeX2", String.valueOf(amplitudeX2));
		values.put("amplitudeY2", String.valueOf(amplitudeY2));
		values.put("multiplierX2", String.valueOf(multiplierX2));
		values.put("multiplierY2", String.valueOf(multiplierY2));

		values.put("speed3", String.valueOf(speed3));
		values.put("step3", String.valueOf(step3));
		values.put("amplitudeX3", String.valueOf(amplitudeX3));
		values.put("amplitudeY3", String.valueOf(amplitudeY3));
		values.put("multiplierX3", String.valueOf(multiplierX3));
		values.put("multiplierY3", String.valueOf(multiplierY3));
	}

	/**
	 * Update effect value, calling on GUI value are changing
	 * @param changeKey
	 * @param values
	 */
	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		textureFileName = values.get("texture");

		count = Integer.parseInt(values.get("count"));

		if (count > 50000){
			count = 50000;
		}
		if (count < 1){
			count = 1;
		}

		countSteps = Integer.parseInt(values.get("countSteps"));
		if (countSteps > 30){
			countSteps = 30;
		}
		if (countSteps < 1){
			countSteps = 1;
		}

		size = Float.parseFloat(values.get("size"));

		amplitudeAdd = Float.parseFloat(values.get("amplitudeAdd"));
		amplitudeAdd2 = Float.parseFloat(values.get("amplitudeAdd2"));
		multiplerAdd = Float.parseFloat(values.get("multiplerAdd"));
		stepAdd = Float.parseFloat(values.get("stepAdd"));
		speedAdd = Float.parseFloat(values.get("speedAdd"));

		speed = Float.parseFloat(values.get("speed1"));
		step = Float.parseFloat(values.get("step1"));
		amplitudeX = Float.parseFloat(values.get("amplitudeX1"));
		amplitudeY = Float.parseFloat(values.get("amplitudeY1"));
		multiplierX = Float.parseFloat(values.get("multiplierX1"));
		multiplierY = Float.parseFloat(values.get("multiplierY1"));

		speed2 = Float.parseFloat(values.get("speed2"));
		step2 = Float.parseFloat(values.get("step2"));
		amplitudeX2 = Float.parseFloat(values.get("amplitudeX2"));
		amplitudeY2 = Float.parseFloat(values.get("amplitudeY2"));
		multiplierX2 = Float.parseFloat(values.get("multiplierX2"));
		multiplierY2 = Float.parseFloat(values.get("multiplierY2"));

		speed3 = Float.parseFloat(values.get("speed3"));
		step3 = Float.parseFloat(values.get("step3"));
		amplitudeX3 = Float.parseFloat(values.get("amplitudeX3"));
		amplitudeY3 = Float.parseFloat(values.get("amplitudeY3"));
		multiplierX3 = Float.parseFloat(values.get("multiplierX3"));
		multiplierY3 = Float.parseFloat(values.get("multiplierY3"));

		if (changeKey.equals("count") || changeKey.equals("countSteps") || changeKey.equals("texture")){
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

		textureFileName = jsonData.getString("texture");

		count = Integer.parseInt(jsonData.getString("count"));

		countSteps = Integer.parseInt(jsonData.getString("countSteps"));
		if (countSteps > 30){
			countSteps = 30;
		}
		if (countSteps < 1){
			countSteps = 1;
		}

		size = Float.parseFloat(jsonData.getString("size"));

		amplitudeAdd = Float.parseFloat(jsonData.getString("amplitudeAdd"));
		amplitudeAdd2 = Float.parseFloat(jsonData.getString("amplitudeAdd2", "0.0"));
		multiplerAdd = Float.parseFloat(jsonData.getString("multiplerAdd"));
		stepAdd = Float.parseFloat(jsonData.getString("stepAdd"));
		speedAdd = Float.parseFloat(jsonData.getString("speedAdd"));

		speed = Float.parseFloat(jsonData.getString("speed1"));
		step = Float.parseFloat(jsonData.getString("step1"));
		amplitudeX = Float.parseFloat(jsonData.getString("amplitudeX1"));
		amplitudeY = Float.parseFloat(jsonData.getString("amplitudeY1"));
		multiplierX = Float.parseFloat(jsonData.getString("multiplierX1"));
		multiplierY = Float.parseFloat(jsonData.getString("multiplierY1"));

		speed2 = Float.parseFloat(jsonData.getString("speed2"));
		step2 = Float.parseFloat(jsonData.getString("step2"));
		amplitudeX2 = Float.parseFloat(jsonData.getString("amplitudeX2"));
		amplitudeY2 = Float.parseFloat(jsonData.getString("amplitudeY2"));
		multiplierX2 = Float.parseFloat(jsonData.getString("multiplierX2"));
		multiplierY2 = Float.parseFloat(jsonData.getString("multiplierY2"));

		speed3 = Float.parseFloat(jsonData.getString("speed3"));
		step3 = Float.parseFloat(jsonData.getString("step3"));
		amplitudeX3 = Float.parseFloat(jsonData.getString("amplitudeX3"));
		amplitudeY3 = Float.parseFloat(jsonData.getString("amplitudeY3"));
		multiplierX3 = Float.parseFloat(jsonData.getString("multiplierX3"));
		multiplierY3 = Float.parseFloat(jsonData.getString("multiplierY3"));
		init();
	}

	/**
	 * Write efeect values to Json
	 * @param json
	 * @param objectWrite
	 */
	@Override
	public void write(Json json, Object objectWrite){

		json.writeValue("texture", textureFileName);
		json.writeValue("size", size);

		json.writeValue("count", count);

		json.writeValue("countSteps", countSteps);
		json.writeValue("amplitudeAdd", amplitudeAdd);
		json.writeValue("amplitudeAdd2", amplitudeAdd2);
		json.writeValue("multiplerAdd", multiplerAdd);
		json.writeValue("stepAdd", stepAdd);
		json.writeValue("speedAdd", speedAdd);

		json.writeValue("speed1", speed);
		json.writeValue("step1", step);
		json.writeValue("amplitudeX1", amplitudeX);
		json.writeValue("amplitudeY1", amplitudeY);
		json.writeValue("multiplierX1", multiplierX);
		json.writeValue("multiplierY1", multiplierY);

		json.writeValue("speed2", speed2);
		json.writeValue("step2", step2);
		json.writeValue("amplitudeX2", amplitudeX2);
		json.writeValue("amplitudeY2", amplitudeY2);
		json.writeValue("multiplierX2", multiplierX2);
		json.writeValue("multiplierY2", multiplierY2);

		json.writeValue("speed3", speed3);
		json.writeValue("step3", step3);
		json.writeValue("amplitudeX3", amplitudeX3);
		json.writeValue("amplitudeY3", amplitudeY3);
		json.writeValue("multiplierX3", multiplierX3);
		json.writeValue("multiplierY3", multiplierY3);
	}
}
