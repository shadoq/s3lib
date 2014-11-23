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
package mobi.shad.s3lib.gfx.node.filter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.g3d.simpleobject.ObjectMesh;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Math;
import mobi.shad.s3lib.main.S3ResourceManager;

/**
 * @author Jarek
 */
public class FxStarField extends Node{

	protected String starTextureFileName = "sprite/particle0.png";
	private int starFieldType = 0;
	private int mode = 0;
	private int count = 100;
	private float speed = 1.f;
	private float centerX = 0.5f;
	private float centerY = 0.5f;
	private Color starColor = Color.WHITE;
	private float spriteSize = 16f;

	public FxStarField(){
		this(null, null);
	}

	public FxStarField(GuiForm effectData, ChangeListener changeListener){
		super("FxStarField_" + countId, Type.MESH_2D_POINT, effectData, changeListener);
		initData();
		initForm();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}
		disableChange = true;

		formGui.addLabel(S3Lang.get("fxStarField"), S3Lang.get("fxStarField"), Color.YELLOW);

		formGui.addSelectIndex("starFieldType", S3Lang.get("starFieldType"), mode, new String[]{S3Lang.get("Pixel"), S3Lang.get("Sprite")},
							   localChangeListener);
		formGui.addSelectIndex("mode", S3Lang.get("starFieldMode"), mode,
							   new String[]{S3Lang.get("Star"), S3Lang.get("Horizontal"), S3Lang.get("Vertical"), S3Lang.get("toLeft"), S3Lang.get("toRight")},
							   localChangeListener);

		formGui.add("count", S3Lang.get("count"), 100, 50, 3000, 50, localChangeListener);
		formGui.add("speed", S3Lang.get("speed"), 1, -5f, 10, 0.1f, localChangeListener);
		formGui.add("centerX", S3Lang.get("center_X"), 0.5f, 0, 1, 0.1f, localChangeListener);
		formGui.add("centerY", S3Lang.get("center_Y"), 0.5f, 0, 1, 0.1f, localChangeListener);

		formGui.addRandomButton("fxStarFieldrandom", S3Lang.get("random"), new String[]{"count", "speed", "centerX", "centerY"}, localChangeListener);
		formGui.addResetButton("fxStarFieldreset", S3Lang.get("reset"), new String[]{"count", "speed", "centerX", "centerY"}, localChangeListener);

