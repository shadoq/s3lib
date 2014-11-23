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
package mobi.shad.s3lib.main;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import mobi.shad.s3lib.audio.MusicPlayer;
import mobi.shad.s3lib.gfx.g2d.EffectCreator;
import mobi.shad.s3lib.gfx.g2d.screen.ScreenManager;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.constans.MusicPlayType;

import java.io.File;
import java.lang.StringBuilder;

/**
 * Main application class, used composite design pattern
 * to merge other class method
 */
public class S3 implements ApplicationListener{

	//
	// Variables for editor
	//
	public static final S3Cfg cfg = new S3Cfg();
	public static final JsonReader jsonReader = new JsonReader();
	public static final Json json = new Json();
	public static final ArrayMap<String, String> appMaps = new ArrayMap<>(10);
	public static JsonValue cfgJson = null;
	public static boolean saveEnable = false;
	public static boolean disableEffect = true;
	public static String saveBasePackage = "src.screen.";
	public static String currentAppName = "";
	public static int sceneIndex = 0;
	public static ClassLoader classLoader = null;

	//
	// Variables for main class
	//
	public static S3 instance;
	public static S3App app;
	public static S3Gfx gfx;
	public static Graphics graphics;
	public static GL20 gl;
	public static Input input;
	public static Audio audio;
	public static Viewport viewPort;
	public static Stage preRenderStage;
	public static Stage stage;
	public static Stage postRenderStage;
	public static Skin skin;
	public static String currentDir;
	public static InputMultiplexer inputMultiplexer;
	public static ApplicationType osPlatform;
	public static float osTime = 0;
	public static float appTime = 0;
	public static float osDeltaTime = 0;
	public static OrthographicCamera fixedCamera;
	public static ShapeRenderer shapeRenderer;
	public static SpriteBatch spriteBatch;
	public static boolean renderStage = true;
	public static boolean realoadContext = false;

	public static Vector3 mouseScreen = new Vector3();
	public static Vector3 mouseUnproject = new Vector3();

	public static Vector2 mouse = new Vector2();
	public static Vector2 mouseButton0 = new Vector2();
	public static Vector2 mouseButton1 = new Vector2();

	protected static String TAG = "S3";
	protected static boolean LOG = true;
	protected static int inputProcessors;
	private static Label debugBar;

	public S3(){
		app = new S3AppImpl();
	}

	public S3(S3App application){
		super();
		app = application;
	}

	public static S3 getInstance(){
		return instance;
	}

	/**
	 * Add input processor to multiple queue
	 *
	 * @param processor
	 * @return
	 */
	public static int addInputProcessor(InputProcessor processor){
		inputMultiplexer.addProcessor(processor);
		inputProcessors = inputMultiplexer.size();
		return inputProcessors;
	}

	public static int addInputFirstProcessor(InputProcessor processor){
		inputMultiplexer.addProcessor(0, processor);
		inputProcessors = inputMultiplexer.size();
		return inputProcessors;
	}

	/**
	 * Remove last processor form inputs queue
	 *
	 * @return
	 */
	public static int removeLastInputProcessor(){
		inputMultiplexer.removeProcessor(inputProcessors - 1);
		inputProcessors = inputMultiplexer.size();
		return inputProcessors;
	}

	/**
	 * Add Scene Definition to App Maps
	 *
	 * @param sceneName
	 * @param def
	 */
	public static void addApp(String sceneName, String def){
		if (sceneName == null || sceneName.isEmpty()){
			return;
		}
		String srcName = sceneName;
		if (!sceneName.contains(saveBasePackage) && !sceneName.contains("s3lib.editor")){
			srcName = saveBasePackage + sceneName;
		}
		S3Log.info(TAG, "Add app: " + sceneName + " src: " + srcName);
		S3.appMaps.put(srcName, def);
	}

	public static void deleteApp(String sceneName){
		if (sceneName == null || sceneName.isEmpty()){
			return;
		}
		String srcName = sceneName;
		if (!sceneName.contains(saveBasePackage) && !sceneName.contains("s3lib.editor")){
			srcName = saveBasePackage + sceneName;
		}
		S3Log.info(TAG, "Delete app: " + sceneName + " src: " + srcName);
		S3.appMaps.removeKey(srcName);
	}

