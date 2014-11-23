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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;

/**
 * @author Jarek
 */
public class S3GuiWidget2Test extends S3App{

	private List list = null;

	@Override
	public void initalize(){

		Window window = GuiResource.window("Dialog", "window");
		window.setMovable(false);
		window.defaults().align(Align.top).pad(10);

		window.pack();
		S3.stage.addActor(window);
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}
}
