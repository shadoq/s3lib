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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mobi.shad.s3lib.gfx.node.core.DefaultFxEffect;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gfx.node.filter.*;
import mobi.shad.s3lib.gfx.node.pixmap.filter.FxBlur;
import mobi.shad.s3lib.gfx.node.pixmap.filter.FxGlow;
import mobi.shad.s3lib.gfx.node.pixmap.filter.FxGradient;
import mobi.shad.s3lib.gfx.node.pixmap.filter.FxThreshold;
import mobi.shad.s3lib.gfx.node.pixmap.procedural.FxPerlinNoiseColor;
import mobi.shad.s3lib.gfx.node.pixmap.procedural.FxTextureLoader;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class FxEffectTest extends S3App{

	protected float start;
	protected float duration = 10;
	protected float localTime = 0;
	protected float sceneTime = 0;
	protected float endTime = 0;
	protected float procent = 0;
	protected Node fx;
	protected Window sceneEffectWindow;
	protected Table sceneEditCell;
	private DefaultFxEffect effect;
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

		Table bT = GuiResource.table("AAAAAAA");

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
					fx = null;
					if (checked.getName().equalsIgnoreCase(FxEffectSize.class.getName())){
						fx = new FxEffectSize(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxShader.class.getName())){
						fx = new FxShader(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxTextureLoader.class.getName())){
						fx = new FxTextureLoader(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxBlur.class.getName())){
						fx = new FxBlur(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxPerlinNoiseColor.class.getName())){
						fx = new FxPerlinNoiseColor(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxStarField.class.getName())){
						fx = new FxStarField(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxPointSingle.class.getName())){
						fx = new FxPointSingle(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxPointMulti.class.getName())){
						fx = new FxPointMulti(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxPoint3D.class.getName())){
						fx = new FxPoint3D(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
						effect.addFxFilter(new FxCamera(effect.getFormData(), effect.getChangeListener()));
					} else if (checked.getName().equalsIgnoreCase(FxObject3D.class.getName())){
						fx = new FxObject3D(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
						effect.addFxFilter(new FxCamera(effect.getFormData(), effect.getChangeListener()));
					} else if (checked.getName().equalsIgnoreCase(FxCamera.class.getName())){
						fx = new FxCamera(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxPlasma3D.class.getName())){
						fx = new FxPlasma3D(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxPointSingleNoMul.class.getName())){
						fx = new FxPointSingleNoMul(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxPointMultiNoMul.class.getName())){
						fx = new FxPointMultiNoMul(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxTextScroll.class.getName())){
						fx = new FxTextScroll(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxGradient.class.getName())){
						fx = new FxGradient(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxGlow.class.getName())){
						fx = new FxGlow(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					} else if (checked.getName().equalsIgnoreCase(FxThreshold.class.getName())){
						fx = new FxThreshold(effect.getFormData(), effect.getChangeListener());
						effect.addFxFilter(fx);
					}

					if (fx != null){
						fx.process();
					}
					destroyGui();
					gui();
					localTime = 0;
					duration = 10;

				}
			});

			bT.add(buttonLoop).fillX();
			bT.row();
			i++;
		}

		bT.pack();
		ScrollPane guiScrollCell = GuiResource.scrollPaneTransparent(bT, "guiScrollCell");
		guiScrollCell.setScrollingDisabled(true, false);
		guiScrollCell.setFlickScroll(true);
		guiScrollCell.setFadeScrollBars(false);

		window.row();
		window.add(guiScrollCell)
			  .height(S3Screen.height - 29 - buttonPause.getHeight() * 2 - S3Constans.cellPaddding * 2)
			  .width(S3Constans.gridX2_5);
		window.row();
		window.add(buttonPause).fillX();
		window.pack();
		window.setX(0);
		window.setY(S3Screen.height - window.getHeight());
		S3.stage.addActor(window);
	}

	@Override
	public void update(){
		if (!pauseEffect){
			localTime = localTime + S3.osDeltaTime;
			sceneTime = start + localTime;
			endTime = duration - localTime;
			procent = localTime / duration;

			effect.update(localTime, sceneTime, endTime, procent, false);

			if (procent > 0.95f){
				localTime = 0;
			}
		}

	}

	@Override
	public void render(S3Gfx g){
		g.clear(0.2f, 0.0f, 0.0f);
		effect.render(new SpriteBatch(), 1.0f);
	}

	public void gui(){
		sceneEditCell = effect.gui();
		sceneEffectWindow = GuiResource.window("EffectEditor", "sceneEffectWindow");
		ScrollPane guiScrollCell = GuiResource.scrollPaneTransparent(sceneEditCell, "guiScrollCell");
		guiScrollCell.setScrollingDisabled(true, false);
		guiScrollCell.setFlickScroll(true);
		guiScrollCell.setFadeScrollBars(false);

		sceneEffectWindow.row();
		sceneEffectWindow.add(guiScrollCell).height(S3Screen.height - 29).width(S3Constans.gridX3).left().top();

		GuiUtil.windowPosition(sceneEffectWindow, 10, 0);

		sceneEffectWindow.setModal(false);
		sceneEffectWindow.setMovable(true);
		S3.stage.addActor(sceneEffectWindow);
	}

	public void destroyGui(){
		S3.stage.getRoot().removeActor(sceneEffectWindow);
	}
}