	/**
	 *
	 */
	public synchronized static void saveAppDef(){
		saveAppDef(currentAppName);
	}

	/**
	 * @param sceneName
	 */
	public synchronized static void saveAppDef(String sceneName){
		if (!saveEnable){
			return;
		}
		if (sceneName == null || sceneName.isEmpty()){
			S3Log.log(TAG, "AppMaps error [1]: " + sceneName);
			return;
		}
		String srcName = sceneName;
		if (!sceneName.contains(saveBasePackage) && !sceneName.contains("s3lib.editor")){
			srcName = saveBasePackage + sceneName;
		}
		if (!appMaps.containsKey(srcName)){
			S3Log.log(TAG, "AppMaps error [2]: " + srcName);
			return;
		}
		saveEnable = false;
		StringBuilder sb = new StringBuilder();

		S3App localApp = (S3App) stage.getRoot().findActor(app.screenName);
		S3AppImpl insertApp = new S3AppImpl();
		if (localApp == null){
			return;
		}
		insertApp.screenName = localApp.screenName;
		insertApp.screenColor = localApp.screenColor;
		insertApp.screenTransition = localApp.screenTransition;
		insertApp.screenTransitionDuration = localApp.screenTransitionDuration;
		insertApp.screenTransitionInterpolation = localApp.screenTransitionInterpolation;

		insertApp.musicPlayType = localApp.musicPlayType;
		insertApp.sceneBackground = localApp.sceneBackground;
		insertApp.screenMusic = localApp.screenMusic;

		String fileDef = currentDir + "assets/data/config/" + cfg.title.toLowerCase() + "_scene.dat";
		S3Log.log(TAG, "Save app def: " + sceneName + " to: " + fileDef + " scene: " + localApp.getClass().getName());
		for (Actor actor : app.getChildren()){
			S3Log.log(TAG, "Save actor: " + actor.getName() + " class: " + actor.getClass().getName());
			sb.append(json.toJson(actor));
			sb.append("\n");
		}
		sb.append(json.toJson(insertApp));
		sb.append("\n");
		appMaps.put(srcName, sb.toString());

		Gdx.files.absolute(fileDef)
				 .writeString(json.toJson(appMaps, ArrayMap.class, String.class), false);
		sb = null;
		S3ResourceManager.saveAssert();
	}

	public static S3App getApp(){
		return app;
	}

	public static void setApp(S3App app){
		S3Log.log(TAG, "Set scene: " + app.toString());
		S3.app = app;
	}

	public static void setApp(final String sceneName){

		if (sceneName == null){
			S3Log.log(TAG, "Scene name are NULL");
			return;
		}

		String srcName = sceneName;
		if (!sceneName.contains(saveBasePackage) && !sceneName.contains("s3lib.editor")){
			srcName = saveBasePackage + sceneName;
		}

		if (!appMaps.containsKey(srcName)){
			S3Log.log(TAG, "setApp ... not find app def: " + srcName);
			return;
		}

		sceneIndex = appMaps.keys().toArray().indexOf(srcName, false);
		if (app != null){
			app.pause();
			app.dispose();
			app = null;
		}

		try {
			srcName = srcName.replace("src.", "");
			Object sceneClass = null;
			if (classLoader != null){
				if (classLoader.loadClass(srcName) != null){
					sceneClass = classLoader.loadClass(srcName).newInstance();
				}
			} else {
				sceneClass = Class.forName(srcName).newInstance();
			}
			if (sceneClass != null){
				app = (S3App) sceneClass;
			} else {
				app = new S3AppImpl();
			}
			S3Log.log(TAG,
					  "setApp classLoader: " + classLoader + " sceneIndex: " + sceneIndex + " sceneName: " + sceneName + " srcName: " + srcName + " sceneClass: " + sceneClass);

		} catch (InstantiationException e){
			S3Log.log(TAG, "Scene `" + srcName + "` cannot be created, check if scene class constructor is empty ...");
			e.printStackTrace();
		} catch (IllegalAccessException e){
			S3Log.log(TAG, "Scene `" + srcName + "` cannot be created, check if scene class can be found");
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			S3Log.log(TAG, "Scene `" + srcName + "` cannot be created, check if scene class can be found");
			e.printStackTrace();
		}

		loadAppDef(sceneName);

		if (app != null){
			app.initalize();
			app.resize();
		}
	}

