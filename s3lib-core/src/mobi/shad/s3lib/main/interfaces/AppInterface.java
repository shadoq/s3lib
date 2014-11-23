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

import mobi.shad.s3lib.main.S3Gfx;

public interface AppInterface{

	public abstract void initalize();

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();

	public abstract void resize();

	public abstract void update();

	public abstract void preRender();

	public abstract void render(final S3Gfx g);

	public abstract void postRender();
}