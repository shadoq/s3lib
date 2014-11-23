/*******************************************************************************
 * Copyright 2012
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
package mobi.shad.s3lib.gfx.g2d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.OrderedMap;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class ScreenManager{

	protected static final String logTag = "S3ScreenLog";
	protected static boolean logActive = true;
	protected static float backgroundColorR = 0.0f;
	protected static float backgroundColorG = 0.0f;
	protected static float backgroundColorB = 0.0f;
	protected static float backgroundColorA = 1.0f;
	protected static S3 application = null;
	protected static Screen curentScreen;
	protected static Screen lastScreen;
	protected static Screen nextScreen;
	protected static OrderedMap<String, Screen> screens = new OrderedMap<String, Screen>(10);
	protected static boolean isTransistion = false;
	protected static boolean isTransistionEnd = false;
	protected static float transistionDuration = 1;
	protected static float transistionProcent = 0;
	protected static float transistionTime = 0;
	protected static Color transistionColor = Color.BLACK;
	protected static Interpolation transistionInterpolation = Interpolation.linear;
	private static int width = S3Screen.width;
	private static int height = S3Screen.height;
	private static float transistionAlpha = 0;
	private static int transistionMode = 0;

	/**
	 * @param application
	 */
	public static void initialize(S3 application){
		ScreenManager.application = application;
	}

	/**
	 * @param screen
	 */
	public static synchronized void add(final Screen screen){
		if (application == null){
			return;
		}
		if (screen == null){
			return;
		}
		if (!screens.containsValue(screen, true)){
			screens.put(screen.getScreenName(), screen);
			if (logActive){
				S3Log.log(logTag, "Add scene: " + screen.getScreenName());
			}
		}
	}

	/**
	 * @param screen
	 * @param transition
	 */
	public static synchronized void setScreen(final Screen screen, final boolean transition){

		if (curentScreen == screen){
			return;
		}
		if (logActive){
			S3Log.log(logTag, "setScreen: " + screen.getScreenName() + " transition:" + transition);
		}

		if (transition == false){
			if (curentScreen != null){
				curentScreen.hide();
				curentScreen.exit();
			}
			add(screen);
			curentScreen = screen;
			if (curentScreen != null){
				curentScreen.resize(width, height);
				curentScreen.show();
			}
			isTransistion = false;
			isTransistionEnd = true;
		} else {
			transistionTime = 0;
			isTransistion = true;
			isTransistionEnd = false;
			lastScreen = curentScreen;
			curentScreen = screen;
			nextScreen = screen;
			add(screen);
		}
	}

	/**
	 * @return
	 */
	public static synchronized Screen getScreen(){
		return curentScreen;
	}

	/**
	 * @param screen
	 */
	public static synchronized void setScreen(final Screen screen){
		setScreen(screen, false);
	}

	/**
	 * @param delta
	 */
	public static void render(float delta){

		Gdx.gl.glClearColor(backgroundColorR, backgroundColorG, backgroundColorB, backgroundColorA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (isTransistion == false){
			if (curentScreen != null){
				curentScreen.render(delta);
			}
		} else {
			transistionTime += delta;
			transistionProcent = transistionTime / transistionDuration;
			transistionAlpha = transistionInterpolation.apply(0f, 1f, transistionProcent);

			if (transistionProcent < 0.5){
				if (lastScreen != null){
					lastScreen.render(delta);
				}
			} else {
				if (isTransistionEnd == false){
					isTransistionEnd = true;
					if (lastScreen != null){
						lastScreen.hide();
						lastScreen.exit();
					}
					if (nextScreen != null){
						nextScreen.resize(width, height);
						nextScreen.show();
					}
				}
				if (nextScreen != null){
					nextScreen.render(delta);
				}
			}
			if (transistionProcent > 1){
				isTransistion = false;
				lastScreen = null;
				curentScreen = nextScreen;
				nextScreen = null;
			}

			switch (transistionMode){
				default:
					transistionColor.a = (transistionAlpha > 0.5f) ? 1 - (transistionAlpha * 2 - 1) : (transistionAlpha * 2);
					break;
				case 1:
					transistionColor.a = transistionAlpha;
					break;
				case 2:
					transistionColor.a = 1 - transistionAlpha;
					break;
			}
			if (transistionColor.a < 0.001f){
				transistionColor.a = 0;
			}
			if (transistionColor.a > 0.999f){
				transistionColor.a = 1f;
			}
			S3Gfx.drawBackground(transistionColor, 0, 0, width, height);
		}
	}

	/**
	 *
	 */
	public static void show(){
		if (application == null){
			return;
		}
		if (curentScreen != null){
			if (logActive){
				S3Log.log(logTag, "show: " + curentScreen.getScreenName());
			}
			curentScreen.show();
		}
	}

	/**
	 *
	 */
	public static void hide(){
		if (application == null){
			return;
		}
		if (curentScreen != null){
			if (logActive){
				S3Log.log(logTag, "hide: " + curentScreen.getScreenName());
			}
			curentScreen.hide();
		}
	}

	/**
	 *
	 */
	public static void pause(){
		if (curentScreen != null){
			if (logActive){
				S3Log.log(logTag, "pause: " + curentScreen.getScreenName());
			}
			curentScreen.pause();
		}
	}

	/**
	 *
	 */
	public static void resume(){
		if (curentScreen != null){
			if (logActive){
				S3Log.log(logTag, "resume: " + curentScreen.getScreenName());
			}
			curentScreen.resume();
		}
	}

	/**
	 *
	 */
	public static void dispose(){
		if (curentScreen != null){
			if (logActive){
				S3Log.log(logTag, "dispose: " + curentScreen.getScreenName());
			}
			curentScreen.hide();
			curentScreen.dispose();
		}
		curentScreen = null;
		screens.clear();
	}

	/**
	 * @param width
	 * @param height
	 */
	public static void resize(int width, int height){
		if (curentScreen != null){
			curentScreen.resize(width, height);
		}
	}

	/**
	 * @param backgroundColor
	 */
	public static void setBackgroundColor(final Color backgroundColor){
		backgroundColorR = backgroundColor.r;
		backgroundColorG = backgroundColor.g;
		backgroundColorB = backgroundColor.b;
		backgroundColorA = backgroundColor.a;
	}

	/**
	 * @return
	 */
	public static float getBackgroundColorR(){
		return backgroundColorR;
	}

	/**
	 * @param backgroundColorR
	 */
	public static void setBackgroundColorR(float backgroundColorR){
		ScreenManager.backgroundColorR = backgroundColorR;
	}

	/**
	 * @return
	 */
	public static float getBackgroundColorG(){
		return backgroundColorG;
	}

	/**
	 * @param backgroundColorG
	 */
	public static void setBackgroundColorG(float backgroundColorG){
		ScreenManager.backgroundColorG = backgroundColorG;
	}

	/**
	 * @return
	 */
	public static float getBackgroundColorB(){
		return backgroundColorB;
	}

	/**
	 * @param backgroundColorB
	 */
	public static void setBackgroundColorB(float backgroundColorB){
		ScreenManager.backgroundColorB = backgroundColorB;
	}

	/**
	 * @return
	 */
	public static float getBackgroundColorA(){
		return backgroundColorA;
	}

	/**
	 * @param backgroundColorA
	 */
	public static void setBackgroundColorA(float backgroundColorA){
		ScreenManager.backgroundColorA = backgroundColorA;
	}

	/**
	 * @return
	 */
	public static int getWidth(){
		return width;
	}

	/**
	 * @param width
	 */
	public static void setWidth(int width){
		ScreenManager.width = width;
	}

	/**
	 * @return
	 */
	public static int getHeight(){
		return height;
	}

	/**
	 * @param height
	 */
	public static void setHeight(int height){
		ScreenManager.height = height;
	}

	/**
	 * @return
	 */
	public static float getTransistionDuration(){
		return transistionDuration;
	}

	/**
	 * @param transistionDuration
	 */
	public static void setTransistionDuration(float transistionDuration){
		ScreenManager.transistionDuration = transistionDuration;
	}

	/**
	 * @return
	 */
	public static Color getTransistionColor(){
		return transistionColor;
	}

	/**
	 * @param transistionColor
	 */
	public static void setTransistionColor(final Color transistionColor){
		ScreenManager.transistionColor = transistionColor;
	}

	/**
	 * @return
	 */
	public static boolean isIsTransistion(){
		return isTransistion;
	}

	/**
	 * @return
	 */
	public static boolean isIsTransistionEnd(){
		return isTransistionEnd;
	}

	/**
	 * @return
	 */
	public static Interpolation getTransistionInterpolation(){
		return transistionInterpolation;
	}

	/**
	 * @param transistionInterpolation
	 */
	public static void setTransistionInterpolation(Interpolation transistionInterpolation){
		ScreenManager.transistionInterpolation = transistionInterpolation;
	}
}
