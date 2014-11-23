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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mobi.shad.s3lib.gfx.node.core.DefaultFxEffect;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gfx.node.filter.*;
import mobi.shad.s3lib.gfx.node.pixmap.anim.FxJuliaAnim;
import mobi.shad.s3lib.gfx.node.pixmap.anim.FxManderbrotAnim;
import mobi.shad.s3lib.gfx.node.pixmap.anim.FxPlasma;
import mobi.shad.s3lib.gfx.node.pixmap.filter.*;
import mobi.shad.s3lib.gfx.node.pixmap.procedural.*;
import mobi.shad.s3lib.gfx.node.util.FxPixmapSize;
import mobi.shad.s3lib.gfx.node.util.FxPixmapToAnimSprite;
import mobi.shad.s3lib.gfx.node.util.FxPixmapToSprite;
import mobi.shad.s3lib.gfx.node.util.FxPixmapToTextureSprite;
import mobi.shad.s3lib.gui.Gui;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.main.*;

import static mobi.shad.s3lib.main.S3.*;

/**
 * @author Jarek
 */
public class FxMultiEffectTest extends S3App{

	protected InputMultiplexer inputMultiplexer;
	protected float start;
	protected float duration = 10;
	protected float localTime = 0;
	protected float sceneTime = 0;
	protected float endTime = 0;
	protected float procent = 0;
	String fxClass1 = "";
	String fxClass2 = "";
	String fxClass3 = "";
	String fxClass4 = "";
	private DefaultFxEffect effect;
	private Window sceneEffectWindow;
	private boolean pauseEffect = false;
	private String[] effectList = {"", FxPixmapSize.class.getName(), FxPixmapToSprite.class.getName(),
								   FxPixmapToTextureSprite.class.getName(), FxPixmapToAnimSprite.class.getName(), "------",
								   FxGradientMap.class.getName(), FxGradient.class.getName(), FxColorFilter.class.getName(), "------",
								   FxPlasma.class.getName(), FxJuliaAnim.class.getName(), FxManderbrotAnim.class.getName(),
								   FxPlasma3D.class.getName(), "------", FxTextScroll.class.getName(), FxTextureGalleryMask.class.getName(),
								   FxTextureLoader.class.getName(), FxJulia.class.getName(), FxManderbrot.class.getName(),
								   FxCell.class.getName(), FxPerlinNoise.class.getName(), FxPerlinNoiseColor.class.getName(), "------",
								   FxGlow.class.getName(), FxNoise.class.getName(), FxNoiseGrey.class.getName(), FxThreshold.class.getName(),
								   FxInvert.class.getName(), FxNormals.class.getName(), FxBlur.class.getName(), "------",
								   FxEffectSize.class.getName(), FxShader.class.getName(), FxStarField.class.getName(),
								   FxPointSingle.class.getName(), FxPointMulti.class.getName(), FxPoint3D.class.getName(),
								   FxObject3D.class.getName(), FxCamera.class.getName(), FxSinusLine.class.getName(), "------",};
	private String[] effectListNames = {"------", FxPixmapSize.class.getSimpleName(),
										FxPixmapToSprite.class.getSimpleName(), FxPixmapToTextureSprite.class.getSimpleName(),
										FxPixmapToAnimSprite.class.getSimpleName(), "-- Color ----", FxGradientMap.class.getSimpleName(),
										FxGradient.class.getSimpleName(), FxColorFilter.class.getSimpleName(), "-- Anim ----",
										FxPlasma.class.getSimpleName(), FxJuliaAnim.class.getSimpleName(), FxManderbrotAnim.class.getSimpleName(),
										FxPlasma3D.class.getSimpleName(), "-- Proc. Texture ----", FxTextScroll.class.getSimpleName(),
										FxTextureGalleryMask.class.getSimpleName(), FxTextureLoader.class.getSimpleName(),
										FxJulia.class.getSimpleName(), FxManderbrot.class.getSimpleName(), FxCell.class.getSimpleName(),
										FxPerlinNoise.class.getSimpleName(), FxPerlinNoiseColor.class.getSimpleName(), "-- Proc. Filter ----",
										FxGlow.class.getSimpleName(), FxNoise.class.getSimpleName(), FxNoiseGrey.class.getSimpleName(),
										FxThreshold.class.getSimpleName(), FxInvert.class.getSimpleName(), FxNormals.class.getSimpleName(),
										FxBlur.class.getSimpleName(), "-- Point Filter ----", FxEffectSize.class.getSimpleName(),
										FxShader.class.getSimpleName(), FxStarField.class.getSimpleName(), FxPointSingle.class.getSimpleName(),
										FxPointMulti.class.getSimpleName(), FxPoint3D.class.getSimpleName(), FxObject3D.class.getSimpleName(),
										FxCamera.class.getSimpleName(), FxSinusLine.class.getSimpleName(), "------",};
	private List addList;
	private List addList1;
	private List addList2;
	private List addList3;
	private Label debugBar;

