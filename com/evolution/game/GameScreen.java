package com.evolution.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import units.ConsumableEmitter;
import units.EnemyEmiter;
import units.Hero;
import units.Cell;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    int scores = 0;
    private final int CONSUMABLE_NUMBERS = 20;
    private final int ENEMY_NUMBERS = 6;
    private int[] scoresField = new int[ENEMY_NUMBERS];
    private Hero hero;
    private EnemyEmiter enemyEmiter;
    private List<Cell> collisionList;
    private ConsumableEmitter consumableEmitter;
    private Viewport viewport;              //!!!!
    private Camera camera;
    private Camera windowCamera;
    private Music music;
    private Sound consumeSound;
    private MiniMap miniMap;
    private boolean paused;


    private BitmapFont font;

    public Viewport getViewport() {
        return viewport;
    }

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;

    }

    public ConsumableEmitter getConsumableEmitter() {
        return consumableEmitter;
    }

    public EnemyEmiter getEnemyEmiter() {
        return enemyEmiter;
    }

    @Override
    public void show() {
        font = Assets.getInstance().getAssetManager().get("zorque48.ttf", BitmapFont.class);
        hero = new Hero(this);
        collisionList = new ArrayList<>();
        enemyEmiter = new EnemyEmiter(this);
        consumableEmitter = new ConsumableEmitter(this);
        camera = new OrthographicCamera(1280, 720);
        viewport = new FitViewport(1280, 720, camera);
        miniMap = new MiniMap(this);
        music = Assets.getInstance().getAssetManager().get("music.mp3", Music.class);
        music.setLooping(true);
//        music.play();
        music.setVolume(0.05f);
        consumeSound = Assets.getInstance().getAssetManager().get("laser.wav", Sound.class);
        paused = false;
        windowCamera = new OrthographicCamera(1280,720);
        windowCamera.position.set(640,360,0);
        windowCamera.update();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.75f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        hero.render(batch);
        enemyEmiter.render(batch);
        consumableEmitter.render(batch);
        batch.end();

        batch.setProjectionMatrix(windowCamera.combined);

        batch.begin();
        font.draw(batch, "Scores: ", 20, 50);
        miniMap.render(batch);
        batch.end();
    }


    public void checkCollision() {
        collisionList.clear();
        collisionList.add(hero);
        collisionList.addAll(enemyEmiter.activeList);

        for (int i = 0; i < collisionList.size(); i++) {
            for (int j = 0; j < consumableEmitter.getActiveList().size(); j++) {
                if (collisionList.get(i).getPosition().dst(consumableEmitter.getActiveList().get(j).getPosition()) <
                        (collisionList.get(i).getScale() * 32.0f + consumableEmitter.getActiveList().get(j).getScale() * 32.0f) * 0.8) {
                    collisionList.get(i).eatConsumable(consumableEmitter.getActiveList().get(j).getType());
//                    collisionList.get(i).scoresCounter();
                    consumableEmitter.getActiveList().get(j).consumed();
                    consumeSound.play();
                }
            }
        }
        for (int i = 0; i < collisionList.size() - 1; i++) {
            for (int j = i + 1; j < collisionList.size(); j++) {
                if (collisionList.get(i).chekCollision(collisionList.get(j))) {
                    if (collisionList.get(i).getScale() > collisionList.get(j).getScale()) {
                        collisionList.get(i).grow();
                        collisionList.get(j).consumed();
                        consumeSound.play();
//                        collisionList.get(i).scoresCounter();
//                        units[j].setScores(0);

                    } else {
                        collisionList.get(j).grow();
                        collisionList.get(i).consumed();

                    }
                }
            }
        }
    }

    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            paused = !paused;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
        }



        if (!paused) {

            hero.update(dt);
            camera.position.set(hero.getPosition().x - 32, hero.getPosition().y - 32, 0);
            camera.update();
            enemyEmiter.update(dt);
            consumableEmitter.update(dt);
            checkCollision();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        viewport.apply();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        music.dispose();
    }

    public Hero getHero() {
        return hero;
    }
}

