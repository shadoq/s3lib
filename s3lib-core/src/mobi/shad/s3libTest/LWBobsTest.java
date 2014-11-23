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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mobi.shad.s3lib.gfx.node.core.DefaultFxEffect;
import mobi.shad.s3lib.gfx.node.pixmap.anim.FxPlasma;
import mobi.shad.s3lib.gfx.node.util.FxPixmapSize;
import mobi.shad.s3lib.gfx.node.util.FxPixmapToAnimSprite;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.main.*;

import static mobi.shad.s3lib.main.S3.*;

/**
 * @author Jarek
 */
public class LWBobsTest extends S3App{

	private static boolean showGui = false;
	private static int presentId = 0;
	protected float start;
	protected float duration = 10;
	protected float localTime = 0;
	protected float sceneTime = 0;
	protected float endTime = 0;
	protected float procent = 0;
	protected Window sceneEffectWindow;
	protected Table sceneEditCell;
	private DefaultFxEffect effect;
	private boolean pauseEffect = false;
	private Label debugBar;
	private Table presentTable;

	@Override
	public void initalize(){

		//
		// PresentTable
		//
		TextButton demo1Btn = GuiResource.textButton("Demo 1", "demo1");
		demo1Btn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				GuiForm.setGlobalPresent(new String[][]{
				{"FxPlasma_plasmaSpeed", "100.0"},
				{"FxPlasma_plasmaScrollSpeed", "20.0"},
				{"FxPlasma_cellSizeX", "6.0"},
				{"FxPlasma_cellSizeY", "80.0"},
				{"transformMode", "0"},
				{"speedSizeX", "0.0"},
				{"speedSizeY", "0.0"},
				{"startSizeX", "0.0"},
				{"startSizeY", "0.0"},
				{"amplitudeSizeX", "0.0"},
				{"amplitudeSizeY", "0.0"},
				{"speedPositionX", "0.0"},
				{"speedPositionY", "0.0"},
				{"amplitudePositionX", "0.0"},
				{"amplitudePositionY", "0.0"}
				});
			}
		});

		TextButton demo2Btn = GuiResource.textButton("Demo 2", "demo2");

		demo2Btn.addListener(
		new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				GuiForm.setGlobalPresent(new String[][]{
				{"FxPlasma_plasmaSpeed", "24.0"},
				{"FxPlasma_plasmaScrollSpeed", "12.0"},
				{"FxPlasma_cellSizeX", "60.0"},
				{"FxPlasma_cellSizeY", "30.0"},
				{"transformMode", "4"},
				{"speedSizeX", "0.0"},
				{"speedSizeY", "0.0"},
				{"startSizeX", "0.0"},
				{"startSizeY", "0.0"},
				{"amplitudeSizeX", "0.0"},
				{"amplitudeSizeY", "0.0"},
				{"speedPositionX", "0.5"},
				{"speedPositionY", "0.0"},
				{"amplitudePositionX", "10.0"},
				{"amplitudePositionY", "0.0"},});
			}
		});

		TextButton demo3Btn = GuiResource.textButton("Demo 3", "demo3");

		demo3Btn.addListener(
		new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				GuiForm.setGlobalPresent(new String[][]{
				{"FxPlasma_plasmaSpeed", "40.0"},
				{"FxPlasma_plasmaScrollSpeed", "4.0"},
				{"FxPlasma_cellSizeX", "80.0"},
				{"FxPlasma_cellSizeY", "6.0"},
				{"transformMode", "4"},
				{"speedSizeX", "0.0"},
				{"speedSizeY", "0.0"},
				{"startSizeX", "0.0"},
				{"startSizeY", "0.0"},
				{"amplitudeSizeX", "0.0"},
				{"amplitudeSizeY", "0.0"},
				{"speedPositionX", "0.5"},
				{"speedPositionY", "2.7"},
				{"amplitudePositionX", "10.0"},
				{"amplitudePositionY", "6.8"},});
			}
		});

		TextButton demo4Btn = GuiResource.textButton("Demo 4", "demo4");

		demo4Btn.addListener(
		new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				GuiForm.setGlobalPresent(new String[][]{
				{"FxPlasma_plasmaSpeed", "100.0"},
				{"FxPlasma_plasmaScrollSpeed", "50.0"},
				{"FxPlasma_cellSizeX", "100.0"},
				{"FxPlasma_cellSizeY", "100.0"},
				{"transformMode", "4"},
				{"speedSizeX", "0.0"},
				{"speedSizeY", "0.0"},
				{"startSizeX", "0.0"},
				{"startSizeY", "0.0"},
				{"amplitudeSizeX", "0.0"},
				{"amplitudeSizeY", "0.0"},
				{"speedPositionX", "4.0"},
				{"speedPositionY", "4.0"},
				{"amplitudePositionX", "50.0"},
				{"amplitudePositionY", "50.0"},});
			}
		});

		TextButton demo5Btn = GuiResource.textButton("Demo 5", "demo5");

		demo5Btn.addListener(
		new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				GuiForm.setGlobalPresent(new String[][]{
				{"FxPlasma_plasmaSpeed", "50.0"},
				{"FxPlasma_plasmaScrollSpeed", "10.0"},
				{"FxPlasma_cellSizeX", "10.0"},
				{"FxPlasma_cellSizeY", "10.0"},
				{"transformMode", "5"},
				{"speedSizeX", "2.0"},
				{"speedSizeY", "1.5"},
				{"startSizeX", "15.0"},
				{"startSizeY", "22.0"},
				{"amplitudeSizeX", "48.0"},
				{"amplitudeSizeY", "48.0"},
				{"speedPositionX", "0.5"},
				{"speedPositionY", "2.7"},
				{"amplitudePositionX", "10.0"},
				{"amplitudePositionY", "6.8"},});
			}
		});

		presentTable = GuiResource.table("presentTable");

		presentTable.row();

		presentTable.add(demo1Btn).expandX().fillX();
		presentTable.add(demo2Btn).expandX().fillX();
		presentTable.add(demo3Btn).expandX().fillX();
		presentTable.add(demo4Btn).expandX().fillX();
		presentTable.add(demo5Btn).expandX().fillX();

		effect = new DefaultFxEffect();

		effect.addFxFilter(
		new FxPixmapSize(effect.getFormData(), effect.getChangeListener()));
		effect.addFxFilter(
		new FxPlasma(effect.getFormData(), effect.getChangeListener()));
		effect.addFxFilter(
		new FxPixmapToAnimSprite(effect.getFormData(), effect.getChangeListener()));
		effect.getFxFilter()
			  .process();

		GuiForm.setGlobalPresent(new String[][]{
		{"FxPlasma_plasmaSpeed", "100.0"},
		{"FxPlasma_plasmaScrollSpeed", "50.0"},
		{"FxPlasma_cellSizeX", "100.0"},
		{"FxPlasma_cellSizeY", "100.0"},
		{"transformMode", "4"},
		{"speedSizeX", "0.0"},
		{"speedSizeY", "0.0"},
		{"startSizeX", "0.0"},
		{"startSizeY", "0.0"},
		{"amplitudeSizeX", "0.0"},
		{"amplitudeSizeY", "0.0"},
		{"speedPositionX", "4.0"},
		{"speedPositionY", "4.0"},
		{"amplitudePositionX", "50.0"},
		{"amplitudePositionY", "50.0"},});


		gui();
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
		}
	}

	@Override
	public void render(S3Gfx g){

		g.clear();
		effect.render(new SpriteBatch(), 1.0f);

		if (S3Constans.SCREEN_DEBUG_BAR){
			if (debugBar != null){
				debugBar.setText(""
								 + " fps: " + graphics.getFramesPerSecond()
								 + " osTime: " + String.format("%.2f", osTime)
								 + " h: " + (Gdx.app.getJavaHeap() / 1024) + " kb"
								 + " m: " + (Gdx.app.getNativeHeap() / 1024) + " kb" + "\n");
			}
		}
	}

	@Override
	public void resize(){
		if (stage != null){
			destroyGui();
			gui();
		}
	}

	public void gui(){
		Image logo = GuiResource.image("gfx/logo.png");
		logo.setWidth(260);
		logo.setHeight(93);
		Label url = GuiResource.label("http://shad.mobi", "");
		TextButton rateApp = GuiResource.textButton("Rate My App", "");
		TextButton moreApp = GuiResource.textButton("More App", "");

		rateApp.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Gdx.net.openURI("https://play.google.com/store/apps/developer?id=shad.net.pl");
			}
		});
		moreApp.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Gdx.net.openURI("https://play.google.com/store/apps/developer?id=shad.net.pl");
			}
		});

		sceneEditCell = effect.gui();
		sceneEffectWindow = GuiResource.window("", "sceneEffectWindow");
		ScrollPane guiScrollCell = GuiResource.scrollPaneTransparent(sceneEditCell, "guiScrollCell");
		guiScrollCell.setScrollingDisabled(true, false);
		guiScrollCell.setFlickScroll(true);
		guiScrollCell.setFadeScrollBars(false);

		sceneEffectWindow.row();
		sceneEffectWindow.add(logo).center().colspan(2).height(93);

		sceneEffectWindow.row();
		sceneEffectWindow.add(presentTable).height(S3Constans.gridY1).width(S3Constans.gridX9_75).left().top().colspan(2);

		sceneEffectWindow.row();
		sceneEffectWindow.add(guiScrollCell).height(S3Constans.gridY5_5).width(S3Constans.gridX9_75).left().top().colspan(2);

		sceneEffectWindow.row();
		sceneEffectWindow.add(rateApp).height(S3Constans.gridY0_50).center().expandX().fillX();
		sceneEffectWindow.add(moreApp).height(S3Constans.gridY0_50).center().expandX().fillX();

		GuiUtil.windowPosition(sceneEffectWindow, 5, 5);
		//		sceneEffectWindow.setModal(false);
		//		sceneEffectWindow.setMovable(true);
		stage.addActor(sceneEffectWindow);
	}

	public void destroyGui(){
		effect.getFormData().clearGuiTable();
		stage.getRoot().removeActor(sceneEffectWindow);
	}
}