	public static void setAppWithDelay(final String sceneName, float delay){

		S3Log.log("setAppWithDelay", "sceneName: " + sceneName + " delay: " + delay);
		Timer.schedule(new Timer.Task(){
			@Override
			public void run(){
				S3Log.log("setAppWithDelay", "Set scene: " + sceneName);
				setApp(sceneName);
			}
		}, delay);
	}

	/**
	 * @param sceneName
	 */
	public synchronized static void loadAppDef(String sceneName){

		if (sceneName == null || sceneName.isEmpty()){
			S3Log.log(TAG, "Error load app def: " + sceneName);
			return;
		}

		String srcName = sceneName;
		if (!sceneName.contains(saveBasePackage) && !sceneName.contains("s3lib.editor")){
			srcName = saveBasePackage + sceneName;
		}
		if (!appMaps.containsKey(srcName)){
			S3Log.log(TAG, "loadAppDef ... not find app def: " + srcName);
			return;
		}

		S3Log.log(TAG, "Load app def: " + sceneName + " src: " + srcName);

		if (app != null){
			app.clear();
		}

		if (stage != null){
			stage.getRoot().clearActions();
			stage.getRoot().clearChildren();
			stage.getRoot().removeActor(app);
		}

		String str = appMaps.get(srcName);
		String[] lines = str.split("\n");

		for (String line : lines){
			if (line == null){
				continue;
			}
			if (line.trim().isEmpty()){
				continue;
			}
			json.fromJson(null, line);
		}

		if (stage != null){
			app.screenName = sceneName;
			app.setName(app.screenName);
		}

		S3Log.log(TAG, "--------------------------------------");
		S3Log.log(TAG, "Load App: " + app.getName());
		S3Log.log(TAG, "Load App: " + app.toString());
		S3Log.log(TAG, "--------------------------------------");

		if (stage != null){
			stage.addActor(app);
			if (!app.screenTransition.equals("None") && disableEffect == false){
				EffectCreator.createEffect(app, app.screenTransition, 0f,
										   app.screenTransitionDuration, app.screenTransitionInterpolation);
			}
			if (!app.sceneBackground.equals("None")){
				app.setBackground(app.sceneBackground);
			} else {
				app.removeBackground();
			}
		}

		if (!app.screenMusic.equals("None") && app.musicPlayType != MusicPlayType.None && disableEffect == false){
			MusicPlayer.getInstance().play(app.screenMusic, app.musicPlayType);
		} else if (app.musicPlayType == MusicPlayType.None){
			MusicPlayer.getInstance().stop();
		}
		currentAppName = app.screenName;
		appTime = 0;
	}

