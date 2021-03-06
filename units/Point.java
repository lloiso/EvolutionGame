package units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.evolution.game.GameScreen;
import com.evolution.game.Poolable;

public abstract class Point implements Poolable {
    GameScreen gs;
    TextureRegion texture;
    Vector2 position;
    Vector2 velocity;
    float scale;
    boolean active;

    @Override
    public boolean isActive() {
        return active;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getScale() {
        return scale;
    }

    public boolean chekCollision(Point another) {
        return this.position.dst(another.position) < (this.getScale() * 32.0f + another.getScale() * 32.0f) * 0.8f;
    }
}
