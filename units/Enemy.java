package units;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.evolution.game.Assets;
import com.evolution.game.GameScreen;
import com.evolution.game.Rules;

public class Enemy extends Cell {

    public Vector2 getPosition() {
        return position;
    }

    public Enemy(GameScreen gs) {
        super(0, 0, 100.0f);
        this.gs = gs;
        this.texture = Assets.getInstance().getAtlas().findRegion("Enemy");
        this.active = false;
    }

    @Override
    public void consumed() {
        active = false;
    }

    public void init() {
        position.set(MathUtils.random(64, Rules.GLOBAL_WIDTH), MathUtils.random(64, Rules.GLOBAL_HEIGHT));
        scale = 1.0f + MathUtils.random(0.0f, 0.4f);
//        setScores(0);
        active = true;
    }

    public void update(float dt) {
        super.update(dt);

        tmp.set(position);
        float min = 10000.0f;
        for (int j = 0; j < gs.getConsumableEmitter().getActiveList().size(); j++) {
            if (gs.getConsumableEmitter().getActiveList().get(j).getType() == Consumable.Type.FOOD) {
                float distance = position.dst(gs.getConsumableEmitter().getActiveList().get(j).getPosition());
                if (distance < min) {
                    min = distance;
                    tmp.set(gs.getConsumableEmitter().getActiveList().get(j).getPosition());
                }
            }
        }

        float angleToTarget = tmp.sub(position).angle();
        if (angle > angleToTarget) {
            if (Math.abs(angle - angleToTarget) <= 180.0f) {
                angle -= 180.0f * dt;
            } else {
                angle += 180.0f * dt;
            }
        }
        if (angle < angleToTarget) {
            if (Math.abs(angle - angleToTarget) <= 180.0f) {
                angle += 180.0f * dt;
            } else {
                angle -= 180.0f * dt;
            }
        }


        velocity.add(acceleration * (float) Math.cos(Math.toRadians(angle)) * dt,
                acceleration * (float) Math.sin(Math.toRadians(angle)) * dt);
        position.mulAdd(velocity, dt);

        if (position.x > Rules.GLOBAL_WIDTH)

        {
            position.x = 0.0f;
            position.y = (float) MathUtils.random(0, Rules.GLOBAL_HEIGHT);
        }
        velocity.scl(0.98f);
    }



}
