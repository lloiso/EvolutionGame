package com.evolution.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeBuild;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public class Assets {
    private static Assets ourInstance = new Assets();

    public static Assets getInstance() {
        return ourInstance;
    }

    private AssetManager assetManager;
    private TextureAtlas atlas;

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    private Assets() {
        this.assetManager = new AssetManager();
    }

    public void clear() {
        assetManager.clear();
    }

    public void loadAssets(ScreenManager.ScreenType type) {
        switch (type){
            case GAME:
                assetManager.load("game.pack", TextureAtlas.class);
                assetManager.load("music.mp3", Music.class);
                assetManager.load("laser.wav",Sound.class);
                creatStdFont(48);
                break;
            case MENU:
                assetManager.load("game.pack", TextureAtlas.class);
                creatStdFont(32);
                creatStdFont(96);
                break;
        }

    }

    public void creatStdFont(int size) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.fontFileName = "zorque.ttf";
        parameter.fontParameters.size = size;
        parameter.fontParameters.color = Color.DARK_GRAY;
        parameter.fontParameters.borderColor = Color.LIME;
        parameter.fontParameters.borderWidth = 2;
        parameter.fontParameters.shadowOffsetX = 3;
        parameter.fontParameters.shadowOffsetY = -3;
        parameter.fontParameters.shadowColor = Color.OLIVE;
        assetManager.load("zorque" + size + ".ttf", BitmapFont.class, parameter);

    }
    public void makeLinks(){
        atlas = assetManager.get("game.pack", TextureAtlas.class);

    }

}
