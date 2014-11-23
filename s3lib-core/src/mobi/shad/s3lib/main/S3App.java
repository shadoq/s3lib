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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.main.constans.InterpolationType;
import mobi.shad.s3lib.main.constans.MusicPlayType;
import mobi.shad.s3lib.main.constans.ScreenEffectType;
import mobi.shad.s3lib.main.interfaces.AppInterface;
import mobi.shad.s3lib.main.interfaces.AppScreenInterface;
import mobi.shad.s3lib.main.interfaces.KeyboardInterface;
import mobi.shad.s3lib.main.interfaces.TouchInterface;

/**
 * The main abstract GUI class contains GUI elements and necessary methods
 * This class used a pattern integration composition
 * of the various elements from system library
 */
public abstract class S3App extends Group implements AppInterface, AppScreenInterface, TouchInterface, KeyboardInterface{

	private static final String TAG = "S3App";
	private static final boolean enableDebug = false;

	public String screenName = "";
	public Color screenColor = new Color(Color.BLACK);
	public ScreenEffectType screenTransition = ScreenEffectType.None;
	public float screenTransitionDuration = 2f;
	public InterpolationType screenTransitionInterpolation = InterpolationType.Linear;

	public String screenMusic = "None";
	public MusicPlayType musicPlayType = MusicPlayType.None;
	public String sceneBackground = "None";
	private Image sceneBackgroundImage = null;

	/**
	 * Returns the current app class
	 */
	public static S3App getApp(){
		return S3.getApp();
	}

	//----------------------------------------------------------
	//-------------------------------------------------------------------
	// Input method
	//-------------------------------------------------------------------
	//----------------------------------------------------------
	@Override
	public void onClick(InputEvent event, int x, int y){
		if (enableDebug){
			S3Log.event(TAG, "onClick event: " + event + " x: " + x + " y:" + y);
		}
	}

	public void onClick(InputEvent event, Actor actor, int x, int y){
		if (enableDebug){
			S3Log.event(TAG, "onClick on Actor event: " + event + " actor: " + actor + " x: " + x + " y:" + y);
		}
	}

	@Override
	public void onDrag(int x, int y){
		if (enableDebug){
			S3Log.event(TAG, "onDrag x: " + x + " y:" + y);
		}
	}

	@Override
	public void onTouchDown(int x, int y, int button){
		if (enableDebug){
			S3Log.event(TAG, "onTouchDown x: " + x + " y:" + y + " button: " + button);
		}
	}

	@Override
	public void onTouchUp(int x, int y, int button){
		if (enableDebug){
			S3Log.event(TAG, "onTouchUp x: " + x + " y:" + y + " button: " + button);
		}
	}

	@Override
	public void onKeyDown(int keycode){
		if (enableDebug){
			S3Log.event(TAG, "onKeyDown keycode: " + keycode);
		}
		if (keycode == Input.Keys.MENU){
			Gdx.input.vibrate(300);
		}
	}

	@Override
	public void onKeyUp(int keycode){
		if (enableDebug){
			S3Log.event(TAG, "onKeyUp keycode: " + keycode);
		}
	}

	public void onMouseMoved(int screenX, int screenY){
	}

	//----------------------------------------------------------
	//-------------------------------------------------------------------
	// Gesture detection method
	//-------------------------------------------------------------------
	//----------------------------------------------------------
	public void onZoom(float initialDistance, float distance){
		if (enableDebug){
			S3Log.event(TAG, "onZoom initialDistance: " + initialDistance + " distance: " + distance);
		}
	}

	public void onTap(float x, float y, int count, int button){
		if (enableDebug){
			S3Log.event(TAG, "onTap x: " + x + " y: " + y + " count: " + count + " button: " + button);
		}
	}

