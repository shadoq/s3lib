/*******************************************************************************
 * Copyright 2014
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

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import mobi.shad.s3lib.gfx.g3d.shaders.BaseShader;
import mobi.shad.s3lib.gfx.g3d.shaders.ShaderToyShader;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class G3DShaderToyTest extends S3App{

	protected float start;
	protected float duration = 60;
	protected float localTime = 0;
	protected float sceneTime = 0;
	protected float endTime = 0;
	protected float procent = 0;
	private String textureName = "texture/def256.png";
	private String shaderName = "";
	private Texture texture;
	private BaseShader shader;

	@Override
	public void initalize(){
		createResuorce();
	}

	@Override
	public void update(){
		//
		// Effect Time
		//
		localTime = localTime + S3.osDeltaTime;
		sceneTime = start + localTime;
		endTime = duration - localTime;
		procent = (float) localTime / duration;

		if (procent > 0.95f){
			localTime = 0;
		}
	}

	@Override
	public void render(S3Gfx g){
		S3.gl.glViewport(0, 0, S3Screen.width, S3Screen.height);
		S3.gl.glClearColor(0.3f, 0.2f, 0.1f, 1.0f);
		S3.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		S3.gl.glEnable(GL20.GL_DEPTH_TEST);
		S3Gfx.drawBackground(texture, shader);
	}

	/**
	 * Tworzy zasoby testowego objektu
	 */
	private void createResuorce(){

		texture = new Texture(S3File.getFileHandle(textureName), true);
		texture.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Linear);

		shader = new ShaderToyShader("page2", 9, false, false, 1);
	}

	@Override
	public void onTouchDown(int x, int y, int button){
		if (shader instanceof ShaderToyShader){
			((ShaderToyShader) shader).setMousePos(x, S3Screen.width - y);
		}
	}
}
