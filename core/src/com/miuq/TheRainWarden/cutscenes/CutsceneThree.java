package com.miuq.TheRainWarden.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.GameScreen;
import com.miuq.TheRainWarden.TheRainWarden;
import com.miuq.animation.AnimatedText;

public class CutsceneThree extends Cutscene{

    public CutsceneThree(OrthographicCamera camera, FitViewport viewport, TheRainWarden game, GameScreen gameScreen) {
        super(camera, viewport, game, gameScreen);

        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene3/frame7.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene3/frame8.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene3/frame9.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene3/frame10.png")));

        this.cutsceneTexts.add(new AnimatedText("As I escape the cold, I am greeted with warmth and light.", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("The bountiful fruits and lush trees welcome me with a warm presence.", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("Yet the warm presence fuels my doubt and uncertainty.", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("What truth does this greenery hide from me?", 0.05f));
    }

    @Override
    public void render(float delta){
        renderCutscene(delta);
    }
}
