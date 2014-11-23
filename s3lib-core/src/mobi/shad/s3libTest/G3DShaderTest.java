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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.g3d.shaders.BaseShader;
import mobi.shad.s3lib.gfx.g3d.shaders.EffectShader;
import mobi.shad.s3lib.gui.Gui;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.gui.widget.WidgetStringListener;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class G3DShaderTest extends S3App{

	protected float start;
	protected float duration = 60;
	protected float localTime = 0;
	protected float sceneTime = 0;
	protected float endTime = 0;
	protected float procent = 0;
	private String objectName = "2D Plane";
	private String shaderName = "default";
	private String textureName = "texture/def256.png";
	//
	// ShaderOld data
	//
	private EffectShader.ShaderParameter shaderParameter = new EffectShader.ShaderParameter();
	private Color shaderColourStart = Color.BLACK;
	private Color shaderColourProcess = Color.WHITE;
	private float shaderAmplitudeColourR = 1f;
	private float shaderAmplitudeColourG = 1f;
	private float shaderAmplitudeColourB = 1f;
	//
	// Flash
	//
	private float shaderSpeedFlashR = 1f;
	private float shaderSpeedFlashG = 1f;
	private float shaderSpeedFlashB = 1f;
	private float shaderAmplitudeFlashR = 0f;
	private float shaderAmplitudeFlashG = 0f;
	private float shaderAmplitudeFlashB = 0f;
	//
	//
	//
	private float shaderMainSpeedX = 1f;
	private float shaderMainSpeedY = 1f;
	private float shaderMainSpeedZ = 1f;
	private float shaderMainAmplitudeX = 1f;
	private float shaderMainAmplitudeY = 1f;
	private float shaderMainAmplitudeZ = 1f;
	private float shaderMainStepX = 1f;
	private float shaderMainStepY = 1f;
	private float shaderMainStepZ = 1f;
	//
	//
	//
	private float shaderMouseSpeedX = 1f;
	private float shaderMouseSpeedY = 1f;
	private float shaderMouseAmplitudeX = 1f;
	private float shaderMouseAmplitudeY = 1f;
	private float shaderMouseStepX = 1f;
	private float shaderMouseStepY = 1f;
	//
	//
	//
	private float shaderMainIterations = 10f;
	private float shaderMainPower = 1f;
	private float shaderMainLight = 0.1f;
	//
	// Resuorce data
	//
	private Window window;
	private InputMultiplexer inputMultiplexer = null;
	private Texture texture;
	private BaseShader shader;
	private boolean initDone = false;
	private Label debugBar;
	private Label labelShaderName;
	private Label labelSshaderAutor;

	@Override
	public void initalize(){

		shader = new EffectShader();

		//
		// Gui
		//
		Gui gui = new Gui();

		ChangeListener listener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				createResuorce();
			}
		};

		WidgetStringListener listener2 = new WidgetStringListener(){
			@Override
			public void Action(String text){
				createResuorce();
			}
		};

		// --------------------------------------------------------
		// Gui Shader Parametr
		// --------------------------------------------------------
		String[] listOfShader = S3ResourceManager.getShaderList();
		gui.addList(S3Lang.get("shaderName"), this, "shaderName", listOfShader, shaderName, true, 1, listener);

		gui.addFileBrowser(S3Lang.get("textureName"), this, "textureName", textureName, true, 1, true, listener2);

		//
		//
		//
		gui.addLabel("Procedure", true, 1);

		final Slider sliderMainSpeedX = gui
		.addSlider(S3Lang.get("shaderMainSpeedX"), this, "shaderMainSpeedX", shaderMainSpeedX, -10f, 10f, 0.1f);
		final Slider sliderMainSpeedY = gui
		.addSlider(S3Lang.get("shaderMainSpeedY"), this, "shaderMainSpeedY", shaderMainSpeedY, -10f, 10f, 0.1f);
		final Slider sliderMainSpeedZ = gui
		.addSlider(S3Lang.get("shaderMainSpeedZ"), this, "shaderMainSpeedZ", shaderMainSpeedZ, -10f, 10f, 0.1f);

		ChangeListener listenerRandomMainSpeed = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderMainSpeedX = (float) (0.0 + (5.0 - (Math.random() * 5.0)));
				shaderMainSpeedY = (float) (0.0 + (5.0 - (Math.random() * 5.0)));
				shaderMainSpeedZ = (float) (0.0 + (5.0 - (Math.random() * 5.0)));

				sliderMainSpeedX.setValue(shaderMainSpeedX);
				sliderMainSpeedY.setValue(shaderMainSpeedY);
				sliderMainSpeedZ.setValue(shaderMainSpeedZ);
			}
		};
		gui.addButton("Random", listenerRandomMainSpeed);

		ChangeListener listenerResetMainSpeed = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderMainSpeedX = 1f;
				shaderMainSpeedY = 1f;
				shaderMainSpeedZ = 1f;

				sliderMainSpeedX.setValue(shaderMainSpeedX);
				sliderMainSpeedY.setValue(shaderMainSpeedY);
				sliderMainSpeedZ.setValue(shaderMainSpeedZ);

			}
		};
		gui.addButton("Reset", listenerResetMainSpeed);

		final Slider sliderMainAmplitudeX = gui
		.addSlider(S3Lang.get("shaderMainAmplitudeX"), this, "shaderMainAmplitudeX", shaderMainAmplitudeX, -10f, 10f, 0.1f);
		final Slider sliderMainAmplitudeY = gui
		.addSlider(S3Lang.get("shaderMainAmplitudeY"), this, "shaderMainAmplitudeY", shaderMainAmplitudeY, -10f, 10f, 0.1f);
		final Slider sliderMainAmplitudeZ = gui
		.addSlider(S3Lang.get("shaderMainAmplitudeZ"), this, "shaderMainAmplitudeZ", shaderMainAmplitudeZ, -10f, 10f, 0.1f);

		ChangeListener listenerRandomMainAmplitude = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderMainAmplitudeX = (float) (0.0 + (5.0 - (Math.random() * 5.0)));
				shaderMainAmplitudeY = (float) (0.0 + (5.0 - (Math.random() * 5.0)));
				shaderMainAmplitudeZ = (float) (0.0 + (5.0 - (Math.random() * 5.0)));

				sliderMainAmplitudeX.setValue(shaderMainAmplitudeX);
				sliderMainAmplitudeY.setValue(shaderMainAmplitudeY);
				sliderMainAmplitudeZ.setValue(shaderMainAmplitudeZ);

			}
		};
		gui.addButton(S3Lang.get("Random"), listenerRandomMainAmplitude);

		ChangeListener listenerResetMainAmplitude = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderMainAmplitudeX = 1f;
				shaderMainAmplitudeY = 1f;
				shaderMainAmplitudeZ = 1f;

				sliderMainAmplitudeX.setValue(shaderMainAmplitudeX);
				sliderMainAmplitudeY.setValue(shaderMainAmplitudeY);
				sliderMainAmplitudeZ.setValue(shaderMainAmplitudeZ);
			}
		};
		gui.addButton(S3Lang.get("Reset"), listenerResetMainAmplitude);

		final Slider sliderMainStepX = gui
		.addSlider(S3Lang.get("shaderMainStepX"), this, "shaderMainStepX", shaderMainStepX, -10f, 10f, 0.1f);
		final Slider sliderMainStepY = gui
		.addSlider(S3Lang.get("shaderMainStepY"), this, "shaderMainStepY", shaderMainStepY, -10f, 10f, 0.1f);
		final Slider sliderMainStepZ = gui
		.addSlider(S3Lang.get("shaderMainStepZ"), this, "shaderMainStepZ", shaderMainStepZ, -10f, 10f, 0.1f);

		ChangeListener listenerRandomMainStep = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderMainStepX = (float) (0.0 + (5.0 - (Math.random() * 5.0)));
				shaderMainStepY = (float) (0.0 + (5.0 - (Math.random() * 5.0)));
				shaderMainStepZ = (float) (0.0 + (5.0 - (Math.random() * 5.0)));

				sliderMainStepX.setValue(shaderMainStepX);
				sliderMainStepY.setValue(shaderMainStepY);
				sliderMainStepZ.setValue(shaderMainStepZ);

			}
		};
		gui.addButton(S3Lang.get("Random"), listenerRandomMainStep);

		ChangeListener listenerResetMainStep = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderMainStepX = 1f;
				shaderMainStepY = 1f;
				shaderMainStepZ = 1f;

				sliderMainStepX.setValue(shaderMainStepX);
				sliderMainStepY.setValue(shaderMainStepY);
				sliderMainStepZ.setValue(shaderMainStepZ);
			}
		};
		gui.addButton(S3Lang.get("Reset"), listenerResetMainStep);

		//
		// SetUp Process
		//
		gui.addLabel("Process Setup", true, 1);
		final Slider sliderMainIterations = gui
		.addSlider(S3Lang.get("shaderMainIterations"), this, "shaderMainIterations", shaderMainIterations, 1f, 255f, 1f);
		final Slider sliderMainPower = gui
		.addSlider(S3Lang.get("shaderMainPower"), this, "shaderMainPower", shaderMainPower, -1f, 1f, 0.1f);
		final Slider sliderMainLight = gui
		.addSlider(S3Lang.get("shaderMainLight"), this, "shaderMainLight", shaderMainLight, -1f, 1f, 0.1f);

		ChangeListener listenerRandomColour = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderMainIterations = (float) (1.0 + (int) (Math.random() * 100.0));
				shaderMainPower = (float) (0.0 + (1.0 - (Math.random() * 1.0)));
				shaderMainLight = (float) (0.0 + (1.0 - (Math.random() * 1.0)));

				sliderMainIterations.setValue(shaderMainIterations);
				sliderMainPower.setValue(shaderMainPower);
				sliderMainLight.setValue(shaderMainLight);
			}
		};
		gui.addButton(S3Lang.get("Random"), listenerRandomColour);

		ChangeListener listenerResetColour = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderMainIterations = 10f;
				shaderMainPower = 1f;
				shaderMainLight = 0.1f;

				sliderMainIterations.setValue(shaderMainIterations);
				sliderMainPower.setValue(shaderMainPower);
				sliderMainLight.setValue(shaderMainLight);
			}
		};
		gui.addButton(S3Lang.get("Reset"), listenerResetColour);

		//
		// Color Process
		//
		gui.addLabel(S3Lang.get("Color Process"), true, 1);
		final Slider sliderAmplitudeColourR = gui
		.addSlider(S3Lang.get("shaderAmplitudeColourR"), this, "shaderAmplitudeColourR", shaderAmplitudeColourR, -1f, 1f, 0.1f);
		final Slider sliderAmplitudeColourG = gui
		.addSlider(S3Lang.get("shaderAmplitudeColourG"), this, "shaderAmplitudeColourG", shaderAmplitudeColourG, -1f, 1f, 0.1f);
		final Slider sliderAmplitudeColourB = gui
		.addSlider(S3Lang.get("shaderAmplitudeColourB"), this, "shaderAmplitudeColourB", shaderAmplitudeColourB, -1f, 1f, 0.1f);

		ChangeListener listenerRandomMainSet = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderAmplitudeColourR = (float) (0.0 + (1.0 - (Math.random() * 1.0)));
				shaderAmplitudeColourG = (float) (0.0 + (1.0 - (Math.random() * 1.0)));
				shaderAmplitudeColourB = (float) (0.0 + (1.0 - (Math.random() * 1.0)));

				sliderAmplitudeColourR.setValue(shaderAmplitudeColourR);
				sliderAmplitudeColourG.setValue(shaderAmplitudeColourG);
				sliderAmplitudeColourB.setValue(shaderAmplitudeColourB);
			}
		};
		gui.addButton(S3Lang.get("Random"), listenerRandomMainSet);

		ChangeListener listenerResetMainSet = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderAmplitudeColourR = 1f;
				shaderAmplitudeColourG = 1f;
				shaderAmplitudeColourB = 1f;

				sliderAmplitudeColourR.setValue(shaderAmplitudeColourR);
				sliderAmplitudeColourG.setValue(shaderAmplitudeColourG);
				sliderAmplitudeColourB.setValue(shaderAmplitudeColourB);
			}
		};
		gui.addButton(S3Lang.get("Reset"), listenerResetMainSet);

		gui.addColorBrowser(S3Lang.get("shaderColourStart"), this, "shaderColourStart", shaderColourStart);
		gui.addColorBrowser(S3Lang.get("shaderColourProcess"), this, "shaderColourProcess", shaderColourProcess);

		//
		// Color Flash
		//
		gui.addLabel(S3Lang.get("Color Flash"), true, 1);
		final Slider sliderSpeedFlashR = gui
		.addSlider(S3Lang.get("shaderSpeedFlashR"), this, "shaderSpeedFlashR", shaderSpeedFlashR, -10f, 10f, 0.5f);
		final Slider sliderSpeedFlashG = gui
		.addSlider(S3Lang.get("shaderSpeedFlashG"), this, "shaderSpeedFlashG", shaderSpeedFlashG, -10f, 10f, 0.5f);
		final Slider sliderSpeedFlashB = gui
		.addSlider(S3Lang.get("shaderSpeedFlashB"), this, "shaderSpeedFlashB", shaderSpeedFlashB, -10f, 10f, 0.5f);

		final Slider sliderAmplitudeFlashR = gui
		.addSlider(S3Lang.get("shaderAmplitudeFlashR"), this, "shaderAmplitudeFlashR", shaderAmplitudeFlashR, -2f, 2f, 0.1f);
		final Slider sliderAmplitudeFlashG = gui
		.addSlider(S3Lang.get("shaderAmplitudeFlashG"), this, "shaderAmplitudeFlashG", shaderAmplitudeFlashG, -2f, 2f, 0.1f);
		final Slider sliderAmplitudeFlashB = gui
		.addSlider(S3Lang.get("shaderAmplitudeFlashB"), this, "shaderAmplitudeFlashB", shaderAmplitudeFlashB, -2f, 2f, 0.1f);

		ChangeListener listenerRandomFlash = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderSpeedFlashR = (float) (0.0 + (5.0 - (Math.random() * 5.0)));
				shaderSpeedFlashG = (float) (0.0 + (5.0 - (Math.random() * 5.0)));
				shaderSpeedFlashB = (float) (0.0 + (5.0 - (Math.random() * 5.0)));

				shaderAmplitudeFlashR = (float) (0.0 + (1.0 - (Math.random() * 1.0)));
				shaderAmplitudeFlashG = (float) (0.0 + (1.0 - (Math.random() * 1.0)));
				shaderAmplitudeFlashB = (float) (0.0 + (1.0 - (Math.random() * 1.0)));

				sliderSpeedFlashR.setValue(shaderSpeedFlashR);
				sliderSpeedFlashG.setValue(shaderSpeedFlashG);
				sliderSpeedFlashB.setValue(shaderSpeedFlashB);

				sliderAmplitudeFlashR.setValue(shaderAmplitudeFlashR);
				sliderAmplitudeFlashG.setValue(shaderAmplitudeFlashG);
				sliderAmplitudeFlashB.setValue(shaderAmplitudeFlashB);
			}
		};
		gui.addButton(S3Lang.get("Random"), listenerRandomFlash);

		ChangeListener listenerResetFlash = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				shaderSpeedFlashR = 1f;
				shaderSpeedFlashG = 1f;
				shaderSpeedFlashB = 1f;

				shaderAmplitudeFlashR = 0f;
				shaderAmplitudeFlashG = 0f;
				shaderAmplitudeFlashB = 0f;

				sliderSpeedFlashR.setValue(shaderSpeedFlashR);
				sliderSpeedFlashG.setValue(shaderSpeedFlashG);
				sliderSpeedFlashB.setValue(shaderSpeedFlashB);

				sliderAmplitudeFlashR.setValue(shaderAmplitudeFlashR);
				sliderAmplitudeFlashG.setValue(shaderAmplitudeFlashG);
				sliderAmplitudeFlashB.setValue(shaderAmplitudeFlashB);
			}
		};
		gui.addButton(S3Lang.get("Reset"), listenerResetFlash);

		//
		// Create window Shader Config Window
		//
		window = GuiResource.window(S3Lang.get("shaderConfig"), "shaderConfig");
		window.left();
		final Table sceneEditCell = gui.getTable();
		ScrollPane guiScrollCell = GuiResource.scrollPaneTransparent(sceneEditCell, "guiScrollCell");

		guiScrollCell.setScrollingDisabled(true, false);
		guiScrollCell.setFadeScrollBars(false);

		window.row();

		window.add(guiScrollCell).height(S3Screen.height - 29).width(S3Constans.gridX3).left().top();
		GuiUtil.windowPosition(window, 10, 0);

		window.setModal(false);
		window.setMovable(true);

		S3.stage.addActor(window);

		//
		// Dodanie Elementów tekstowych (debug bar, shader name)
		//
		debugBar = GuiResource.label(S3Lang.get("Debug"), "Debug");
		debugBar.setX(10);
		debugBar.setY(24);
		S3.stage.addActor(debugBar);

		labelShaderName = GuiResource.label(S3Lang.get("labelShaderName"), "labelShaderName");
		labelShaderName.setX(10);
		labelShaderName.setY(S3Screen.height - 34);
		labelShaderName.setFontScale(2.0f);
		labelShaderName.setColor(Color.YELLOW);
		S3.stage.addActor(labelShaderName);

		labelSshaderAutor = GuiResource.label(S3Lang.get("labelSshaderAutor"), "labelSshaderAutor");
		labelSshaderAutor.setX(10);
		labelSshaderAutor.setY(S3Screen.height - 60);
		labelSshaderAutor.setFontScale(1.0f);
		S3.stage.addActor(labelSshaderAutor);

		initDone = true;
		createResuorce();
	}

	@Override
	public void update(){
		//
		// Effect Time
		//
		localTime = localTime + S3.osDeltaTime;
		sceneTime = start + localTime;
		endTime = duration - localTime;
		procent = (float) localTime / duration;

		if (procent > 0.95f){
			localTime = 0;
		}
	}

	@Override
	public void render(S3Gfx g){

		//
		// Update debug bar
		//
		debugBar.setText(" eT: " + String.format("%.2f", localTime) + " osT: " + String.format("%.2f", S3.osTime)
						 + " fps: " + S3.graphics.getFramesPerSecond() + " h: " + (Gdx.app.getJavaHeap() / 1024) + " kb" + " m: "
						 + (Gdx.app.getNativeHeap() / 1024) + " kb" + "\n" + " status: " + shader.getLog());

		//
		// Render
		//
		S3.gl.glViewport(0, 0, S3Screen.width, S3Screen.height);
		S3.gl.glClearColor(0.3f, 0.2f, 0.1f, 1.0f);
		S3.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		S3.gl.glEnable(GL20.GL_DEPTH_TEST);

		shaderParameter.time = localTime;
		shaderParameter.colourStart = shaderColourStart;
		shaderParameter.colourProcess = shaderColourProcess;

		shaderParameter.amplitudeColourR = shaderAmplitudeColourR;
		shaderParameter.amplitudeColourG = shaderAmplitudeColourG;
		shaderParameter.amplitudeColourB = shaderAmplitudeColourB;

		//
		// Flash
		//
		shaderParameter.speedFlashR = shaderSpeedFlashR;
		shaderParameter.speedFlashG = shaderSpeedFlashG;
		shaderParameter.speedFlashB = shaderSpeedFlashB;

		shaderParameter.amplitudeFlashR = shaderAmplitudeFlashR;
		shaderParameter.amplitudeFlashG = shaderAmplitudeFlashG;
		shaderParameter.amplitudeFlashB = shaderAmplitudeFlashB;

		//
		//
		//
		shaderParameter.mainSpeedX = shaderMainSpeedX;
		shaderParameter.mainSpeedY = shaderMainSpeedY;
		shaderParameter.mainSpeedZ = shaderMainSpeedZ;

		shaderParameter.mainAmplitudeX = shaderMainAmplitudeX;
		shaderParameter.mainAmplitudeY = shaderMainAmplitudeY;
		shaderParameter.mainAmplitudeZ = shaderMainAmplitudeZ;

		shaderParameter.mainStepX = shaderMainStepX;
		shaderParameter.mainStepY = shaderMainStepY;
		shaderParameter.mainStepZ = shaderMainStepZ;

		//
		//
		//
		shaderParameter.mouseSpeedX = shaderMouseSpeedX;
		shaderParameter.mouseSpeedY = shaderMouseSpeedY;

		shaderParameter.mouseAmplitudeX = shaderMouseAmplitudeX;
		shaderParameter.mouseAmplitudeY = shaderMouseAmplitudeY;

		shaderParameter.mouseStepX = shaderMouseStepX;
		shaderParameter.mouseStepY = shaderMouseStepY;

		//
		//
		//
		shaderParameter.mainIterations = shaderMainIterations;
		shaderParameter.mainPower = shaderMainPower;
		shaderParameter.mainLight = shaderMainLight;

		if (shader instanceof EffectShader){
			((EffectShader) shader).setShaderParams(shaderParameter);
		}
		S3Gfx.drawBackground(texture, shader);

	}

	/**
	 * Tworzy zasoby testowego objektu
	 */
	private void createResuorce(){

		if (initDone == false){
			return;
		}

		texture = new Texture(S3File.getFileHandle(textureName), true);
		texture.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Linear);

		shader = new EffectShader(shaderName, false, false, 1);

		labelShaderName.setText(shader.getName());
		labelSshaderAutor.setText(shader.getAuthor());
	}
}
