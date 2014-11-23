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
import mobi.shad.s3lib.gfx.g3d.simpleobject.Cube;
import mobi.shad.s3lib.gfx.g3d.simpleobject.Cylinder;
import mobi.shad.s3lib.gfx.g3d.simpleobject.Plane;
import mobi.shad.s3lib.gfx.g3d.simpleobject.Sphere;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxObject3D extends Node{

	private int objectId;
	private int objectType;

	public FxObject3D(){
		this(null, null);
	}

	public FxObject3D(GuiForm formGui, ChangeListener changeListener){
		super("FxObject3D_" + countId, Type.MESH_3D_OBJECT, formGui, changeListener);
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

		formGui.addLabel(S3Lang.get("fxObjecy3D"), S3Lang.get("fxObjecy3D"), Color.YELLOW);

		String[] listOfObjectType = {"Pixel", "Line", "Solid"};
		formGui.addSelectIndex("objectType", S3Lang.get("objectType"), 0, listOfObjectType, localChangeListener);

		String[] listOfObject = {"Cube", "Sphere", "Cylinder", "Plane"};
		formGui.addSelectIndex("objectName", S3Lang.get("objectName"), 0, listOfObject, localChangeListener);

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
			objectId = formGui.getInt("objectName");
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

		switch (objectId){
			default:
				data.objectMesh = Cube.cube(2.0f, 2.0f, 2.0f);
				break;
			case 1:
				data.objectMesh = Sphere.sphere(2.0f, 2.0f, 2.0f, 10f, 10f);
				break;
			case 2:
				data.objectMesh = Cylinder.cylinder(2.0f, 2.0f, 10f, 10f);
				break;
			case 3:
				data.objectMesh = Plane.plane(2.0f, 2.0f);
				break;
		}
	}
}
