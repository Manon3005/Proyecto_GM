package spacenav.code.domain;

import spacenav.code.interfaces.DifficultyStrategy;

public class EasyDifficultyStrategy implements DifficultyStrategy {
	private final int BASE_X_SPEED = 1;
	private final int BASE_Y_SPEED = 1;
	private final int BASE_NB = 5;
	
	 @Override
    public LevelParams next(int round) {
        int nextSx = BASE_X_SPEED + round;     // +1 de velocidad
        int nextSy = BASE_Y_SPEED + round;
        int nextNb = BASE_NB + 2*round;     // +2 asteroides
        return new LevelParams(nextSx, nextSy, nextNb);
    }
	
}
