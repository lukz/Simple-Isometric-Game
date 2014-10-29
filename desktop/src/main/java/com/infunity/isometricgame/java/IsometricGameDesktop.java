package com.infunity.isometricgame.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.infunity.isometricgame.core.IsometricGame;

public class IsometricGameDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
        config.width = 1024;
        config.height = 576;

		new LwjglApplication(new IsometricGame(), config);
	}
}
