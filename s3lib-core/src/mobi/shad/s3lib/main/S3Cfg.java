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
package mobi.shad.s3lib.main;

/**
 * @author Jarek
 */
public class S3Cfg{

	public boolean pause = false;
	public boolean debug = false;
	public boolean grid = false;
	public boolean ansiLog = true;

	public String title = "";
	public String projectFile = "";
	public String targetSize = "800x480";
	public String screenSize = "800x480";
	public int audioBufferCount = 10;

	public boolean resize = false;
	public boolean forceExit = true;
	public boolean fullScreen = false;
	public boolean vSync = false;
	public boolean disableAudio = false;
	public boolean logging = false;
	public String version = "";
	public String target = "";
	public boolean multiResolution = false;

	public S3Screen.ViewPortType viewPortType = S3Screen.ViewPortType.Stretch;
	public boolean virtualScreen = false;

	public void reset(){
		this.title = "";
		this.projectFile = "";
		this.targetSize = "800x480";
		this.screenSize = "800x480";
		this.audioBufferCount = 10;
		this.resize = false;
		this.forceExit = true;
		this.fullScreen = false;
		this.vSync = true;
		this.disableAudio = false;
		this.logging = true;
		this.version = "";
		this.target = "";
		this.viewPortType = S3Screen.ViewPortType.Stretch;
		this.virtualScreen = false;
	}

	@Override
	public String toString(){
		return "S3Cfg{" +
		"pause=" + pause +
		", debug=" + debug +
		", grid=" + grid +
		", ansiLog=" + ansiLog +
		", title='" + title + '\'' +
		", projectFile='" + projectFile + '\'' +
		", targetSize='" + targetSize + '\'' +
		", screenSize='" + screenSize + '\'' +
		", audioBufferCount=" + audioBufferCount +
		", resize=" + resize +
		", forceExit=" + forceExit +
		", fullScreen=" + fullScreen +
		", vSync=" + vSync +
		", disableAudio=" + disableAudio +
		", logging=" + logging +
		", version='" + version + '\'' +
		", target='" + target + '\'' +
		'}';
	}
}
