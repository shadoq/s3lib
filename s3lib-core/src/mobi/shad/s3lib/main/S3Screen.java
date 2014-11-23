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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.GraphicsType;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * <p/>
 * Przechowywuje informację na temat rozdzielczości aplikacji oraz wirtualnego
 * ekranu w przypadku renderowania do niskiej rozdzielczosci: normalne parametry
 * - rozdzielczosc wirtualnego ekranu gui - rzeczywista rozdzielczosc
 * urządzenia, używana do renderowania obiektów GUI
 * <p/>
 * PC: Screen size: 1280:720 center: 640:360 aspectRatioX: 1.0
 * aspectRatioY: 1.0 windowDensity: 0.6 windowType: LWJGL
 * Galaxy S: Window size: 800:480 center: 400:240 aspectRatioX: 0.78125
 * aspectRatioY: 0.6666667 windowDensity: 1.5 windowType: AndroidGL
 * Sony Xperia Z: Window size: 1794:1080 center: 897:540
 * aspectRatioX: 1.7519531 aspectRatioY: 1.5 windowDensity: 3.0 windowType: AndroidGL
 * Tablet Nexus 7: Window size: 1280:736 center: 640:368 aspectRatioX:
 * 1.25 aspectRatioY: 1.0222223 windowDensity: 1.3312501 windowType: AndroidGL
 */
public class S3Screen{
	private static final String TAG = S3Screen.class.getSimpleName();
	public static Graphics graphics;
	public static GL20 gl20;
	public static GraphicsType type;
	public static float density = 0;
	//
	// Virtual screen
	//
	public static int width = 0;
	public static int viewportWidth = width;
	public static int height = 0;
	public static int viewportHeight = height;
	public static int centerX = 0;
	public static int centerY = 0;
	//
	// Real screen
	//
	public static int screenWidth = 0;
	public static int screenHeight = 0;
	public static int screenCenterX = 0;
	public static int screenCenterY = 0;
	public static float scale = 1.0f;
	public static float aspectRatio = 1.0f;
	public static float realAspectRatio = 1.0f;
	public static float aspectRatioX = 1.0f;
	public static float aspectRatioY = 1.0f;
	public static ResolutionType resolution;
	public static ViewPortType viewPortType;
	public static int viewportX = 0;
	public static int viewportY = 0;
	public static int VIRTUAL_WIDTH = 800;
	public static int VIRTUAL_HEIGHT = 480;
	public static boolean virtualScreen = true;

	public static enum ResolutionType{
		ldpi, mdpi, hdpi, xhdpi
	}

	public static enum ViewPortType{
		Screen, Stretch, Fill
	}

	private S3Screen(){
	}

	public static void init(){
		graphics = Gdx.app.getGraphics();
		gl20 = graphics.getGL20();
		type = graphics.getType();
		density = graphics.getDensity();
		virtualScreen=S3.cfg.virtualScreen;

		S3Log.info(TAG, "GL_VENDOR=" + gl20.glGetString(GL20.GL_VENDOR));
		S3Log.info(TAG, "GL_VENDOR=" + gl20.glGetString(GL20.GL_VENDOR));
		S3Log.info(TAG, "GL_RENDERER=" + gl20.glGetString(GL20.GL_RENDERER));
		S3Log.info(TAG, "GL_VERSION=" + gl20.glGetString(GL20.GL_VERSION));
		S3Log.info(TAG, "GL_EXTENSIONS :\n" + gl20.glGetString(GL20.GL_EXTENSIONS).trim().replace(" ", " "));

		String[] screenSize = S3.cfg.screenSize.split("x");
		if (Integer.parseInt(screenSize[0]) > 0 && Integer.parseInt(screenSize[1]) > 0){
			VIRTUAL_WIDTH = Integer.parseInt(screenSize[0]);
			VIRTUAL_HEIGHT = Integer.parseInt(screenSize[1]);
		}

		if (virtualScreen){
			width = VIRTUAL_WIDTH;
			height = VIRTUAL_HEIGHT;
		} else {
			width = graphics.getWidth();
			height = graphics.getHeight();
		}

		//
		// Detect screen type
		//
		if (S3.cfg.multiResolution){
			if ((width > height && width <= 481) || (height > width && height <= 481)){
				resolution = ResolutionType.ldpi;
			} else if ((width > height && width < 721) || (height > width && height < 721)){
				resolution = ResolutionType.mdpi;
			} else if (((width > height && width < 1301) || (height > width && height < 1301)) && density < 1.75f){
				resolution = ResolutionType.hdpi;
			} else {
				resolution = ResolutionType.xhdpi;
			}
		} else {
			resolution = ResolutionType.hdpi;
		}
		resize(graphics.getWidth(), graphics.getHeight());
	}