	@Override
	public void create(){

		Serializer.initialize();

		instance = this;

		graphics = Gdx.app.getGraphics();
		input = Gdx.app.getInput();
		audio = Gdx.app.getAudio();
		osPlatform = Gdx.app.getType();
		gl = graphics.getGL20();

		//
		// Odczyt bierzącego katalogu aplikacji
		//
		if (currentDir == null){
			try {
				File dir1 = new File(".");
				currentDir = dir1.getCanonicalPath() + "/";
				//				currentDir = this.getClass().getClassLoader().getResource("").getPath();
			} catch (Exception e){
				//				try {
				//					File dir1 = new File(".");
				//					currentDir = dir1.getCanonicalPath();
				currentDir = this.getClass().getClassLoader().getResource("").getPath();
				//				} catch (IOException io){
				//
				//				}
			}
		}

		if (LOG){
			S3Log.info(TAG, "Current dir: " + currentDir);
			S3Log.info(TAG, "External dir: " + Gdx.files.getExternalStoragePath());
			S3Log.info(TAG, S3.cfg.toString());
		}

		//
		// Inicjacja procedur okien
		//
		S3Screen.init();
		S3Constans.setup();
		ScreenManager.resize(S3Screen.width, S3Screen.height);
		S3Skin.init();
		skin = S3Skin.getSkin();
		stage = new Stage(viewPort);
		postRenderStage = new Stage(viewPort);
		preRenderStage = new Stage(viewPort);
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();

		//
		// Inicjacja input procesora
		//
		if (LOG){
			S3Log.info(TAG, "Init GestureDetector listener  ...");
		}

		inputMultiplexer = new InputMultiplexer();

		final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.GestureListener(){

			@Override
			public boolean touchDown(float x, float y, int pointer, int button){
				return false;
			}

			@Override
			public boolean zoom(float initialDistance, float distance){
				if (app != null){
					try {
						app.onZoom(initialDistance, distance);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onZoom ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button){
				y = S3Screen.height - y;
				if (app != null){
					try {
						app.onTap(x, y, count, button);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onTap ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2){
				if (app != null){
					try {
						app.onPinch(initialPointer1, initialPointer2, pointer1, pointer2);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onPinch ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY){
				y = S3Screen.height - y;
				if (app != null){
					try {
						app.onPan(x, y, deltaX, deltaY);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onPan ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean panStop(float x, float y, int pointer, int button){
				y = S3Screen.height - y;
				if (app != null){
					try {
						app.onPanStop(x, y, pointer, button);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onPanStop ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean longPress(float x, float y){
				y = S3Screen.height - y;
				if (app != null){
					try {
						app.onLongPress(x, y);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onLongPress ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button){
				if (app != null){
					try {
						app.onFling(velocityX, velocityY, button);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onFling ...", ex);
					}
				}
				return false;
			}
		});

		if (LOG){
			S3Log.info(TAG, "Init InputProcessor listener  ...");
		}

		final InputProcessor inputProcessornew = new InputProcessor(){

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer){

				mouseScreen.x = screenX;
				mouseScreen.y = screenY;
				mouseScreen.z = 0;

				mouseUnproject = fixedCamera.unproject(mouseScreen);

				if (app != null){
					try {
						app.onDrag((int) mouseUnproject.x, (int) mouseUnproject.y);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onDrag ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button){

				mouseScreen.x = screenX;
				mouseScreen.y = screenY;
				mouseScreen.z = 0;

				mouseUnproject = fixedCamera.unproject(mouseScreen);

				if (button == 0){
					mouseButton0.set(mouseUnproject.x, mouseUnproject.y);
				}
				if (button == 1){
					mouseButton1.set(mouseUnproject.x, mouseUnproject.y);
				}
				if (app != null){
					try {
						app.onTouchDown((int) mouseUnproject.x, (int) mouseUnproject.y, button);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onTouchDown ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button){

				mouseScreen.x = screenX;
				mouseScreen.y = screenY;
				mouseScreen.z = 0;

				mouseUnproject = fixedCamera.unproject(mouseScreen);

				if (app != null){
					try {
						app.onTouchUp(screenX, screenY, button);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onTouchUp ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean scrolled(int amount){
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY){

				mouseScreen.x = screenX;
				mouseScreen.y = screenY;
				mouseScreen.z = 0;

				mouseUnproject = fixedCamera.unproject(mouseScreen);

				mouse.set(mouseUnproject.x, mouseUnproject.y);
				if (app != null){
					try {
						app.onMouseMoved((int) mouseUnproject.x, (int) mouseUnproject.y);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onMouseMoved ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean keyUp(int keycode){
				if (app != null){
					try {
						app.onKeyUp(keycode);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onKeyUp ...", ex);
					}
				}
				return false;
			}

			@Override
			public boolean keyTyped(char character){
				return false;
			}

			@Override
			public boolean keyDown(int keycode){
				if (app != null){
					try {
						app.onKeyDown(keycode);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onKeyDown ...", ex);
					}
				}
				return false;
			}
		};

		final ClickListener clickListener = new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y){

				if (app != null){
					try {
						final Actor hit = app.hit((int) x, (int) y);
						if (hit != null){
							app.onClick(event, hit, (int) x, (int) y);
						}
						app.onClick(event, (int) x, (int) y);
					} catch (Exception ex){
						S3Log.error("S3", "Exception onClick ...", ex);
					}
				}
			}
		};

		//
		// Inicjacja podklas
		//
		S3Gfx.initalize(graphics, gl, stage, skin, fixedCamera, shapeRenderer, spriteBatch);
		S3Math.init();
		S3Lang.init();

		//
		// Załadowanie danych assetów
		//
		S3ResourceManager.asset.clear();
		if (Gdx.files.internal(currentDir + "assets/data/config/" + S3.cfg.projectFile.toLowerCase() + "_asset.dat").exists()){
			try {
				JsonValue av = S3.jsonReader.parse(Gdx.files.internal(currentDir + "assets/data/config/" + S3.cfg.projectFile.toLowerCase() + "_asset.dat"));
				for (JsonValue jv : av.iterator()){
					S3ResourceManager.asset.put(jv.name, jv.asString());
				}
			} catch (SerializationException ex){
				S3Log.info(TAG, "Error parse data ...");
			}
			S3Log.info(TAG, "--------------------------------------");
			S3Log.info(TAG, " Asset data: " + S3ResourceManager.asset);
			S3Log.info(TAG, "--------------------------------------");
		}
		S3ResourceManager.initialize();

		//
		// Załadowanie danych sceny
		//
		appMaps.clear();
		String mapsFile = "config/" + S3.cfg.projectFile.toLowerCase() + "_scene.dat";
		if (LOG){
			S3Log.info(TAG, "Load maps data: " + mapsFile);
		}
		final FileHandle mapsFileHandle = S3File.getFileHandle(mapsFile);
		if (mapsFileHandle != null && mapsFileHandle.exists()){
			try {
				JsonValue sv = S3.jsonReader.parse(mapsFileHandle);
				for (JsonValue jv : sv.iterator()){
					appMaps.put(jv.name, jv.asString());
				}
			} catch (SerializationException ex){
				S3Log.info(TAG, "Error parse data ...");
			}

			S3Log.info(TAG, "--------------------------------------");
			S3Log.info(TAG, " Scene data: " + appMaps);
			S3Log.info(TAG, "--------------------------------------");
		} else {
			S3Log.info(TAG, "File not extis ..." + mapsFile);
		}

		if (appMaps != null && appMaps.size > 0){
			if (LOG){
				S3Log.info(TAG, "--------------------------------------");
				S3Log.info(TAG, " Load scene data: " + appMaps.firstKey());
				S3Log.info(TAG, "--------------------------------------");
			}
			loadAppDef(appMaps.firstKey().replace(saveBasePackage, ""));
		}

		//
		// Inicjacja Appki
		//
		if (LOG){
			S3Log.info(TAG, "--------------------------------------");
			S3Log.info(TAG, " Init main app  ...");
			S3Log.info(TAG, "--------------------------------------");
		}
		if (app != null){
			app.initalize();
		}
		disableEffect = false;

		//
		// Zapisywanie pamięci po inicjacji aplikacji
		//
		Runtime rt = Runtime.getRuntime();
		S3Log.info(TAG, "- Memory: -----------------------------");
		S3Log.info(TAG, " Free memory: " + (rt.freeMemory() / 1024 / 1024) + " MB");
		S3Log.info(TAG, " Max memory: " + (rt.maxMemory() / 1024 / 1024) + " MB");
		S3Log.info(TAG, " Total memory: " + (rt.totalMemory() / 1024 / 1024) + " MB");
		S3Log.info(TAG, " Java Heap: " + (Gdx.app.getJavaHeap() / 1024 / 1024) + " MB");
		S3Log.info(TAG, " Native Heap: " + (Gdx.app.getNativeHeap() / 1024 / 1024) + " MB");
		S3Log.info(TAG, "- Memory: -----------------------------");

		//
		//
		//
		debugBar = GuiResource.label(S3Lang.get("Debug"), "Debug");
		debugBar.setX(10);
		debugBar.setY(10);
		debugBar.setVisible(true);

		if (app != null){
			stage.addActor(app);
		}
		postRenderStage.addActor(debugBar);


		inputMultiplexer.addProcessor(inputProcessornew);
		inputMultiplexer.addProcessor(gestureDetector);
		stage.addListener(clickListener);
		inputMultiplexer.addProcessor(stage);
		input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void resize(int width, int height){
		S3Log.info(TAG, "Resize App to: " + width + ":" + height);
		S3Screen.resize(width, height);
		S3Constans.setup();
		ScreenManager.resize(S3Screen.width, S3Screen.height);
		if (app != null){
			app.resize();
		}
	}

	@Override
	public void render(){

		if (realoadContext){
			realoadContext = false;
			S3ResourceManager.loadAssert();
			resume();
		}

		if (app != null && app.screenColor != null){
			S3Gfx.clear(app.screenColor);
		} else {
			S3Gfx.clear();
		}
		osDeltaTime = Gdx.graphics.getDeltaTime();
		if (cfg.pause == false){
			osTime = osTime + osDeltaTime;
			appTime = appTime + osDeltaTime;
		}

		if (app != null){
			if (cfg.pause == false){
				app.update();
			}
		}

		if (cfg.grid){
			S3Gfx.setColor(Color.DARK_GRAY);
			S3Gfx.drawGrid(0, 0, S3Screen.width, S3Screen.height, 10, 10);
		}

		if (app != null){
			app.preRender();
		}

		preRenderStage.act(osDeltaTime);
		preRenderStage.draw();

		if (app != null){
			if (cfg.pause == false){
				app.update();
			}
			app.render(gfx);
		}

		if (stage != null && renderStage == true){
			//
			// Resetowanie GUI dla texture0
			//
			S3Screen.gl20.glActiveTexture(GL20.GL_TEXTURE0);
			if (cfg.pause == false){
				stage.act(osDeltaTime);
			}
			stage.draw();
		}

		if (debugBar != null){
			if (cfg.debug){
				debugBar.setVisible(true);
				debugBar.setText("osT: " + String.format("%.2f", S3.osTime)
								 + " appT: " + String.format("%.2f", S3.appTime)
								 + " dT: " + String.format("%.4f", S3.osDeltaTime)
								 + " fps: " + S3.graphics.getFramesPerSecond()
								 + " h: " + (Gdx.app.getJavaHeap() / 1024) + " kb" + " m: "
								 + (Gdx.app.getNativeHeap() / 1024) + " kb");
			} else {
				debugBar.setVisible(false);
			}
		}

		if (cfg.grid){
			S3Gfx.drawCircle(mouse.x, mouse.y, 5f, Color.RED);
			if (mouseButton0.x > 0 && mouseButton0.y > 0){
				S3Gfx.drawCircle(mouseButton0.x, mouseButton0.y, 5f, Color.YELLOW);
			}
			if (mouseButton1.x > 0 && mouseButton1.y > 0){
				S3Gfx.drawCircle(mouseButton1.x, mouseButton1.y, 5f, Color.CYAN);
			}
		}

		postRenderStage.act(osDeltaTime);
		postRenderStage.draw();

		if (app != null){
			app.postRender();
		}

		if (osPlatform == ApplicationType.Desktop){
			String appTitle = S3Constans.appName + " - time: " + String.format("%.2f", osTime) + " fps: "
			+ graphics.getFramesPerSecond() + " h: " + (Gdx.app.getJavaHeap() / 1024) + " kb" + " m: "
			+ (Gdx.app.getNativeHeap() / 1024) + " kb" + " d: " + graphics.getDeltaTime();
			graphics.setTitle(appTitle);
		}

		try {
			if (1000 / 60 - osDeltaTime > 0){
				Thread.sleep((long) (1000 / 60 - osDeltaTime));
			}
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	@Override
	public void pause(){
		ScreenManager.pause();
		if (app != null){
			app.pause();
		}
	}

	@Override
	public void resume(){
		S3ResourceManager.rebind();
		ScreenManager.resume();
		if (app != null){
			app.resume();
		}
	}

	@Override
	public void dispose(){
		if (app != null){
			app.dispose();
			app = null;
		}
		MusicPlayer.getInstance().stop();
		S3ResourceManager.dispose();
		ScreenManager.dispose();
		if (stage != null){
			stage.dispose();
		}
		graphics = null;
		gfx = null;
		gl = null;
		input = null;
		audio = null;
		input = null;
		currentDir = null;
		S3Skin.skin = null;
		stage = null;
		S3Log.info(TAG, "Clean App Done");
		S3Log.dispose();
	}
}
