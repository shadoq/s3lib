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
package mobi.shad.s3lib.main.constans;

/**
 * Created by Jarek on 2014-05-13.
 */
public enum ScreenEffectType{
	None,
	ScaleIn, ScaleOut,
	FadeIn, FadeOut, FadeInOut,
	SlideRight, SlideLeft, SlideUp, SlideDown
}

enum EffectDuration{
	Once,
	OnceToAndBack,
	Looping,
	LoopingToAndBack
}