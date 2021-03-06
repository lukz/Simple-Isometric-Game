package com.infunity.isometricgame.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.infunity.isometricgame.core.IsometricGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(990, 557);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new IsometricGame();
        }
}