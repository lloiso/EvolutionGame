package units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evolution.game.GameScreen;
import com.evolution.game.ObjectPool;

public class EnemyEmiter extends ObjectPool<Enemy> {
    private GameScreen gs;
    private float time;

    @Override
    protected Enemy newObject() {
        return new Enemy(gs);
    }

    public EnemyEmiter(GameScreen gs) {
        this.gs = gs;
        addObjectsToFreeList(2);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public void update(float dt) {
        time += dt;
        if (time >= 0.5f) {
            time = 0.0f;
            getActiveElement().init();
        }
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }
}
