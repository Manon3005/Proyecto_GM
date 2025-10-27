package spacenav.code.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Asteroid extends Entity {
	private int x;
    private int y;

    public Asteroid(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
    	super(x, y, xSpeed, ySpeed, tx);
    	
    	this.x = x;
    	this.y = y;
 	
        // validate that the edge of the sphere does not stick out
    	if (x-size < 0) this.x = x+size;
    	if (x+size > Gdx.graphics.getWidth())this.x = x-size;
         
        // validate that the edge of the sphere does not stick out
    	if (y-size < 0) this.y = y+size;
    	if (y+size > Gdx.graphics.getHeight())this.y = y-size;
    }
    
    public void update() {
        x += getXSpeed();
        y += getYSpeed();

        if (x+getXSpeed() < 0 || x+getXSpeed()+sprite.getWidth() > Gdx.graphics.getWidth())
        	setXSpeed(getXSpeed() * -1);
        if (y+getYSpeed() < 0 || y+getYSpeed()+sprite.getHeight() > Gdx.graphics.getHeight())
        	setYSpeed(getYSpeed() * -1);
        sprite.setPosition(x, y);
    }
    
    @Override
    public void draw(SpriteBatch batch) {
    	sprite.draw(batch);
    }
    
    public void doBounce(Asteroid a2) {   	
    	if (getXSpeed() ==0) setXSpeed(getXSpeed() + a2.getXSpeed()/2);
        if (a2.getXSpeed() ==0) a2.setXSpeed(a2.getXSpeed() + getXSpeed()/2);
    	setXSpeed(- getXSpeed());
    	a2.setXSpeed(-a2.getXSpeed());
        
        if (getYSpeed() ==0) setYSpeed(getYSpeed() + a2.getYSpeed()/2);
        if (a2.getYSpeed() ==0) a2.setYSpeed(a2.getYSpeed() + getYSpeed()/2);
        setYSpeed(- getYSpeed());
        a2.setYSpeed(- a2.getYSpeed()); 
    }
}
