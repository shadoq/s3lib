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

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import mobi.shad.s3lib.gfx.g2d.EffectCreator;
import mobi.shad.s3lib.gfx.g2d.screen.Layer;
import mobi.shad.s3lib.gfx.g2d.screen.Screen;
import mobi.shad.s3lib.gfx.g2d.screen.ScreenManager;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;

/**
 * @author Jarek
 */
public class S3ScreenEffectCreatorTest extends S3App{

	Screen screenSplash;

	class TestLayer extends Layer{

		public TestLayer(){
			Image guiTextButton = GuiResource.image("texture/def256.jpg");
			guiTextButton.setPosition(50, 50);
			guiTextButton.setSize(100, 100);

			Image guiTextButton1 = GuiResource.image("texture/def256.jpg");
			guiTextButton1.setPosition(150, 50);
			guiTextButton1.setSize(100, 100);

			Image guiTextButton2 = GuiResource.image("texture/def256.jpg");
			guiTextButton2.setPosition(200, 50);
			guiTextButton2.setSize(100, 100);

			Image guiTextButton3 = GuiResource.image("texture/def256.jpg");
			guiTextButton3.setPosition(250, 50);
			guiTextButton3.setSize(100, 100);

			Image guiTextButton5 = GuiResource.image("texture/def256.jpg");
			guiTextButton5.setPosition(50, 50);
			guiTextButton5.setSize(100, 100);
			guiTextButton5.setRotation(50);

			Image guiTextButton6 = GuiResource.image("texture/def256.jpg");
			guiTextButton6.setPosition(450, 450);
			guiTextButton5.setSize(100, 100);

			Image guiTextButton7 = GuiResource.image("texture/def256.jpg");
			guiTextButton7.setPosition(650, 600);
			guiTextButton5.setSize(100, 100);

			Image guiTextButton8 = GuiResource.image("texture/def256.jpg");
			guiTextButton8.setPosition(750, 550);
			guiTextButton5.setSize(100, 100);

			addActor(guiTextButton);
			addActor(guiTextButton1);
			addActor(guiTextButton2);
			addActor(guiTextButton3);

			addActor(guiTextButton5);
			addActor(guiTextButton6);
			addActor(guiTextButton7);
			addActor(guiTextButton8);

			EffectCreator.fadeOut(guiTextButton, 5, 0, this, false);
			EffectCreator.scaleBactToFadeOut(guiTextButton5, 10, 10, 5, 2, this, false);
			EffectCreator.fadeOut(guiTextButton2, 5, 15, this, false);
			EffectCreator.scaleBackTo(guiTextButton3, 5, 5, 10, this, false);

			EffectCreator.shake(guiTextButton5, 20, 30, 8, this, false);
			EffectCreator.moveFadeIn(guiTextButton6, 10, 10, 0, 0, 3, this, false);

			EffectCreator.inOut(EffectCreator.EffectType.BOTTOM_RIGHT_TO_TOP_LEFT, guiTextButton7, guiTextButton8, 5, this, false);
		}
	}

	@Override
	public void initalize(){
		screenSplash = new Screen(this);
		ScreenManager.add(screenSplash);
		ScreenManager.setScreen(screenSplash);

		Layer t1 = new TestLayer();
		screenSplash.addLayer(t1);
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		ScreenManager.render(S3.osDeltaTime);
	}
}
