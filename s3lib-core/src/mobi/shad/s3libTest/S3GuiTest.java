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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import mobi.shad.s3lib.gui.Gui;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Gfx;

/**
 * @author Jarek
 */
public class S3GuiTest extends S3App{

	//
	// Test value
	//
	public Float testFloat = 5f;
	public Integer testInteger = 40;
	public Boolean testBoolean = false;
	public String testListString = "";
	public Color testColor = Color.BLACK;
	public String testDir = "";
	public String[] imageList;
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

		gui = new Gui();
		gui2 = new Gui();
		gui3 = new Gui();

		gui.addLabel("Test Button", true, 3);
		gui.addButton("btnFloat1", this, "testFloat", 10f, true, "gr2");
		gui.addButton("btnFloat2", this, "testFloat", 20f, false, "gr2");
		gui.addButton("btnFloat3", this, "testFloat", 30f, false, "gr2");

		gui.addButton("btnBoolean1", this, "testBoolean", true, true, "gr3");
		gui.addButton("btnBoolean2", this, "testBoolean", false, false, "gr3");

		gui.addLabel("Test Button Toggle", true, 3);
		gui.addButtonToggle("btnInteger1", this, "testInteger", 10, true, "gr0");
		gui.addButtonToggle("btnInteger2", this, "testInteger", 20, false, "gr0");
		gui.addButtonToggle("btnInteger3", this, "testInteger", 30, false, "gr0");

		gui.addButtonToggle("btnInteger1G", this, "testInteger", 30, true, "gr1");
		gui.addButtonToggle("btnInteger2G", this, "testInteger", 40, false, "gr1");
		gui.addButtonToggle("btnInteger3G", this, "testInteger", 50, false, "gr1");

		gui.addLabel("Test Slider All colspan-1", true, 3);
		gui.addSlider("Slider Float 1", this, "testFloat", testFloat, 0f, 10f, 2f, 1);
		gui.addSlider("Slider Float 1", this, "testFloat", testFloat, 0f, 10f, 1f, 2);

		gui.addLabel("Test CheckBox", true, 3);
		gui.addCheckBox("CheckBox", this, "testBoolean", true, false, true, 1, null, null);
		gui.addCheckBox("CheckBox", this, "testBoolean", true, false, true, 1, null, null);
		gui.addCheckBox("CheckBox", this, "testBoolean", true, false, true, 1, null, null);

		gui2.addFileBrowser("Test File", this, "testDir", "sprite", null);

		gui2.addLabel("Test List", true, 3);
		String[] listItem = {"Pierwsza opcja", "Druga opcja", "Trzecia opcja", "Czwarta opcja"};
		gui2.addList("Test list", this, "testListString", listItem, "", true, 2, null);

		gui2.addSelectBox("Test select box", this, "testListString", listItem, "", true, 2, null);
		gui2.addColorBrowser("Test Color", this, "testColor", Color.RED, true);

		gui3.addEditor2d("Editor 2D", this, "testFloat", "testFloat", "testFloat", "testFloat", 300, 200, 200, 200, true, 1, null);
		gui3.addImageList("Image List", this, "imageList", new String[]{"texture/texture_1.jpg", "texture/texture_4.jpg"}, true, 1, null);

		Window window = GuiResource.window("Test Effect", "window");
		window.defaults();

		window.row();
		window.add(gui.getScrollPane()).height(S3Constans.gridY8);
		window.add(gui2.getScrollPane()).height(S3Constans.gridY8);
		window.add(gui3.getScrollPane()).height(S3Constans.gridY8);
		window.setHeight(S3Constans.gridY9);
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
