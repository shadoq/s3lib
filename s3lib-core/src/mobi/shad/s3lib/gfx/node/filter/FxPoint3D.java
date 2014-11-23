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
import mobi.shad.s3lib.main.S3Math;

/**
 * @author Jarek
 */
public class FxPoint3D extends Node{

	private int countX;
	private int countY;
	private float speedX1;
	private float speedY1;
	private float stepX1;
	private float stepY1;
	private float amplitudeX1;
	private float amplitudeY1;
	private float speedX2;
	private float speedY2;
	private float stepX2;
	private float stepY2;
	private float amplitudeX2;
	private float amplitudeY2;
	private float speedX3;
	private float speedY3;
	private float stepX3;
	private float stepY3;
	private float amplitudeX3;
	private float amplitudeY3;
	private float countStepX;
	private float countStepY;
	private int objectType;

	public FxPoint3D(){
		this(null, null);
	}

	public FxPoint3D(GuiForm formGui, ChangeListener changeListener){
		super("FxStarField_" + countId, Type.MESH_3D_POINT, formGui, changeListener);
		initData();
		initForm();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}

		disableChange = true;

		formGui.addLabel(S3Lang.get("fxPoint3D"), S3Lang.get("fxPoint3D"), Color.YELLOW);

		//		String[] listOfObjectType={"Pixel", "Line", "Solid"};
		//		formGui.addSelectIndex("objectType", S3Lang.get("objectType"), 0, listOfObjectType, localChangeListener);

		formGui.add("countX", S3Lang.get("countX"), 100, 30, 300, 1, localChangeListener);
		formGui.add("countY", S3Lang.get("countY"), 100, 30, 300, 1, localChangeListener);

		formGui.add("countStepX", S3Lang.get("countStepX"), 1, 0.1f, 10, 0.1f, localChangeListener);
		formGui.add("countStepY", S3Lang.get("countStepY"), 1, 0.1f, 10, 0.1f, localChangeListener);

