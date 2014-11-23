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
package mobi.shad.s3lib.gfx.node.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Log;

import java.io.IOException;

/**
 * @author Jarek
 */
public abstract class FxEffect extends Effect{

	protected Node fxFilter = new Node();
	protected GuiForm formData = new GuiForm(this.getClass().getSimpleName());
	protected ChangeListener changeListener = new ChangeListener(){

		@Override
		public void changed(ChangeListener.ChangeEvent event, Actor actor){
			fxFilter.process();
		}
	};

	/**
	 *
	 */
	@Override
	public void init(){
		fxFilter.process();
	}

	/**
	 *
	 */
	@Override
	public void start(){
	}

	/**
	 *
	 */
	@Override
	public void stop(){
	}

	/**
	 *
	 */
	@Override
	public void render(Batch batch, float parentAlpha){
		Render.render(fxFilter.data);
	}

	/**
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 * @param isPause
	 */
	@Override
	public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){
		if (fxFilter.data.shader != null){
			fxFilter.data.shader.setTimeShader(effectTime);
		}
		fxFilter.updateProcess(effectTime, sceneTime, endTime, procent, isPause);
	}

	/**
	 * @return
	 */
	@Override
	public Table gui(){
		fxFilter.disableChange = true;
		Table table = formData.getTable();
		fxFilter.disableChange = false;
		return table;
	}

	/**
	 * @param writer
	 * @return
	 */
	@Override
	public XmlWriter savePrefs(XmlWriter writer){
		try {
			writer.element("effect").attribute("class", this.getClass().getName());
			formData.save(writer);
			writer.pop();
			return writer;
		} catch (IOException ex){
			S3Log.log("savePrefsToJson", ex.toString() + " Exception in " + this.getClass().getName());
			return writer;
		}
	}

	/**
	 * @param effectElement
	 */
	@Override
	public void readPrefs(Element effectElement){
		if (effectElement == null){
			return;
		}
		String effectClass = effectElement.getAttribute("class", "null");

		S3Log.log("readPrefs", " Effect class:" + effectClass);
		if (!effectClass.equalsIgnoreCase(this.getClass().getName())){
			S3Log.log("readPrefs", " !!! Class is not the same.");
			return;
		}
		formData.read(effectElement);
		fxFilter.process();
	}

	/**
	 *
	 */
	@Override
	public void preRender(){
	}

	/**
	 *
	 */
	@Override
	public void postRender(){
	}

	/**
	 * @return
	 */
	@Override
	public boolean isRender(){
		return true;
	}

	public Node getFxFilter(){
		return fxFilter;
	}
}
