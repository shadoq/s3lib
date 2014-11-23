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

package mobi.shad.s3lib.main.interfaces;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public interface TouchInterface{

	public abstract void onTouchDown(int x, int y, int button);

	public abstract void onDrag(int x, int y);

	public abstract void onTouchUp(int x, int y, int button);

	public abstract void onClick(InputEvent event, int x, int y);

	public abstract void onClick(InputEvent event, Actor actor, int x, int y);
}