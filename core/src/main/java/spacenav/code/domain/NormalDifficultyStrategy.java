package spacenav.code.domain;

public class NormalDifficultyStrategy implements DifficultyStrategy {

    @Override
    public LevelParams next(int round, int sx, int sy, int nb) {
        int nextSx = sx + 2;     // +2 de velocidad
        int nextSy = sy + 2;
        int nextNb = nb + 2;     // +2 asteroides

        // cada 3 rondas, un pequeño salto extra
        if (round % 3 == 0) {
            nextSx += 1;
            nextSy += 1;
            nextNb += 2;
        }
        return new LevelParams(nextSx, nextSy, nextNb);
    }
}