		formGui.addColorSelect("starColor", S3Lang.get("color"), starColor, localChangeListener);
		formGui.add("spriteSize", S3Lang.get("spriteSize"), spriteSize, 1, 40, 1, localChangeListener);
		formGui.addFileBrowser("starTextureFileName", S3Lang.get("starTextureFileName"), starTextureFileName, "sprite", localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		data.type = Data.Type.EFFECT_2D;

		if (formGui != null){
			starFieldType = formGui.getInt("starFieldType");
			mode = formGui.getInt("mode");
			count = formGui.getInt("count");
			speed = formGui.getFloat("speed");

			centerX = data.startX + formGui.getFloat("centerX") * data.width;
			centerY = data.startY + formGui.getFloat("centerY") * data.height;
			starColor = formGui.getColor("starColor");

			spriteSize = formGui.getFloat("spriteSize") * data.width * 0.001f;

			starTextureFileName = formGui.getString("starTextureFileName");
			data.spriteTexture = S3ResourceManager.getTexture(starTextureFileName, 64);
			data.spriteHeight = spriteSize;
			data.spriteWidth = spriteSize;
		}

		data.objectMesh = null;
		data.spritePosition = null;

		switch (starFieldType){

			default:
				data.color = starColor;
				data.objectMesh = new ObjectMesh(false, true, false);
				data.objectMesh.useIndicesIndex = false;

				switch (mode){
					default:
						for (int i = 0; i < count; i++){
							float color = S3Math.randomize(1, 0);
							data.objectMesh.addVertex(
							(float) Math.round(data.startX + S3Math.randomize(data.width, centerX)),
							(float) Math.round(data.startY + S3Math.randomize(data.height, centerY)),
							color * starColor.r, color * starColor.g, color * starColor.b, starColor.a);
						}
						break;
					case 1:
						for (int i = 0; i < count; i++){
							float color = S3Math.randomize(1, 0);
							data.objectMesh.addVertex(
							(float) Math.round(data.startX + S3Math.randomize(data.width, centerX)),
							(i % 2 == 0) ? (float) Math.round(
							data.startY + data.height * 0.5f + S3Math.randomize(data.height * 0.5f, centerY)) : (float) Math.round(
							data.startY + S3Math.randomize(data.height * 0.5f, centerY)),
							color * starColor.r, color * starColor.g, color * starColor.b, starColor.a);
						}
						break;
					case 2:
						for (int i = 0; i < count; i++){
							float color = S3Math.randomize(1, 0);
							data.objectMesh.addVertex(
							(i % 2 == 0) ? (float) Math.round(
							data.startX + data.width * 0.5f + S3Math.randomize(data.width * 0.5f, centerX)) : (float) Math.round(
							data.startX + S3Math.randomize(data.width * 0.5f, centerX)),
							(float) Math.round(data.startY + S3Math.randomize(data.height, centerY)),
							color * starColor.r, color * starColor.g, color * starColor.b, starColor.a);
						}
						break;
				}
				break;
			case 1:
				data.spritePosition = new float[count * 2 + 1];
				data.spriteColor = new float[count * 4 + 1];

				switch (mode){
					default:
						for (int i = 0, j = 0, k = 0; i < count; i++, j += 2, k += 4){
							float color = 0.5f + S3Math.randomize(0.5f, 0);
							data.spritePosition[j] = (float) Math.round(data.startX - spriteSize + S3Math.randomize(data.width + spriteSize, centerX));
							data.spritePosition[j + 1] = (float) Math.round(data.startY - spriteSize + S3Math.randomize(data.height + spriteSize, centerY));

							data.spriteColor[k] = color * starColor.r;
							data.spriteColor[k + 1] = color * starColor.g;
							data.spriteColor[k + 2] = color * starColor.b;
							data.spriteColor[k + 3] = starColor.a;
						}
						break;
					case 1:
						for (int i = 0, j = 0, k = 0; i < count; i++, j += 2, k += 4){
							float color = 0.5f + S3Math.randomize(0.5f, 0);
							data.spritePosition[j] = (float) Math.round(data.startX + S3Math.randomize(data.width, centerX));
							data.spritePosition[j + 1] = (j % 4 == 0) ? (float) Math.round(
							data.startY + data.height * 0.5f + S3Math.randomize(data.height * 0.5f, centerY)) : (float) Math.round(
							data.startY + S3Math.randomize(data.height * 0.5f, centerY));

							data.spriteColor[k] = color * starColor.r;
							data.spriteColor[k + 1] = color * starColor.g;
							data.spriteColor[k + 2] = color * starColor.b;
							data.spriteColor[k + 3] = starColor.a;
						}
						break;
					case 2:
						for (int i = 0, j = 0, k = 0; i < count; i++, j += 2, k += 4){
							float color = 0.5f + S3Math.randomize(0.5f, 0);
							data.spritePosition[j] = (j % 4 == 0) ? (float) Math.round(
							data.startX + data.width * 0.5f + S3Math.randomize(data.width * 0.5f, centerX)) : (float) Math.round(
							data.startX + S3Math.randomize(data.width * 0.5f, centerX));
							data.spritePosition[j + 1] = (float) Math.round(data.startY + S3Math.randomize(data.height, centerY));

							data.spriteColor[k] = color * starColor.r;
							data.spriteColor[k + 1] = color * starColor.g;
							data.spriteColor[k + 2] = color * starColor.b;
							data.spriteColor[k + 3] = starColor.a;
						}
						break;
				}

				break;
		}
	}

