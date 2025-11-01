package spacenav.code.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class PowerUp extends Entity {

    private final PowerUpType type;
    private boolean collected = false;

    public PowerUp(float x, float y, float size, Texture tx, PowerUpType type) {
        super((int)x, (int)y, 0, 0, tx);
        this.type = type;
        sprite.setBounds(x, y, size, size);
        sprite.setOriginCenter();
    }

    public void update(float delta) {
       sprite.translateY((float)Math.sin(TimeUtils.millis()/200.0) * delta);
       
    }

    public void draw(SpriteBatch batch) {
        if (!collected) sprite.draw(batch);
    }

    public PowerUpType getType() {
        return type;
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }
}
