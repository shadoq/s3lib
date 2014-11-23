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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;

import static mobi.shad.s3lib.main.S3.skin;

/**
 * @author Jarek
 */
public class Screen3Menu extends S3App{

	Label label;
	Table main;
	private TextButton playBtn;
	private TextButton configBtn;
	private TextButton exitBtn;

	@Override
	public void initalize(){

		main = GuiResource.table("mainTable");

		label = GuiResource.label("BIG TITLE", "label1");
		label.setColor(Color.YELLOW);
		label.setStyle(skin.get("default-title", Label.LabelStyle.class));

		playBtn = GuiResource.textButton("Play", "play");
		configBtn = GuiResource.textButton("Config", "config");
		exitBtn = GuiResource.textButton("Exit", "exit");

		playBtn.setStyle(skin.get("default-light", TextButton.TextButtonStyle.class));
		configBtn.setStyle(skin.get("default-light", TextButton.TextButtonStyle.class));
		exitBtn.setStyle(skin.get("default-light", TextButton.TextButtonStyle.class));

		main.row();
		main.add(label);

		main.row();
		main.add(playBtn).center().fillX();
		main.row();
		main.add(configBtn).center().fillX();
		main.row();
		main.add(exitBtn).center().fillX();

		GuiUtil.windowPosition(main, GuiUtil.Position.CENTER);
		S3.stage.addActor(main);
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx gfx){
		S3Gfx.clear();
	}

	@Override
	public void resize(){
		GuiUtil.windowPosition(main, GuiUtil.Position.CENTER);
		main.invalidate();
	}
}
