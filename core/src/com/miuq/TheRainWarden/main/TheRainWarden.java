package com.miuq.TheRainWarden.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.menu.MainMenu;

public class TheRainWarden extends Game{
	public static TheRainWarden INSTANCE;
	private int widthScreen;
	private int heightScreen;
	private OrthographicCamera camera;
	private FitViewport viewport;

	public TheRainWarden(){
		INSTANCE = this;
	}
	
	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, widthScreen, heightScreen);
		this.viewport = new FitViewport(widthScreen, heightScreen, camera);
		setScreen(new MainMenu(camera, viewport, this, true));
	}
}
