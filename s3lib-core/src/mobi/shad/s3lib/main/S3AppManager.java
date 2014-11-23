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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import mobi.shad.s3lib.main.interfaces.AppInterface;
import mobi.shad.s3lib.main.interfaces.AppScreenInterface;
import mobi.shad.s3lib.main.interfaces.KeyboardInterface;
import mobi.shad.s3lib.main.interfaces.TouchInterface;

public abstract class S3AppManager extends S3App implements AppInterface, AppScreenInterface, TouchInterface, KeyboardInterface{

	private static final String TAG = "S3AppManager";
	private static boolean logEnable = true;

	private Array<S3App> apps = new Array<S3App>();
	private S3App currentApp;
	private S3App lastApp;
	private S3App nextApp;
	private boolean isTransistion = false;
	private boolean isTransistionEnd = false;
	private float transistionDuration = 0.5f;
	private float transistionProcent = 0;
	private float transistionTime = 0;
	private Interpolation transistionInterpolation = Interpolation.linear;
	private float transistionAlpha = 0;
	private int transistionMode = 0;

	private Color backGroundColor = Color.BLACK;
	private Color transistionColor = Color.BLACK;

	private boolean pauseApp = false;

	public synchronized void add(final S3App app){

		if (apps.contains(app, true)){
			switchTo(app, false);
			return;
		}
		if (app != null){
			app.initalize();
			apps.add(app);
			currentApp = app;
		}
	}

	/**
	 * @param app
	 * @param transition
	 */
	public synchronized void switchTo(final S3App app, final boolean transition){

		if (currentApp == app){
			return;
		}
		if (logEnable){
			S3Log.log(TAG, "switchTo: " + app.getClass().getSimpleName() + " transition:" + transition);
		}

		if (transition == false){
			if (currentApp != null){
				currentApp.hide();
			}

			if (!apps.contains(app, true)){
				add(app);
			}

			if (app != null){
				app.show();
			}
			currentApp = app;
			isTransistion = false;
			isTransistionEnd = true;
		} else {
			transistionTime = 0;
			isTransistion = true;
			isTransistionEnd = false;
			S3.renderStage = false;

			if (!apps.contains(app, true)){
				if (logEnable){
					S3Log.log(TAG, "add: " + app.getClass().getSimpleName());
				}
				add(app);
			}

			lastApp = currentApp;
			nextApp = app;
		}
	}

	@Override
	public void update(){
		if (pauseApp){
			return;
		}
		if (isTransistion == false){
			if (currentApp != null){
				currentApp.update();
			}
		}
	}

	/**
	 * @param gfx
	 * @param delta
	 */
	public void render(final S3Gfx gfx){

		if (pauseApp){
			return;
		}

		S3.gl.glClearColor(backGroundColor.r, backGroundColor.g, backGroundColor.b, backGroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (isTransistion == false){
			if (currentApp != null){
				currentApp.render(gfx);
			}
		} else {
			transistionTime += S3.osDeltaTime;
			transistionProcent = transistionTime / transistionDuration;
			transistionAlpha = transistionInterpolation.apply(0f, 1f, transistionProcent);

			if (transistionProcent < 0.5){
				if (currentApp != null){
					currentApp.update();
					currentApp.render(gfx);
				}
			} else {
				if (isTransistionEnd == false){
					isTransistionEnd = true;
					if (lastApp != null){
						if (logEnable){
							S3Log.log(TAG, "hide: " + lastApp.getClass().getSimpleName());
						}
						lastApp.hide();
					}
					if (nextApp != null){
						if (logEnable){
							S3Log.log(TAG, "show: " + nextApp.getClass().getSimpleName());
						}
						nextApp.show();
					}
				}
				if (nextApp != null){
					nextApp.update();
					nextApp.render(gfx);
				}
			}
			if (transistionProcent > 1){
				isTransistion = false;
				lastApp = null;
				currentApp = nextApp;
				nextApp = null;
				S3.renderStage = true;
			}

			switch (transistionMode){
				default:
					transistionColor.a = (transistionAlpha > 0.5f) ? 1 - (transistionAlpha * 2 - 1)
					: (transistionAlpha * 2);
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

			if (S3.stage != null){
				//
				// Resetowanie GUI dla texture0
				//
				S3Screen.gl20.glActiveTexture(GL20.GL_TEXTURE0);
				S3.stage.act(S3.osDeltaTime);
				S3.stage.draw();
			}

			S3Gfx.drawBackground(transistionColor);
		}
	}

	@Override
	public void onKeyDown(int keycode){
		if (isTransistion == false){
			if (currentApp != null){
				currentApp.onKeyDown(keycode);
			}
		}
	}

	@Override
	public void onKeyUp(int keycode){
		if (isTransistion == false){
			if (currentApp != null){
				currentApp.onKeyUp(keycode);
			}
		}
	}

	@Override
	public void onTouchDown(int x, int y, int button){
		if (isTransistion == false){
			if (currentApp != null){
				currentApp.onTouchDown(x, y, button);
			}
		}
	}

	@Override
	public void onDrag(int x, int y){
		if (isTransistion == false){
			if (currentApp != null){
				currentApp.onDrag(x, y);
			}
		}
	}

	@Override
	public void onTouchUp(int x, int y, int button){
		if (isTransistion == false){
			if (currentApp != null){
				currentApp.onTouchUp(x, y, button);
			}
		}
	}

	@Override
	public void initalize(){
		if (isTransistion == false){
			if (currentApp != null){
				currentApp.initalize();
			}
		}
	}

	@Override
	public void pause(){
		for (S3App s3App : apps){
			s3App.pause();
		}
		pauseApp = true;
	}

	@Override
	public void resume(){
		for (S3App s3App : apps){
			s3App.resume();
		}
		pauseApp = false;
	}

	@Override
	public void dispose(){
		for (S3App s3App : apps){
			s3App.dispose();
		}
	}

	@Override
	public void resize(){
		for (S3App s3App : apps){
			s3App.resize();
		}
	}

	@Override
	public void show(){
		if (isTransistion == false){
			if (currentApp != null){
				currentApp.show();
			}
		}
	}

	@Override
	public void hide(){
		if (isTransistion == false){
			if (currentApp != null){
				currentApp.hide();
			}
		}
	}
}