		formGui.add("speedX1", S3Lang.get("speedX1"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("speedY1", S3Lang.get("speedY1"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("stepX1", S3Lang.get("stepX1"), 1f, -40f, 80, 0.1f, localChangeListener);
		formGui.add("stepY1", S3Lang.get("stepY1"), 1f, -40f, 80, 0.1f, localChangeListener);
		formGui.add("amplitudeX1", S3Lang.get("amplitudeX1"), 1, -10, 10, 0.1f, localChangeListener);
		formGui.add("amplitudeY1", S3Lang.get("amplitudeY1"), 1, -10, 10, 0.1f, localChangeListener);

		formGui.addRandomButton("fxPoint3DRandom1", S3Lang.get("random"), new String[]{"speedX1", "speedY1", "stepX1", "stepY1", "amplitudeX1", "amplitudeY1"},
								localChangeListener);
		formGui.addResetButton("fxPoint3DReset1", S3Lang.get("reset"), new String[]{"speedX1", "speedY1", "stepX1", "stepY1", "amplitudeX1", "amplitudeY1"},
							   localChangeListener);

		formGui.add("speedX2", S3Lang.get("speedX2"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("speedY2", S3Lang.get("speedY2"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("stepX2", S3Lang.get("stepX2"), 1f, -40f, 80, 0.1f, localChangeListener);
		formGui.add("stepY2", S3Lang.get("stepY2"), 1f, -40f, 80, 0.1f, localChangeListener);
		formGui.add("amplitudeX2", S3Lang.get("amplitudeX2"), 1, -10, 10, 0.1f, localChangeListener);
		formGui.add("amplitudeY2", S3Lang.get("amplitudeY2"), 1, -10, 10, 0.1f, localChangeListener);

		formGui.addRandomButton("fxPoint3DRandom2", S3Lang.get("random"), new String[]{"speedX2", "speedY2", "stepX2", "stepY2", "amplitudeX2", "amplitudeY2"},
								localChangeListener);
		formGui.addResetButton("fxPoint3DReset2", S3Lang.get("reset"), new String[]{"speedX2", "speedY2", "stepX2", "stepY2", "amplitudeX2", "amplitudeY2"},
							   localChangeListener);

		formGui.add("speedX3", S3Lang.get("speedX3"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("speedY3", S3Lang.get("speedY3"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("stepX3", S3Lang.get("stepX3"), 1f, -40f, 80, 0.1f, localChangeListener);
		formGui.add("stepY3", S3Lang.get("stepY3"), 1f, -40f, 80, 0.1f, localChangeListener);
		formGui.add("amplitudeX3", S3Lang.get("amplitudeX3"), 1, -10, 10, 0.1f, localChangeListener);
		formGui.add("amplitudeY3", S3Lang.get("amplitudeY3"), 1, -10, 10, 0.1f, localChangeListener);

		formGui.addRandomButton("fxPoint3DRandom3", S3Lang.get("random"), new String[]{"speedX3", "speedY3", "stepX3", "stepY3", "amplitudeX3", "amplitudeY3"},
								localChangeListener);
		formGui.addResetButton("fxPoint3DReset3", S3Lang.get("reset"), new String[]{"speedX3", "speedY3", "stepX3", "stepY3", "amplitudeX3", "amplitudeY3"},
							   localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		data.type = Data.Type.EFFECT_3D;

		if (formGui != null){

			objectType = formGui.getInt("objectType");

			countX = formGui.getInt("countX");
			countY = formGui.getInt("countY");

			countStepX = formGui.getFloat("countStepX") / 10;
			countStepY = formGui.getFloat("countStepY") / 10;

			speedX1 = formGui.getFloat("speedX1");
			speedY1 = formGui.getFloat("speedY1");
			stepX1 = formGui.getFloat("stepX1");
			stepY1 = formGui.getFloat("stepY1");
			amplitudeX1 = formGui.getFloat("amplitudeX1") / 10;
			amplitudeY1 = formGui.getFloat("amplitudeY1") / 10;

			speedX2 = formGui.getFloat("speedX2");
			speedY2 = formGui.getFloat("speedY2");
			stepX2 = formGui.getFloat("stepX2");
			stepY2 = formGui.getFloat("stepY2");
			amplitudeX2 = formGui.getFloat("amplitudeX2") / 10;
			amplitudeY2 = formGui.getFloat("amplitudeY2") / 10;

			speedX3 = formGui.getFloat("speedX3");
			speedY3 = formGui.getFloat("speedY3");
			stepX3 = formGui.getFloat("stepX3");
			stepY3 = formGui.getFloat("stepY3");
			amplitudeX3 = formGui.getFloat("amplitudeX3") / 10;
			amplitudeY3 = formGui.getFloat("amplitudeY3") / 10;
		}

		switch (objectType){
			default:
				data.primitiveType = GL20.GL_POINTS;
				break;
			case 1:
				data.primitiveType = GL20.GL_LINES;
				break;
			case 2:
				data.primitiveType = GL20.GL_TRIANGLES;
				break;
		}

		data.objectMesh = new ObjectMesh(false, true, false);
		data.objectMesh.useIndicesIndex = false;

		int tmpC = countX * countY;
		for (int i = 0; i < tmpC; i++){
			data.objectMesh.addVertex(
			(float) Math.round(data.startX + S3Math.randomize(data.width, data.centerX)),
			(float) Math.round(data.startY + S3Math.randomize(data.height, data.centerY)),
			(float) Math.round(data.startY + S3Math.randomize(data.height, data.centerX)));
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

		int offset = 0;

		float z = 0;
		float angleX = 0;
		float angleY = 0;
		float angleX2 = 0;
		float angleY2 = 0;
		float angleX3 = 0;
		float angleY3 = 0;

		float angleXStart = effectTime * speedX1;
		float angleYStart = effectTime * speedY1;

		float angleX2Start = effectTime * speedX2;
		float angleY2Start = effectTime * speedY2;

		float angleX3Start = effectTime * speedX3;
		float angleY3Start = effectTime * speedY3;

		float startX = -(countX / 2 * countStepX);
		float startY = -(countY / 2 * countStepY);

		for (int x = 0; x < countX; x++){

			angleX = (angleXStart + x * stepX1) * S3Math.DIV_PI2_360;
			angleX2 = (angleX2Start + x * stepX2) * S3Math.DIV_PI2_360;
			angleX3 = (angleX3Start + x * stepX3) * S3Math.DIV_PI2_360;

			offset = x * countY;
			for (int y = 0; y < countY; y++){
				angleY = (angleYStart + y * stepY1) * S3Math.DIV_PI2_360;
				angleY2 = (angleY2Start + y * stepY2) * S3Math.DIV_PI2_360;
				angleY3 = (angleY3Start + y * stepY3) * S3Math.DIV_PI2_360;
				z = S3Math.matchSinCos3D(
				angleX, angleY, amplitudeX1, amplitudeY1,
				angleX2, angleY2, amplitudeX2, amplitudeY2,
				angleX3, angleY3, amplitudeX3, amplitudeY3);

				data.objectMesh.setVertex(offset + y, startX + x * countStepX, z, startY + y * countStepY);
			}
		}
	}
}
