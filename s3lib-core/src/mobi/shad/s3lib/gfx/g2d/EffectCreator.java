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
package mobi.shad.s3lib.gfx.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3Screen;
import mobi.shad.s3lib.main.constans.InterpolationType;
import mobi.shad.s3lib.main.constans.ScreenEffectType;

public class EffectCreator{

	private static final String logTag = "EffectCreator";
	public static boolean logActive = true;

	public static Interpolation[] interpolationsValue = {
	Interpolation.bounce, Interpolation.bounceIn, Interpolation.bounceOut,
	Interpolation.circle, Interpolation.circleIn, Interpolation.circleOut,
	Interpolation.elastic, Interpolation.elasticIn, Interpolation.elasticOut,
	Interpolation.exp10, Interpolation.exp10In, Interpolation.exp10Out,
	Interpolation.exp5, Interpolation.exp5In, Interpolation.exp5Out,
	Interpolation.linear, Interpolation.fade,
	Interpolation.pow2, Interpolation.pow2In, Interpolation.pow2Out,
	Interpolation.pow3, Interpolation.pow3In, Interpolation.pow3Out,
	Interpolation.pow4, Interpolation.pow4In, Interpolation.pow4Out,
	Interpolation.pow5, Interpolation.pow5In, Interpolation.pow5Out,
	Interpolation.sine, Interpolation.sineIn, Interpolation.sineOut,
	Interpolation.swing, Interpolation.swingIn, Interpolation.swingOut,
	};

	/**
	 *
	 */
	public enum EffectType{
		CENTER_TO_OUT_CENTER,
		BOTTOM_LEFT_TO_TOP_RIGHT,
		BOTTOM_RIGHT_TO_TOP_LEFT,
		TOP_RIGHT_TO_BOTTOM_LEFT,
		TOP_LEFT_TO_BOTTOM_RIGHT,
		STRETCH_BOTTOM_TO_TOP,
		STRETCH_TOP_TO_BOTTOM,
		STRETCH_LEFT_TO_RIGHT,
		STRETCH_RIGHT_TO_LEFT,
		STRETCH_BOTTOM_TO_NORMAL_TOP,
		STRETCH_TOP_TO_NORMAL_BOTTOM,
		STRETCH_LEFT_TO_NORMAL_RIGHT,
		STRETCH_RIGHT_TO_NORMAL_LEFT
	}

	/**
	 * Util class, can't create instance
	 */
	private EffectCreator(){

	}

	/**
	 * @param actor
	 * @param effectType
	 * @param value
	 * @param duration
	 * @param type
	 */
	public static void createEffect(Actor actor, ScreenEffectType effectType, float value,
									float duration, InterpolationType type){
		if (actor == null){
			return;
		}
		Interpolation interp = InterpolationType.getInterpolation(type);
		float x = 0;//actor.getX();
		float y = 0;//actor.getY();
		switch (effectType){
			case SlideLeft:
				actor.setPosition(999, y);
				actor.addAction(Actions.moveTo(x, y, duration, interp));
				break;
			case SlideRight:
				actor.setPosition(-999, y);
				actor.addAction(Actions.moveTo(x, y, duration, interp));
				break;
			case SlideUp:
				actor.setPosition(x, -999);
				actor.addAction(Actions.moveTo(x, y, duration, interp));
				break;
			case SlideDown:
				actor.setPosition(x, 999);
				actor.addAction(Actions.moveTo(x, y, duration, interp));
				break;
			case FadeIn:
				Color color = actor.getColor();
				color.a = 0f;
				actor.setColor(color);
				actor.addAction(Actions.fadeIn(duration, interp));
				break;
			case FadeOut:
				Color color2 = actor.getColor();
				color2.a = 1f;
				actor.setColor(color2);
				actor.addAction(Actions.fadeOut(duration, interp));
				break;
			case FadeInOut:
				actor.addAction(fadeInOut(value, duration, interp));
				break;
			case ScaleIn:
				actor.setScale(0, 0);
				actor.addAction(Actions.scaleTo(1, 1, duration, interp));
				break;
			case ScaleOut:
				actor.setScale(1, 1);
				actor.addAction(Actions.scaleTo(0, 0, duration, interp));
				break;
			case None:
				break;
			default:
				break;
		}
	}