	public void onPinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2){
		if (enableDebug){
			S3Log.event(TAG, "onPinch initialPointer1: " + initialPointer1 + " initialPointer2: " + initialPointer2
			+ " pointer1: " + pointer1 + " pointer2: " + pointer2);
		}
	}

	public void onPan(float x, float y, float deltaX, float deltaY){
		if (enableDebug){
			S3Log.event(TAG, "onPan x: " + x + " y: " + y + " deltaX: " + deltaX + " deltaY: " + deltaY);
		}
	}

	public void onPanStop(float x, float y, int pointer, int button){
		if (enableDebug){
			S3Log.event(TAG, "onPanStop x: " + x + " y: " + y + " pointer: " + pointer + " button: " + button);
		}
	}

	public void onLongPress(float x, float y){
		if (enableDebug){
			S3Log.event(TAG, "onLongPress x: " + x + " y: " + y);
		}
	}

	public void onFling(float velocityX, float velocityY, int button){
		if (enableDebug){
			S3Log.event(TAG, "onFling velocityX: " + velocityX + " velocityY: " + velocityY + " button: " + button);
		}
	}

	//----------------------------------------------------------
	//-------------------------------------------------------------------
	// Main abstract method
	//-------------------------------------------------------------------
	//----------------------------------------------------------
	@Override
	public abstract void initalize();

	@Override
	public abstract void update();

	@Override
	public void preRender(){
	}

	@Override
	public abstract void render(S3Gfx g);

	@Override
	public void postRender(){
	}

	@Override
	public void resize(){
		if (enableDebug){
			S3Log.event(TAG, "Resize ...");
		}
	}

	@Override
	public void pause(){
		if (enableDebug){
			S3Log.event(TAG, "Pause ...");
		}
	}


	@Override
	public void dispose(){
		if (enableDebug){
			S3Log.event(TAG, "Dispose ...");
		}
	}

	@Override
	public void resume(){
		if (enableDebug){
			S3Log.event(TAG, "Resume ...");
		}
	}

	@Override
	public void show(){
		if (enableDebug){
			S3Log.event(TAG, "Show ...");
		}
	}

	@Override
	public void hide(){
		if (enableDebug){
			S3Log.event(TAG, "Hide ...");
		}
	}

	@Override
	public String toString(){
		return "S3App{" +
		"screenName='" + screenName + '\'' +
		", screenColor=" + screenColor +
		", screenTransition=" + screenTransition +
		", screenTransitionDuration=" + screenTransitionDuration +
		", screenTransitionInterpolation=" + screenTransitionInterpolation +
		", screenMusic='" + screenMusic + '\'' +
		", musicPlayType=" + musicPlayType +
		'}';
	}

	//----------------------------------------------------------
	//----------------------------------------------------------
	// Scene method
	//----------------------------------------------------------
	//----------------------------------------------------------

	/**
	 * Load the app class and data for name
	 *
	 * @param sceneName - Application Name
	 */
	public static void setApp(String sceneName){
		S3.setApp(sceneName);
	}

	/**
	 * Load the app class and data for name with delay
	 *
	 * @param sceneName - Application Name
	 * @param delay - Dalay in second
	 */
	public static void setAppWithDelay(String sceneName, float delay){
		S3.setAppWithDelay(sceneName, delay);
	}

	/**
	 * Load the next app class and data
	 */
	public static void nextApp(){
		if (S3.sceneIndex <= S3.appMaps.size){
			S3.sceneIndex++;
		}
		S3.setApp(S3.appMaps.getKeyAt(S3.sceneIndex));
	}

	/**
	 * Load the previous app class and data
	 */
	public static void prevApp(){
		if (S3.sceneIndex >= 0){
			S3.sceneIndex--;
		}
		S3.setApp(S3.appMaps.getKeyAt(S3.sceneIndex));
	}

	/**
	 * Load the next app class and data with delay
	 *
	 * @param delay
	 */
	public static void nextAppWithDelay(float delay){
		if (S3.sceneIndex <= S3.appMaps.size){
			S3.sceneIndex++;
		}
		S3.setAppWithDelay(S3.appMaps.getKeyAt(S3.sceneIndex), delay);
	}

	/**
	 * Load the previous app class and data with delay
	 *
	 * @param delay
	 */
	public static void prevAppWithDelay(float delay){
		if (S3.sceneIndex >= 0){
			S3.sceneIndex--;
		}
		S3.setAppWithDelay(S3.appMaps.getKeyAt(S3.sceneIndex), delay);
	}


	/**
	 * Settings the application image background
	 * @param backgroundFileName
	 */
	public void setBackground(String backgroundFileName){
		if (sceneBackgroundImage != null){
			removeBackground();
		}
		if (S3ResourceManager.getTextureRegion(backgroundFileName) != null){
			sceneBackgroundImage = new Image(new TextureRegionDrawable(S3ResourceManager.getTextureRegion(
			backgroundFileName)), Scaling.stretch);
			sceneBackgroundImage.setFillParent(true);
			S3.preRenderStage.addActor(sceneBackgroundImage);
			sceneBackgroundImage.toBack();
		}
	}

	/**
	 * Remove the background image
	 */
	public void removeBackground(){
		S3.preRenderStage.getRoot().removeActor(sceneBackgroundImage);
	}

	/**
	 * Add actor to the application
	 *
	 * @param actor
	 * @param x
	 * @param y
	 */
	public void addActor(Actor actor, float x, float y){
		if (actor != null){
			actor.setPosition(x, y);
			addActor(actor);
		}
	}
	 /**
	 * Add actor to the application with delay
	 * @param actor
	 * @param delay
	 */
	public void addActorWithDelay(final Actor actor, float delay){
		Timer.schedule(new Timer.Task(){
			@Override
			public void run(){
				addActor(actor);
			}
		}, delay);
	}

	/**
	 * Remove actor form application
	 *
	 * @param actorName
	 * @return
	 */
	public boolean removeActor(String actorName){
		return removeActor(findActor(actorName));
	}

	/**
	 * Remove actor form application with delay
	 *
	 * @param actor
	 * @param delay
	 */
	public void removeActorWithDelay(Actor actor, float delay){
		addAction(Actions.sequence(Actions.delay(delay), Actions.removeActor(actor)));
	}

	/**
	 * Finds the actor in App on the X/Y position
	 * @param x
	 * @param y
	 * @return
	 */
	public Actor hit(float x, float y){
		return hit(x, y, true);
	}

	/**
	 * Save text to log
	 * @param text
	 */
	public void log(String text){
		S3Log.log(screenName, text);
	}

	/**
	 * Return random float value on 0 to range
	 * @param range
	 * @return
	 */
	public float random(float range){
		return MathUtils.random(range);
	}

	/**
	 * Copy a basic parameters from source actor to the destination actor
	 *
	 * @param source
	 * @param destination
	 * @return
	 */
	public Actor copyActor(Actor source, Actor destination){
		GuiUtil.copyActor(source, destination);
		return destination;
	}

	/**
	 * Copy a basic parameters from source actor to the destination actor
	 * and replace in App
	 *
	 * @param source
	 * @param destination
	 * @return
	 */
	public Actor replaceActorInApp(Actor source, Actor destination){
		GuiUtil.copyActor(source, destination);
		removeActor(source);
		addActor(destination);
		return destination;
	}

	/**
	 * Show Toast GUI element
	 *
	 * @param message
	 * @param duration
	 */
	public void showToast(String message, float duration){
		Table table = new Table(S3.skin);
		table.add(message).center();
		table.setBackground(S3.skin.getDrawable("dialogDim"));
		table.pack();
		table.setPosition(S3Screen.width / 2 - table.getWidth() / 2, S3Screen.height / 2 - table.getHeight() / 2);
		addActor(table);
		table.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.4f), Actions.delay(duration), Actions.fadeOut(0.4f), Actions.removeActor(table)));
	}

	/**
	 * Show simple Dialog with OK button
	 *
	 * @param message
	 */
	public Dialog showDialog(String message){
		Dialog dialog = new Dialog("Dialog", S3.skin);
		dialog.getContentTable().add(message);
		dialog.button("OK", "OK");
		dialog.pack();
		dialog.show(getStage());
		dialog.setPosition(S3Screen.width / 2 - dialog.getWidth() / 2, S3Screen.height / 2 - dialog.getHeight() / 2);
		return dialog;
	}

	/**
	 * Show simple Dialog with OK button and custom title
	 *
	 * @param title
	 * @param message
	 */
	public Dialog showMessageDialog(String title, String message){
		Dialog dialog = new Dialog(title, S3.skin);
		dialog.getContentTable().add(message);
		dialog.button("OK", "OK");
		dialog.pack();
		dialog.show(getStage());
		dialog.setPosition(S3Screen.width / 2 - dialog.getWidth() / 2, S3Screen.height / 2 - dialog.getHeight() / 2);
		return dialog;
	}

	/**
	 * Show confirm Dialog with YES.NO button and custom title
	 *
	 * @param title
	 * @param message
	 * @return
	 */
	public Dialog showConfirmDialog(String title, String message){
		Dialog dialog = new Dialog(title, S3.skin);
		dialog.getContentTable().add(message);
		dialog.button("Yes", "Yes");
		dialog.button("No", "No");
		dialog.pack();
		dialog.show(getStage());
		dialog.setPosition(S3Screen.width / 2 - dialog.getWidth() / 2, S3Screen.height / 2 - dialog.getHeight() / 2);
		return dialog;
	}
}