	@Override
	public void initalize(){

		effect = new DefaultFxEffect();
		gui();

		final Button buttonPause = GuiResource.textButtonToggle("Pause", "Pause");
		buttonPause.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				pauseEffect = !pauseEffect;
			}
		});

		Window window = GuiResource.window("Test Effect", "window");
		window.defaults().align(Align.top | Align.left);

		ChangeListener changeListener = new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){

				Node fx1 = null;
				Node fx2 = null;
				Node fx3 = null;
				Node fx4 = null;

				String fx1ClassName = effectList[addList.getSelectedIndex()];
				String fx2ClassName = effectList[addList1.getSelectedIndex()];
				String fx3ClassName = effectList[addList2.getSelectedIndex()];
				String fx4ClassName = effectList[addList3.getSelectedIndex()];

				S3Log.log("FxMultiEffect", "------------------------------------------------------------------------", 1);
				S3Log.log("FxMultiEffect", "Create filter: fx1: " + fx1ClassName + " fx2: " + fx2ClassName + " fx3: "
				+ fx3ClassName, 1);
				effect = new DefaultFxEffect();
				try {
					if (!fx1ClassName.equalsIgnoreCase("")){
						fx1 = (Node) Class.forName(fx1ClassName).newInstance();
						fx1.setFormGui(effect.getFormData(), effect.getChangeListener());
					}

					try {
						if (!fx2ClassName.equalsIgnoreCase("")){
							fx2 = (Node) Class.forName(fx2ClassName).newInstance();
							fx2.setFormGui(effect.getFormData(), effect.getChangeListener());
						}
					} catch (Exception ex){
					}
					try {
						if (!fx3ClassName.equalsIgnoreCase("")){
							fx3 = (Node) Class.forName(fx3ClassName).newInstance();
							fx3.setFormGui(effect.getFormData(), effect.getChangeListener());
						}
					} catch (Exception ex){
					}
					try {
						if (!fx4ClassName.equalsIgnoreCase("")){
							fx4 = (Node) Class.forName(fx4ClassName).newInstance();
							fx4.setFormGui(effect.getFormData(), effect.getChangeListener());
						}
					} catch (Exception ex){
					}

				} catch (InstantiationException ex){
					S3Log.error(this.getClass().getName(), "Error creare effect ...", ex);
				} catch (IllegalAccessException ex){
					S3Log.error(this.getClass().getName(), "Error creare effect ...", ex);
				} catch (ClassNotFoundException ex){
					S3Log.error(this.getClass().getName(), "Error creare effect ...", ex);
				}
				effect.addFxFilter(fx1);
				effect.addFxFilter(fx2);
				effect.addFxFilter(fx3);
				effect.addFxFilter(fx4);

				destroyGui();
				gui();

				S3Log.log("FxMultiEffect::process", "------------------------------------------------------------------------", 1);
				effect.getFxFilter().process();
				S3Log.log("FxMultiEffect::process", "------------------------------------------------------------------------", 1);

			}
		};

		Gui gui = new Gui();
		addList = gui
		.addList("fxClass1", this, "fxClass1", effectListNames, "------", true, 1, changeListener, S3Constans.gridY2);
		addList1 = gui
		.addList("fxClass2", this, "fxClass2", effectListNames, "------", true, 1, changeListener, S3Constans.gridY2);
		addList2 = gui
		.addList("fxClass3", this, "fxClass3", effectListNames, "------", true, 1, changeListener, S3Constans.gridY2);
		addList3 = gui
		.addList("fxClass4", this, "fxClass4", effectListNames, "------", true, 1, changeListener, S3Constans.gridY2);

		window.row();
		window.add(gui.getScrollPane()).height(S3Screen.height - 29 - S3Constans.cellPaddding * 2).width(S3Constans.gridX3)
			  .left().top();
		window.row();
		window.add(buttonPause).fillX();
		window.pack();
		window.setX(0);
		window.setY(S3Screen.height - window.getHeight());
		stage.addActor(window);

		inputMultiplexer = new InputMultiplexer(Gdx.input.getInputProcessor());

		//
		// Debug Bar
		//
		if (S3Constans.SCREEN_DEBUG_BAR){
			debugBar = new Label("debugBar:", S3Skin.skin);
			debugBar.setX(0);
			debugBar.setY(10);
			stage.addActor(debugBar);
		}

	}

	@Override
	public void update(){
		if (!pauseEffect){
			localTime = localTime + S3.osDeltaTime;
			sceneTime = start + localTime;
			endTime = duration - localTime;
			procent = (float) localTime / duration;

			effect.update(localTime, sceneTime, endTime, procent, false);

			if (procent > 0.95f){
				localTime = 0;
			}
		}
	}

	@Override
	public void render(S3Gfx g){

		g.clear(0.1f, 0.1f, 0.1f);
		effect.render(new SpriteBatch(), 1.0f);

		if (S3Constans.SCREEN_DEBUG_BAR){
			if (debugBar != null){
				debugBar.setText("" + " fps: " + graphics.getFramesPerSecond() + " osTime: "
								 + String.format("%.2f", osTime) + " h: " + (Gdx.app.getJavaHeap() / 1024) + " kb" + " m: "
								 + (Gdx.app.getNativeHeap() / 1024) + " kb" + "\n");
			}
		}
	}

	public void gui(){
		sceneEffectWindow = GuiResource.window("EffectEditor", "sceneEffectWindow");

		final Table sceneEditCell = effect.gui();
		ScrollPane guiScrollCell = GuiResource.scrollPaneTransparent(sceneEditCell, "guiScrollCell");
		guiScrollCell.setScrollingDisabled(true, false);
		guiScrollCell.setFlickScroll(true);
		guiScrollCell.setFadeScrollBars(false);

		sceneEffectWindow.row();
		sceneEffectWindow.add(guiScrollCell).height(S3Screen.height - 29 - S3Constans.cellPaddding * 2)
						 .width(S3Constans.gridX3).left().top();

		GuiUtil.windowPosition(sceneEffectWindow, 10, 0);

		sceneEffectWindow.setModal(false);
		sceneEffectWindow.setMovable(true);
		stage.addActor(sceneEffectWindow);
	}

	public void destroyGui(){
		stage.getRoot().removeActor(sceneEffectWindow);
	}
}
