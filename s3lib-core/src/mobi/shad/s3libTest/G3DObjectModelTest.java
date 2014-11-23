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
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.*;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.JsonReader;
import mobi.shad.s3lib.gfx.g3d.shaders.EffectShader;
import mobi.shad.s3lib.gfx.g3d.simpleobject.*;
import mobi.shad.s3lib.gui.Gui;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.gui.widget.WidgetColorListener;
import mobi.shad.s3lib.gui.widget.WidgetStringListener;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class G3DObjectModelTest extends S3App{

	private static Vector3 tmpVec3a = new Vector3();
	private static Vector3 tmpVec3b = new Vector3();
	private static Vector3 tmpVec3c = new Vector3();
	private static Vector3 tmpVec3d = new Vector3();

	private static Vector3 tmpNormalVec3a = new Vector3();
	private static Vector3 tmpNormalVec3b = new Vector3();
	private static Vector3 tmpNormalVec3c = new Vector3();
	private static Vector3 tmpNormalVec3d = new Vector3();
	public float sizeX = 2.0f;
	public float sizeY = 2.0f;
	public float sizeZ = 2.0f;
	public float sizeP = 1.0f;
	public float sizeQ = 1.0f;
	public float sizeA = 1.0f;
	public float segmentsWidth = 10f;
	public float segmentsHeight = 10f;
	public int renderMode = 0;
	public boolean isPlane2dMode = false;
	public boolean isAutoRotate = true;
	public float autoRotateX = 20f;
	public float autoRotateY = 20f;
	public float autoRotateZ = 0.0f;
	//
	// Lights
	//
	public boolean isLights = true;
	public boolean lightsAutoRotate = true;
	public boolean lights1 = true;
	public boolean lights2 = true;
	public boolean lights3 = true;
	public boolean lights4 = false;
	public boolean lights5 = false;
	public boolean ambientCubeMap = false;
	public boolean isTextureDiffuse = true;
	public boolean isTextureSpecular = false;
	// private FxShaderProvider fxShaderProvider;
	//
	// Opacity
	//
	public boolean isTransparent = false;
	protected float start;
	protected float duration = 60;
	protected float localTime = 0;
	protected float sceneTime = 0;
	protected float endTime = 0;
	protected float procent = 0;
	private String objectName = "Torus";
	private String shaderName = "default";
	private String textureName = "texture/def256.png";
	private String specularTextureName = "texture/def32.png";
	private int shaderType = 0;
	private Color lights1Color = Color.WHITE;
	private int lights1Type = 0;
	private Color lights2Color = Color.RED;
	private int lights2Type = 0;
	private Color lights3Color = Color.BLUE;
	private int lights3Type = 0;
	//
	// ShaderOld data
	//
	private Shader shader;
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
	private PerspectiveCamera camera;
	private OrthographicCamera orthographicCamera;
	private ShapeRenderer shapeRenderer;
	private float angleY = 0;
	private float angleX = 0;
	private float touchStartX = 0;
	private float touchStartY = 0;
	private Window window2;
	private boolean initDone = false;
	private Label debugBar;
	private Label labelShaderName;
	private Label labelSshaderAutor;
	//
	// New LibGdx 3dModel
	//
	private Model model;
	private ModelInstance modelInstance;
	private ModelBatch modelBatch;
	private Environment lights;
	private BaseLight light1 = new PointLight();
	private BaseLight light2 = new PointLight();
	private BaseLight light3 = new PointLight();
	//
	// Material Data
	//
	private Material material = new Material(ColorAttribute.createDiffuse(Color.WHITE));
	//
	private float lightShiness = 10;
	private float lightIntensity = 10;
	// Ambient
	private Color colorAmbient = Color.BLACK;
	// Diffuse
	private Color colorDiffuse = Color.LIGHT_GRAY;
	private Texture textureDiffuse;
	// Specular
	private Color colorSpecular = Color.WHITE;
	private Texture textureSpecular;
	private float materialOpacity = 10;

	// class LocalShaderProvider extends BaseShaderProvider{
	//
	// @Override
	// protected Shader createShader(Renderable renderable){
	// return null;
	// }
	//
	// }
	//
	//
	@Override
	public void initalize(){

		//
		// Resuorce
		//
		camera = new PerspectiveCamera(45, S3Screen.width, S3Screen.height);
		camera.position.set(5f, 5f, 5f);
		camera.lookAt(0, 0, 0);
		camera.update();

		orthographicCamera = new OrthographicCamera(S3Screen.width, S3Screen.height);
		orthographicCamera.position.set(0f, 0f, 5f);
		orthographicCamera.lookAt(0, 0, 0);
		orthographicCamera.update();

		shapeRenderer = new ShapeRenderer();

		//
		// Gui
		//
		Gui gui = new Gui();
		Gui gui2 = new Gui();

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
		WidgetColorListener listener3 = new WidgetColorListener(){
			@Override
			public void Action(Color color){
				createResuorce();
			}
		};

		// --------------------------------------------------------
		// Gui object
		// --------------------------------------------------------

		//
		// Model
		// "Plane 2D",
		gui2.addLabel("Model Type", true, 1, Color.YELLOW);
		String[] listOfObject = {"Plane 2D", "Cube", "Sphere", "Cylinder", "Plane", "Torus", "Torus Knot",
								 "Octahedron", "Icosahedron", "Head", "MB: Cube", "MB: Sphere", "MB: Cylinder"};
		gui2.addList(S3Lang.get("objectName"), this, "objectName", listOfObject, "Torus", true, 1, listener);

		gui2.addCheckBox(S3Lang.get("isAutoRotate"), this, "isAutoRotate", true, false, true, 1, null, null);

		//
		// Lights
		//
		gui2.addSeparator(Color.RED, true, 2);
		gui2.addLabel("Lights Config", true, 1, Color.YELLOW);
		gui2.addCheckBox(S3Lang.get("isLights"), this, "isLights", true, false, true, 1, null, listener);
		gui2.addCheckBox(S3Lang.get("lightsAutoRotate"), this, "lightsAutoRotate", true, false, true, 1, null, null);

		// Ambient
		gui2.addColorBrowser(S3Lang.get("colorAmbient"), this, "colorAmbient", colorAmbient, true, 1, listener3);
		// Light Shiness
		gui2.addSlider(S3Lang.get("lightShiness"), this, "lightShiness", lightShiness, 1.0f, 100f, 0.2f, listener);
		gui2.addSlider(S3Lang.get("lightIntensity"), this, "lightIntensity", lightIntensity, 1.0f, 100f, 0.2f, listener);

		String[] lightTypes = {"Point", "Directional"};
		gui2.addCheckBox(S3Lang.get("lights1"), this, "lights1", true, false, true, 1, null, listener);
		gui2.addSelectIndexBox(S3Lang.get("lights1Type"), this, "lights1Type", lightTypes, lights1Type, true, 1, listener);
		gui2.addColorBrowser(S3Lang.get("lights1Color"), this, "lights1Color", lights1Color, true, 1, listener3);

		gui2.addCheckBox(S3Lang.get("lights2"), this, "lights2", true, false, true, 1, null, listener);
		gui2.addSelectIndexBox(S3Lang.get("lights2Type"), this, "lights2Type", lightTypes, lights2Type, true, 1, listener);
		gui2.addColorBrowser(S3Lang.get("lights2Color"), this, "lights2Color", lights2Color, true, 1, listener3);

		gui2.addCheckBox(S3Lang.get("lights3"), this, "lights3", true, false, true, 1, null, listener);
		gui2.addSelectIndexBox(S3Lang.get("lights3Type"), this, "lights3Type", lightTypes, lights3Type, true, 1, listener);
		gui2.addColorBrowser(S3Lang.get("lights3Color"), this, "lights3Color", lights3Color, true, 1, listener3);

		// gui2.addCheckBox(S3Lang.get("lights4"), this, "lights4", true, false,
		// true, 1, null, null);
		// gui2.addCheckBox(S3Lang.get("lights5"), this, "lights5", true, false,
		// true, 1, null, null);

		gui2.addCheckBox(S3Lang.get("ambientCubeMap"), this, "ambientCubeMap", true, false, true, 1, null, listener);

		//
		// Material Config
		//
		gui2.addSeparator(Color.RED, true, 2);
		gui2.addLabel("Material Config", true, 1, Color.YELLOW);

		// Diffuse
		gui2.addColorBrowser(S3Lang.get("diffuseColor"), this, "colorDiffuse", colorDiffuse, true, 1, listener3);
		gui2.addCheckBox(S3Lang.get("isTextureDiffuse"), this, "isTextureDiffuse", true, false, true, 1, null, listener);
		gui2.addFileBrowser(S3Lang.get("diffuseTextureName"), this, "textureName", textureName, true, 1, true, listener2);

		// Specular
		gui2.addColorBrowser(S3Lang.get("colorSpecular"), this, "colorSpecular", colorSpecular, true, 1, listener3);
		// gui2.addCheckBox(S3Lang.get("isTextureSpecular"), this,
		// "isTextureSpecular", true, false, true, 1, null, listener);
		// gui2.addFileBrowser(S3Lang.get("specularTextureName"), this,
		// "specularTextureName", specularTextureName, true, 1, true,
		// listener2);

		// Opaticy
		gui2.addCheckBox(S3Lang.get("isTransparent"), this, "isTransparent", true, false, true, 1, null, listener);
		gui2.addSlider(S3Lang.get("materialOpacity"), this, "materialOpacity", materialOpacity, 0f, 1f, 0.1f, listener);

		//
		// Model Config
		//
		gui2.addSeparator(Color.RED, true, 2);
		gui2.addLabel("Model Config", true, 1, Color.YELLOW);
		gui2.addSlider(S3Lang.get("sizeX"), this, "sizeX", sizeX, 0.2f, 20f, 0.2f, listener);
		gui2.addSlider(S3Lang.get("sizeY"), this, "sizeY", sizeY, 0.2f, 20f, 0.2f, listener);
		gui2.addSlider(S3Lang.get("sizeZ"), this, "sizeZ", sizeZ, 0.2f, 20f, 0.2f, listener);

		gui2.addSlider(S3Lang.get("segmentsWidth"), this, "segmentsWidth", segmentsWidth, 1f, 50f, 1f, listener);
		gui2.addSlider(S3Lang.get("segmentsHeight"), this, "segmentsHeight", segmentsHeight, 1f, 50f, 1f, listener);

		gui2.addSlider(S3Lang.get("sizeP"), this, "sizeP", sizeP, 0.2f, 20f, 0.2f, listener);
		gui2.addSlider(S3Lang.get("sizeQ"), this, "sizeQ", sizeQ, 0.2f, 20f, 0.2f, listener);
		gui2.addSlider(S3Lang.get("sizeA"), this, "sizeA", sizeA, 0.2f, 20f, 0.2f, listener);

		// --------------------------------------------------------
		// Gui Shader Parametr
		// --------------------------------------------------------
		gui.addLabel("Shader", true, 1, Color.YELLOW);

		String[] shaderTypes = {"Vertex Light", "Pixel Light", "Fx Shader"};
		gui.addSelectIndexBox(S3Lang.get("objectName"), this, "shaderType", shaderTypes, 0, true, 1, listener);

		String[] listOfShader = S3ResourceManager.getShaderList();
		gui.addList(S3Lang.get("shaderName"), this, "shaderName", listOfShader, shaderName, true, 1, listener);

		//
		//
		//
		gui.addSeparator(Color.RED, true, 2);
		gui.addLabel("Fx Shader Parameters", true, 1, Color.YELLOW);

		gui.addLabel("Procedure", true, 1, Color.YELLOW);

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
		gui.addLabel("Process Setup", true, 1, Color.YELLOW);
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
		gui.addLabel(S3Lang.get("Color Process"), true, 1, Color.YELLOW);
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
		gui.addLabel(S3Lang.get("Color Flash"), true, 1, Color.YELLOW);
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

		window.add(guiScrollCell).height(S3Screen.height - 29 - S3Constans.cellPaddding * 2).width(S3Constans.gridX3)
			  .left().top();
		GuiUtil.windowPosition(window, 10, 0);

		window.setModal(false);
		window.setMovable(true);

		//
		// Create window Object Config Window
		//
		window2 = GuiResource.window(S3Lang.get("objectConfig"), "objectConfig");
		window2.left();
		final Table objectEditCell = gui2.getTable();
		ScrollPane objectScrollCell = GuiResource.scrollPaneTransparent(objectEditCell, "guiObjectScrollCell");

		objectScrollCell.setScrollingDisabled(true, false);
		objectScrollCell.setFadeScrollBars(false);

		window2.row();
		window2.add(objectScrollCell).height(S3Screen.height - 29 - S3Constans.cellPaddding * 2).width(S3Constans.gridX3)
			   .left().top();
		GuiUtil.windowPosition(window2, 0, 0);

		window2.setModal(false);
		window2.setMovable(true);

		S3.stage.addActor(window);
		S3.stage.addActor(window2);

		//
		// Dodanie Elementów tekstowych (debug bar, shader name)
		//
		debugBar = GuiResource.label(S3Lang.get("Debug"), "Debug");
		debugBar.setX(window2.getX() + window2.getWidth() + 10);
		debugBar.setY(24);
		S3.stage.addActor(debugBar);

		labelShaderName = GuiResource.label(S3Lang.get("labelShaderName"), "labelShaderName");
		labelShaderName.setX(window2.getX() + window2.getWidth() + 10);
		labelShaderName.setY(S3Screen.height - 34);
		labelShaderName.setFontScale(2.0f);
		labelShaderName.setColor(Color.YELLOW);
		S3.stage.addActor(labelShaderName);

		labelSshaderAutor = GuiResource.label(S3Lang.get("labelSshaderAutor"), "labelSshaderAutor");
		labelSshaderAutor.setX(window2.getX() + window2.getWidth() + 10);
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

		//
		// AutoRotate
		//
		if (isAutoRotate){
			camera.rotateAround(new Vector3(), Vector3.X, S3.osDeltaTime * autoRotateX);
			camera.rotateAround(new Vector3(), Vector3.Y, S3.osDeltaTime * autoRotateY);
			camera.update();
		}

		//
		// AutoRotate Light
		//
		if (lightsAutoRotate && lights != null){
			for (int i = 0; i < lights.pointLights.size; i++){
				lights.pointLights.get(i).position.rotate(Vector3.Y, S3.osDeltaTime * 15f);
				lights.pointLights.get(i).position.rotate(Vector3.X, S3.osDeltaTime * 45f);
				lights.pointLights.get(i).position.rotate(Vector3.Z, S3.osDeltaTime * 75f);
			}

			for (int i = 0; i < lights.directionalLights.size; i++){
				lights.directionalLights.get(i).direction.rotate(Vector3.X, S3.osDeltaTime * 25f).nor();
				lights.directionalLights.get(i).direction.rotate(Vector3.Y, S3.osDeltaTime * 45f).nor();
				lights.directionalLights.get(i).direction.rotate(Vector3.Z, S3.osDeltaTime * 75f).nor();
			}
		}
	}

	@Override
	public void render(S3Gfx g){

		//
		// Update debug bar
		//
		String lp = " eT: " + String.format("%.2f", localTime) + " osT: " + String.format("%.2f", S3.osTime) + " fps: "
		+ S3.graphics.getFramesPerSecond() + " h: " + (Gdx.app.getJavaHeap() / 1024) + " kb" + " m: "
		+ (Gdx.app.getNativeHeap() / 1024) + " kb";
		if (lights != null){
			lp += "\n LP: " + lights.pointLights.size + " LD: " + lights.directionalLights.size;
		}
		debugBar.setText(lp);

		//
		// Set Shader Parametrs
		//
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

		// if (fxShaderProvider.shader instanceof FxShader){
		// ((FxShader)
		// fxShaderProvider.shader).setShaderParams(shaderParameter);
		// }

		S3Screen.gl20.glViewport(0, 0, S3Screen.width, S3Screen.height);
		S3Screen.gl20.glClearColor(0.3f, 0.2f, 0.1f, 1.0f);
		S3Screen.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		S3Screen.gl20.glEnable(GL20.GL_DEPTH_TEST);

		if (isPlane2dMode){
			modelBatch.begin(orthographicCamera);
			if (isLights){
				modelBatch.render(modelInstance, lights);
			} else {
				modelBatch.render(modelInstance);
			}
			modelBatch.end();
		} else {
			//
			// Dabug render
			//
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.line(0, 0, 0, 100, 0, 0);
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.line(0, 0, 0, 0, 100, 0);
			shapeRenderer.setColor(Color.BLUE);
			shapeRenderer.line(0, 0, 0, 0, 0, 100);
			shapeRenderer.end();

			//
			// Render
			//
			modelBatch.begin(camera);
			if (isLights){
				modelBatch.render(modelInstance, lights);
			} else {
				modelBatch.render(modelInstance);
			}
			modelBatch.end();
		}
		//
		// // if (shader instanceof FxUniversalShader){
		// // ((FxUniversalShader) shader).setShaderParams(shaderParameter);
		// // }
		// if (isPlane2dMode){
		// modelBatch.begin(orthographicCamera);
		// modelBatch.render(modelInstance, shader);
		// modelBatch.end();
		// } else {
		//
		// if (isLights){
		// //
		// // Light
		// //
		// // if (lightsAutoRotate){
		// // for (int i=0; i < lights.length; i++){
		// // lights[i].position.rotate(Vector3.Y, S3System.osDeltaTime * 45f);
		// // lights[i].direction.set(-lights[i].position.x,
		// -lights[i].position.y, -lights[i].position.z).nor();
		// // }
		// // }
		//
		// modelBatch.begin(camera);
		// // modelBatch.render(modelInstance, lights, shader);
		// modelBatch.render(modelInstance);
		// modelBatch.end();
		//
		// //
		// // Render Light
		// //
		// shapeRenderer.setProjectionMatrix(camera.combined);
		// shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		// // for (int i=0; i < lights.length; i++){
		// //
		// // shapeRenderer.setColor(lights[i].color);
		// // shapeRenderer.line(
		// // lights[i].position.x - lights[i].direction.x,
		// // lights[i].position.y - lights[i].direction.y,
		// // lights[i].position.z - lights[i].direction.z,
		// // lights[i].position.x,
		// // lights[i].position.y,
		// // lights[i].position.z);
		// // }
		// shapeRenderer.end();
		// } else {
		// modelBatch.begin(camera);
		// // modelBatch.render(modelInstance, shader);
		// modelBatch.render(modelInstance);
		// modelBatch.end();
		// }
		// }
		// }
	}

	/**
	 * Tworzy zasoby testowego objektu
	 */
	private void createResuorce(){

		if (initDone == false){
			return;
		}

		//
		// Create Material
		//
		material = new Material();

		//
		// Diffuse
		//
		textureDiffuse = S3ResourceManager.getTexture(textureName, S3Constans.proceduralTextureSize);
		material.set(ColorAttribute.createDiffuse(colorDiffuse));
		if (isTextureDiffuse){
			material.set(TextureAttribute.createDiffuse(textureDiffuse));
		}

		//
		// Specular
		//
		material.set(ColorAttribute.createSpecular(colorSpecular));
		if (isTextureSpecular){
			material.set(TextureAttribute.createSpecular(textureSpecular));
		}
		material.set(new ColorAttribute(ColorAttribute.Ambient, colorAmbient));
		material.set(FloatAttribute.createShininess(lightShiness));

		if (isTransparent){
			material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, materialOpacity));
			material.set(new IntAttribute(IntAttribute.CullFace, 0));
			material.set(new FloatAttribute(FloatAttribute.AlphaTest, 0));
			// material.set(new
			// IntAttribute(IntAttribute.CullFace,GL10.GL_BACK));
			// material.set(new
			// IntAttribute(IntAttribute.CullFace,GL10.GL_FRONT));
			// material.set(new
			// IntAttribute(IntAttribute.CullFace,GL10.GL_CULL_FACE));
		}

		//
		// Create Model
		//
		isPlane2dMode = false;
		if (objectName.equalsIgnoreCase("Sphere")){
			model = Sphere.sphere(sizeX, sizeY, sizeZ, segmentsWidth, segmentsHeight).getModel(material);
		} else if (objectName.equalsIgnoreCase("Cylinder")){
			model = Cylinder.cylinder(sizeX, sizeY, segmentsWidth, segmentsHeight).getModel(material);
		} else if (objectName.equalsIgnoreCase("Plane")){
			model = Plane.plane(sizeX, sizeY, segmentsWidth, segmentsHeight).getModel(material);
		} else if (objectName.equalsIgnoreCase("Torus")){
			model = Torus.torus(sizeX, sizeY, segmentsWidth, segmentsHeight).getModel(material);
		} else if (objectName.equalsIgnoreCase("Torus Knot")){
			model = TorusKnot.torusKnot(sizeX, sizeY, (int) segmentsWidth, (int) segmentsHeight, sizeP, sizeQ, sizeA)
							 .getModel(material);
		} else if (objectName.equalsIgnoreCase("Octahedron")){
			model = Octahedron.octahedron(sizeX).getModel(material);
		} else if (objectName.equalsIgnoreCase("Icosahedron")){
			model = Icosahedron.icosahedron(sizeX).getModel(material);
		} else if (objectName.equalsIgnoreCase("Plane 2D")){
			model = Plane.plane(S3Screen.width, S3Screen.height, 1, 1).getModel(material);
			isPlane2dMode = true;
		} else if (objectName.equalsIgnoreCase("head")){
			G3dModelLoader loader = new G3dModelLoader(new JsonReader());
			model = loader.loadModel(S3File.getFileHandle("g3d/head2.g3dj"));
		} else if (objectName.equalsIgnoreCase("mb: cube")){
			ModelBuilder mb = new ModelBuilder();
			model = mb.createBox(sizeX, sizeY, sizeZ, material, VertexAttributes.Usage.Position
			| VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
		} else if (objectName.equalsIgnoreCase("mb: sphere")){
			ModelBuilder mb = new ModelBuilder();
			model = mb
			.createSphere(sizeX, sizeY, sizeZ, (int) segmentsWidth, (int) segmentsHeight, material, VertexAttributes.Usage.Position
			| VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
		} else if (objectName.equalsIgnoreCase("mb: cylinder")){
			ModelBuilder mb = new ModelBuilder();
			model = mb
			.createCylinder(sizeX, sizeY, sizeZ, (int) segmentsWidth, material, VertexAttributes.Usage.Position
			| VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
		} else {
			model = Cube.cube(sizeX, sizeY, sizeZ).getModel(material);
		}

		if (isLights){
			lights = new Environment();
			lights.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.0f, 0.0f, 0.0f, 1.f));

			// if (ambientCubeMap){
			// AmbientCubemap ambientCubemap=new AmbientCubemap();
			// }

			//
			// Create Light
			//
			if (lights1){
				switch (lights1Type){
					case 0:
						// Point
						light1 = new PointLight().set(lights1Color, tmpVec3a.set(-3f, 3f, -3f), lightIntensity);
						break;
					case 1:
						// Directional
						light1 = new DirectionalLight().set(lights1Color, tmpVec3a.set(10f, 0f, -5f));
						break;
				}
				lights.add(light1);
			}
			if (lights2){
				switch (lights2Type){
					case 0:
						// Point
						light2 = new PointLight().set(lights2Color, tmpVec3a.set(-5f, -4f, 3f), lightIntensity);
						break;
					case 1:
						// Directional
						light2 = new DirectionalLight().set(lights2Color, tmpVec3a.set(0f, 5f, -3f));
						break;
				}
				lights.add(light2);

			}
			if (lights3){
				switch (lights3Type){
					case 0:
						// Point
						light3 = new PointLight().set(lights3Color, tmpVec3a.set(4f, 3f, 2f), lightIntensity);
						break;
					case 1:
						// Directional
						light3 = new DirectionalLight().set(lights3Color, tmpVec3a.set(-10f, -4f, 5f));
						break;
				}
				lights.add(light3);

			}
		} else {
			lights = null;
		}

		//
		// Shader Type
		//
		S3Log.log("createResuorce:", "Shader type: " + shaderType + " name: " + shaderName);
		// fxShaderProvider=new FxShaderProvider(shaderType, shaderName);
		// modelBatch=new ModelBatch(fxShaderProvider);
		modelBatch = new ModelBatch();
		// S3Log.log("createResuorce:", "Lights: " + lights.length);

		// switch (shaderType){
		// default:
		// UniversalShader.shaderType=0;
		// shader=new UniversalShader(material, lights.length);
		// break;
		// case 1:
		// UniversalShader.shaderType=1;
		// shader=new UniversalShader(material, lights.length);
		// break;
		// case 2:
		// // shader=new FxShader(shaderName, material);
		// shader=new FxUniversalShader(shaderName, material, lights.length);
		// break;
		// }

		//
		// Create instance
		//
		modelInstance = new ModelInstance(model);
	}
	// @Override
	// public boolean touchDown(int screenX, int screenY, int pointer, int
	// button){
	// touchStartX=screenX;
	// touchStartY=screenY;
	// return true;
	// }
	//
	// @Override
	// public boolean touchDragged(int screenX, int screenY, int pointer){
	// float deltaX=(screenX - touchStartX) * 0.5f;
	// float deltaY=(screenY - touchStartX) * 0.01f;
	// camera.rotateAround(new Vector3(), Vector3.Z, deltaX);
	// camera.rotateAround(new Vector3(), Vector3.X, deltaY);
	// camera.update();
	//
	// touchStartX=screenX;
	// touchStartY=screenY;
	// return false;
	// }
}
