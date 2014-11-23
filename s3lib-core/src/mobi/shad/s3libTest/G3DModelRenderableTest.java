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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;
import mobi.shad.s3lib.gfx.g3d.simpleobject.Cube;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3ResourceManager;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class G3DModelRenderableTest extends S3App{

	private Camera perspectiveCamera;
	private ModelBatch renderBatch;
	private Renderable renderable;
	private float angleY = 0f;
	private float angleX = 0f;
	private float touchStartX = 0;
	private float touchStartY = 0;

	@Override
	public void initalize(){

		perspectiveCamera = new PerspectiveCamera(45, S3Screen.width, S3Screen.height);
		perspectiveCamera.position.set(5, 5, 5);
		perspectiveCamera.lookAt(0, 0, 0);
		perspectiveCamera.rotateAround(new Vector3(), Vector3.X, 50f);
		perspectiveCamera.rotateAround(new Vector3(), Vector3.Y, 40f);
		perspectiveCamera.rotateAround(new Vector3(), Vector3.Z, 30f);
		perspectiveCamera.update();

		renderBatch = new ModelBatch();

		renderable = new Renderable();
		renderable.primitiveType = GL20.GL_TRIANGLES;
		renderable.mesh = Cube.cube(2).getMesh();
		renderable.meshPartOffset = 0;
		renderable.meshPartSize = renderable.mesh.getNumVertices();
		renderable.material = new Material();
		renderable.material.set(ColorAttribute.createDiffuse(Color.WHITE));
		renderable.material.set(TextureAttribute.createDiffuse(S3ResourceManager.getTexture("texture/def256.jpg", 512)));
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		g.clear(0.2f, 0.0f, 0.0f);
		renderBatch.begin(perspectiveCamera);
		renderBatch.render(renderable);
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
