package spacenav.code.domain;

import spacenav.code.interfaces.DifficultyStrategy;

public class EasyDifficultyStrategy implements DifficultyStrategy {
	
	 @Override
    public LevelParams next(int round, int sx, int sy, int n) {
        int nextSx = sx + 1;     // +1 de velocidad
        int nextSy = sy + 1;
        int nextNb = n + 2;     // +2 asteroides
        return new LevelParams(nextSx, nextSy, nextNb);
    }
	
}
