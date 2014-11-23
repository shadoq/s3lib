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

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import mobi.shad.s3lib.gfx.g2d.EffectCreator;
import mobi.shad.s3lib.gfx.g2d.screen.Layer;
import mobi.shad.s3lib.gfx.g2d.screen.Screen;
import mobi.shad.s3lib.gfx.g2d.screen.ScreenManager;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;

import static mobi.shad.s3lib.main.S3.osDeltaTime;

/**
 * @author Jarek
 */
public class S3ScreenLayerTest extends S3App{

	Screen screenSplash;

	class TestLayer extends Layer{

		public TestLayer(){
			Table guiTable = GuiResource.table("Abcdh");
			TextButton guiTextButton = GuiResource.textButton("Abc 1", "");
			TextButton guiTextButton1 = GuiResource.textButton("Abc 2", "");
			TextButton guiTextButton2 = GuiResource.textButton("Abc 3", "");
			TextButton guiTextButton3 = GuiResource.textButton("Abc 4", "");
			guiTable.add(guiTextButton);
			guiTable.add(guiTextButton1);
			guiTable.add(guiTextButton2);
			guiTable.add(guiTextButton3);
			GuiUtil.windowPosition(guiTable, GuiUtil.Position.BOTTOM_RIGHT);
			addActor(guiTable);

			EffectCreator.fadeOut(this, 5, 1, this, false);
		}
	}

	class TestLayer2 extends Layer{

		public TestLayer2(){
			Table guiTable = GuiResource.table("AXC");
			TextButton guiTextButton = GuiResource.textButtonToggle("AXC 1", "");
			TextButton guiTextButton1 = GuiResource.textButtonToggle("AXC 2", "");
			TextButton guiTextButton2 = GuiResource.textButtonToggle("AXC 3", "");
			TextButton guiTextButton3 = GuiResource.textButtonToggle("AXC 4", "");
			guiTable.add(guiTextButton);
			guiTable.add(guiTextButton1);
			guiTable.add(guiTextButton2);
			guiTable.add(guiTextButton3);
			GuiUtil.windowPosition(guiTable, GuiUtil.Position.CENTER);
			addActor(guiTable);
		}
	}

	@Override
	public void initalize(){
		screenSplash = new Screen(this);
		ScreenManager.add(screenSplash);
		ScreenManager.setScreen(screenSplash);

		Layer t1 = new TestLayer();
		screenSplash.addLayer(t1);

		Layer t2 = new TestLayer2();
		screenSplash.addLayer(t2);

	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		ScreenManager.render(osDeltaTime);
	}
}
