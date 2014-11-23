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
package mobi.shad.s3lib.main;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import mobi.shad.s3lib.main.S3Screen.ResolutionType;

/**
 * Class to load and setup GUI skin
 */
public class S3Skin{

	private static final String TAG = S3Skin.class.getSimpleName();

	public static Skin skin;

	public static void init(){
		skin = null;
		loadSkin();
	}

	/**
	 * Load skin from json
	 *
	 * @return
	 */
	public static Skin getSkin(){
		if (skin == null){
			loadSkin();
		}
		return skin;
	}

	/**
	 * Load skin and setup GUI config
	 */
	protected static void loadSkin(){
		S3Log.info(TAG, "Load skin for resolution: " + S3Screen.resolution, 0);

		if (S3Screen.resolution == ResolutionType.hdpi){
			skin = S3ResourceManager.getSkin("ui/ui_hdpi.json");
			S3Constans.buttonPaddingSmall = 6;
			S3Constans.buttonPadding = 12;
			S3Constans.buttonPaddingHight = 18;

			S3Constans.cellPadddingSmall = 3;
			S3Constans.cellPaddding = 6;
			S3Constans.cellPadddingHight = 9;

		} else if (S3Screen.resolution == ResolutionType.xhdpi){
			skin = S3ResourceManager.getSkin("ui/ui_xhdpi.json");
			S3Constans.buttonPaddingSmall = 8;
			S3Constans.buttonPadding = 16;
			S3Constans.buttonPaddingHight = 24;

			S3Constans.cellPadddingSmall = 4;
			S3Constans.cellPaddding = 8;
			S3Constans.cellPadddingHight = 12;

		} else if (S3Screen.resolution == ResolutionType.ldpi){
			skin = S3ResourceManager.getSkin("ui/ui_ldpi.json");
			S3Constans.buttonPaddingSmall = 0;
			S3Constans.buttonPadding = 4;
			S3Constans.buttonPaddingHight = 6;

			S3Constans.cellPadddingSmall = 1;
			S3Constans.cellPaddding = 3;
			S3Constans.cellPadddingHight = 4;
		} else {
			skin = S3ResourceManager.getSkin("ui/ui_mdpi.json");
			S3Constans.buttonPaddingSmall = 6;
			S3Constans.buttonPadding = 10;
			S3Constans.buttonPaddingHight = 14;

			S3Constans.cellPadddingSmall = 1;
			S3Constans.cellPaddding = 2;
			S3Constans.cellPadddingHight = 4;
		}
	}
}
