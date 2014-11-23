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

import mobi.shad.s3lib.gfx.g2d.screen.Screen;
import mobi.shad.s3lib.gfx.g2d.screen.ScreenManager;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3ResourceManager;

/**
 * @author Jarek
 */
public class S3ScreenTest extends S3App{

	Screen screenSplash;
	Screen screenMain;
	Screen screenAbout;
	Screen screenConfig;
	Screen screenStart;

	@Override
	public void initalize(){

		screenSplash = new Screen(this, S3ResourceManager.getTexture("texture/def256.jpg", 512));
		screenMain = new Screen(this, S3ResourceManager.getTexture("texture/texture_1.jpg", 512));
		screenAbout = new Screen(this, S3ResourceManager.getTexture("texture/texture_0.jpg", 512));
		screenConfig = new Screen(this, S3ResourceManager.getTexture("texture/texture_2.jpg", 512));
		screenStart = new Screen(this, S3ResourceManager.getTexture("texture/texture_3.jpg", 512));

		ScreenManager.add(screenSplash);
		ScreenManager.add(screenMain);
		ScreenManager.add(screenAbout);
		ScreenManager.add(screenConfig);
		ScreenManager.add(screenStart);

		ScreenManager.setScreen(screenSplash);
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){

		if (S3.osTime > 2 && S3.osTime < 3 && ScreenManager.getScreen() != screenMain){
			ScreenManager.setScreen(screenMain);
		} else if (S3.osTime > 4 && S3.osTime < 5 && ScreenManager.getScreen() != screenAbout){
			ScreenManager.setScreen(screenAbout);
		}

		ScreenManager.render(S3.osDeltaTime);
	}
}
