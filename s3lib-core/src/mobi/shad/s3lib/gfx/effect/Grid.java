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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Math;
import mobi.shad.s3lib.main.S3ResourceManager;
import mobi.shad.s3lib.main.S3Screen;

import java.util.ArrayList;

/**
 * Drawing an animated bitmap liquid
 */
public class Grid extends AbstractEffect{

	private String textureFileNameEffect = "def256";
	private TextureRegion texture = null;
	private int gridSize = 6;
	private int gridX = 16;
	private int gridY = 16;
	private int count = gridX * gridY;
	private int mode = 0;
	private float angle = 0;
	private float multiplierX = 5;
	private float multiplierY = 3;
	private float amplitudeX = 2;
	private float amplitudeY = 4;
	private float flagSpeed = 2;

	private float[] positions;

	/**
	 *
	 */
	public Grid(){
		init();
	}

	public Grid(String textureFileName){
		textureFileNameEffect = textureFileName;
		init();
	}

	/**
	 * @param random
	 */
	public Grid(boolean random){
		init();
	}

	/**
	 * Initialize effect properties
	 */
	@Override
	public final void init(){

		switch (gridSize){
			default:
				gridX = 1;
				gridY = 1;
				break;
			case 2:
				gridX = 2;
				gridY = 2;
				break;
			case 3:
				gridX = 3;
				gridY = 3;
				break;
			case 4:
				gridX = 4;
				gridY = 4;
				break;
			case 5:
				gridX = 8;
				gridY = 8;
				break;
			case 6:
				gridX = 16;
				gridY = 16;
				break;
			case 7:
				gridX = 32;
				gridY = 32;
				break;
			case 8:
				gridX = 64;
				gridY = 64;
				break;
			case 9:
				gridX = 128;
				gridY = 128;
				break;
		}
		count = gridX * gridY;

		texture = S3ResourceManager.getTextureRegion(textureFileNameEffect);


		positions = new float[count * 8 + 8];

		final float startX = 0;
		final float startY = 0;
		final float width = S3Screen.width;
		final float height = S3Screen.height;

		float xStep = width / (float) gridX;
		float yStep = height / (float) gridY;

		float txStep = (texture.getU2() - texture.getU()) / (float) gridX;
		float tyStep = (texture.getV2() - texture.getV()) / (float) gridY;

		float txStart = texture.getU();
		float txEnd = texture.getU2();

		float tyStart = texture.getV2();
		float tyEnd = texture.getV();

		int index = 0;
		for (int y = 0; y < gridY; ++y){
			for (int x = 0; x < gridX; ++x){

				float xStart = (x) * xStep + startX;
				float xEnd = (x + 1) * xStep + startX;

				float yStart = (y) * yStep + startY;
				float yEnd = (y + 1) * yStep + startY;

				txStart = x * txStep + texture.getU();
				txEnd = (x + 1) * txStep + texture.getU();

				tyStart = texture.getV2() - ((y) * tyStep);
				tyEnd = texture.getV2() - ((y + 1) * tyStep);

				positions[index] = xStart;
				positions[index + 1] = yStart;
				positions[index + 2] = xStart - xEnd;
				positions[index + 3] = yStart - yEnd;
				positions[index + 4] = txStart;
				positions[index + 5] = tyStart;
				positions[index + 6] = txEnd;
				positions[index + 7] = tyEnd;

				index += 8;
			}
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
	 * Update effect value, recalculate quad position
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


		if (flagSpeed > 0){

			angle = angle + S3.osDeltaTime * flagSpeed;

			float xStep = width / (float) gridX;
			float yStep = height / (float) gridY;

			float xStart = 0;
			float xEnd = 0;

			float yStart = 0;
			float yEnd = 0;

			try {

				int index = 0;

				for (int y = 0; y < gridY; ++y){
					for (int x = 0; x < gridX; ++x){

						switch (mode){
							default:
								//
								// Mode 0
								//
							case 0:
								xStart = S3Math.fastSin(angle + (x) * multiplierX) * amplitudeX + ((x) * xStep + startX);
								xEnd = S3Math.fastSin(angle + (x + 1) * multiplierX) * amplitudeX + ((x + 1) * xStep + startX);

								yStart = S3Math.fastCos(angle + (y * multiplierY)) * amplitudeY + ((y) * yStep + startY);
								yEnd = S3Math.fastCos(angle + (y + 1) * multiplierY) * amplitudeY + ((y + 1) * yStep + startY);
								break;
							//
							// Mode 1
							//
							case 1:
								xStart = S3Math.fastCos(angle + x * multiplierX) * amplitudeX + (x) * xStep + startX;
								xEnd = S3Math.fastCos(angle + (x + 1) * multiplierX) * amplitudeX + (x + 1) * xStep + startX;

								yStart = S3Math.fastSin(angle + (y) * multiplierY) * amplitudeY + (y) * yStep + startY;
								yEnd = S3Math.fastSin(angle + (y + 1) * multiplierY) * amplitudeY + (y + 1) * yStep + startY;
								break;
							//
							// Mode 2
							//
							case 2:
								xStart = S3Math.fastCos(angle + x * multiplierX) * amplitudeX + (x) * xStep + startX;
								xEnd = S3Math.fastSin(angle + (x + 1) * multiplierX) * amplitudeX + (x + 1) * xStep + startX;

								yStart = S3Math.fastCos(angle + (y) * multiplierY) * amplitudeY + (y) * yStep + startY;
								yEnd = S3Math.fastSin(angle + (y + 1) * multiplierY) * amplitudeY + (y + 1) * yStep + startY;
								break;
							//
							// Mode 3
							//
							case 3:
								xStart = S3Math.fastSin(angle + x * multiplierX) * amplitudeX + (x) * xStep + startX;
								xEnd = S3Math.fastCos(angle + (x + 1) * multiplierX) * amplitudeX + (x + 1) * xStep + startX;

								yStart = S3Math.fastSin(angle + (y) * multiplierY) * amplitudeY + (y) * yStep + startY;
								yEnd = S3Math.fastCos(angle + (y + 1) * multiplierY) * amplitudeY + (y + 1) * yStep + startY;
								break;
							//
							// Mode 4
							//
							case 4:
								xStart = S3Math.fastSin(angle + x * multiplierX) * amplitudeX + (x) * xStep + startX;
								xEnd = S3Math.fastCos(angle + (x + 1) * multiplierX) * amplitudeX + (x + 1) * xStep + startX;

								yStart = S3Math.fastCos(angle + (y) * multiplierY) * amplitudeY + (y) * yStep + startY;
								yEnd = S3Math.fastSin(angle + (y + 1) * multiplierY) * amplitudeY + (y + 1) * yStep + startY;
								break;
							//
							// Mode 5
							//
							case 5:
								xStart = S3Math.fastCos(angle + x * multiplierX) * amplitudeX + (x) * xStep + startX;
								xEnd = S3Math.fastSin(angle + (x + 1) * multiplierX) * amplitudeX + (x + 1) * xStep + startX;

								yStart = S3Math.fastSin(angle + (y) * multiplierY) * amplitudeY + (y) * yStep + startY;
								yEnd = S3Math.fastCos(angle + (y + 1) * multiplierY) * amplitudeY + (y + 1) * yStep + startY;
								break;
						}

						positions[index] = xStart;
						positions[index + 1] = yStart;
						positions[index + 2] = xEnd - xStart;
						positions[index + 3] = yEnd - yStart;

						index += 8;
					}
				}
			} catch (Exception ex){
				ex.printStackTrace();
			}
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
		int countDraw = gridX * gridY;
		for (int i = 0, idx = 0; i < countDraw; ++i, idx = idx + 8){
			batch.draw(texture.getTexture(), positions[idx], positions[idx + 1], positions[idx + 2], positions[idx + 3], positions[idx + 4], positions[idx + 5],
					   positions[idx + 6], positions[idx + 7]);
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

		guiDef.add(new String[]{"gridMode", "gridMode", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"gridSize", "gridSize", "SPINNER_INT", "1"});

		guiDef.add(new String[]{"texture", "texture", "TEXTURE_LIST", ""});

		guiDef.add(new String[]{"speed", "speed", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitudeX", "amplitudeX", "SPINNER", "1.0"});
		guiDef.add(new String[]{"amplitudeY", "amplitudeY", "SPINNER", "0.0"});
		guiDef.add(new String[]{"multiplierX", "multiplierX", "SPINNER", "0.5"});
		guiDef.add(new String[]{"multiplierY", "multiplierY", "SPINNER", "0.5"});
	}

	/**
	 *
	 * @param values
	 */
	@Override
	public void getValues(ArrayMap<String, String> values){

		values.put("texture", textureFileNameEffect);

		values.put("gridMode", String.valueOf(mode));
		values.put("gridSize", String.valueOf(gridSize));

		values.put("speed", String.valueOf(flagSpeed));
		values.put("amplitudeX", String.valueOf(amplitudeX));
		values.put("amplitudeY", String.valueOf(amplitudeY));
		values.put("multiplierX", String.valueOf(multiplierX));
		values.put("multiplierY", String.valueOf(multiplierY));
	}

	/**
	 *
	 * @param changeKey
	 * @param values
	 */
	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){
		textureFileNameEffect = values.get("texture");

		mode = Integer.parseInt(values.get("gridMode"));

		if (mode > 5){
			mode = 5;
		}
		if (mode < 0){
			mode = 0;
		}

		gridSize = Integer.parseInt(values.get("gridSize"));
		if (gridSize > 9){
			gridSize = 9;
		}
		if (gridSize < 0){
			gridSize = 0;
		}

		flagSpeed = Float.parseFloat(values.get("speed"));
		amplitudeX = Float.parseFloat(values.get("amplitudeX"));
		amplitudeY = Float.parseFloat(values.get("amplitudeY"));
		multiplierX = Float.parseFloat(values.get("multiplierX"));
		multiplierY = Float.parseFloat(values.get("multiplierY"));

		if (changeKey.equals("gridMode") || changeKey.equals("gridSize") || changeKey.equals("texture")){
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
		textureFileNameEffect = jsonData.getString("texture");

		mode = Integer.parseInt(jsonData.getString("gridMode"));

		if (mode > 5){
			mode = 5;
		}
		if (mode < 0){
			mode = 0;
		}

		gridSize = Integer.parseInt(jsonData.getString("gridSize"));
		if (gridSize > 9){
			gridSize = 9;
		}
		if (gridSize < 0){
			gridSize = 0;
		}

		flagSpeed = Float.parseFloat(jsonData.getString("speed"));
		amplitudeX = Float.parseFloat(jsonData.getString("amplitudeX"));
		amplitudeY = Float.parseFloat(jsonData.getString("amplitudeY"));
		multiplierX = Float.parseFloat(jsonData.getString("multiplierX"));
		multiplierY = Float.parseFloat(jsonData.getString("multiplierY"));

		init();
	}

	/**
	 *
	 * @param json
	 * @param objectWrite
	 */
	@Override
	public void write(Json json, Object objectWrite){

		json.writeValue("texture", textureFileNameEffect);

		json.writeValue("gridMode", String.valueOf(mode));
		json.writeValue("gridSize", String.valueOf(gridSize));

		json.writeValue("speed", String.valueOf(flagSpeed));
		json.writeValue("amplitudeX", String.valueOf(amplitudeX));
		json.writeValue("amplitudeY", String.valueOf(amplitudeY));
		json.writeValue("multiplierX", String.valueOf(multiplierX));
		json.writeValue("multiplierY", String.valueOf(multiplierY));
	}
}
