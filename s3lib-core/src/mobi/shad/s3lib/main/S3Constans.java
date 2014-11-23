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

/**
 * @author Jarek
 */
public class S3Constans{

	public final static boolean INFO = true;
	public final static boolean DEBUG = true;
	public final static boolean NOTICE2 = true;
	public final static boolean NOTICE = true;
	public final static boolean DEBUG_SHADER = true;
	public final static boolean SCREEN_DEBUG = true;
	public final static boolean GUI_DEBUG = true;
	public final static boolean RESOURCE_DEBUG = true;
	public final static boolean TRACk_DEBUG = true;
	public final static boolean SCREEN_DEBUG_BAR = true;
	public final static boolean USE_PRESENT = false;
	public final static boolean SAVE_PRESENT = false;
	public static int textureButtonSize = 64;
	public static int textureImageSize = 256;
	public static int textureImageSizeHight = 512;
	public static int textureImageSizeLow = 128;
	public static int textureSolidFontSize = 1024;
	public static String appName = "s3lib";
	public static String appDirectory = "mobi.shad.s3lib";
	public static String defaultDir = "sprite";
	public static int proceduralTextureSizeLow = 128;
	public static int proceduralTextureSize = 256;
	public static int proceduralTextureSizeHight = 512;
	public static int listSizeYSmall = 100;
	public static int listSizeY = 180;
	public static int listSizeYLarge = 240;
	public static int listImageSizeXSmall = 180;
	public static int listImageSizeYSmall = 150;
	public static int listImageButtonXSmall = 2;
	public static int listImageSizeX = 270;
	public static int listImageSizeY = 250;
	public static int listImageButtonX = 2;
	public static int listImageSizeXLarge = 300;
	public static int listImageSizeYLarge = 330;
	public static int listImageButtonXLarge = 3;
	public static int buttonPaddingSmall = 2;
	public static int buttonPadding = 6;
	public static int buttonPaddingHight = 10;
	public static int minButtonSizeXSmall = 30;
	public static int minButtonSizeX = 60;
	public static int minButtonSizeXLarge = 120;
	public static int cellPadddingSmall = 0;
	public static int cellPaddding = 3;
	public static int cellPadddingHight = 6;
	public static int buttonTrackSize = 20;
	public static int textInputNumericLabel = 50;
	public static int textInputNumericPlus = 30;
	public static int textInputNumeric = 80;
	public static int sliderMinTextWidth = 60;
	public static int gridX0 = 0;
	public static int gridX1 = (S3Screen.width / 10) * 1;
	public static int gridX2 = (S3Screen.width / 10) * 2;
	public static int gridX3 = (S3Screen.width / 10) * 3;
	public static int gridX4 = (S3Screen.width / 10) * 4;
	public static int gridX5 = (S3Screen.width / 10) * 5;
	public static int gridX6 = (S3Screen.width / 10) * 6;
	public static int gridX7 = (S3Screen.width / 10) * 7;
	public static int gridX8 = (S3Screen.width / 10) * 8;
	public static int gridX9 = (S3Screen.width / 10) * 9;
	public static int gridX10 = S3Screen.width;
	public static int gridY0 = 0;
	public static int gridY1 = (S3Screen.height / 10) * 1;
	public static int gridY2 = (S3Screen.height / 10) * 2;
	public static int gridY3 = (S3Screen.height / 10) * 3;
	public static int gridY4 = (S3Screen.height / 10) * 4;
	public static int gridY5 = (S3Screen.height / 10) * 5;
	public static int gridY6 = (S3Screen.height / 10) * 6;
	public static int gridY7 = (S3Screen.height / 10) * 7;
	public static int gridY8 = (S3Screen.height / 10) * 8;
	public static int gridY9 = (S3Screen.height / 10) * 9;
	public static int gridY10 = S3Screen.height;
	public static int gridX0_01 = (int) ((S3Screen.width / 10) * 0.01f);
	public static int gridX0_02 = (int) ((S3Screen.width / 10) * 0.02f);
	public static int gridX0_05 = (int) ((S3Screen.width / 10) * 0.05f);
	public static int gridX0_10 = (int) ((S3Screen.width / 10) * 0.10f);
	public static int gridX0_15 = (int) ((S3Screen.width / 10) * 0.15f);
	public static int gridX0_20 = (int) ((S3Screen.width / 10) * 0.20f);
	public static int gridX0_25 = (int) ((S3Screen.width / 10) * 0.25f);
	public static int gridX0_30 = (int) ((S3Screen.width / 10) * 0.30f);
	public static int gridX0_35 = (int) ((S3Screen.width / 10) * 0.35f);
	public static int gridX0_40 = (int) ((S3Screen.width / 10) * 0.40f);
	public static int gridX0_45 = (int) ((S3Screen.width / 10) * 0.45f);
	public static int gridX0_50 = (int) ((S3Screen.width / 10) * 0.50f);
	public static int gridX0_60 = (int) ((S3Screen.width / 10) * 0.60f);
	public static int gridX0_65 = (int) ((S3Screen.width / 10) * 0.65f);
	public static int gridX0_70 = (int) ((S3Screen.width / 10) * 0.70f);
	public static int gridX0_75 = (int) ((S3Screen.width / 10) * 0.75f);
	public static int gridX0_80 = (int) ((S3Screen.width / 10) * 0.80f);
	public static int gridX0_85 = (int) ((S3Screen.width / 10) * 0.85f);
	public static int gridX0_90 = (int) ((S3Screen.width / 10) * 0.90f);
	public static int gridX0_95 = (int) ((S3Screen.width / 10) * 0.95f);
	public static int gridX1_25 = (int) ((S3Screen.width / 10) * 1.25f);
	public static int gridX1_5 = (int) ((S3Screen.width / 10) * 1.5f);
	public static int gridX1_75 = (int) ((S3Screen.width / 10) * 1.75f);
	public static int gridX2_25 = (int) ((S3Screen.width / 10) * 2.25f);
	public static int gridX2_5 = (int) ((S3Screen.width / 10) * 2.5f);
	public static int gridX2_75 = (int) ((S3Screen.width / 10) * 2.75f);
	public static int gridX3_25 = (int) ((S3Screen.width / 10) * 3.25f);
	public static int gridX3_5 = (int) ((S3Screen.width / 10) * 3.5f);
	public static int gridX3_75 = (int) ((S3Screen.width / 10) * 3.75f);
	public static int gridX4_25 = (int) ((S3Screen.width / 10) * 4.25f);
	public static int gridX4_5 = (int) ((S3Screen.width / 10) * 4.5f);
	public static int gridX4_75 = (int) ((S3Screen.width / 10) * 4.75f);
	public static int gridX5_25 = (int) ((S3Screen.width / 10) * 5.25f);
	public static int gridX5_5 = (int) ((S3Screen.width / 10) * 5.5f);
	public static int gridX5_75 = (int) ((S3Screen.width / 10) * 5.75f);
	public static int gridX6_25 = (int) ((S3Screen.width / 10) * 6.25f);
	public static int gridX6_5 = (int) ((S3Screen.width / 10) * 6.5f);
	public static int gridX6_75 = (int) ((S3Screen.width / 10) * 6.75f);
	public static int gridX7_25 = (int) ((S3Screen.width / 10) * 7.25f);
	public static int gridX7_5 = (int) ((S3Screen.width / 10) * 7.5f);
	public static int gridX7_75 = (int) ((S3Screen.width / 10) * 7.75f);
	public static int gridX8_25 = (int) ((S3Screen.width / 10) * 8.25f);
	public static int gridX8_5 = (int) ((S3Screen.width / 10) * 8.5f);
	public static int gridX8_75 = (int) ((S3Screen.width / 10) * 8.75f);
	public static int gridX9_25 = (int) ((S3Screen.width / 10) * 9.25f);
	public static int gridX9_5 = (int) ((S3Screen.width / 10) * 9.5f);
	public static int gridX9_75 = (int) ((S3Screen.width / 10) * 9.75f);
	public static int gridY0_01 = (int) ((S3Screen.height / 10) * 0.01f);
	public static int gridY0_02 = (int) ((S3Screen.height / 10) * 0.02f);
	public static int gridY0_05 = (int) ((S3Screen.height / 10) * 0.05f);
	public static int gridY0_10 = (int) ((S3Screen.height / 10) * 0.10f);
	public static int gridY0_15 = (int) ((S3Screen.height / 10) * 0.15f);
	public static int gridY0_20 = (int) ((S3Screen.height / 10) * 0.20f);
	public static int gridY0_25 = (int) ((S3Screen.height / 10) * 0.25f);
	public static int gridY0_30 = (int) ((S3Screen.height / 10) * 0.30f);
	public static int gridY0_35 = (int) ((S3Screen.height / 10) * 0.35f);
	public static int gridY0_40 = (int) ((S3Screen.height / 10) * 0.40f);
	public static int gridY0_45 = (int) ((S3Screen.height / 10) * 0.45f);
	public static int gridY0_50 = (int) ((S3Screen.height / 10) * 0.50f);
	public static int gridY0_55 = (int) ((S3Screen.height / 10) * 0.55f);
	public static int gridY0_60 = (int) ((S3Screen.height / 10) * 0.60f);
	public static int gridY0_65 = (int) ((S3Screen.height / 10) * 0.65f);
	public static int gridY0_70 = (int) ((S3Screen.height / 10) * 0.70f);
	public static int gridY0_75 = (int) ((S3Screen.height / 10) * 0.75f);
	public static int gridY1_25 = (int) ((S3Screen.height / 10) * 1.25f);
	public static int gridY1_5 = (int) ((S3Screen.height / 10) * 1.5f);
	public static int gridY1_75 = (int) ((S3Screen.height / 10) * 1.75f);
	public static int gridY2_25 = (int) ((S3Screen.height / 10) * 2.25f);
	public static int gridY2_5 = (int) ((S3Screen.height / 10) * 2.5f);
	public static int gridY2_75 = (int) ((S3Screen.height / 10) * 2.75f);
	public static int gridY3_25 = (int) ((S3Screen.height / 10) * 3.25f);
	public static int gridY3_5 = (int) ((S3Screen.height / 10) * 3.5f);
	public static int gridY3_75 = (int) ((S3Screen.height / 10) * 3.75f);
	public static int gridY4_25 = (int) ((S3Screen.height / 10) * 4.25f);
	public static int gridY4_5 = (int) ((S3Screen.height / 10) * 4.5f);
	public static int gridY4_75 = (int) ((S3Screen.height / 10) * 4.75f);
	public static int gridY5_25 = (int) ((S3Screen.height / 10) * 5.25f);
	public static int gridY5_5 = (int) ((S3Screen.height / 10) * 5.5f);
	public static int gridY5_75 = (int) ((S3Screen.height / 10) * 5.75f);
	public static int gridY6_25 = (int) ((S3Screen.height / 10) * 6.25f);
	public static int gridY6_5 = (int) ((S3Screen.height / 10) * 6.5f);
	public static int gridY6_75 = (int) ((S3Screen.height / 10) * 6.75f);
	public static int gridY7_25 = (int) ((S3Screen.height / 10) * 7.25f);
	public static int gridY7_5 = (int) ((S3Screen.height / 10) * 7.5f);
	public static int gridY7_75 = (int) ((S3Screen.height / 10) * 7.75f);
	public static int gridY8_25 = (int) ((S3Screen.height / 10) * 8.25f);
	public static int gridY8_5 = (int) ((S3Screen.height / 10) * 8.5f);
	public static int gridY8_75 = (int) ((S3Screen.height / 10) * 8.75f);
	public static int gridY9_25 = (int) ((S3Screen.height / 10) * 9.25f);
	public static int gridY9_5 = (int) ((S3Screen.height / 10) * 9.5f);
	public static int gridY9_75 = (int) ((S3Screen.height / 10) * 9.75f);
	// -----------------------------------------------------
	// Icon Size (Low, Normal, Large)
	// mdip - 48
	// hdpi - 72
	// xhdpi - 96
	// -----------------------------------------------------
	public static int buttonImageSmall = 36;
	public static int buttonImageMedium = 48;
	public static int buttonImage = 60;
	public static int buttonImageLarge = 72;
	public static int listImageButtonSizeSmall = 48;
	public static int listImageButtonSize = 48;
	public static int listImageButtonSizeLarge = 48;

