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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.dialog.Alert;
import mobi.shad.s3lib.gui.dialog.ColorBrowser;
import mobi.shad.s3lib.gui.dialog.Confirm;
import mobi.shad.s3lib.gui.dialog.FileBrowser;
import mobi.shad.s3lib.main.*;

import static mobi.shad.s3lib.main.S3.skin;

/**
 * @author Jarek
 */
public class S3GuiAllTest extends S3App{

	private List list = null;

	@Override
	public void initalize(){

		//
		// Color Dialog
		//
		final Button button = GuiResource.textButton("ColorDialog", "buttonColorDialog");
		button.addListener(new ClickListener(){
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
		final Button button2 = GuiResource.textButton("ColorDialog Set", "buttonColorDialogSet");
		button2.addListener(new ClickListener(){
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
				fileDialog.show("scene", "scene");
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


		//
		// Add UI Test - tworzenie elementow testowych
		//
		TextureRegion image = new TextureRegion(new Texture(S3File.getFileHandle("texture/def32.png")));
		TextureRegion image2 = new TextureRegion(new Texture(S3File.getFileHandle("texture/def256.png")));
		TextureRegion image3 = new TextureRegion(new Texture(S3File.getFileHandle("texture/def256.png")));

		final Button buttonTest = GuiResource.textButton("Single", "button-sl");
		final Button buttonTestMulti = GuiResource.textButton("Multi\nLine\nToggle", "button-ml-tgl");

		final Button buttonTestImg = GuiResource.button(image, "imageButton");
		final Button buttonTestImgToggle = GuiResource.buttonToggle(image, "btnTogle");

		final CheckBox checkBox = GuiResource.checkBox("Check me", "checkbox");

		final Slider slider = GuiResource.slider(0, 10, 1, "slider");
		final TextField textfield = GuiResource.textField("", "Click here!", "textfield");
		final SelectBox dropdown = GuiResource.selectBox(new String[]{"Android", "Windows", "Linux", "OSX"}, "combo");

		final ScrollPane scrollPane = GuiResource.flickScrollPane(image2, "flickscroll");

		final String[] listEntries = {"This is a list entry", "And another one", "The meaning of life", "Is hard to come by",
									  "This is a list entry", "And another one", "The meaning of life", "Is hard to come by", "This is a list entry",
									  "And another one", "The meaning of life", "Is hard to come by", "This is a list entry", "And another one",
									  "The meaning of life", "Is hard to come by", "This is a list entry", "And another one", "The meaning of life",
									  "Is hard to come by"};

		final ScrollPane scrollPane2 = GuiResource.scrollPane(new Image(image3), "scroll");

		final SplitPane splitPane = GuiResource.splitPaneHorizontal(new Image(image3), new Image(image3), "scroll");
		final SplitPane splitPane2 = GuiResource.splitPaneVertical(new Image(image3), new Image(image3), "scroll");

		//
		// List Test
		//
		final List listScroll1 = GuiResource.list(listEntries, "list");
		final ScrollPane listScroll = GuiResource.scrollPane(listScroll1, "list");
		listScroll.setFlickScroll(true);
		listScroll.setFadeScrollBars(false);
		listScroll1.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				S3Log.log("list.addListener::ChangeListener",
						  "List Value: " + listScroll1.getSelection() + " Event: " + event.toString() + " actor: " + actor.toString());
			}
		});

		//
		// List 2 Test
		//
		final List list2 = GuiResource.list(listEntries, "list");
		final ScrollPane scrollPane3 = GuiResource.flickScrollPane(list2, "flickscroll");
		list2.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				S3Log.log("list2.addListener::ChangeListener",
						  "List2 Value: " + list2.getSelection() + " Event: " + event.toString() + " actor: " + actor.toString());
			}
		});


		//
		// Test UI
		//
		final Image imageActorUI = new Image(image2);
		final ScrollPane scrollPaneUI = new ScrollPane(imageActorUI, S3Skin.skin);
		final List listUI = new List(S3Skin.skin);
		listUI.setItems(listEntries);
		final ScrollPane scrollPane2UI = new ScrollPane(listUI, S3Skin.skin);
		final SplitPane splitPaneUI = new SplitPane(scrollPaneUI, scrollPane2UI, false, S3Skin.skin);


		ChangeListener changeListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				S3Log.log("changeListener", "event: " + event.toString() + " actor: " + actor.toString(), 3);
			}
		};

		//
		// New Font
		//
		Label l1 = new Label("Label default", skin, "default");
		Label l2 = new Label("Label default-light", skin, "default-light");
		Label l3 = new Label("Label default-opaque", skin, "default-opaque");

		Label l4 = new Label("Label default-light-opaque", skin, "default-light-opaque");
		Label l5 = new Label("Label default-title", skin, "default-title");
		Label l6 = new Label("Label default-title-opaque", skin, "default-title-opaque");

		//
		//  ScrollImageButton widgetScrollImageButton=new ScrollImageButton();
		//  widgetScrollImageButton.show(SceneManager.getEffectButton(), false, 2, 48, changeListener);
		//
		//  ScrollBoxImageButton widgetScrollIBoxmageButton=new ScrollBoxImageButton();
		//  widgetScrollIBoxmageButton.show(SceneManager.getEffectButton(), 3, 200, 200, 48, 2, changeListener);
		//  widgetScrollIBoxmageButton.setValue(1);
		//

		Window window = GuiResource.window("Dialog", "window");
		window.setHeight(S3Screen.height);
		window.setWidth(S3Screen.centerX);
		window.defaults().align(Align.top);
		window.row().fill().expandX();

		Window window2 = GuiResource.window("Dialog2", "window2");
		window2.setHeight(S3Screen.height);
		window2.setWidth(S3Screen.centerX);
		window2.setX(S3Screen.centerX);
		window2.defaults().align(Align.top);
		window2.row().fill().expandX();

		window.add(l1);
		window.add(l2);
		window.add(l3);
		window.row();

		window.add(l4);
		window.add(l5);
		window.add(l6);
		window.row();

		window.add(button);
		window.add(button2);
		window.add(fileButton);

		window.row();
		window.add(buttonTest);
		window.add(buttonTestMulti);
		window.add(alertButton);

		window.row();
		window.add(buttonTestImg);
		window.add(buttonTestImgToggle);
		window.add(confirmButton);

		window.row();
		window.add(checkBox);
		window.add(slider);

		window.row();
		window.add(textfield);
		window.add(dropdown);

		//  window.row();
		//  window.add(widgetScrollImageButton.getTable()).colspan(3);
		//
		//  window.row();
		//  window.add(widgetScrollIBoxmageButton.getTable()).colspan(3);

		window2.row();
		window2.add(splitPane).colspan(2);

		window2.row();
		window2.add(scrollPane).colspan(2);

		window2.row();
		window2.add(scrollPane2).width(192).height(128);
		window2.add(splitPane2).width(192).height(128);


		window2.row();
		window2.add(listScroll).width(192);
		window2.add(scrollPane3).width(192);

		window.setMovable(false);
		window2.setMovable(false);

		S3.stage.addActor(window);
		S3.stage.addActor(window2);
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
