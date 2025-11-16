package spacenav.code.domain;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import spacenav.code.utils.AssetLoader;

public abstract class PowerUp extends Entity {

	protected float duration;
    protected boolean collected = false;
    protected float remainingTime = 0f;
    protected boolean active = false;

    public PowerUp(float x, float y, float size, Texture tx, float duration) {
        super((int)x, (int)y, 0, 0, tx);
        this.duration = duration;
        sprite.setBounds(x, y, size, size);
        sprite.setOriginCenter();
    }
    
    public final void applyTo(Spaceship ship) {
        collected = true;
        playActivationAnimation();
        applyEffect(ship);
    }

    protected abstract void applyEffect(Spaceship ship);
    
    protected void playActivationAnimation() {
    	AssetLoader assets = AssetLoader.getInstance();
    	assets.get(AssetLoader.POWERUP_SOUND, Sound.class).play();
    }

    public void update(float delta) {
        sprite.translateY((float)Math.sin(TimeUtils.millis()/200.0) * delta);
    }
    
    public void draw(SpriteBatch batch) {
        if (!collected) sprite.draw(batch);
    }

    public boolean isCollected() {
        return collected;
    }
}
