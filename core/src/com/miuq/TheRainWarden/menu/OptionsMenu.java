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
import com.miuq.TheRainWarden.TheRainWarden;
import com.miuq.helper.GameOptionsHandler;

public class OptionsMenu extends ScreenAdapter{
    private TheRainWarden game;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private GameOptionsHandler options;

    private Drawable backButtonDrawable;
    private Drawable backButtonHoverDrawable;
    private Drawable speedrunButtonCheckedDrawable;
    private Drawable speedrunButtonUncheckedDrawable;

    private ImageButtonStyle backButtonStyle;
    private ImageButtonStyle speedrunButtonStyle;
    private ImageButton backButton;
    private ImageButton speedrunButton;

    private Stage stage;

    public OptionsMenu(TheRainWarden game, OrthographicCamera camera, FitViewport viewport){
        this.game = game;
        this.viewport = viewport;
        this.camera = camera;
        this.options = new GameOptionsHandler();
        
        this.backButtonDrawable = new TextureRegionDrawable(new Texture("assets/buttons/backSaveButton/backButton.png"));
        this.backButtonHoverDrawable = new TextureRegionDrawable(new Texture("assets/buttons/backSaveButton/backButtonHover.png"));
        this.speedrunButtonCheckedDrawable = new TextureRegionDrawable(new Texture("assets/buttons/speedrun/speedrunModeOn.png"));
        this.speedrunButtonUncheckedDrawable = new TextureRegionDrawable(new Texture("assets/buttons/speedrun/speedrunModeOff.png"));

        this.backButtonStyle = new ImageButtonStyle();
        this.backButtonStyle.up = backButtonDrawable;
        this.backButtonStyle.over = backButtonHoverDrawable;

        this.speedrunButtonStyle = new ImageButtonStyle();
        this.speedrunButtonStyle.up = speedrunButtonUncheckedDrawable;

        this.backButton = new ImageButton(backButtonStyle);
        this.speedrunButton = new ImageButton(speedrunButtonStyle);

        this.stage = new Stage();
        this.stage.addActor(backButton);
        this.stage.addActor(speedrunButton);

        this.backButton.center();
        this.backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                handleBackClick();
            }
        });

        this.speedrunButton.center();
        this.speedrunButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                handleSpeedrunClick();
            }
        });

        this.speedrunButton.setPosition(Gdx.graphics.getWidth() / 2 - 250, 300);
        this.backButton.setPosition(Gdx.graphics.getWidth() / 2 - 170, 100);
    }

    private void handleBackClick(){
        MainMenu mainMenu = new MainMenu(camera, viewport, game, false);
        game.setScreen(mainMenu);
        Gdx.input.setInputProcessor(mainMenu.getStage());
        stage.dispose();
        this.dispose();
    }

    private void handleSpeedrunClick(){
        options.updateOptions(0);
    }

    private void updateButtons(){
        if(options.cutscenesDisabled()){
            speedrunButtonStyle.up = speedrunButtonCheckedDrawable;
        }else{
            speedrunButtonStyle.up = speedrunButtonUncheckedDrawable;
        }
    }

    private void handleDeathClick(){
        //show the death counter in the top right of the gamescreen
    }

    private void handleGodModeClick(){
        //player starts with all abilities unlocked
    }

    public Stage getStage(){
        return stage;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateButtons();
        stage.act(delta);
        stage.draw();
    }
}
