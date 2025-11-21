package spacenav.code.interfaces;

import spacenav.code.domain.LevelParams;

public interface DifficultyStrategy {
	/**
     * Calcula los parámetros del próximo nivel.
     *
     * @param currentRound   ronda actual (0, 1, 2, 3, ...)
     * @return parámetros del siguiente nivel
     */
	LevelParams next(int currentRound);
}
