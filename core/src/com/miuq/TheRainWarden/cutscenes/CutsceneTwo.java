package com.miuq.TheRainWarden.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.animation.AnimatedText;
import com.miuq.TheRainWarden.main.GameScreen;
import com.miuq.TheRainWarden.main.TheRainWarden;

public class CutsceneTwo extends Cutscene {
    public CutsceneTwo(OrthographicCamera camera, FitViewport viewport, TheRainWarden game, GameScreen gameScreen) {
        super(camera, viewport, game, gameScreen);
        
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene2/frame4.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene2/frame5.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene2/frame6.png")));
        
        this.cutsceneTexts.add(new AnimatedText("As the rain fell, and as the wind blew...", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("There was a light, pointing me somewhere below...", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("Curiosity took me over, as I ventured towards the ice and stone beneath my feet...", 0.05f));
    }

    @Override
    public void render(float delta){
        renderCutscene(delta);
    }
}