	/**
	 * @param type
	 * @param actorIn
	 * @param actorOut
	 * @param duration
	 * @param group
	 * @param removeActor
	 */
	public static void inOut(EffectType type, Actor actorIn, Actor actorOut, float duration, final Group group,
							 final boolean removeActor){
		switch (type){
			case CENTER_TO_OUT_CENTER:
				moveFadeIn(actorIn, 0, 0, S3Screen.centerX, S3Screen.centerY, duration, group, removeActor);
				moveFadeOut(actorOut, 0, 0, S3Screen.centerX, S3Screen.centerY, duration, group, removeActor);
				break;
			case BOTTOM_LEFT_TO_TOP_RIGHT:
				moveFadeIn(actorIn, 0, 0, 0, 0, duration, group, removeActor);
				moveFadeOut(actorOut, 0, 0, S3Screen.centerX, S3Screen.centerY, duration, group, removeActor);
				break;
			case BOTTOM_RIGHT_TO_TOP_LEFT:
				moveFadeIn(actorIn, 0, 0, S3Screen.centerX, S3Screen.centerY, duration, group, removeActor);
				moveFadeOut(actorOut, 0, 0, 0, 0, duration, group, removeActor);
				break;
			//	  //
			//	  // In Down Left -> Out Top Righr
			//	  //
			//	  case 1:
			//		initAnimation(0f, 0f, S3.screenSizeX, S3.screenSizeY, 0f, 0f, 0f, 0f);
			//		break;
			//	  //
			//	  // In Top Right -> Out Down Left
			//	  //
			//	  case 2:
			//		initAnimation(0f, 0f, 0.0f, 0.0f, 0f, 0f, S3.screenSizeX, S3.screenSizeY);
			//		break;
			//	  //
			//	  // In Top Left -> Out Down Righr
			//	  //
			//	  case 3:
			//		initAnimation(0f, 0f, S3.screenSizeX, 0f, 0f, 0f, 0f, S3.screenSizeY);
			//		break;
			//	  //
			//	  // In Down Right -> Out Top Left
			//	  //
			//	  case 4:
			//		initAnimation(0f, 0f, 0.0f, S3.screenSizeY, 0f, 0f, S3.screenSizeX, 0.0f);
			//		break;
			//
			//	  //
			//	  // Stretch Down->Top
			//	  //
			//	  case 5:
			//		initAnimation(1f, 0f, 0.0f, S3.screenSizeY, 1f, 0f, 0.0f, 0.0f);
			//		break;
			//	  //
			//	  // Stretch Top->Down
			//	  //
			//	  case 6:
			//		initAnimation(1f, 0f, 0.0f, 0.0f, 1f, 0f, 0.0f, S3.screenSizeY);
			//		break;
			//	  //
			//	  // Stretch Left->Right
			//	  //
			//	  case 7:
			//		initAnimation(0f, 1f, S3.screenSizeX, 0.0f, 0f, 1f, 0.0f, 0.0f);
			//		break;
			//	  //
			//	  // Stretch Right->Left
			//	  //
			//	  case 8:
			//		initAnimation(0f, 1f, 0.0f, 0.0f, 0f, 1f, S3.screenSizeX, 0.0f);
			//		break;
			//
			//
			//	  //
			//	  // Stretch Down-> Normal Top
			//	  //
			//	  case 9:
			//		initAnimation(1f, 1f, 0.0f, S3.screenSizeY, 1f, 0f, 0.0f, 0.0f);
			//		break;
			//	  //
			//	  // Stretch Top-> Normal Down
			//	  //
			//	  case 10:
			//		initAnimation(1f, 0f, 0.0f, 0.0f, 1f, 1f, 0.0f, S3.screenSizeY);
			//		break;
			//	  //
			//	  // Stretch Left-> Normal Right
			//	  //
			//	  case 11:
			//		initAnimation(1f, 1f, S3.screenSizeX, 0.0f, 0f, 1f, 0.0f, 0.0f);
			//		break;
			//	  //
			//	  // Stretch Right-> Normal Left
			//	  //
			//	  case 12:
			//		initAnimation(0f, 1f, 0.0f, 0.0f, 1f, 1f, S3.screenSizeX, 0.0f);
			//		break;
		}
	}

	/**
	 * @param value
	 * @param duration
	 * @param interp
	 * @return
	 */
	public static Action scaleInOut(float value, float duration, Interpolation interp){
		return Actions.sequence(Actions.scaleTo(value, value, duration, interp),
								Actions.scaleTo(1, 1, duration, interp));
	}

