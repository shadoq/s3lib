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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.widget.ColorBrowser;
import mobi.shad.s3lib.gui.widget.FileBrowser;
import mobi.shad.s3lib.gui.widget.ImageList;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class S3GuiWidgetTest extends S3App{

	private List list = null;

	@Override
	public void initalize(){

		ChangeListener changeListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				S3Log.log("S3GuiWidgetTest::changeListener()", "event: " + event.toString() + " actor: " + actor.toString(), 3);
			}
		};


		ImageList imageList = new ImageList();
		imageList.show(new String[]{"texture/texture_1.jpg", "texture/texture_5.jpg"}, changeListener);

		FileBrowser fileBrowser = new FileBrowser();
		fileBrowser.show();

		//  ScrollImageButton widgetScrollImageButton=new ScrollImageButton();
		//  widgetScrollImageButton.show(SceneManager.getEffectButton(), false, 2, 48, changeListener);
		//
		//  ScrollBoxImageButton widgetScrollIBoxmageButton=new ScrollBoxImageButton();
		//  widgetScrollIBoxmageButton.show(SceneManager.getEffectButton(), 3, 200, 200, 48, 2, changeListener);
		//  widgetScrollIBoxmageButton.setValue(1);

		ColorBrowser colorBrowser = new ColorBrowser();
		colorBrowser.show();

		Window window = GuiResource.window("Dialog", "window");
		window.setMovable(false);
		window.defaults().align(Align.top).pad(10);

		window.row();
		window.add(imageList.getTable()).width(S3Constans.gridX2);
		//  window.add(widgetScrollImageButton.getTable()).width(S3Constans.gridX2);
		window.add(fileBrowser.getTable("texture", "texture")).width(S3Constans.gridX2);
		//  window.add(widgetScrollIBoxmageButton.getTable()).width(S3Constans.gridX2);

		window.row();
		window.add(colorBrowser.getTable()).width(S3Constans.gridX2);

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
