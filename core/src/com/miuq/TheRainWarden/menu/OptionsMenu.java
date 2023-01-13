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
    private Drawable retryButtonCheckedDrawable;
    private Drawable retryButtonUncheckedDrawable;

    private ImageButtonStyle backButtonStyle;
    private ImageButtonStyle speedrunButtonStyle;
    private ImageButtonStyle retryButtonStyle;
    private ImageButton backButton;
    private ImageButton speedrunButton;
    private ImageButton retryButton;

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
        this.retryButtonCheckedDrawable = new TextureRegionDrawable(new Texture("assets/buttons/retry/RetryCounterOn.png"));
        this.retryButtonUncheckedDrawable = new TextureRegionDrawable(new Texture("assets/buttons/retry/retryCounterOff.png"));

        this.backButtonStyle = new ImageButtonStyle();
        this.backButtonStyle.up = backButtonDrawable;
        this.backButtonStyle.over = backButtonHoverDrawable;

        this.speedrunButtonStyle = new ImageButtonStyle();
        this.speedrunButtonStyle.up = speedrunButtonUncheckedDrawable;
        this.retryButtonStyle = new ImageButtonStyle();
        this.retryButtonStyle.up = retryButtonUncheckedDrawable;

        this.backButton = new ImageButton(backButtonStyle);
        this.speedrunButton = new ImageButton(speedrunButtonStyle);
        this.retryButton = new ImageButton(retryButtonStyle);

        this.stage = new Stage();
        this.stage.addActor(backButton);
        this.stage.addActor(speedrunButton);
        this.stage.addActor(retryButton);

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

        this.retryButton.center();
        this.retryButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                handleRetryClick();
            }
        });

        this.speedrunButton.setPosition(Gdx.graphics.getWidth() / 2 - 250, 900);
        this.retryButton.setPosition(Gdx.graphics.getWidth() / 2 - 250, 600);
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

    private void handleRetryClick(){
        options.updateOptions(1);
    }

    private void handleGodModeClick(){
        //player starts with all abilities unlocked
    }

    private void updateButtons(){
        if(options.cutscenesDisabled()){
            speedrunButtonStyle.up = speedrunButtonCheckedDrawable;
        }else{
            speedrunButtonStyle.up = speedrunButtonUncheckedDrawable;
        }

        if(options.showDeathCounter()){
            retryButtonStyle.up = retryButtonCheckedDrawable;
        }else{
            retryButtonStyle.up = retryButtonUncheckedDrawable;
        }
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
