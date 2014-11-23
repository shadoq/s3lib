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

import com.badlogic.gdx.ApplicationListener;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Jarek
 */
public class RunTest extends S3 implements ApplicationListener{

	public static final Class[] tests = {
	EmptyApp.class,
	FxEffectTest.class,
	FxMultiEffectTest.class,
	FxNodeTest.class,
	G2DTextureAtlas.class,
	G2DTTFFont.class,
	G2DSimpleShapes.class,
	G3DFogTest.class,
	G3DMaterialTest.class,
	G3DModelInstanceTest.class,
	G3DModelRenderableTest.class,
	G3DModelTest.class,
	G3DNewBatchRenderTest.class,
	G3DNewModelTest.class,
	G3DShaderTest.class,
	G3DShaderToyTest.class,
	G3DShaderSimpleTest.class,
	G3DObjectModelTest.class,
	Gl20Test.class,
	LWBobsTest.class,
	ProceduralPixmapTest.class,
	S3AudioSpectrumTest.class,
	S3GfxGradientTest.class,
	S3GuiAllTest.class,
	S3GuiDialogTest.class,
	S3GuiEditor2dTest.class,
	S3GuiFormTest.class,
	S3GuiResourceTest.class,
	S3GuiTest.class,
	S3GuiWidgetTest.class,
	S3GuiWidget2Test.class,
	S3ScreenEffectCreatorTest.class,
	S3ScreenFadeTest.class,
	S3ScreenLayerTest.class,
	S3ScreenTest.class,
	VirtualSpritePicking.class,
	ScrollPaneTest.class,
	Screen3Menu.class,
	ScreenConfig.class,
	ScreenGameArea.class,
	ScreenGameArea2.class,
	TabHostTest.class,
	TabHostGuiElementsTest.class,
	OzPlayTest.class,
	VirtualScreenTest.class,
	BackGroundPostProcessignTest.class,
	MultiAppScreenTest.class,
	SaveDataTest.class
	};

	public static String[] getNames(){
		List<String> names = new ArrayList<String>();
		for (Class clazz : tests){
			names.add(clazz.getSimpleName());
		}
		Collections.sort(names);
		return names.toArray(new String[names.size()]);
	}

	public static S3App getApplication(String testName){
		try {
			Class clazz = Class.forName("mobi.shad.s3libTest." + testName);
			return (S3App) clazz.newInstance();
		} catch (Exception e1){
			try {
				Class clazz = Class.forName("mobi.shad.s3libTest." + testName);
				return (S3App) clazz.newInstance();
			} catch (Exception e2){
				try {
					Class clazz = Class.forName("mobi.shad.s3libTest." + testName);
					return (S3App) clazz.newInstance();
				} catch (Exception e3){
					e3.printStackTrace();
					return null;
				}
			}
		}
	}

	public boolean needsGL20(){
		return true;
	}
}