	/**
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 * @param isPause
	 */
	@Override
	protected void updateLocal(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){

		float osSpeed = S3.osDeltaTime * speed / 10f;
		float factor = 0;
		float x = 0;
		float y = 0;

		switch (starFieldType){

			default:

				switch (mode){
					default:
						for (int i = 0; i < count; i++){
							factor = (data.objectMesh.getColorR(i) + data.objectMesh.getColorG(i) + data.objectMesh.getColorB(i)) * osSpeed;
							x = data.objectMesh.getVertexX(i);
							y = data.objectMesh.getVertexY(i);
							if (x < data.startX || y < data.startY || x > data.endX || y > data.endY){
								x = (float) Math.round(data.startX + S3Math.randomize(data.width, centerX));
								y = (float) Math.round(data.startY + S3Math.randomize(data.height, centerY));
							}
							x = (float) (x + (x - centerX) * factor);
							y = (float) (y + (y - centerY) * factor);

							data.objectMesh.setVertex(i, x, y);
						}
						break;
					case 1:
						for (int i = 0; i < count; i++){
							factor = (data.objectMesh.getColorR(i) + data.objectMesh.getColorG(i) + data.objectMesh.getColorB(i)) * osSpeed;
							x = data.objectMesh.getVertexX(i);
							y = data.objectMesh.getVertexY(i);
							if (x < data.startX || y < data.startY || x > data.endX || y > data.endY){
								x = (float) Math.round(data.startX + S3Math.randomize(data.width, centerX));
								y = (float) Math.round(data.startY + centerY);
							}
							if (i % 2 == 0){
								y = (float) (y + 100 * factor);
							} else {
								y = (float) (y - 100 * factor);
							}

							data.objectMesh.setVertex(i, x, y);
						}
						break;
					case 2:
						for (int i = 0; i < count; i++){
							factor = (data.objectMesh.getColorR(i) + data.objectMesh.getColorG(i) + data.objectMesh.getColorB(i)) * osSpeed;
							x = data.objectMesh.getVertexX(i);
							y = data.objectMesh.getVertexY(i);
							if (x < data.startX || y < data.startY || x > data.endX || y > data.endY){
								x = (float) Math.round(data.startX + centerX);
								y = (float) Math.round(data.startY + S3Math.randomize(data.height, centerY));
							}
							if (i % 2 == 0){
								x = (float) (x + 100 * factor);
							} else {
								x = (float) (x - 100 * factor);
							}

							data.objectMesh.setVertex(i, x, y);
						}
						break;
					case 3:
						for (int i = 0; i < count; i++){
							factor = (data.objectMesh.getColorR(i) + data.objectMesh.getColorG(i) + data.objectMesh.getColorB(i)) * osSpeed;
							x = data.objectMesh.getVertexX(i);
							y = data.objectMesh.getVertexY(i);
							if (x > data.endX){
								x = (float) Math.round(data.startX);
								y = (float) Math.round(data.startY + S3Math.randomize(data.height, centerY));
							}
							x = (float) (x + 100 * factor);
							data.objectMesh.setVertex(i, x, y);
						}

						break;
					case 4:
						for (int i = 0; i < count; i++){
							factor = (data.objectMesh.getColorR(i) + data.objectMesh.getColorG(i) + data.objectMesh.getColorB(i)) * osSpeed;
							x = data.objectMesh.getVertexX(i);
							y = data.objectMesh.getVertexY(i);
							if (x < data.startX){
								x = (float) Math.round(data.endX);
								y = (float) Math.round(data.startY + S3Math.randomize(data.height, centerY));
							}
							x = (float) (x - 100 * factor);
							data.objectMesh.setVertex(i, x, y);
						}
						break;
				}
				break;
			case 1:

				int size = 0;

				switch (mode){
					default:

						size = count * 2;
						for (int i = 0, j = 0; i < size; i += 2, j += 4){
							factor = (data.spriteColor[j] + data.spriteColor[j + 1] + data.spriteColor[j + 2]) * osSpeed;
							x = data.spritePosition[i];
							y = data.spritePosition[i + 1];
							if (x < data.startX || y < data.startY || x > data.endX || y > data.endY){
								x = (float) Math.round(data.startX + S3Math.randomize(data.width, centerX));
								y = (float) Math.round(data.startY + S3Math.randomize(data.height, centerY));
							}
							x = (float) (x + (x - centerX) * factor);
							y = (float) (y + (y - centerY) * factor);
							data.spritePosition[i] = x;
							data.spritePosition[i + 1] = y;
						}

						break;
					case 1:
						size = count * 2;
						for (int i = 0, j = 0; i < size; i += 2, j += 4){
							factor = (data.spriteColor[j] + data.spriteColor[j + 1] + data.spriteColor[j + 2]) * osSpeed;
							x = data.spritePosition[i];
							y = data.spritePosition[i + 1];
							if (x < data.startX - spriteSize || y < data.startY - spriteSize || x > data.endX + spriteSize || y > data.endY + spriteSize){
								x = (float) Math.round(data.startX - spriteSize + S3Math.randomize(data.width + spriteSize, centerX));
								y = (float) Math.round(data.startY + centerY);
							}
							if (i % 4 == 0){
								y = (float) (y + 100 * factor);
							} else {
								y = (float) (y - 100 * factor);
							}
							data.spritePosition[i] = x;
							data.spritePosition[i + 1] = y;
						}
						break;
					case 2:
						size = count * 2;
						for (int i = 0, j = 0; i < size; i += 2, j += 4){
							factor = (data.spriteColor[j] + data.spriteColor[j + 1] + data.spriteColor[j + 2]) * osSpeed;
							x = data.spritePosition[i];
							y = data.spritePosition[i + 1];
							if (x < data.startX - spriteSize || y < data.startY - spriteSize || x > data.endX + spriteSize || y > data.endY + spriteSize){
								x = (float) Math.round(data.startX + centerX);
								y = (float) Math.round(data.startY - spriteSize + S3Math.randomize(data.height + spriteSize, centerY));
							}
							if (i % 4 == 0){
								x = (float) (x + 100 * factor);
							} else {
								x = (float) (x - 100 * factor);
							}
							data.spritePosition[i] = x;
							data.spritePosition[i + 1] = y;
						}
						break;
					case 3:
						size = count * 2;
						for (int i = 0, j = 0; i < size; i += 2, j += 4){
							factor = (data.spriteColor[j] + data.spriteColor[j + 1] + data.spriteColor[j + 2]) * osSpeed;
							x = data.spritePosition[i];
							y = data.spritePosition[i + 1];
							if (x > data.endX + spriteSize){
								x = (float) Math.round(data.startX - spriteSize);
								y = (float) Math.round(data.startY - spriteSize + S3Math.randomize(data.height + spriteSize, centerY));
							}
							x = (float) (x + 100 * factor);
							data.spritePosition[i] = x;
							data.spritePosition[i + 1] = y;
						}
						break;
					case 4:
						size = count * 2;
						for (int i = 0, j = 0; i < size; i += 2, j += 4){
							factor = (data.spriteColor[j] + data.spriteColor[j + 1] + data.spriteColor[j + 2]) * osSpeed;
							x = data.spritePosition[i];
							y = data.spritePosition[i + 1];
							if (x < data.startX - spriteSize){
								x = (float) Math.round(data.endX + spriteSize);
								y = (float) Math.round(data.startY - spriteSize + S3Math.randomize(data.height + spriteSize, centerY));
							}
							x = (float) (x - 100 * factor);
							data.spritePosition[i] = x;
							data.spritePosition[i + 1] = y;
						}
						break;
				}
				break;
		}
	}
}
