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
import com.badlogic.gdx.utils.*;

import java.util.ArrayList;

/**
 * @author Jarek
 */
public abstract class Effect{

	//
	// Czas trwania efektu
	//
	protected float duration = 0;

	protected Actor parentActor;


	/**
	 * Ustawia czas trwania efektu
	 *
	 * @return
	 */
	public float getDuration(){
		return duration;
	}

	/**
	 * Zwraca czas trwania efektu
	 *
	 * @param duration
	 */
	public void setDuration(float duration){
		this.duration = duration;
	}

	public Actor getParentActor(){
		return parentActor;
	}

	public void setParentActor(Actor parentActor){
		this.parentActor = parentActor;
	}

	/**
	 * Inicjuje efekt - metoda wywoływana podczas
	 * inicjacji prezentacji
	 */
	abstract public void init();

	/**
	 * Start efektu
	 */
	abstract public void start();

	/**
	 * Stop efektu
	 */
	abstract public void stop();

	/**
	 * Wykonuje przeliczanie parametrów efektu
	 *
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 * @param isPause
	 */
	abstract public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause);

	/*
	 * Metoda wywoływana przed renderowaniem wszytkich elementów prezentacji
	 */
	abstract public void preRender();

	/**
	 * Renderowanie
	 */
	abstract public void render(Batch batch, float parentAlpha);

	abstract public void postRender();

	abstract public Table gui();

	abstract public void readPrefs(XmlReader.Element effectElement);

	abstract public XmlWriter savePrefs(XmlWriter writer);

	abstract public boolean isRender();

	abstract public void getGuiDefinition(final ArrayList<String[]> guiDef);

	abstract public void getValues(final ArrayMap<String, String> values);

	abstract public void setValues(final String changeKey, final ArrayMap<String, String> values);

	abstract public void read(final Json json, final JsonValue jsonData);

	abstract public void write(final Json json, final Object objectWrite);

}
