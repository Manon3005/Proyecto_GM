package spacenav.code;
import spacenav.code.domain.DifficultyStrategy;
import spacenav.code.domain.NormalDifficultyStrategy;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import spacenav.code.screens.MenuScreen;
import spacenav.code.utils.AssetLoader;




public class SpaceNavigation extends Game {
	private SpriteBatch batch;
	private int highScore;	
	private AssetLoader assets;
	private DifficultyStrategy difficultyStrategy = new NormalDifficultyStrategy();

	public void create() {
		highScore = 0;
		batch = new SpriteBatch();
		assets = AssetLoader.getInstance();
        assets.queueLoading();
        
        while (!assets.update()) {
        	System.out.println("Loading: " + (int)(assets.getProgress() * 100) + "%");
        }
        
		Screen ss = new MenuScreen(this);
		this.setScreen(ss);
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		assets.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	
    public DifficultyStrategy getDifficultyStrategy() {
        return difficultyStrategy;
    }

    public void setDifficultyStrategy(DifficultyStrategy difficultyStrategy) {
        this.difficultyStrategy = difficultyStrategy;
        
    }

}
