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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;

import static mobi.shad.s3lib.main.S3.skin;
import static mobi.shad.s3lib.main.S3.stage;

/**
 * @author Jarek
 */
public class ScreenGameArea extends S3App{

	Label label;
	Table main;
	private TextButton playBtn;
	private TextButton configBtn;
	private TextButton exitBtn;
	private Slider speedSlider;
	private SelectBox gridSize;

	@Override
	public void initalize(){

		main = GuiResource.table("mainTable");

		label = GuiResource.label("BIG TITLE", "label1");
		label.setColor(Color.YELLOW);
		label.setStyle(skin.get("default-title", Label.LabelStyle.class));

		playBtn = GuiResource.textButtonToggle("Play", "play");
		configBtn = GuiResource.textButton("Random", "random");
		exitBtn = GuiResource.textButton("Menu", "menu");

		speedSlider = GuiResource.slider(0, 10, 1, "Speed");
		gridSize = GuiResource.selectBox(new String[]{"32x32", "64x64", "128x128", "256x256", "512x512"}, "GridSize");

		main.row();
		main.add(label).colspan(5).center();

		main.row();
		main.add("Grid");
		main.add(gridSize);

		main.row();
		main.add(speedSlider).colspan(2);
		main.add(playBtn);
		main.add(configBtn);
		main.add(exitBtn);

		main.row();
		main.add("Main").expandX().expandY().fillX().fillY();

		GuiUtil.windowPosition(main, GuiUtil.Position.TOP);
		stage.addActor(main);
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx gfx){
		gfx.clear();
	}

	@Override
	public void resize(){
		GuiUtil.windowPosition(main, GuiUtil.Position.TOP);
		main.invalidate();
	}
}
