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
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3Math;

/**
 * @author Jarek
 */
public class FxPointSingleNoMul extends Node{

	private int count;
	private float speed;
	private float centerX;
	private float centerY;
	private float amplitudeX;
	private float amplitudeY;
	private float speed2;
	private float amplitudeX2;
	private float amplitudeY2;
	private float speed3;
	private float amplitudeX3;
	private float amplitudeY3;
	private float stepX1;
	private float stepY1;
	private float stepX2;
	private float stepY2;
	private float stepX3;
	private float stepY3;

	public FxPointSingleNoMul(){
		this(null, null);
	}

	public FxPointSingleNoMul(GuiForm formGui, ChangeListener changeListener){
		super("FxStarField_" + countId, Type.MESH_2D_POINT, formGui, changeListener);
		initData();
		initForm();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}

		disableChange = true;

		formGui.addLabel(S3Lang.get("fxPointSingle"), S3Lang.get("fxPointSingle"), Color.YELLOW);

		formGui.add("count", S3Lang.get("count"), 200, 100, 10000, 1, localChangeListener);

		formGui.add("speed1", S3Lang.get("speed"), 1f, -20, 40, 0.1f, localChangeListener);
		formGui.add("stepX1", S3Lang.get("stepX1"), 1f, -10f, 20, 0.1f, localChangeListener);
		formGui.add("stepY1", S3Lang.get("stepY1"), 1f, -10f, 20, 0.1f, localChangeListener);
		formGui.add("amplitudeX1", S3Lang.get("amplitudeX1"), 1, -5, 5, 0.1f, localChangeListener);
		formGui.add("amplitudeY1", S3Lang.get("amplitudeY1"), 1, -5, 5, 0.1f, localChangeListener);

		formGui.addRandomButton("fxPointSinglerandom1", S3Lang.get("random"), new String[]{"speed1", "stepX1", "stepY1", "amplitudeX1", "amplitudeY1"},
								localChangeListener);
		formGui.addResetButton("fxPointSinglereset1", S3Lang.get("reset"), new String[]{"speed1", "stepX1", "stepY1", "amplitudeX1", "amplitudeY1"},
							   localChangeListener);

		formGui.add("speed2", S3Lang.get("speed2"), 1f, -20, 40, 0.1f, localChangeListener);
		formGui.add("stepX2", S3Lang.get("stepX2"), 5, -10f, 20, 0.1f, localChangeListener);
		formGui.add("stepY2", S3Lang.get("stepY2"), 5, -10f, 20, 0.1f, localChangeListener);
		formGui.add("amplitudeX2", S3Lang.get("amplitudeX2"), 0, -5, 5, 0.1f, localChangeListener);
		formGui.add("amplitudeY2", S3Lang.get("amplitudeY2"), 0, -5, 5, 0.1f, localChangeListener);

		formGui.addRandomButton("fxPointSinglerandom2", S3Lang.get("random"), new String[]{"speed2", "stepX2", "stepY2", "amplitudeX2", "amplitudeY2"},
								localChangeListener);
		formGui.addResetButton("fxPointSinglereset2", S3Lang.get("reset"), new String[]{"speed2", "stepX2", "stepY2", "amplitudeX2", "amplitudeY2"},
							   localChangeListener);

		formGui.add("speed3", S3Lang.get("speed3"), 1f, -20, 40, 0.1f, localChangeListener);
		formGui.add("stepX3", S3Lang.get("stepX3"), 10, -10f, 20, 0.1f, localChangeListener);
		formGui.add("stepY3", S3Lang.get("stepY3"), 10, -10f, 20, 0.1f, localChangeListener);
		formGui.add("amplitudeX3", S3Lang.get("amplitudeX3"), 0, -5, 5, 0.1f, localChangeListener);
		formGui.add("amplitudeY3", S3Lang.get("amplitudeY3"), 0, -5, 5, 0.1f, localChangeListener);

		formGui.addRandomButton("fxPointSinglerandom3", S3Lang.get("random"), new String[]{"speed3", "stepX3", "stepY3", "amplitudeX3", "amplitudeY3"},
								localChangeListener);
		formGui.addResetButton("fxPointSinglereset3", S3Lang.get("reset"), new String[]{"speed3", "stepX3", "stepY3", "amplitudeX3", "amplitudeY3"},
							   localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		data.type = Data.Type.EFFECT_2D;
		if (formGui != null){
			count = formGui.getInt("count");

			speed = formGui.getFloat("speed1");
			stepX1 = formGui.getFloat("stepX1") / 5.0f;
			stepY1 = formGui.getFloat("stepY1") / 5.0f;
			amplitudeX = formGui.getFloat("amplitudeX1") * data.width / 5.0f;
			amplitudeY = formGui.getFloat("amplitudeY1") * data.height / 5.0f;

			speed2 = formGui.getFloat("speed2");
			stepX2 = formGui.getFloat("stepX2") / 5.0f;
			stepY2 = formGui.getFloat("stepY2") / 5.0f;
			amplitudeX2 = formGui.getFloat("amplitudeX2") * data.width / 5.0f;
			amplitudeY2 = formGui.getFloat("amplitudeY2") * data.height / 5.0f;

			speed3 = formGui.getFloat("speed3");
			stepX3 = formGui.getFloat("stepX3") / 5.0f;
			stepY3 = formGui.getFloat("stepY3") / 5.0f;
			amplitudeX3 = formGui.getFloat("amplitudeX3") * data.width / 5.0f;
			amplitudeY3 = formGui.getFloat("amplitudeY3") * data.height / 5.0f;
		}

		data.objectMesh = new ObjectMesh(false, true, false);
		data.objectMesh.useIndicesIndex = false;

		for (int i = 0; i < count; i++){
			data.objectMesh.addVertex(
			(float) Math.round(data.startX + S3Math.randomize(data.width, centerX)),
			(float) Math.round(data.startY + S3Math.randomize(data.height, centerY)));
		}

		if (S3Constans.DEBUG){
			S3Log.log("processLocal", data.objectMesh.toString());
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

		float pos = effectTime * speed * 2;
		float pos2 = effectTime * speed2 * 2;
		float pos3 = effectTime * speed3 * 2;

		float angleX1 = 0;
		float angleY1 = 0;
		float angleX2 = 0;
		float angleY2 = 0;
		float angleX3 = 0;
		float angleY3 = 0;

		for (int i = 0; i < count; i++){

			angleX1 = (pos + i * stepX1) * S3Math.DIV_PI2_360;
			angleY1 = (pos + i * stepY1) * S3Math.DIV_PI2_360;
			angleX2 = (pos2 + i * stepX2) * S3Math.DIV_PI2_360;
			angleY2 = (pos2 + i * stepY2) * S3Math.DIV_PI2_360;
			angleX3 = (pos3 + i * stepX3) * S3Math.DIV_PI2_360;
			angleY3 = (pos3 + i * stepY3) * S3Math.DIV_PI2_360;

			float x = S3Math.newSinCosArrayX(
			angleX1, angleY1, amplitudeX, amplitudeY,
			angleX2, angleY2, amplitudeX2, amplitudeY2,
			angleX3, angleY3, amplitudeX3, amplitudeY3) + data.centerX;
			float y = S3Math.newSinCosArrayY(
			amplitudeY,
			amplitudeY2,
			amplitudeY3) + data.centerY;

			data.objectMesh.setVertex(i, x, y);
		}
	}
}
