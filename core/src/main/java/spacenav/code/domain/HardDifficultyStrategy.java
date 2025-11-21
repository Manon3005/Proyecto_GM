package spacenav.code.domain;

import spacenav.code.interfaces.DifficultyStrategy;

public class HardDifficultyStrategy implements DifficultyStrategy{
	private final int BASE_X_SPEED = 2;
	private final int BASE_Y_SPEED = 2;
	private final int BASE_NB = 10;
	
	@Override
    public LevelParams next(int round) {
        // base fuerte
        int nextSx = BASE_X_SPEED + 3*round;     // +3 de velocidad cada nivel
        int nextSy = BASE_Y_SPEED + 3*round;
        int nextNb = BASE_NB + 4*round;     // + asteroides por nivel

        // a partir de la ronda 5, el crecimiento se acelera aún más
        if (round >= 5) {
            nextSx += 2;
            nextSy += 2;
            nextNb += 4;
        }
        return new LevelParams(nextSx, nextSy, nextNb);
    }

}