	public static void resize(int screenWidth, int screenHeight){

		S3Screen.screenWidth = screenWidth;
		S3Screen.screenHeight = screenHeight;

		screenCenterX = screenWidth / 2;
		screenCenterY = screenHeight / 2;

		centerX = width / 2;
		centerY = height / 2;

		realAspectRatio = (float) screenWidth / (float) screenHeight;
		aspectRatio = (float) width / (float) height;
		aspectRatioX = (float) screenWidth / (float) VIRTUAL_WIDTH;
		aspectRatioY = (float) screenHeight / (float) VIRTUAL_HEIGHT;

		scale = 1.0f;
		viewportX = 0;
		viewportY = 0;

		if (realAspectRatio > aspectRatio){
			scale = (float) S3Screen.screenHeight / (float) height;
			viewportX = (int) ((S3Screen.screenWidth - width * scale) / 2f);
		} else if (realAspectRatio < aspectRatio){
			scale = (float) S3Screen.screenWidth / (float) width;
			viewportY = (int) ((S3Screen.screenHeight - height * scale) / 2f);
		} else {
			scale = (float) S3Screen.screenWidth / (float) width;
		}

		viewportWidth = (int) ((float) width * scale);
		viewportHeight = (int) ((float) height * scale);

		//
		// Camera setup
		//
		if (S3.fixedCamera == null){
			S3.fixedCamera = new OrthographicCamera(width, height);
			S3.fixedCamera.position.set(S3Screen.centerX, S3Screen.centerY, S3.fixedCamera.position.z);
			S3.fixedCamera.update();
		}

		//
		// Viewport Setup
		//
		if (S3.viewPort == null){
			setViewport(S3.cfg.viewPortType);
		}

		//
		// Stage setup
		//
		if (S3.stage != null){
			S3.stage.getViewport().update(screenWidth, screenHeight, true);
		}
		if (S3.postRenderStage != null){
			S3.postRenderStage.getViewport().update(screenWidth, screenHeight, true);
		}
		if (S3.preRenderStage != null){
			S3.preRenderStage.getViewport().update(screenWidth, screenHeight, true);
		}

		S3Log.info(TAG, "Screen density: " + density + " type:" + type, 2);
		S3Log.info(TAG, "Screen size: " + S3Screen.screenWidth + ":" + S3Screen.screenHeight + " center: " + screenCenterX + ":" + screenCenterY, 2);
		S3Log.info(TAG, "Virtual size: " + width + ":" + height + " center: " + centerX + ":" + centerY, 2);

		S3Log.info(TAG, "aspectVirtual: " + aspectRatio + " aspectRatio: " + realAspectRatio + " scale: " + scale, 2);
		S3Log.info(TAG, "aspectRatioX: " + aspectRatioX + " aspectRatioY: " + aspectRatioY, 2);
		S3Log.info(TAG, "windowDensity: " + density + " windowType: " + type + "resolution: " + resolution, 2);
		S3Log.info(TAG, "viewport: " + viewportX + ":" + viewportY + " viewport Size: " + viewportWidth + ":" + viewportHeight, 2);
		S3Log.info(TAG, "viewport: " + S3.viewPort, 2);
	}

	/**
	 * @param viewPortType
	 * @return
	 */
	public static Viewport setViewport(ViewPortType viewPortType){


		switch (viewPortType){
			case Stretch:
				S3.viewPort = new StretchViewport(width, height, S3.fixedCamera);
				break;
			default:
			case Screen:
				S3.viewPort = new ScreenViewport(S3.fixedCamera);
				break;
			case Fill:
				S3.viewPort = new FillViewport(width, height, S3.fixedCamera);
				break;
		}

		S3.viewPort.update(S3Screen.screenWidth, S3Screen.screenHeight, true);
		return S3.viewPort;
	}

}
