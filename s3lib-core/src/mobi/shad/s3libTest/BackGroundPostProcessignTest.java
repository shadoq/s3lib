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
package mobi.shad.s3libTest;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import mobi.shad.s3lib.gfx.effect.PostRender;
import mobi.shad.s3lib.gfx.g3d.shaders.BaseShader;
import mobi.shad.s3lib.gfx.g3d.shaders.EffectShader;
import mobi.shad.s3lib.gfx.node.core.DefaultFxEffect;
import mobi.shad.s3lib.gfx.node.core.Effect;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gfx.node.filter.*;
import mobi.shad.s3lib.gfx.node.pixmap.filter.FxBlur;
import mobi.shad.s3lib.gfx.node.pixmap.filter.FxGlow;
import mobi.shad.s3lib.gfx.node.pixmap.filter.FxGradient;
import mobi.shad.s3lib.gfx.node.pixmap.filter.FxThreshold;
import mobi.shad.s3lib.gfx.node.pixmap.procedural.FxPerlinNoiseColor;
import mobi.shad.s3lib.gfx.node.pixmap.procedural.FxTextureLoader;
import mobi.shad.s3lib.gfx.util.CaptureTexture;
import mobi.shad.s3lib.gui.Gui;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.main.*;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Jarek
 */
public class BackGroundPostProcessignTest extends S3App{

	protected float start;
	protected float duration = 10;
	protected float localTime = 0;
	protected float sceneTime = 0;
	protected float endTime = 0;
	protected float procent = 0;
	private DefaultFxEffect effect;
	private Effect postProcessingEffect;
	private Effect effect2;
	private Window scenePostProcessingEffectWindow;
	private Window sceneEffectWindow;
	private boolean pauseEffect = false;

	private String[] effectList = {FxEffectSize.class.getName(), FxShader.class.getName(),
								   FxTextureLoader.class.getName(), FxBlur.class.getName(), FxPerlinNoiseColor.class.getName(),
								   FxStarField.class.getName(), FxPointSingle.class.getName(), FxPointMulti.class.getName(),
								   FxPoint3D.class.getName(), FxObject3D.class.getName(), FxCamera.class.getName(),
								   FxPlasma3D.class.getName(), FxPointSingleNoMul.class.getName(), FxPointMultiNoMul.class.getName(),
								   FxTextScroll.class.getName(), FxGradient.class.getName(), FxGlow.class.getName(),
								   FxThreshold.class.getName(),};
	private String[] effectListName = {"FxEffectSize", "FxShader", "FxTextureLoader", "FxTextureOpBlur",
									   "FxTexturePerlinNoise", "FxStarField", "FxPointSingle", "FxPointMulti", "FxPoint3D", "FxObject3D",
									   "FxCamera", "FxPlasma3D", "FxPointSingleNoMul", "FxPointMultiNoMul", "FxTextScroll", "FxGradient",
									   "FxGlow", "FxThreshold"};

	private String[] listOfShader;
	private EffectShader.ShaderParameter params = new EffectShader.ShaderParameter();
	private Gui gui;
	private boolean shaderPostProcessing = false;
	private int shaderId = 0;
	private BaseShader shader;
	private EffectShader.ShaderParameter shaderParameter = new EffectShader.ShaderParameter();
	private CaptureTexture captureTexture;

