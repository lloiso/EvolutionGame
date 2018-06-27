package units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract  class Cell extends Point {

    float angle;
    float acceleration;
    Vector2 tmp;
    int scores;

    public void scoresCounter() {
        scores++;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getScores() {
        return scores;
    }

    public Cell(float x, float y, float acceleration) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.tmp = new Vector2(0.0f, 0.0f);
        this.angle = 0.0f;
        this.acceleration = acceleration;
        this.scale = 1.0f;
        this.scores = scores;
        this.active = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32, 64, 64, scale, scale, angle);
    }

    public void update(float dt) {

        if (angle < 0.0f) {
            angle += 360.0f;
        }
        if (angle > 360.0f) {
            angle -= 360.0f;
        }
        velocity.scl(0.98f);
        position.mulAdd(velocity, dt);


    }

    public abstract void consumed();

    public void eatConsumable(Consumable.Type type) {
        switch (type){
            case FOOD:
                grow();
                break;
            case BAD_FOOD:
                decrease();
                break;
        }
    }


    public void grow() {
        scale += 0.05f;
    }

    public void decrease(){
        scale -= 0.05f;
    }
}

