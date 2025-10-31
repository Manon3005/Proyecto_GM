package spacenav.code.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacenav.code.interfaces.Damageable;


public class Asteroid extends Entity implements Damageable {

    private int x;
    private int y;
    private final int size;

    private boolean destroyed = false;
    private int health;

    public Asteroid(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, xSpeed, ySpeed, tx);

        this.x = x;
        this.y = y;
        this.size = size;

        // Evitar que el asteroide nazca “fuera” de la pantalla
        if (x - size < 0) this.x = x + size;
        if (x + size > Gdx.graphics.getWidth()) this.x = x - size;
        if (y - size < 0) this.y = y + size;
        if (y + size > Gdx.graphics.getHeight()) this.y = y - size;

        // Ajustar sprite al tamaño solicitado
        sprite.setBounds(this.x, this.y, size, size);
        sprite.setOriginCenter();
        
        // Asignar vida en función del tamaño
        if (size <= 20)       health = 1;  // Pequeños: 1 golpe
        else if (size <= 40)  health = 2;  // Medianos: 2 golpes
        else if (size <= 50)  health = 3;  // Grandes: 3 golpes
        else                  health = 4;  // Gigantes: 4 golpes

        // Ajustar velocidad (más grandes = más lentos)
        if (size > 40) {
            setXSpeed((int)(xSpeed * 0.4f));
            setYSpeed((int)(ySpeed * 0.4f));
        } else if (size < 20) {
            setXSpeed((int)(xSpeed * 1.3f));
            setYSpeed((int)(ySpeed * 1.3f));
        }
    }

    /** Actualiza posición y rebote en bordes (si no está destruido). */
    public void update() {
        if (destroyed) return;

        x += getXSpeed();
        y += getYSpeed();

        if (x + getXSpeed() < 0 || x + getXSpeed() + sprite.getWidth() > Gdx.graphics.getWidth())
            setXSpeed(getXSpeed() * -1);
        if (y + getYSpeed() < 0 || y + getYSpeed() + sprite.getHeight() > Gdx.graphics.getHeight())
            setYSpeed(getYSpeed() * -1);

        sprite.setPosition(x, y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!destroyed) {
            sprite.draw(batch);
        }
    }

    public void doBounce(Asteroid a2) {
        if (getXSpeed() == 0) setXSpeed(getXSpeed() + a2.getXSpeed() / 2);
        if (a2.getXSpeed() == 0) a2.setXSpeed(a2.getXSpeed() + getXSpeed() / 2);
        setXSpeed(-getXSpeed());
        a2.setXSpeed(-a2.getXSpeed());

        if (getYSpeed() == 0) setYSpeed(getYSpeed() + a2.getYSpeed() / 2);
        if (a2.getYSpeed() == 0) a2.setYSpeed(a2.getYSpeed() + getYSpeed() / 2);
        setYSpeed(-getYSpeed());
        a2.setYSpeed(-a2.getYSpeed());
    }

    @Override
    public void takeDamage(int damage) {
        if (destroyed || damage <= 0) return;
        health -= damage;
        if (health <= 0) {
            destroyed = true;
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    publi
