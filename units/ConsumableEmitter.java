package units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.evolution.game.Assets;
import com.evolution.game.GameScreen;
import com.evolution.game.ObjectPool;

public class ConsumableEmitter extends ObjectPool<Consumable> {
    private GameScreen gs;
    private float time;
    private TextureRegion[] regions;
//    private float scale;

    @Override
    protected Consumable newObject() {
        return new Consumable(gs, regions);
    }

    public ConsumableEmitter(GameScreen gs) {
//        this.scale = scale;
        this.gs = gs;
        this.regions = new TextureRegion[2];
        this.regions[Consumable.Type.FOOD.getTextureIndex()] = Assets.getInstance().getAtlas().findRegion("Food");
        this.regions[Consumable.Type.BAD_FOOD.getTextureIndex()] = Assets.getInstance().getAtlas().findRegion("BadFood");
        this.generateConsumable(10);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public void update(float dt) {
        time += dt;
        if (time >= 0.4f) {
            generateConsumable();
            time = 0.0f;
        }
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }

    public void generateConsumable() {
        Consumable.Type type = Consumable.Type.FOOD;
        if (MathUtils.random(0, 100) < 10) {
            type = Consumable.Type.BAD_FOOD;
        }
        getActiveElement().init(type);

    }

    public void generateConsumable(int count) {
        for (int i = 0; i < count; i++) {
            generateConsumable();
        }
    }
}
