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
package mobi.shad.s3libTest;

import com.badlogic.gdx.files.FileHandle;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3File;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3Log;
import ozmod.ChipPlayer;
import ozmod.OZMod;

public class OzPlayTest extends S3App{

	OZMod ozm;
	ChipPlayer player;
	int frequency;

	@Override
	public void initalize(){
		ozm = new OZMod();
		ozm.initOutput();
		play("sound/fish_and_chips.xm", 1);
		//		play("sound/radix-imaginary_friend.xm", 1);
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx gfx){
		gfx.clear(0.2f, 0.0f, 0.0f);
	}

	public void play(String file, float volume){

		FileHandle module = S3File.getFileHandle(file);
		S3Log.info("OzMod", "Play: " + module.path());
		player = ozm.getPlayer(module);
		frequency = 44100;
		// frequency = 48000;
		//		frequency = 96000;
		player.setFrequency(frequency);
		player.setVolume(volume);
		player.setDaemon(true);
		player.setLoopable(false);
		player.play();
	}
}