	/**
	 * @param value
	 * @param duration
	 * @param interp
	 * @return
	 */
	public static Action shakeInOut(float value, float duration, Interpolation interp){
		return Actions.sequence(Actions.rotateTo(value, duration, interp), Actions.rotateTo(-value, duration, interp),
								Actions.rotateTo(0, duration, interp));
	}

	/**
	 * @param value
	 * @param duration
	 * @param interp
	 * @return
	 */
	public static Action fadeInOut(float value, float duration, Interpolation interp){
		return Actions.sequence(Actions.fadeIn(duration, interp), Actions.fadeOut(duration, interp));
	}

	/**
	 * @param value
	 * @param duration
	 * @param interp
	 * @return
	 */
	public static Action fadeOutIn(float value, float duration, Interpolation interp){
		return Actions.sequence(Actions.fadeOut(duration, interp), Actions.fadeIn(duration, interp));
	}

	/**
	 * @param actor
	 * @param scaleRatioX
	 * @param scaleRatioY
	 * @param duration
	 * @param group
	 * @param removeActor
	 */
	public static void scaleTo(Actor actor, float scaleRatioX, float scaleRatioY, float duration, final Group group,
							   final boolean removeActor){
		if (actor != null){
			actor.addAction(Actions.sequence(
			Actions.scaleTo(scaleRatioX, scaleRatioY, duration),
			new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param duration
	 * @param delay
	 * @param group
	 * @param removeActor
	 */
	public static void fadeOut(Actor actor, float duration, float delay,
							   final Group group, final boolean removeActor){
		if (actor != null){
			actor.addAction(Actions.sequence(Actions.delay(delay),
											 Actions.fadeOut(duration), new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param duration
	 * @param delay
	 * @param group
	 * @param removeActor
	 */
	public static void fadeIn(Actor actor, float duration, float delay,
							  final Group group, final boolean removeActor){
		if (actor != null){
			actor.addAction(Actions.sequence(Actions.delay(delay),
											 Actions.fadeIn(duration), new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param duration
	 * @param delayBefore
	 * @param posX
	 * @param posY
	 * @param group
	 * @param removeActor
	 */
	public static void modeTo(Actor actor, float duration, float delayBefore, float posX, float posY, final Group group,
							  final boolean removeActor){
		if (actor != null){
			actor.addAction(Actions.sequence(Actions.delay(delayBefore),
											 Actions.moveTo(posX, posY, duration), new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param shakeAngle
	 * @param originalAngle
	 * @param duration
	 * @param group
	 * @param removeActor
	 */
	public static void shake(Actor actor, float shakeAngle, float originalAngle, float duration, final Group group,
							 final boolean removeActor){
		if (actor != null){
			actor.addAction(Actions.sequence(
			Actions.rotateTo(shakeAngle, duration),
			Actions.rotateTo(-shakeAngle, duration),
			Actions.rotateTo(originalAngle, duration), new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param scaleRatioX
	 * @param scaleRatioY
	 * @param duration
	 * @param group
	 * @param removeActor
	 */
	public static void scaleBack(Actor actor, float scaleRatioX, float scaleRatioY, float duration, final Group group,
								 final boolean removeActor){
		if (actor != null){
			float originalScaleX = actor.getScaleX();
			float originalScaleY = actor.getScaleY();
			actor.addAction(Actions.sequence(
			Actions.scaleTo(scaleRatioX, scaleRatioY, duration),
			Actions.scaleTo(originalScaleX, originalScaleY, duration),
			new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param scaleRatioX
	 * @param scaleRatioY
	 * @param duration
	 * @param group
	 * @param removeActor
	 */
	public static void scaleBackTo(Actor actor, float scaleRatioX, float scaleRatioY, float duration, final Group group,
								   final boolean removeActor){
		if (actor != null){
			actor.addAction(Actions.sequence(
			Actions.scaleTo(scaleRatioX, scaleRatioY, duration),
			Actions.scaleTo(1.0f, 1.0f, duration), new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param scaleRatioX
	 * @param scaleRatioY
	 * @param duration
	 * @param delayBeforeFadeOut
	 * @param group
	 * @param removeActor
	 */
	public static void scaleFadeOut(Actor actor, float scaleRatioX, float scaleRatioY, float duration,
									float delayBeforeFadeOut, final Group group, final boolean removeActor){
		if (actor != null){
			actor.addAction(Actions.sequence(
			Actions.scaleTo(scaleRatioX, scaleRatioY, duration),
			Actions.delay(delayBeforeFadeOut),
			Actions.fadeOut(duration), new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param scaleRatioX
	 * @param scaleRatioY
	 * @param shakeAngle
	 * @param originalAngle
	 * @param duration
	 * @param group
	 * @param removeActor
	 */
	public static void scaleShake(Actor actor, float scaleRatioX, float scaleRatioY, float shakeAngle,
								  float originalAngle, float duration, final Group group, final boolean removeActor){
		if (actor != null){
			actor.addAction(Actions.sequence(
			Actions.scaleTo(scaleRatioX, scaleRatioY, duration),
			Actions.rotateTo(shakeAngle, duration),
			Actions.rotateTo(-shakeAngle, duration),
			Actions.rotateTo(originalAngle, duration), new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param scaleRatioX
	 * @param scaleRatioY
	 * @param duration
	 * @param delayBeforeFadeOut
	 * @param group
	 * @param removeActor
	 */
	public static void scaleBactToFadeOut(Actor actor, float scaleRatioX, float scaleRatioY, float duration,
										  float delayBeforeFadeOut, final Group group, final boolean removeActor){
		if (actor != null){
			actor.addAction(Actions.sequence(
			Actions.scaleTo(scaleRatioX, scaleRatioY, duration),
			Actions.scaleTo(1, 1, duration),
			Actions.delay(delayBeforeFadeOut),
			Actions.fadeOut(duration), new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param scaleRatioX
	 * @param scaleRatioY
	 * @param shakeAngle
	 * @param originalAngle
	 * @param duration
	 * @param group
	 * @param removeActor
	 */
	public static void scaleShakeBackTo(Actor actor, float scaleRatioX,
										float scaleRatioY, float shakeAngle, float originalAngle,
										float duration, final Group group, final boolean removeActor){
		if (actor != null){
			actor.addAction(Actions.sequence(
			Actions.scaleTo(scaleRatioX, scaleRatioY, duration),
			Actions.rotateTo(shakeAngle, duration),
			Actions.rotateTo(-shakeAngle, duration),
			Actions.rotateTo(originalAngle, duration),
			Actions.scaleTo(1, 1, duration), new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param scaleX
	 * @param scaleY
	 * @param positionX
	 * @param positionY
	 * @param duration
	 * @param group
	 * @param removeActor
	 */
	public static void moveFadeIn(Actor actor,
								  float scaleX, float scaleY, float positionX, float positionY,
								  float duration, final Group group, final boolean removeActor){
		if (actor != null){

			float posX = actor.getX();
			float posY = actor.getY();

			actor.addAction(Actions.sequence(
			Actions.alpha(0, 0),
			Actions.scaleTo(scaleX, scaleY, 0),
			Actions.moveTo(positionX, positionY, 0),
			Actions.parallel(
			Actions.fadeIn(duration),
			Actions.scaleTo(1f, 1f, duration),
			Actions.moveTo(posX, posY, duration)),
			new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));
		}
	}

	/**
	 * @param actor
	 * @param scaleX
	 * @param scaleY
	 * @param positionX
	 * @param positionY
	 * @param duration
	 * @param group
	 * @param removeActor
	 */
	public static void moveFadeOut(Actor actor,
								   float scaleX, float scaleY, float positionX, float positionY,
								   float duration, final Group group, final boolean removeActor){
		if (actor != null){

			actor.addAction(Actions.sequence(
			Actions.alpha(1, 0),
			Actions.scaleTo(1, 1, 0),
			Actions.parallel(
			Actions.fadeOut(duration),
			Actions.scaleTo(scaleX, scaleY, duration),
			Actions.moveTo(positionX, positionY, duration)),
			new Action(){
				@Override
				public boolean act(float delta){
					if (removeActor){
						removeActor(group, actor);
						return false;
					} else {
						return true;
					}
				}
			}
			));

		}
	}

	/**
	 * @param group
	 * @param actor
	 */
	private static void removeActor(Group group, Actor actor){
		if (group != null && actor != null){
			actor.clearActions();
			String actorName = actor.getName();
			if (group.removeActor(actor)){
				if (logActive){
					S3Log.log(logTag, "Actor removed! name: " + actorName, 2);
				}
			} else {
				if (logActive){
					S3Log.log(logTag, "Actor removed! name: " + actorName, 1);
				}
			}
		}
	}
}
