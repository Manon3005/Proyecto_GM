package spacenav.code.domain;

public interface DifficultyStrategy {
	/**
     * Calcula los parámetros del próximo nivel.
     *
     * @param currentRound   ronda actual (1, 2, 3, ...)
     * @param baseSpeedX     velocidad X actual
     * @param baseSpeedY     velocidad Y actual
     * @param baseCount      cantidad actual de asteroides
     * @return parámetros del siguiente nivel
     */
	LevelParams next(int currentRound, int baseSpeedX, int baseSpeedY, int baseCount);
}
