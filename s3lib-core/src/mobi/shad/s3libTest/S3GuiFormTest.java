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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class S3GuiFormTest extends S3App{

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
	protected GuiForm guiForm;
	protected GuiForm guiForm2;

	@Override
	public void initalize(){

		String[] listItem = {"Pierwsza opcja", "Druga opcja", "Trzecia opcja", "Czwarta opcja"};

		guiForm = new GuiForm(this.getClass().getSimpleName());
		guiForm2 = new GuiForm(this.getClass().getSimpleName());

		ChangeListener valLitsener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				S3Log.log("valueChangeListener", "TestValue1: " + guiForm.getFloat("testValue1"));
				S3Log.log("valueChangeListener", "TestValue2: " + guiForm.getFloat("testValue2"));
				S3Log.log("valueChangeListener", "TestValue3: " + guiForm.getFloat("testValue3"));
				S3Log.log("valueChangeListener", "TestValue4: " + guiForm.getFloat("testValue4"));
			}
		};

		guiForm.addFileBrowser("testListString", "diffuseTextureName", testListString, "texture", null);

		guiForm.add("testValue1", "Test Value 1", 0, 0, 1, 0.1f, valLitsener);
		guiForm.add("testValue2", "Test Value 2", 0, 0, 10, 1f, valLitsener);
		guiForm.add("testValue3", "Test Value 3", 0, -10, 10, 1f, valLitsener);
		guiForm.add("testValue4", "Test Value 4", 0, -100, 111, 1f, valLitsener);
		guiForm.addEditorBox("testValue4", "", 200, 200, 300, 100, valLitsener);

		guiForm.addSelectIndex("selectIndex", "Test select index", 0, listItem, valLitsener);
		guiForm.addColorList("colorIndex", "Color index text", valLitsener);

		guiForm2.add("testValue1", "Test Value 1", 0, 0, 1, 0.1f, valLitsener);
		guiForm2.addCheckBox("testBool 1", "Test boolean 1", false, valLitsener);
		guiForm2.addCheckBox("testBool 2", "Test boolean 2", true, valLitsener);
		guiForm2.addCheckBox("testBool 3", "Test boolean 3", false, valLitsener);

		guiForm2.addImageList("imageList", "Image List", new String[]{"sprite/bobs0.png", "sprite/bobs1.png", "sprite/bobs2.png"}, valLitsener);

		Window window = GuiResource.window("Test Effect", "window");
		window.defaults().align(Align.top | Align.left);
		window.row();
		window.add(guiForm.getScrollPane()).height(S3Constans.gridY9);
		window.add(guiForm2.getScrollPane()).height(S3Constans.gridY9);
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
