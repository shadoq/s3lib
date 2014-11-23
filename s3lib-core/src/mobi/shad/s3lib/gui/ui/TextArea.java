/*******************************************************************************
 * Copyright 2014
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
package mobi.shad.s3lib.gui.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Jarek on 2014-06-13.
 */
public class TextArea extends com.badlogic.gdx.scenes.scene2d.ui.TextArea{

	protected String styleName = "default";

	public TextArea(String text, Skin skin){
		super(text, skin);
	}

	public TextArea(String text, Skin skin, String styleName){
		super(text, skin, styleName);
		this.styleName = styleName;
	}

	public TextArea(String text, TextFieldStyle style){
		super(text, style);
	}

	public String getStyleName(){
		return styleName;
	}

	public void setStyleName(String styleName){
		this.styleName = styleName;
	}

}
