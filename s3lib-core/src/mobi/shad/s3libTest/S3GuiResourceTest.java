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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import mobi.shad.s3lib.gui.Gui;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;

/**
 * @author Jarek
 */
public class S3GuiResourceTest extends S3App{

	//
	// Test value
	//
	public Float testFloat = 5f;
	public Integer testInteger = 40;
	public Boolean testBoolean = false;
	public String testListString = "";
	public Color testColor = Color.BLACK;
	public String testDir = "";
	protected float start;
	protected float duration = 10;
	protected float localTime = 0;
	protected float sceneTime = 0;
	protected float endTime = 0;
	protected float procent = 0;
	protected Gui gui = null;
	protected Gui gui2 = null;
	protected Gui gui3 = null;

	@Override
	public void initalize(){

		TextButton guiButton = GuiResource.textButton("Button 1", "Button 1");
		TextButton guiButton1 = GuiResource.textButton("Button 2", "Button 2");
		TextButton guiButton2 = GuiResource.textButton("Button 3", "Button 3");
		TextButton guiTextButtonToggle = GuiResource.textButtonToggle("Toggle 1", "Toggle 1");
		TextButton guiTextButtonToggle1 = GuiResource.textButtonToggle("Toggle 2", "Toggle 2");
		TextButton guiTextButtonToggle2 = GuiResource.textButtonToggle("Toggle 3", "Toggle 3");
		Button guiButton3 = GuiResource.button("texture/def256.jpg");
		Button guiButton4 = GuiResource.button("texture/logo3.png");
		Button guiButton5 = GuiResource.button("texture/logo1.png");
		CheckBox guiCheckBox = GuiResource.checkBox("CheckBox 1", "CheckBox 1");
		CheckBox guiCheckBox1 = GuiResource.checkBox("CheckBox 2", "CheckBox 2");
		CheckBox guiCheckBox2 = GuiResource.checkBox("CheckBox 3", "CheckBox 3");

		Button guiButton6 = GuiResource.button("texture/def256.jpg");
		ScrollPane guiFlickScrollPane = GuiResource.flickScrollPane(guiButton6, "FlickScroll");

		Button guiButton7 = GuiResource.button("texture/def256.jpg");
		ScrollPane guiScrollPane = GuiResource.scrollPane(guiButton7, "ScrollPane");

		String[] listItem = {"Pierwsza opcja", "Druga opcja", "Trzecia opcja", "Czwarta opcja"};
		SelectBox guiSelectBox = GuiResource.selectBox(listItem, "SelectBox");
		List guiList = GuiResource.list(listItem, "ListItem");
		Label guiLabel = GuiResource.label("Label 1", "Label 1");
		Label guiLabel1 = GuiResource.label("Label 2", "Label 2");
		Label guiLabel2 = GuiResource.label("Label 3", "Label 3");

		Window window = GuiResource.window("Test Effect", "window");
		window.defaults().align(Align.top | Align.left);

		window.row();
		window.add(guiButton);
		window.add(guiButton1);
		window.add(guiButton2);

		window.row();
		window.add(guiTextButtonToggle);
		window.add(guiTextButtonToggle1);
		window.add(guiTextButtonToggle2);

		window.row();
		window.add(guiButton3).width(100).height(100);
		window.add(guiButton4).width(100).height(100);
		window.add(guiButton5).width(100).height(100);

		window.row();
		window.add(guiCheckBox);
		window.add(guiCheckBox1);
		window.add(guiCheckBox2);

		window.row();
		window.add(guiFlickScrollPane).width(100).height(100);
		window.add(guiScrollPane).width(100).height(100);

		window.row();
		window.add(guiLabel);
		window.add(guiLabel1);
		window.add(guiLabel2);

		window.row();
		window.add(guiSelectBox);

		window.row();
		window.add(guiList);

		window.pack();

		S3.stage.addActor(window);
	}

	@Override
	public void update(){
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
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