	private S3Constans(){

	}

	// 320dp: a typical phone screen (240x320 ldpi, 320x480 mdpi, 480x800 hdpi,
	// etc).
	// 480dp: a tweener tablet like the Streak (480x800 mdpi).
	// 600dp: a 7” tablet (600x1024 mdpi).
	// 720dp: a 10” tablet (720x1280 mdpi, 800x1280 mdpi, etc).
	//
	// 480x320 HVGA
	// 320x240 QVGA
	// 400x240 WQVGA
	// 480x320 HVGA
	// 640x480 VGA
	// 800x480 WVGA
	// 854x480 FWVGA
	// 960x540 qHD
	// 1024x600 XGA
	// 1024x768 XGA
	// 1280x720 HD
	// 1280x768 WXGA
	// 1280x800 WXGA
	// 1920x1080 FHD
	//
	public static void setup(){

		gridX0 = 0;
		gridX1 = (S3Screen.width / 10) * 1;
		gridX2 = (S3Screen.width / 10) * 2;
		gridX3 = (S3Screen.width / 10) * 3;
		gridX4 = (S3Screen.width / 10) * 4;
		gridX5 = (S3Screen.width / 10) * 5;
		gridX6 = (S3Screen.width / 10) * 6;
		gridX7 = (S3Screen.width / 10) * 7;
		gridX8 = (S3Screen.width / 10) * 8;
		gridX9 = (S3Screen.width / 10) * 9;
		gridX10 = S3Screen.width;
		gridY0 = 0;
		gridY1 = (S3Screen.height / 10) * 1;
		gridY2 = (S3Screen.height / 10) * 2;
		gridY3 = (S3Screen.height / 10) * 3;
		gridY4 = (S3Screen.height / 10) * 4;
		gridY5 = (S3Screen.height / 10) * 5;
		gridY6 = (S3Screen.height / 10) * 6;
		gridY7 = (S3Screen.height / 10) * 7;
		gridY8 = (S3Screen.height / 10) * 8;
		gridY9 = (S3Screen.height / 10) * 9;
		gridY10 = S3Screen.height;
		gridX0_01 = (int) ((S3Screen.width / 10) * 0.01f);
		gridX0_02 = (int) ((S3Screen.width / 10) * 0.02f);
		gridX0_05 = (int) ((S3Screen.width / 10) * 0.05f);
		gridX0_10 = (int) ((S3Screen.width / 10) * 0.10f);
		gridX0_15 = (int) ((S3Screen.width / 10) * 0.15f);
		gridX0_20 = (int) ((S3Screen.width / 10) * 0.20f);
		gridX0_25 = (int) ((S3Screen.width / 10) * 0.25f);
		gridX0_30 = (int) ((S3Screen.width / 10) * 0.30f);
		gridX0_35 = (int) ((S3Screen.width / 10) * 0.35f);
		gridX0_40 = (int) ((S3Screen.width / 10) * 0.40f);
		gridX0_45 = (int) ((S3Screen.width / 10) * 0.45f);
		gridX0_50 = (int) ((S3Screen.width / 10) * 0.50f);
		gridX0_60 = (int) ((S3Screen.width / 10) * 0.60f);
		gridX0_65 = (int) ((S3Screen.width / 10) * 0.65f);
		gridX0_70 = (int) ((S3Screen.width / 10) * 0.70f);
		gridX0_75 = (int) ((S3Screen.width / 10) * 0.75f);
		gridX0_80 = (int) ((S3Screen.width / 10) * 0.80f);
		gridX0_85 = (int) ((S3Screen.width / 10) * 0.85f);
		gridX0_90 = (int) ((S3Screen.width / 10) * 0.90f);
		gridX0_95 = (int) ((S3Screen.width / 10) * 0.95f);
		gridX1_25 = (int) ((S3Screen.width / 10) * 1.25f);
		gridX1_5 = (int) ((S3Screen.width / 10) * 1.5f);
		gridX1_75 = (int) ((S3Screen.width / 10) * 1.75f);
		gridX2_25 = (int) ((S3Screen.width / 10) * 2.25f);
		gridX2_5 = (int) ((S3Screen.width / 10) * 2.5f);
		gridX2_75 = (int) ((S3Screen.width / 10) * 2.75f);
		gridX3_25 = (int) ((S3Screen.width / 10) * 3.25f);
		gridX3_5 = (int) ((S3Screen.width / 10) * 3.5f);
		gridX3_75 = (int) ((S3Screen.width / 10) * 3.75f);
		gridX4_25 = (int) ((S3Screen.width / 10) * 4.25f);
		gridX4_5 = (int) ((S3Screen.width / 10) * 4.5f);
		gridX4_75 = (int) ((S3Screen.width / 10) * 4.75f);
		gridX5_25 = (int) ((S3Screen.width / 10) * 5.25f);
		gridX5_5 = (int) ((S3Screen.width / 10) * 5.5f);
		gridX5_75 = (int) ((S3Screen.width / 10) * 5.75f);
		gridX6_25 = (int) ((S3Screen.width / 10) * 6.25f);
		gridX6_5 = (int) ((S3Screen.width / 10) * 6.5f);
		gridX6_75 = (int) ((S3Screen.width / 10) * 6.75f);
		gridX7_25 = (int) ((S3Screen.width / 10) * 7.25f);
		gridX7_5 = (int) ((S3Screen.width / 10) * 7.5f);
		gridX7_75 = (int) ((S3Screen.width / 10) * 7.75f);
		gridX8_25 = (int) ((S3Screen.width / 10) * 8.25f);
		gridX8_5 = (int) ((S3Screen.width / 10) * 8.5f);
		gridX8_75 = (int) ((S3Screen.width / 10) * 8.75f);
		gridX9_25 = (int) ((S3Screen.width / 10) * 9.25f);
		gridX9_5 = (int) ((S3Screen.width / 10) * 9.5f);
		gridX9_75 = (int) ((S3Screen.width / 10) * 9.75f);
		gridY0_01 = (int) ((S3Screen.height / 10) * 0.01f);
		gridY0_02 = (int) ((S3Screen.height / 10) * 0.02f);
		gridY0_05 = (int) ((S3Screen.height / 10) * 0.05f);
		gridY0_10 = (int) ((S3Screen.height / 10) * 0.10f);
		gridY0_15 = (int) ((S3Screen.height / 10) * 0.15f);
		gridY0_20 = (int) ((S3Screen.height / 10) * 0.20f);
		gridY0_25 = (int) ((S3Screen.height / 10) * 0.25f);
		gridY0_30 = (int) ((S3Screen.height / 10) * 0.30f);
		gridY0_35 = (int) ((S3Screen.height / 10) * 0.35f);
		gridY0_40 = (int) ((S3Screen.height / 10) * 0.40f);
		gridY0_45 = (int) ((S3Screen.height / 10) * 0.45f);
		gridY0_50 = (int) ((S3Screen.height / 10) * 0.50f);
		gridY0_55 = (int) ((S3Screen.height / 10) * 0.55f);
		gridY0_60 = (int) ((S3Screen.height / 10) * 0.60f);
		gridY0_65 = (int) ((S3Screen.height / 10) * 0.65f);
		gridY0_70 = (int) ((S3Screen.height / 10) * 0.70f);
		gridY0_75 = (int) ((S3Screen.height / 10) * 0.75f);
		gridY1_25 = (int) ((S3Screen.height / 10) * 1.25f);
		gridY1_5 = (int) ((S3Screen.height / 10) * 1.5f);
		gridY1_75 = (int) ((S3Screen.height / 10) * 1.75f);
		gridY2_25 = (int) ((S3Screen.height / 10) * 2.25f);
		gridY2_5 = (int) ((S3Screen.height / 10) * 2.5f);
		gridY2_75 = (int) ((S3Screen.height / 10) * 2.75f);
		gridY3_25 = (int) ((S3Screen.height / 10) * 3.25f);
		gridY3_5 = (int) ((S3Screen.height / 10) * 3.5f);
		gridY3_75 = (int) ((S3Screen.height / 10) * 3.75f);
		gridY4_25 = (int) ((S3Screen.height / 10) * 4.25f);
		gridY4_5 = (int) ((S3Screen.height / 10) * 4.5f);
		gridY4_75 = (int) ((S3Screen.height / 10) * 4.75f);
		gridY5_25 = (int) ((S3Screen.height / 10) * 5.25f);
		gridY5_5 = (int) ((S3Screen.height / 10) * 5.5f);
		gridY5_75 = (int) ((S3Screen.height / 10) * 5.75f);
		gridY6_25 = (int) ((S3Screen.height / 10) * 6.25f);
		gridY6_5 = (int) ((S3Screen.height / 10) * 6.5f);
		gridY6_75 = (int) ((S3Screen.height / 10) * 6.75f);
		gridY7_25 = (int) ((S3Screen.height / 10) * 7.25f);
		gridY7_5 = (int) ((S3Screen.height / 10) * 7.5f);
		gridY7_75 = (int) ((S3Screen.height / 10) * 7.75f);
		gridY8_25 = (int) ((S3Screen.height / 10) * 8.25f);
		gridY8_5 = (int) ((S3Screen.height / 10) * 8.5f);
		gridY8_75 = (int) ((S3Screen.height / 10) * 8.75f);
		gridY9_25 = (int) ((S3Screen.height / 10) * 9.25f);
		gridY9_5 = (int) ((S3Screen.height / 10) * 9.5f);
		gridY9_75 = (int) ((S3Screen.height / 10) * 9.75f);

	}
}
