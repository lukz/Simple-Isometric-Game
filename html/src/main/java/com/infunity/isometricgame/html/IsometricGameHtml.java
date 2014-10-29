package com.infunity.isometricgame.html;

import com.infunity.isometricgame.core.IsometricGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class IsometricGameHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new IsometricGame();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(990, 557);
	}
}
