package spacenav.code.domain;

import com.badlogic.gdx.graphics.Texture;

public class ExtraLifePowerUp extends PowerUp {

	public ExtraLifePowerUp(float x, float y, float size, Texture tx, float duration) {
		super(x, y, size, tx, duration);
	}

	@Override
	protected void applyEffect(Spaceship ship) {
		ship.setLives(ship.getLives() + 1);
	}
}
