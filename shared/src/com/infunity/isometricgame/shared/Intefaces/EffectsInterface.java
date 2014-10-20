package com.infunity.isometricgame.shared.intefaces;

/**
 * Created by Lukasz on 2014-10-15.
 */
public interface EffectsInterface {

    public enum Effect {
        COIN_EFFECT
    }

    public void createEffect(int x, int y, Effect effectType);
}
