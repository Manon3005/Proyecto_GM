package spacenav.code.domain;

import spacenav.code.interfaces.DifficultyStrategy;

public class HardDifficultyStrategy implements DifficultyStrategy{
	
	@Override
    public LevelParams next(int round, int sx, int sy, int nb) {
        // base fuerte
        int nextSx = sx + 3;     // +3 de velocidad cada nivel
        int nextSy = sy + 3;
        int nextNb = nb + 3;     // +6 asteroides por nivel

        // a partir de la ronda 5, el crecimiento se acelera aún más
        if (round >= 5) {
            nextSx += 2;
            nextSy += 2;
            nextNb += 4;
        }
        return new LevelParams(nextSx, nextSy, nextNb);
    }

}
