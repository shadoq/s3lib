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

import com.badlogic.gdx.math.Interpolation;
import mobi.shad.s3lib.gfx.g2d.EffectCreator;

/**
 * Created by Jarek on 2014-05-13.
 */
public enum InterpolationType{
	Bounce, BounceIn, BounceOut, Circle, CircleIn, CircleOut,
	Elastic, ElasticIn, ElasticOut, Exp10, Exp10In, Exp10Out,
	Exp5, Exp5In, Exp5Out, Linear, Fade,
	Pow2, Pow2In, Pow2Out, Pow3, Pow3In, Pow3Out,
	Pow4, Pow4In, pow4Out, Pow5, Pow5In, Pow5Out,
	Sine, SineIn, SineOut, Swing, SwingIn, SwingOut;

	public static InterpolationType getInterpolationType(Interpolation interpolation){
		for (int i = 0; i < EffectCreator.interpolationsValue.length; i++){
			if (EffectCreator.interpolationsValue[i].equals(getInterpolation(InterpolationType.values()[i]))){
				return InterpolationType.values()[i];
			}
		}
		return InterpolationType.Linear;
	}

	public static Interpolation getInterpolation(InterpolationType type){
		for (int i = 0; i < InterpolationType.values().length; i++){
			if (type.equals(InterpolationType.values()[i])){
				return EffectCreator.interpolationsValue[i];
			}
		}
		return Interpolation.linear;
	}

	public static Interpolation getInterpolation(String typename){
		return getInterpolation(InterpolationType.valueOf(typename));
	}
}