	@Override
	public void initalize(){

		// effect=new TextureGridEffect();
		// effect2=new BobsEffect();
		postProcessingEffect = new PostRender();

		gui();

		gui = new Gui();
		Window window = GuiResource.window("Test Effect", "window");
		window.defaults().align(Align.top | Align.left);

		CheckBox addCheckBox = gui
		.addCheckBox("PostProcessing", this, "shaderPostProcessing", false, true, true, 1, null, null);
		addCheckBox.setChecked(shaderPostProcessing);

		window.row();
		window.add(gui.getTable());
		window.row();

		// -------------------------------------------------------
		// Effect Gui
		// -------------------------------------------------------

		final Button buttonPause = GuiResource.textButtonToggle("Pause", "Pause");
		buttonPause.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				pauseEffect = !pauseEffect;
			}
		});

		final Button buttonSave = GuiResource.textButton("Save", "Save");
		buttonSave.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){

				String fileName = "test/test_bg.xml";

				try {
					S3Log.log("saveXML", "Save XML file: " + fileName);
					FileHandle fsH = S3File.getFileHandle(fileName);

					StringWriter writer = new StringWriter();
					XmlWriter xml = new XmlWriter(writer);
					XmlWriter el = xml.element("sceneManager");
					effect.savePrefs(el);
					el.pop();

					fsH.writeString(writer.toString(), false);
				} catch (IOException ex){
					S3Log.error("saveXML", " Exception in Scenemanager: ", ex);
				}

				try {
					XmlReader reader = new XmlReader();
					XmlReader.Element parse = reader.parse(S3File.getFileHandle(fileName));
					XmlReader.Element elementLayer = parse.getChildByName("effect");
					effect.readPrefs(elementLayer);
				} catch (IOException ex){
					S3Log.error("readXML", " Exception in Scenemanager: ", ex);
				}
				effect.stop();
				effect.start();
				destroyGui();
				gui();

			}
		});

		window.row();

		int i = 0;
		final ButtonGroup buttonGroup = new ButtonGroup();

		for (String effectName : effectList){
			Button buttonLoop = GuiResource.textButton(effectListName[i], effectName);

			buttonLoop.setName(effectName);
			buttonGroup.add(buttonLoop);

			buttonLoop.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					Button checked = buttonGroup.getChecked();

					effect = new DefaultFxEffect();
					Node fx1 = null;

					try {
						fx1 = (Node) Class.forName(checked.getName()).newInstance();
						fx1.setFormGui(effect.getFormData(), effect.getChangeListener());
					} catch (InstantiationException ex){
						S3Log.error(this.getClass().getName(), "Error creare effect ...", ex);
					} catch (IllegalAccessException ex){
						S3Log.error(this.getClass().getName(), "Error creare effect ...", ex);
					} catch (ClassNotFoundException ex){
						S3Log.error(this.getClass().getName(), "Error creare effect ...", ex);
					}

					effect.addFxFilter(fx1);

					destroyGui();
					gui();

					effect.getFxFilter().process();

					localTime = 0;
					duration = 10;

				}
			});

			window.add(buttonLoop).fillX();
			if (i % 2 == 1){
				window.row();
			}
			i++;
		}

		window.row();
		window.add(buttonPause).fillX();
		window.add(buttonSave).fillX();
		window.pack();
		window.setX(0);
		window.setY(S3Screen.height - window.getHeight());
		S3.stage.addActor(window);

		// -------------------------------------------------------
		// PostProcessing Effect Gui
		// -------------------------------------------------------

		scenePostProcessingEffectWindow = GuiResource.window("PostProcessing Editor", "scenePostProcessingWindow");
		final Table sceneEditCell = postProcessingEffect.gui();
		ScrollPane guiScrollCell = GuiResource.scrollPaneTransparent(sceneEditCell, "scenePostProcessingCell");
		guiScrollCell.setScrollingDisabled(true, false);
		guiScrollCell.setFlickScroll(true);
		guiScrollCell.setFadeScrollBars(false);

		scenePostProcessingEffectWindow.row();
		scenePostProcessingEffectWindow.add(guiScrollCell).height((S3Screen.height - 60) / 2).width(S3Constans.gridX3)
									   .left().top();

		GuiUtil.windowPosition(scenePostProcessingEffectWindow, 10, 0);

		scenePostProcessingEffectWindow.setModal(false);
		scenePostProcessingEffectWindow.setMovable(true);
		S3.stage.addActor(scenePostProcessingEffectWindow);
	}

	@Override
	public void update(){
		// TODO Auto-generated method stub

	}

	@Override
	public void render(S3Gfx g){

		if (!pauseEffect){
			localTime = localTime + S3.osDeltaTime;
			sceneTime = start + localTime;
			endTime = duration - localTime;
			procent = (float) localTime / duration;

			if (effect != null){
				effect.update(localTime, sceneTime, endTime, procent, false);
			}
			if (effect2 != null){
				effect2.update(localTime, sceneTime, endTime, procent, false);
			}

			if (procent > 0.95f){
				localTime = 0;
			}
		}

		if (!shaderPostProcessing){

			// -------------------------------------------------------
			// PostProcessing Render
			// -------------------------------------------------------

			postProcessingEffect.preRender();

			S3.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
			S3.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			if (effect != null){
				effect.render(new SpriteBatch(), 1.0f);
			}

			postProcessingEffect.postRender();

			if (effect2 != null){
				effect2.render(new SpriteBatch(), 1.0f);
			}

		} else {

			// -------------------------------------------------------
			// Normal Render
			// -------------------------------------------------------

			S3.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
			S3.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			if (effect != null){
				effect.render(new SpriteBatch(), 1.0f);
			}
			if (effect2 != null){
				effect2.render(new SpriteBatch(), 1.0f);
			}
		}
	}

	public void gui(){

		sceneEffectWindow = GuiResource.window("EffectEditor", "sceneEffectWindow");

		if (effect == null){
			return;
		}

		final Table sceneEditCell = effect.gui();
		ScrollPane guiScrollCell = GuiResource.scrollPaneTransparent(sceneEditCell, "guiScrollCell");
		guiScrollCell.setScrollingDisabled(true, false);
		guiScrollCell.setFlickScroll(true);
		guiScrollCell.setFadeScrollBars(false);

		sceneEffectWindow.row();
		sceneEffectWindow.add(guiScrollCell).height((S3Screen.height - 60) / 2).width(S3Constans.gridX3).left().top();

		GuiUtil.windowPosition(sceneEffectWindow, 10, 10);

		sceneEffectWindow.setModal(false);
		sceneEffectWindow.setMovable(true);
		S3.stage.addActor(sceneEffectWindow);
	}

	public void destroyGui(){
		S3.stage.getRoot().removeActor(sceneEffectWindow);
	}

}
