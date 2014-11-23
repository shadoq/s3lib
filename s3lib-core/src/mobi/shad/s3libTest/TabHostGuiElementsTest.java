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
import com.badlogic.gdx.graphics.GL20;
import mobi.shad.s3lib.gui.widget.TabHost;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class TabHostGuiElementsTest extends S3App{

	@Override
	public void initalize(){
		TabHost tabHost = new TabHost(S3Skin.skin);
		TabHost tabHost2 = new TabHost(S3Skin.skin);
		TabHost tabHost3 = new TabHost(S3Skin.skin, true);

		tabHost.addTab("Test", "Test Main", S3Skin.skin);
		tabHost.addTab("Main Screen", tabHost2, S3Skin.skin);
		tabHost.addTab("Game", tabHost3, S3Skin.skin);
		tabHost.addTab("Config", "Test Main 4", S3Skin.skin);
		tabHost.addTab("Render", "Test Main 5", S3Skin.skin);

		tabHost2.addTab("Test Tab 2a", "Tab 2 a ........");
		tabHost2.addTab("Test Tab 2b", "Tab 2 b ........");
		tabHost2.addTab("Test Tab 2c", "Tab 2 c ........");
		tabHost2.addTab("Test Tab 2d", "Tab 2 d ........");

		tabHost3.addTab("Test Tab 3a", "Tab 3 a ........");
		tabHost3.addTab("Test Tab 3b", "Tab 3 b ........");
		tabHost3.addTab("Test Tab 3c", "Tab 3 c ........");
		tabHost3.addTab("Test Tab 3d", "Tab 3 d ........");

		tabHost.setX(0);
		tabHost.setY(0);
		tabHost.setWidth(S3Screen.width);
		tabHost.setHeight(S3Screen.height);

		S3.stage.addActor(tabHost);

		//		TabHost tabHost2=new TabHost(S3Skin.skin, true);
		//		tabHost2.add("Test", "Test Main", S3Skin.skin);
		//		tabHost2.add("Test 2", "Test Main 2", S3Skin.skin);
		//		tabHost2.add("Test 3", "Test Main 3", S3Skin.skin);
		//		tabHost2.add("Test 4", "Test Main 4", S3Skin.skin);
		//		tabHost2.add("Test 5", "Test Main 5", S3Skin.skin);
		//
		//		String code=""
		//			+ "<html>"
		//			+ "<p>Test P</p>"
		//			+ "<p font=\"sans12\" color=\"red\">Test P</p>"
		//			+ "<p font=\"sans13\">Test P</p>"
		//			+ "<p font=\"sans14\">Test P</p>"
		//			+ "<h1>Test H1</h1>"
		//			+ "<h2>Test H2</h2>"
		//			+ "<h3>Test H3</h3>"
		//			+ "<h4>Test H4</h4>"
		//			+ "<h5>Test H5</h5>"
		//			+ "<h6>Test H6</h6>"
		//			+ "<p align=\"left\" collspan=\"2\">Test P left</p>"
		//			+ "<p align=\"right\" collspan=\"2\">Test P right</p>"
		//			+ "<p align=\"center\" collspan=\"2\">Test P center</p>"
		//			+ "<div color=\"yellow\">Test DIV yellow</div>"
		//			+ "<div>Test DIV</div>"
		//			+ "<img src=\"def256.jpg\" width=\"64\" height=\"64\">Test IMG</img>"
		//			+ "<img src=\"def256.jpg\" width=\"128\" height=\"128\" align=\"left\">Test IMG left</img>"
		//			+ "<img src=\"def256.jpg\" width=\"64\" height=\"64\" align=\"right\">Test IMG right</img>"
		//			+ "<img src=\"def256.jpg\" width=\"64\" height=\"64\" align=\"center\" collspan=\"2\">Test IMG center</img>"
		//			+ "</html>";
		//		HtmlView htmlView=new HtmlView(code, S3File.getFileHandle("texture"), S3Skin.skin);
		//
		//		Table mainTest=new Table(S3Skin.skin);
		//		mainTest.add(tabHost).width(S3Constans.gridX3).top().fillX().fillY();
		//		mainTest.add(htmlView).width(S3Constans.gridX3).top().expandX();
		//		mainTest.add(tabHost2).width(S3Constans.gridX3).top().expandX();
		//		GuiUtil.windowPosition(mainTest, GuiUtil.Position.CENTER);
		//		S3.stage.addActor(mainTest);
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
