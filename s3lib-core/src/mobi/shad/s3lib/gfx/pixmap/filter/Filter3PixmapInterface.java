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
package mobi.shad.s3lib.gfx.pixmap.filter;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * Interface to define class with 3 pixmap process
 * filter(source + source2) -> pixmapDst
 */
public interface Filter3PixmapInterface{
	public void filter(final Pixmap pixmapDst, final Pixmap pixmapSource, final Pixmap pixmapSource2);
}
