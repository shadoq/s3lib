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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3Screen;

/**
 * Klasa Screen implementująca obsługę ekranu (full screen), odpowada ona screen
 * activity w systemie Android
 *
 * @author Jarek
 */
public class Screen implements com.badlogic.gdx.Screen, NodeInterface{

	protected static final String logTag = "S3ScreenLog";
	protected static boolean logActive = true;
	protected static int screenCount = 0;
	protected final S3App application;
	protected final Viewport viewPort;
	protected final Stage stage;
	protected final Actor actor;
	protected final TextureRegion backGroundTexture;
	protected final Image backGroundImage;
	protected final Array<NodeInterface> nodes;
	protected String screenName = "Screen";
	protected long startTime = System.nanoTime();
	protected long secondsTime = 0L;
	protected float stateTime = 0.0f;
	protected boolean isBackButtonActive = false;

	/**
	 *
	 */
	public Screen(){
		this(null, "Screen_" + screenCount, null, null);
	}

	/**
	 * @param application
	 */
	public Screen(S3App application){
		this(application, "Screen_" + screenCount, null, null);
	}

	/**
	 * @param backGroundTexture
	 */
	public Screen(TextureRegion backGroundTexture){
		this(null, "Screen_" + screenCount, backGroundTexture, null);
	}

	/**
	 * @param backGroundTexture
	 */
	public Screen(Texture backGroundTexture){
		this(null, "Screen_" + screenCount, new TextureRegion(backGroundTexture), null);
	}

	/**
	 * @param application
	 * @param backGroundTexture
	 */
	public Screen(S3App application, Texture backGroundTexture){
		this(application, "Screen_" + screenCount, new TextureRegion(backGroundTexture), null);
	}

	/**
	 * @param application
	 * @param screenName
	 * @param backGroundTexture
	 * @param actor
	 */
	public Screen(S3App application, String screenName, TextureRegion backGroundTexture, Actor actor){

		if (logActive){
			S3Log.log(logTag, "Create screen: " + screenName + " backGroundTexture: " + backGroundTexture + " actor: "
			+ actor);
		}

		nodes = new Array<NodeInterface>(10);

		this.screenName = screenName;
		this.application = application;
		this.backGroundTexture = backGroundTexture;

		// stage=new Stage(S3Screen.width, S3Screen.height, false);
		viewPort = new com.badlogic.gdx.utils.viewport.FillViewport(S3Screen.width, S3Screen.height);
		stage = new Stage(viewPort);
		stage.getCamera().position.set(S3Screen.width / 2, S3Screen.height / 2, 0);
		S3.addInputProcessor(stage);

		if (backGroundTexture != null){
			backGroundImage = new Image(new TextureRegionDrawable(backGroundTexture), Scaling.stretch);
			backGroundImage.setFillParent(true);
			stage.addActor(backGroundImage);
		} else {
			backGroundImage = null;
		}

		if (actor != null){
			this.actor = actor;
			stage.addActor(actor);
		} else {
			this.actor = null;
		}

		screenCount++;
	}

	/**
	 * @return
	 */
	public static int getScreenCount(){
		return screenCount;
	}

	/**
	 * @param screenCount
	 */
	public static void setScreenCount(int screenCount){
		Screen.screenCount = screenCount;
	}

	/**
	 * @param isBackButtonActive
	 */
	public void setBackButtonActive(boolean isBackButtonActive){

		if (logActive){
			S3Log.log(logTag, "setBackButtonActive: " + isBackButtonActive);
		}

		Gdx.input.setCatchBackKey(isBackButtonActive);
		this.isBackButtonActive = isBackButtonActive;
	}

	/**
	 *
	 */
	protected void setBackBackButton(){
		stage.addListener(new InputListener(){
			@Override
			public boolean keyUp(InputEvent event, int keycode){
				if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE){
					if (isBackButtonActive){
						if (logActive){
							S3Log.log(logTag, "Press back button ... event: " + event + " keycode: " + keycode);
						}
						keyBackPressed();
					}
				}
				return false;
			}
		});
	}

	/**
	 *
	 */
	public void keyBackPressed(){
	}

	/**
	 * @param layer
	 */
	public void addLayer(Layer layer){

		if (logActive){
			S3Log.log(logTag, "addLayer layer: " + layer.getName());
		}

		nodes.add(layer);
		stage.addActor(layer);
	}

	/**
	 * @param layer
	 */
	public void removeLayer(Layer layer){

		if (logActive){
			S3Log.log(logTag, "removeLayer layer: " + layer.getName());
		}

		int index = nodes.indexOf(layer, false);
		if (index >= 0){
			nodes.removeIndex(index);
			stage.getRoot().removeActor(layer);
		}
	}

	/**
	 * @return
	 */
	public S3App getApplication(){
		return application;
	}

	/**
	 * @return
	 */
	public String getScreenName(){
		return screenName;
	}

	/**
	 * @param screenName
	 */
	public void setScreenName(String screenName){
		this.screenName = screenName;
	}

	/**
	 * @return
	 */
	public long getStartTime(){
		return startTime;
	}

	/**
	 * @return
	 */
	public long getSecondsTime(){
		return secondsTime;
	}

	/**
	 * @return
	 */
	public float getStateTime(){
		return stateTime;
	}

	/**
	 * @return
	 */
	public String getScreenTime(){
		int seconds = (int) (secondsTime % 60);
		int minutes = (int) ((secondsTime / 60) % 60);
		int hours = (int) ((secondsTime / 3600) % 24);
		String secondsStr = (seconds < 10 ? "0" : "") + seconds;
		String minutesStr = (minutes < 10 ? "0" : "") + minutes;
		String hoursStr = (hours < 10 ? "0" : "") + hours;
		return new StringBuilder().append(hoursStr).append(":").append(minutesStr).append(":").append(secondsStr)
								  .toString();
	}

	/**
	 * @return
	 */
	public boolean isIsBackButtonActive(){
		return isBackButtonActive;
	}

	/**
	 *
	 */
	public void enter(){

		if (logActive){
			S3Log.log(logTag, "enter layer ...");
		}

		int size = nodes.size;
		for (int i = 0; i < size; i++){
			nodes.get(i).enter();
		}
	}

	/**
	 *
	 */
	public void exit(){
		if (logActive){
			S3Log.log(logTag, "exit layer ...");
		}

		int size = nodes.size;
		for (int i = 0; i < size; i++){
			nodes.get(i).exit();
		}
	}

	/**
	 * @param delta
	 */
	@Override
	public void render(float delta){
		if (System.nanoTime() - startTime >= 1000000000){
			secondsTime++;
			startTime = System.nanoTime();
		}
		stateTime += delta;
		stage.act(delta);
		stage.draw();
	}

	/**
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height){
		viewPort.setWorldSize(width, height);
		stage.setViewport(viewPort);
	}

	/**
	 *
	 */
	@Override
	public void show(){
	}

	/**
	 *
	 */
	@Override
	public void hide(){
	}

	/**
	 *
	 */
	@Override
	public void pause(){
	}

	/**
	 *
	 */
	@Override
	public void resume(){
	}

	/**
	 *
	 */
	@Override
	public void dispose(){
	}

	/**
	 * @return
	 */
	@Override
	public String toString(){
		return new StringBuilder().append("S3Screen{screenName: ").append(screenName).append(" startTime: ")
								  .append(startTime).append(" secondsTime: ").append(secondsTime).append(" stateTime: ")
								  .append(stateTime).append('}').toString();
	}
}
