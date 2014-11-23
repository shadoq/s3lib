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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.g3d.simpleobject.ObjectMesh;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3Math;

/**
 * @author Jarek
 */
public class FxSinusLine extends Node{

	private int count;
	private float centerX;
	private float centerY;
	//
	// Line 1
	//
	private float speed;
	private float step;
	private float amplitudeX;
	private float multiplerX;
	private float amplitudeY;
	private float multiplerY;
	//
	// Line 2
	//
	private float speed2;
	private float step2;
	private float amplitudeX2;
	private float amplitudeY2;
	private float multiplerX2;
	private float multiplerY2;

	public FxSinusLine(){
		this(null, null);
	}

	public FxSinusLine(GuiForm formGui, ChangeListener changeListener){
		super("FxSinusLine_" + countId, Type.MESH_2D_POINT, formGui, changeListener);
		initData();
		initForm();
	}

	/**
	 *
	 */
	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}

		disableChange = true;

		formGui.addLabel(S3Lang.get("FxSinusLine"), S3Lang.get("FxSinusLine"), Color.YELLOW);

		formGui.add("count", S3Lang.get("count"), 360, 10, 720, 1, localChangeListener);

		//
		// Line 1
		//
		formGui.add("speed1", S3Lang.get("speed"), 1f, -20, 40, 0.1f, localChangeListener);
		formGui.add("step1", S3Lang.get("step"), 1f, -10f, 20, 0.1f, localChangeListener);
		formGui.add("amplitudeX1", S3Lang.get("amplitude_X1"), 1, -5, 5, 0.1f, localChangeListener);
		formGui.add("amplitudeY1", S3Lang.get("amplitude_Y1"), 1, -5, 5, 0.1f, localChangeListener);
		formGui.add("multiplerX1", S3Lang.get("multipler_X1"), 1, -5, 5, 0.1f, localChangeListener);
		formGui.add("multiplerY1", S3Lang.get("multipler_Y1"), 1, -5, 5, 0.1f, localChangeListener);

		formGui.addRandomButton("FxSinusLineRandom1", S3Lang.get("random"),
								new String[]{"speed1", "step1", "amplitudeX1", "amplitudeY1", "multiplerX1", "multiplerY1"}, localChangeListener);
		formGui.addResetButton("FxSinusLineReset1", S3Lang.get("reset"),
							   new String[]{"speed1", "step1", "amplitudeX1", "amplitudeY1", "multiplerX1", "multiplerY1"}, localChangeListener);

		//
		// Line 2
		//
		formGui.add("speed2", S3Lang.get("speed_2"), 1f, -20, 40, 0.1f, localChangeListener);
		formGui.add("step2", S3Lang.get("step_2"), 5, -10f, 20, 0.1f, localChangeListener);
		formGui.add("amplitudeX2", S3Lang.get("amplitude_X2"), 1, -5, 5, 0.1f, localChangeListener);
		formGui.add("amplitudeY2", S3Lang.get("amplitude_Y2"), 1, -5, 5, 0.1f, localChangeListener);
		formGui.add("multiplerX2", S3Lang.get("multipler_X2"), 1, -5, 5, 0.1f, localChangeListener);
		formGui.add("multiplerY2", S3Lang.get("multipler_Y2"), 1, -5, 5, 0.1f, localChangeListener);

		formGui.addRandomButton("FxSinusLineRandom2", S3Lang.get("random"),
								new String[]{"speed2", "step2", "amplitudeX2", "amplitudeY2", "multiplerX2", "multiplerY2"}, localChangeListener);
		formGui.addResetButton("FxSinusLineReset2", S3Lang.get("reset"),
							   new String[]{"speed2", "step2", "amplitudeX2", "amplitudeY2", "multiplerX2", "multiplerY2"}, localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		if (formGui != null){
			count = formGui.getInt("count");
			speed = formGui.getFloat("speed1");

			step = formGui.getFloat("step1");
			amplitudeX = formGui.getFloat("amplitudeX1") * data.width / 5.0f;
			amplitudeY = formGui.getFloat("amplitudeY1") * data.height / 5.0f;
			multiplerX = formGui.getFloat("multiplerX1");
			multiplerY = formGui.getFloat("multiplerY1");

			speed2 = formGui.getFloat("speed2");
			step2 = formGui.getFloat("step2");
			amplitudeX2 = formGui.getFloat("amplitudeX2") * data.width / 5.0f;
			amplitudeY2 = formGui.getFloat("amplitudeY2") * data.height / 5.0f;
			multiplerX2 = formGui.getFloat("multiplerX2");
			multiplerY2 = formGui.getFloat("multiplerY2");
		}

		data.type = Data.Type.EFFECT_2D;
		data.primitiveType = GL20.GL_LINES;
		data.objectMesh = new ObjectMesh(false, true, false);
		data.objectMesh.useIndicesIndex = false;

		for (int i = 0; i < count; i++){
			data.objectMesh.addVertex(0, 0);
		}

		S3Log.log("processLocal", data.objectMesh.toString());
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

		float angle = 0;
		float angle2 = 0;
		float angle3 = 0;

		float pos = effectTime * speed * 2;
		float pos2 = effectTime * speed2 * 2;

		for (int i = 0; i < count; i += 2){

			angle = (pos + i * step) * S3Math.DIV_PI2_360;
			angle2 = (pos2 + i * step2) * S3Math.DIV_PI2_360 + S3Math.PI1_2;

			float x = S3Math.matchSinCosArrayX(angle, multiplerX, multiplerY, amplitudeX, amplitudeY) + data.centerX;
			float y = S3Math.matchSinCosArrayY(angle, multiplerX, multiplerY, amplitudeX, amplitudeY) + data.centerY;
			data.objectMesh.setVertex(i, x, y);

			x = S3Math.matchSinCosArrayX(angle2, multiplerX2, multiplerY2, amplitudeX2, amplitudeY2) + data.centerX;
			y = S3Math.matchSinCosArrayY(angle2, multiplerX2, multiplerY2, amplitudeX2, amplitudeY2) + data.centerY;
			data.objectMesh.setVertex(i + 1, x, y);
		}
	}
}
