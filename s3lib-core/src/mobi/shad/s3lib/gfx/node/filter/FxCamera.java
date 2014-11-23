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
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class FxCamera extends Node{

	private float positionX;
	private float positionY;
	private float positionZ;
	private float lookX;
	private float lookY;
	private float lookZ;
	private float fieldOfView;

	public FxCamera(){
		this(null, null);
	}

	public FxCamera(GuiForm effectData, ChangeListener changeListener){
		super("FxCamera_" + countId, Type.CAMERA, effectData, changeListener);
		initData();
		initForm();
	}

	/**
	 *
	 */
	@Override
	protected void initData(){
		processLocal();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}

		disableChange = true;

		localProcess = true;

		formGui.addLabel(S3Lang.get("fxCamera"), S3Lang.get("fxCamera"), Color.YELLOW);
		formGui.add("positionX", S3Lang.get("positionX"), 5, -10, 10, 0.1f, localChangeListener);
		formGui.add("positionY", S3Lang.get("positionY"), 5, -10, 10, 0.1f, localChangeListener);
		formGui.add("positionZ", S3Lang.get("positionZ"), 5, -10, 10, 0.1f, localChangeListener);

		formGui.add("lookX", S3Lang.get("lookX"), 0, -10, 10, 0.1f, localChangeListener);
		formGui.add("lookY", S3Lang.get("lookY"), 0, -10, 10, 0.1f, localChangeListener);
		formGui.add("lookZ", S3Lang.get("lookZ"), 0, -10, 10, 0.1f, localChangeListener);

		formGui.add("fieldOfView", S3Lang.get("fieldOfView"), 67, 10, 180, 1f, localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		data.type = Data.Type.EFFECT_3D;
		if (formGui != null){
			positionX = formGui.getFloat("positionX");
			positionY = formGui.getFloat("positionY");
			positionZ = formGui.getFloat("positionZ");

			lookX = formGui.getFloat("lookX");
			lookY = formGui.getFloat("lookY");
			lookZ = formGui.getFloat("lookZ");

			fieldOfView = formGui.getFloat("fieldOfView");
		}

		if (data.perspectiveCamera == null){
			data.perspectiveCamera = new PerspectiveCamera(fieldOfView, S3Screen.width, S3Screen.height);
		}
		data.perspectiveCamera.position.set(positionX, positionY, positionZ);
		data.perspectiveCamera.lookAt(lookX, lookY, lookZ);
		data.perspectiveCamera.fieldOfView = fieldOfView;
		data.perspectiveCamera.update();
	}
}
