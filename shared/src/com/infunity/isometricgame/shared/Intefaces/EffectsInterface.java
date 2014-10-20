package com.infunity.isometricgame.shared.intefaces;

public interface EffectsInterface {

    public enum Effect {
        COIN_EFFECT
    }

    public void createEffect(int x, int y, Effect effectType);
}
