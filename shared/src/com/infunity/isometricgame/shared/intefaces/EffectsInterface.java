package com.infunity.isometricgame.shared.intefaces;

/**
 * Effect interface that is used to pass effect events to client
 */
public interface EffectsInterface {

    public enum Effect {
        COIN_EFFECT
    }

    public void createEffect(int x, int y, Effect effectType);
}
