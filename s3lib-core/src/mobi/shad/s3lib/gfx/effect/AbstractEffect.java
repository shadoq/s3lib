/*******************************************************************************
 * Copyright 2012
 *
 * Jaroslaw Czub
 * http://shad.mobi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 ******************************************************************************/
package mobi.shad.s3lib.gfx.effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.*;

import java.util.ArrayList;

/**
 * Abstract effect class
 */
public abstract class AbstractEffect{

	protected float duration = 0;
	protected Actor parentActor;

	public Actor getParentActor(){
		return parentActor;
	}

	public void setParentActor(Actor parentActor){
		this.parentActor = parentActor;
	}

	abstract public void init();

	abstract public void start();

	abstract public void stop();

	abstract public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause);

	abstract public void preRender();

	abstract public void render(Batch batch, float parentAlpha);

	abstract public void postRender();

	abstract public void getGuiDefinition(final ArrayList<String[]> guiDef);

	abstract public void getValues(final ArrayMap<String, String> values);

	abstract public void setValues(final String changeKey, final ArrayMap<String, String> values);

	abstract public void read(final Json json, final JsonValue jsonData);

	abstract public void write(final Json json, final Object objectWrite);
}
