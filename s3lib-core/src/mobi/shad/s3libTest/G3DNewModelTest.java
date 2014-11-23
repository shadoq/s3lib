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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3File;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3ResourceManager;

/**
 * @author Jarek
 */
public class G3DNewModelTest extends S3App{

	PerspectiveCamera cam;
	ModelBatch modelBatch;
	Model model;
	ModelInstance instance;
	ShapeRenderer shapeRenderer;
	float touchStartX = 0;
	float touchStartY = 0;
	Environment lights;

	@Override
	public void initalize(){

		lights = new Environment();
		lights.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.0f, 0.0f, 0.0f, 1.f));
		lights.add(new DirectionalLight().set(0.0f, 0.3f, 0.3f, -1f, -.8f, -.2f));
		lights.add(new PointLight().set(1f, 0f, 0f, 2f, 2f, 2f, 20f));
		lights.add(new PointLight().set(0f, 0f, 1f, -2f, 2f, 2f, 15f));
		lights.add(new PointLight().set(0f, 1f, 0f, 0f, -2f, -2f, 7f));

		final ObjLoader objLoader = new ObjLoader();
		model = objLoader.loadObj(S3File.getFileHandle("g3d/cube.obj"));

		model.materials.first().clear();
		model.materials.first()
					   .set(ColorAttribute.createDiffuse(Color.LIGHT_GRAY),
							TextureAttribute.createDiffuse(S3ResourceManager.getTexture("texture/def256.jpg", 512)));

		instance = new ModelInstance(model);
		modelBatch = new ModelBatch();
		shapeRenderer = new ShapeRenderer();

		cam = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(2f, 2f, 2f);
		cam.direction.set(-1, -1, -1);
		cam.near = 0.1f;
		cam.far = 300f;
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		cam.update();
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.line(0, 0, 0, 100, 0, 0);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.line(0, 0, 0, 0, 100, 0);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.line(0, 0, 0, 0, 0, 100);
		shapeRenderer.end();

		modelBatch.begin(cam);
		modelBatch.render(instance, lights);
		modelBatch.end();
	}

	@Override
	public void dispose(){
		model.dispose();
		modelBatch.dispose();
	}
}
