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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.dialog.Alert;
import mobi.shad.s3lib.gui.dialog.ColorBrowser;
import mobi.shad.s3lib.gui.dialog.Confirm;
import mobi.shad.s3lib.gui.dialog.FileBrowser;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class S3GuiDialogTest extends S3App{

	private List list = null;

	@Override
	public void initalize(){

		//
		// Color Dialog
		//
		final Button colorButton = GuiResource.textButton("ColorDialog", "buttonColorDialog");
		colorButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				S3Log.log("buttonColorDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				ColorBrowser color = new ColorBrowser();
				color.show();
			}
		});

		//
		// Color Dialog
		//
		final Button colorButton2 = GuiResource.textButton("ColorDialog Set", "buttonColorDialogSet");
		colorButton2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				S3Log.log("buttonColorDialogSet:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				ColorBrowser color = new ColorBrowser();
				color.set((float) Math.random(), (float) Math.random(), (float) Math.random());
				color.show();
			}
		});

		//
		// File Dialog
		//
		final Button fileButton = GuiResource.textButton("File Dialog", "buttonFileDialog");
		fileButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				S3Log.log("buttonFileDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				FileBrowser fileDialog = new FileBrowser();
				fileDialog.show("", "sprite", "png");
			}
		});

		//
		// File Dialog (Write Mode)
		//
		final Button fileWriteButton = GuiResource.textButton("File Dialog Write", "buttonFileDialog");
		fileWriteButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				S3Log.log("buttonFileDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				FileBrowser fileDialog = new FileBrowser();
				fileDialog.show("sprite", "sprite", ".xml", "xml");
			}
		});

		//
		// Alert dialog
		//
		final Button alertButton = GuiResource.textButton("Alert Dialog", "alertFileDialog");
		alertButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				S3Log.log("alertFileDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				Alert alertDialog = new Alert();
				alertDialog.show("Taki sobie testowy alert");
			}
		});

		//
		// Confirm dialog
		//
		final Button confirmButton = GuiResource.textButton("Confirm Dialog", "confirmButtonDialog");
		confirmButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				S3Log.log("confirmButtonDialog:click", "click x:" + x + " y:" + y + " event: " + event.toString());
				Confirm confirmDialog = new Confirm();
				confirmDialog.show("Taki sobie testowy confirm dialog");
			}
		});


		Window window = GuiResource.window("Dialog", "window");
		window.setHeight(S3Screen.height);
		window.setWidth(S3Screen.centerX);
		window.defaults().align(Align.top);
		window.row();
		window.add(colorButton);
		window.add(colorButton2);
		window.add(fileButton);
		window.add(fileWriteButton);
		window.add(alertButton);
		window.add(confirmButton);

		window.setMovable(false);
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
