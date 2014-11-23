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
import mobi.shad.s3lib.gui.dialog.Editor2d;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class S3GuiEditor2dTest extends S3App{

	private Editor2d editor2d;

	@Override
	public void initalize(){
		editor2d = new Editor2d();
		editor2d.create(S3Screen.centerX - 220, S3Screen.centerY - 120, 440, 240);
		editor2d.show();
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
