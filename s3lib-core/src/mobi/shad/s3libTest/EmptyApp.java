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

import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;

/**
 * @author Jarek
 */
public class EmptyApp extends S3App{

	@Override
	public void initalize(){
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx gfx){
		S3Gfx.clear(0.2f, 0.0f, 0.0f);
	}
}
