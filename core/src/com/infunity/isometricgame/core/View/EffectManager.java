package com.infunity.isometricgame.core.view;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.infunity.isometricgame.shared.intefaces.EffectsInterface;

public class EffectManager implements EffectsInterface {

    private ParticleManager particleManager;

    public EffectManager(ParticleManager particleManager) {
        this.particleManager = particleManager;
    }

    @Override
    public void createEffect(int x, int y, Effect effectType) {
        if(effectType == Effect.COIN_EFFECT) {
            ParticleEffectPool.PooledEffect prt = particleManager.obtainEffect(ParticleManager.COIN_EFFECT);

            prt.setPosition(x, y);

            particleManager.addEffect(prt);
        } else {
            throw new IllegalArgumentException("Unknown effectType");
        }
    }

    public void update(float delta) {
        particleManager.update(delta);
    }

    public void draw(SpriteBatch batch) {
        particleManager.draw(batch);
    }
}
