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
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.audio.AudioAnalyzer;
import mobi.shad.s3lib.gui.Gui;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class S3AudioSpectrumTest extends S3App{

	AudioAnalyzer player;
	Texture texture;
	OrthographicCamera camera;
	SpriteBatch batch;
	float barWidth = 5;
	private Window window;
	private float footStart = 1;
	private float footBars = 10;
	private float footDetect = 50;
	private float footThreshold = 90;
	private float kickStart = 25;
	private float kickBars = 35;
	private float kickDetect = 50;
	private float kickThreshold = 70;

	@Override
	public void initalize(){

		player = new AudioAnalyzer("sound/beezerk_-_i_love_bees.mp3");
		//		player=new S3AudioAnalizer("sound/bitjam_148.mp3");
		player.setFootStart((int) footStart);
		player.setFootBars((int) footBars);
		player.setFootDetect(footDetect / 100);
		player.setFootThreshold(footThreshold / 100);

		player.setKickStart((int) kickStart);
		player.setKickBars((int) kickBars);
		player.setKickDetect(kickDetect / 100);
		player.setKickThreshold(kickThreshold / 100);

		player.startPlay();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, S3Screen.width, S3Screen.height);
		Pixmap px = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
		px.setColor(Color.WHITE);
		px.fill();
		texture = new Texture(px);
		batch = new SpriteBatch();

		//
		//
		//

		//
		// Gui
		//
		Gui gui = new Gui();

		ChangeListener listener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				player.setFootStart((int) footStart);
				player.setFootBars((int) footBars);
				player.setFootDetect(footDetect / 100);
				player.setFootThreshold(footThreshold / 100);

				player.setKickStart((int) kickStart);
				player.setKickBars((int) kickBars);
				player.setKickDetect(kickDetect / 100);
				player.setKickThreshold(kickThreshold / 100);
			}
		};

		gui.addLabel("Foot Detect", true, 1);
		gui.addSlider("footStart", this, "footStart", footStart, 0, 50, 1f, listener);
		gui.addSlider("footBars", this, "footBars", footBars, 1, 50, 1f, listener);
		gui.addSlider("footDetect", this, "footDetect", footDetect, 0, 100, 1, listener);
		gui.addSlider("footThreshold", this, "footThreshold", footThreshold, 0, 100, 1, listener);

		gui.addLabel("Kick Detect", true, 1);
		gui.addSlider("kickStart", this, "kickStart", kickStart, 0, 50, 1f, listener);
		gui.addSlider("kickBars", this, "kickBars", kickBars, 1, 50, 1f, listener);
		gui.addSlider("kickDetect", this, "kickDetect", kickDetect, 0, 100, 1, listener);
		gui.addSlider("kickThreshold", this, "kickThreshold", kickThreshold, 0, 100, 1, listener);

		//  final Slider sliderMainSpeedX=gui.addSlider(S3Lang.get("shaderMainSpeedX"), this, "shaderMainSpeedX", shaderMainSpeedX, -10f, 10f, 0.1f);

		window = GuiResource.window("BPM Test", "shaderConfig");
		window.left();
		window.row();
		window.add(gui.getScrollPane()).height(S3Screen.height - 29).width(S3Constans.gridX3).left().top();
		GuiUtil.windowPosition(window, 10, 0);

		window.setModal(false);
		window.setMovable(true);

		S3.stage.addActor(window);
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){

		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		if (player.isFoot()){
			Gdx.gl.glClearColor(0.5f, 0.0f, 0.0f, 1);
		}
		if (player.isKick()){
			Gdx.gl.glClearColor(0.0f, 0.0f, 0.5f, 1);
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		float[] barValues = player.barValues();
		float[] maxValues = player.getMaxValues();
		float[] topValues = player.getTopValues();
		float[] top2Values = player.getTop2Values();
		float[] top3Values = player.getTop3Values();
		barWidth = S3Screen.width / player.getSpectrumBars();

		batch.setColor(Color.GREEN);
		batch.draw(texture, footStart * barWidth, 0, footBars * barWidth, player.getFootSize() * S3Screen.height);

		batch.setColor(Color.YELLOW);
		batch.draw(texture, kickStart * barWidth, 0, kickBars * barWidth, player.getKickSize() * S3Screen.height);

		for (int i = 0; i < player.getSpectrumBars(); i++){
			batch.setColor(Color.BLUE);
			batch.draw(texture, i * barWidth, 0, barWidth, barValues[i] * 6);

			batch.setColor(Color.RED);
			batch.draw(texture, i * barWidth, maxValues[i] * 6, barWidth, 2);

			batch.setColor(Color.GRAY);
			batch.draw(texture, i * barWidth, topValues[i] * 6, barWidth, 2);

			batch.setColor(Color.LIGHT_GRAY);
			batch.draw(texture, i * barWidth, top2Values[i] * 6, barWidth, 2);

			batch.setColor(Color.WHITE);
			batch.draw(texture, i * barWidth, top3Values[i] * 6, barWidth, 2);

		}

		batch.end();
	}
}
