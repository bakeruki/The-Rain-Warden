package com.miuq.TheRainWarden.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.GameScreen;
import com.miuq.TheRainWarden.TheRainWarden;
import com.miuq.TheRainWarden.cutscenes.CutsceneOne;
import com.miuq.TheRainWarden.cutscenes.CutsceneThree;
import com.miuq.TheRainWarden.cutscenes.CutsceneTwo;
import com.miuq.helper.GameSaveHandler;

public class StartMenu extends ScreenAdapter{
    private TheRainWarden game;
    private Stage stage;
    private GameSaveHandler save;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private ImageButton newGame1Button;
    private ImageButton newGame2Button;
    private ImageButton newGame3Button;

    private Drawable newGame1Drawable;
    private Drawable newGame1DownDrawable;
    private Drawable newGame2Drawable;
    private Drawable newGame2DownDrawable;
    private Drawable newGame3Drawable;
    private Drawable newGame3DownDrawable;

    private ImageButtonStyle newGame1ButtonStyle;
    private ImageButtonStyle newGame2ButtonStyle;
    private ImageButtonStyle newGame3ButtonStyle;

    public StartMenu(TheRainWarden game, OrthographicCamera camera, FitViewport viewport){
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;

        this.save = new GameSaveHandler();
        
        this.newGame1Drawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame1Button/newGame1NotHovered.png"));
        this.newGame1DownDrawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame1Button/newGame1Hovered.png"));
        this.newGame2Drawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame2Button/newGame2NotHovered.png"));
        this.newGame2DownDrawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame2Button/newGame2Hovered.png"));
        this.newGame3Drawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame3Button/newGame3NotHovered.png"));
        this.newGame3DownDrawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame3Button/newGame3Hovered.png"));

        this.newGame1ButtonStyle = new ImageButtonStyle();
        this.newGame1ButtonStyle.up = newGame1Drawable;
        this.newGame1ButtonStyle.over = newGame1DownDrawable;
        this.newGame2ButtonStyle = new ImageButtonStyle();
        this.newGame2ButtonStyle.up = newGame2Drawable;
        this.newGame2ButtonStyle.over = newGame2DownDrawable;
        this.newGame3ButtonStyle = new ImageButtonStyle();
        this.newGame3ButtonStyle.up = newGame3Drawable;
        this.newGame3ButtonStyle.over = newGame3DownDrawable;
        
        this.newGame1Button = new ImageButton(newGame1ButtonStyle);
        this.newGame2Button = new ImageButton(newGame2ButtonStyle);
        this.newGame3Button = new ImageButton(newGame3ButtonStyle);

        this.stage = new Stage();
        this.stage.addActor(newGame1Button);
        this.stage.addActor(newGame2Button);
        this.stage.addActor(newGame3Button);

        newGame1Button.center();
        newGame1Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                handleSaveClick(0);
            }
        });
        newGame2Button.center();
        newGame2Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                handleSaveClick(1);
            }
        });
        newGame3Button.center();
        newGame3Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                handleSaveClick(2);
            }
        });

        newGame1Button.setPosition(Gdx.graphics.getWidth() / 2 - 246, 900);
        newGame2Button.setPosition(Gdx.graphics.getWidth() / 2 - 246, 600);
        newGame3Button.setPosition(Gdx.graphics.getWidth() / 2 - 246, 300);

        Gdx.input.setInputProcessor(stage);
    }

    public Stage getStage(){
        return stage;
    }

    private void handleSaveClick(int saveNum){
        int level = save.getLevelFromSave(saveNum);
        int mangos = save.getMangosFromSave(saveNum);

        playCutscene(level, mangos, saveNum);
    }

    private void playCutscene(int level, int mangos, int saveNum){
        GameScreen gameScreen = new GameScreen(camera, viewport, game, level, mangos);
        gameScreen.setSaveNum(saveNum);
        if(level < 2){
            game.setScreen(new CutsceneOne(camera, viewport, game, gameScreen));
        }else if(level < 5){
            game.setScreen(new CutsceneTwo(camera, viewport, game, gameScreen));
        }else if(level < 8){
            game.setScreen(new CutsceneThree(camera, viewport, game, gameScreen));
        }
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(delta);
        stage.draw();
    }
}
