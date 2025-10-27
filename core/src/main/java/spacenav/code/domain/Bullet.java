package spacenav.code.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Bullet extends Entity {

	private boolean destroyed = false;
	    
	    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx) {
	    	super(x, y, xSpeed, ySpeed, tx);
	    }
	    
	    public void update() {
	        sprite.setPosition(sprite.getX()+xSpeed, sprite.getY()+ySpeed);
	        if (sprite.getX() < 0 || sprite.getX()+sprite.getWidth() > Gdx.graphics.getWidth()) {
	            destroyed = true;
	        }
	        if (sprite.getY() < 0 || sprite.getY()+sprite.getHeight() > Gdx.graphics.getHeight()) {
	        	destroyed = true;
	        }
	        
	    }
	    
	    @Override
	    public void draw(SpriteBatch batch) {
	    	sprite.draw(batch);
	    }
	    
	    public void destroy() {
	    	this.destroyed = true;
	    }
	    
	    public boolean isDestroyed() {return destroyed;}
}
