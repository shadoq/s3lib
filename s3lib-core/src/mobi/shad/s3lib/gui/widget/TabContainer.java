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
package mobi.shad.s3lib.gui.widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * @author Jarek
 */
public class TabContainer extends Table{

	private TabContainerStyle style;
	private Actor actor;

	public static class TabContainerStyle{

		public Drawable background;

		public TabContainerStyle(){

		}

	}

	public TabContainer(){
		super();
	}

	public TabContainer(Skin skin){
		this(skin, "default");
	}

	public TabContainer(Skin skin, String styleName){
		super(skin);
		setStyle(skin.get(styleName, TabContainerStyle.class));
	}

	public void setStyle(TabContainerStyle style){
		this.style = style;
		setBackground(style.background);
	}

}