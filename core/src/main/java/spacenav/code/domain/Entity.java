package spacenav.code.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
	 protected Sprite sprite;
	 protected float xSpeed;
	 protected float ySpeed;
	 
	 public Entity(float x, float y, int xSpeed, int ySpeed, Texture tx) {
		 	sprite = new Sprite(tx);
	    	sprite.setPosition(x, y);
	        this.setXSpeed(xSpeed);
	        this.setYSpeed(ySpeed);
	    }

	 public float getXSpeed() {
		return xSpeed;
	 }

	 public void setXSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	 }

	 public float getYSpeed() {
		return ySpeed;
	 }

	 public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	 }
	 
	 private Rectangle getArea() {
	    return sprite.getBoundingRectangle();
	 }
	 
	 public boolean checkCollision(Entity e2) {
        if(this.getArea().overlaps(e2.getArea())){
            return true;
        }
        return false;
    }
	
	 public abstract void draw(SpriteBatch batch);
}
