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

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;
import mobi.shad.s3lib.gfx.g3d.simpleobject.Sphere;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class G3DModelTest extends S3App{

	private Camera perspectiveCamera;
	private S3Mesh s3Mesh;
	private Model model;
	private ModelInstance modelInstance;
	private ModelBatch renderBatch;
	private InputMultiplexer inputMultiplexer = null;
	private float angleY = 0f;
	private float angleX = 0f;
	private float touchStartX = 0;
	private float touchStartY = 0;

	@Override
	public void initalize(){

		perspectiveCamera = new PerspectiveCamera(45, S3Screen.width, S3Screen.height);
		perspectiveCamera.position.set(5, 5, 5);
		perspectiveCamera.lookAt(0, 0, 0);
		perspectiveCamera.update();

		//	ModelBuilder mb=new ModelBuilder();
		//	model=mb.createSphere(5, 5, 5, 5, 5, new Material(ColorAttribute.createDiffuse(Color.WHITE),TextureAttribute.createDiffuse(S3ResourceManager.getTexture("texture/def256.jpg", 512))), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
		model = Sphere.sphere(2, 2, 2, 5, 5)
					  .getModel(new Material(ColorAttribute.createDiffuse(Color.WHITE),
											 TextureAttribute.createDiffuse(S3ResourceManager.getTexture("texture/def256.jpg", 512))));
		modelInstance = new ModelInstance(model);
		renderBatch = new ModelBatch();
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		g.clear(0.2f, 0.0f, 0.0f);
		renderBatch.begin(perspectiveCamera);
		renderBatch.render(modelInstance);
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

		perspectiveCamera.rotateAround(new Vector3(), Vector3.X, deltaX);
		perspectiveCamera.rotateAround(new Vector3(), Vector3.Y, deltaY);
		perspectiveCamera.update();

		touchStartX = x;
		touchStartY = y;
	}
}
