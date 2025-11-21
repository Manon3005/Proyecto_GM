package spacenav.code.domain;

import spacenav.code.interfaces.DifficultyStrategy;

public class NormalDifficultyStrategy implements DifficultyStrategy {
	private final int BASE_X_SPEED = 1;
	private final int BASE_Y_SPEED = 1;
	private final int BASE_NB = 7;

    @Override
    public LevelParams next(int round) {
    	System.out.println("medium");
        int nextSx = BASE_X_SPEED + 2*round;     // +2 de velocidad
        int nextSy = BASE_Y_SPEED + 2*round;
        int nextNb = BASE_NB + 2*round;     // +2 asteroides

        if (round % 3 == 0) {
            nextSx += 1;
            nextSy += 1;
            nextNb += 2;
        }
        return new LevelParams(nextSx, nextSy, nextNb);
    }
}
