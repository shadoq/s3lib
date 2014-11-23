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
package mobi.shad.s3libTest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import mobi.shad.s3lib.gfx.g3d.simpleobject.VertexCube;
import mobi.shad.s3lib.gfx.g3d.simpleobject.VertexSphere;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3ResourceManager;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class G3DModelInstanceTest extends S3App{

	PerspectiveCamera perspectiveCamera;
	private float angleY = 0f;
	private float angleX = 0f;
	private float touchStartX = 0;
	private float touchStartY = 0;
	private Model model;
	private Model model2;
	private ModelInstance modelInstance;
	private ModelInstance modelInstance2;
	private ModelInstance modelInstance3;
	private ModelInstance modelInstance4;
	private ModelInstance modelInstance5;
	private ModelBatch renderBatch;

	@Override
	public void initalize(){

		perspectiveCamera = new PerspectiveCamera(45, S3Screen.width, S3Screen.height);
		perspectiveCamera.position.set(10f, 10f, 2f);
		perspectiveCamera.lookAt(0, 0, 0);
		perspectiveCamera.update();

		renderBatch = new ModelBatch();
		model = VertexCube.cube(2).getModel();
		//		model=Cube.cube(2).getModel();
		model2 = VertexSphere.sphere(2).getModel();

		//
		// Instance 1
		//
		model.materials.get(0).set(ColorAttribute.createDiffuse(Color.WHITE));
		model.materials.get(0)
					   .set(new TextureAttribute(TextureAttribute.Diffuse, new TextureDescriptor(S3ResourceManager.getTexture("texture/def256.jpg", 512))));
		modelInstance = new ModelInstance(model);

		//
		// Instance 2
		//
		Matrix4 matrix2 = new Matrix4();
		matrix2.translate(-0.5f, 0.0f, 4.0f);
		matrix2.scale(0.5f, 0.5f, 0.5f);
		modelInstance2 = new ModelInstance(model, matrix2);
		Material material = new Material(ColorAttribute.createDiffuse(Color.RED), new TextureAttribute(TextureAttribute.Diffuse, new TextureDescriptor(
		S3ResourceManager.getTexture("texture/def256.jpg", 512))));
		modelInstance2.materials.add(material);

		//
		// Instance 3
		//
		Matrix4 matrix3 = new Matrix4();
		matrix3.translate(3.0f, 0f, -2.5f);
		matrix3.scale(0.7f, 0.7f, 0.7f);
		matrix3.rotate(0.5f, 1.0f, 0.0f, 40);
		modelInstance3 = new ModelInstance(model2, matrix3);
		Material material2 = new Material(ColorAttribute.createDiffuse(Color.ORANGE), new TextureAttribute(TextureAttribute.Diffuse, new TextureDescriptor(
		S3ResourceManager.getTexture("texture/def256.jpg", 512))));
		modelInstance2.materials.add(material2);

		//
		// Instance 4
		//
		ModelBuilder mb = new ModelBuilder();
		model = mb.createSphere(3, 3, 3, 5, 5, new Material(ColorAttribute.createDiffuse(Color.WHITE),
															TextureAttribute.createDiffuse(S3ResourceManager.getTexture("texture/def256.jpg", 512))),
								VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
		Matrix4 matrix4 = new Matrix4();
		matrix4.translate(2.0f, -1f, 2.5f);
		modelInstance4 = new ModelInstance(model, matrix4);

		//
		// Instance 5
		//
		model = mb.createCylinder(2, 2, 2, 5, new Material(ColorAttribute.createDiffuse(Color.WHITE),
														   TextureAttribute.createDiffuse(S3ResourceManager.getTexture("texture/def256.jpg", 512))),
								  VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
		Matrix4 matrix5 = new Matrix4();
		matrix5.translate(-2.0f, 1f, -2.5f);
		modelInstance5 = new ModelInstance(model, matrix5);

	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){

		g.clear();

		renderBatch.begin(perspectiveCamera);
		renderBatch.render(modelInstance);
		renderBatch.render(modelInstance2);
		renderBatch.render(modelInstance3);
		renderBatch.render(modelInstance4);
		renderBatch.render(modelInstance5);
		renderBatch.end();
	}

	@Override
	public void onTouchDown(int x, int y, int button){
		touchStartX = x;
		touchStartY = y;
	}

	@Override
	public void onDrag(int x, int y){
		float deltaX = (x - touchStartX) * 0.5f;
		float deltaY = (y - touchStartX) * 0.01f;

		if (deltaY > 0.02f){
			deltaY = 0;
		}
		perspectiveCamera.rotateAround(new Vector3(), Vector3.X, deltaX);
		perspectiveCamera.rotateAround(new Vector3(), Vector3.Y, deltaY);
		perspectiveCamera.update();

		touchStartX = x;
		touchStartY = y;
	}
}
