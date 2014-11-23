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
package mobi.shad.s3lib.gfx.pixmap.procedural;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * A interface for procedural texture filter and pixamp generator
 *
 * @author Jaroslaw Czub (http://shad.net.pl)
 */
public interface ProceduralInterface{
	public static final boolean DEBUG = false;

	public void generate(final Pixmap pixmap);

	public void random(final Pixmap pixmap);
}
