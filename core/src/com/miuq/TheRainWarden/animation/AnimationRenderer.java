package com.miuq.TheRainWarden.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.miuq.TheRainWarden.main.GameScreen;
import com.miuq.TheRainWarden.objects.entities.Player;
import com.miuq.TheRainWarden.objects.entities.npc.Lu;
import com.miuq.TheRainWarden.objects.entities.npc.Manny;
import com.miuq.TheRainWarden.objects.entities.npc.Mimir;
import com.miuq.TheRainWarden.objects.entities.npc.Persephone;
import com.miuq.TheRainWarden.objects.entities.npc.Rose;
import com.miuq.TheRainWarden.objects.gameObjects.Mango;
import com.miuq.TheRainWarden.objects.gameObjects.ShinyRaindrop;

/**
	 * Handles all animations and when they should be played based on world events.
     * @author Michelle Vuong
	 */

public class AnimationRenderer{
    private Player player;
    private SpriteBatch batch;
    private ShinyRaindrop destroyedRaindrop;
    private Array<ShinyRaindrop> shinyRaindrops;
    private Array<Mango> mangos;
    private Mimir mimir;
    private Persephone persephone;
    private Lu lu;
    private Manny manny;
    private Rose rose;
    private GameScreen gameScreen;

    //player animations
    private Animation idle;
    private Animation idleLeft;
    private Animation walk;
    private Animation walkLeft;
    private Animation dash;
    private Animation dashLeft;
    private Animation jump;
    private Animation jumpLeft;
    private Animation doubleJump;
    private Animation doubleJumpLeft;
    private Animation fall;
    private Animation fallLeft;
    private Animation umbrella;
    private Animation umbrellaLeft;
    private Animation death;
    private Animation deathLeft;

    //object animations
    private Animation shinyRaindropAnimation;
    private Animation raindropDestroyedAnimation;
    private Animation mangoAnimation;
    private boolean raindropDestroyed;
    private boolean hasBeenCarried;

    //npc animations
    private Animation mimirIdle;
    private Animation mannyIdle;
    private Animation luIdle;
    private Animation persephoneIdle;
    private Animation roseIdle;
    
    /**
	 * Constructor of the AnimationRenderer. Creates multiple animation objects for each animation that will be used.
     * @param player The player object, passed when Animation Renderer is created. Used to observe the action the player is currently performing.
     * @param batch The SpriteBatch object. Uses the same SpriteBatch as the GameScreen class to save resources.
     * @author Michelle Vuong
	 */
    public AnimationRenderer(Player player, SpriteBatch batch, GameScreen gameScreen){
        this.player = player;
        this.batch = batch;
        this.shinyRaindrops = new Array<ShinyRaindrop>();
        this.mangos = new Array<Mango>();
        this.gameScreen = gameScreen;

        //player animations taken from assets/animmations/player
        this.idle = new Animation(new TextureRegion(new Texture("animations/player/idle.png")), 4, 4f); //idle animation
        this.idleLeft = new Animation(new TextureRegion(new Texture("animations/player/idleLeft.png")), 4, 4f); //idle facing left animation
        this.walk = new Animation(new TextureRegion(new Texture("animations/player/walk.png")), 4, 0.5f); //walking animation
        this.walkLeft = new Animation(new TextureRegion(new Texture("animations/player/walkLeft.png")), 4, 0.5f); //walking facing left animation
        this.dash = new Animation(new TextureRegion(new Texture("animations/player/dash.png")), 14, 0.58f); //dash animation
        this.dashLeft = new Animation(new TextureRegion(new Texture("animations/player/dashleft.png")), 14, 0.58f); //dash facing left animation
        this.jump = new Animation(new TextureRegion(new Texture("animations/player/jump.png")), 8, 0.47f); //jump animation
        this.jumpLeft = new Animation(new TextureRegion(new Texture("animations/player/jumpLeft.png")), 8, 0.47f); //jump facing left animation
        this.doubleJump = new Animation(new TextureRegion(new Texture("animations/player/doubleJump.png")), 6, 0.75f); //double jump animation
        this.doubleJumpLeft = new Animation(new TextureRegion(new Texture("animations/player/doubleJumpLeft.png")), 6, 0.75f); //double jump facing left animation
        this.fall = new Animation(new TextureRegion(new Texture("animations/player/fall.png")), 3, 0.42f); //falling animation
        this.fallLeft = new Animation(new TextureRegion(new Texture("animations/player/fallLeft.png")), 3, 0.42f); //falling facing left animation
        this.umbrella = new Animation(new TextureRegion(new Texture("animations/player/umbrella.png")), 2, 1f); //umbrella animation
        this.umbrellaLeft = new Animation(new TextureRegion(new Texture("animations/player/umbrellaLeft.png")), 2, 1f); //umbrella facing left animation
        this.death = new Animation(new TextureRegion(new Texture("animations/player/death.png")), 12, 0.71f); //death animation
        this.deathLeft = new Animation(new TextureRegion(new Texture("animations/player/deathLeft.png")), 12, 0.71f); //death facing left animation

        //object animations taken from animations/objects
        this.shinyRaindropAnimation = new Animation(new TextureRegion(new Texture("animations/objects/shinyRaindrop.png")), 5, 0.71f); //raindrop animation
        this.raindropDestroyedAnimation = new Animation(new TextureRegion(new Texture("animations/objects/shinyRaindropDestroy.png")), 7, 0.54f); //raindrop being destroyed animation
        this.mangoAnimation = new Animation(new TextureRegion(new Texture("animations/objects/mango.png")), 2, 1f); //mango animation
        //TODO mango destroyed animation
        //TODO wind current animation
        this.raindropDestroyed = false; //boolean for when the raindrop being destroyed animation should be played
        this.hasBeenCarried = false; //boolean for whether jump animation should keep playing (wind currents bug) 
        
        //npc animations from animations/npc
        this.mimirIdle = new Animation(new TextureRegion(new Texture(Gdx.files.internal("animations/npc/mimirIdle.png"))), 2, 1f);
        this.persephoneIdle = new Animation(new TextureRegion(new Texture(Gdx.files.internal("animations/npc/persephoneIdle.png"))), 2, 1f);
        this.luIdle = new Animation(new TextureRegion(new Texture(Gdx.files.internal("animations/npc/luIdle.png"))), 2, 1f);
        this.mannyIdle = new Animation(new TextureRegion(new Texture(Gdx.files.internal("animations/npc/mannyIdle.png"))), 2, 1f);
        this.roseIdle = new Animation(new TextureRegion(new Texture(Gdx.files.internal("animations/npc/roseIdle.png"))), 2, 1f);
    }

    /**
     * Sets the mimir variable to the current mimir object
     * @param mimir Current mimir object
     * @author Michelle Vuong
     */
    public void setMimir(Mimir mimir){
        this.mimir = mimir;
    }

    /**
     * Sets the manny variable to the current manny object
     * @param manny Current manny object
     * @author Michelle Vuong
     */
    public void setManny(Manny manny){
        this.manny = manny;
    }

    /**
     * Sets the persephone variable to the current persephone object
     * @param persephone Current persephone object
     * @author Michelle Vuong
     */
    public void setPersephone(Persephone persephone){
        this.persephone = persephone;
    }

    /**
     * Sets the lu variable to the current lu object
     * @param lu Current lu object
     * @author Michelle Vuong
     */
    public void setLu(Lu lu){
        this.lu = lu;
    }

    /**
     * Sets the rose variable to the current rose object
     * @param rose Current rose object
     * @author Michelle Vuong
     */
    public void setRose(Rose rose){
        this.rose = rose;
    }

    /**
     * Sets the player variable to the current player object
     * @param player Current player object
     * @author Michelle Vuong
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Updates the shiny raindrop and mango objects with new shiny raindrops and mango objects
     * @param shinyRaindrops ArrayList of new shinyraindrops to be animated.
     * @param mangos ArrayList of new mangos to be animated.
     * @author Michelle Vuong 
     */
    public void updateGameObjects(Array<ShinyRaindrop> shinyRaindrops, Array<Mango> mangos){
        clearRaindrops();

        for(ShinyRaindrop shinyRaindrop: shinyRaindrops){
            addRaindrops(shinyRaindrop);
        }

        clearMangos();

        for(Mango mango: mangos){
            addMangos(mango);
        }
    }

    /**
     * Clears shiny raindrop array.
     * @author Michelle Vuong
     */
    private void clearRaindrops(){
        shinyRaindrops.clear();
    }

    /**
     * Clears mango array.
     */
    private void clearMangos(){
        mangos.clear();
    }

    /**
	 * Adds a ShinyRaindrop object to the shinyraindrop arraylist
     * @param shinyRaindrop The shinyRaindrop object to be added to the array.
     * @author Michelle Vuong
	 */
    private void addRaindrops(ShinyRaindrop shinyRaindrop){
        shinyRaindrops.add(shinyRaindrop);
    }

    /**
     * Adds a Mango object to the mango arraylist
     * @param mango The mango object to be added to the array.
     * @author Michelle Vuong.
     */
    private void addMangos(Mango mango){
        mangos.add(mango);
    }

    /**
	 * Tells the program which raindrop has been destroyed, and that it should start playing the destroyed raindrop animation.
     * @param delta Universal game time (GameScreen render method).
     * @author Michelle Vuong
	 */
    public void destroyedRaindropAnimation(ShinyRaindrop shinyRaindrop){
        destroyedRaindrop = shinyRaindrop;
        raindropDestroyed = true;
    }

    /**
     * Checks if the players death animation has finished playing
     * @return True if the animation has finished, false if the animation has not finished
     * @author Michelle Vuong
     */
    public boolean isDeathAnimationFinished(){
        if(deathLeft.getFrameCount()-1 == deathLeft.getFrameNum() || death.getFrameCount()-1 == death.getFrameNum()){
            deathLeft.setFrame(0);
            death.setFrame(0);
            return true;
        }
        return false;
    }

    /**
     * Draws all animations that should be drawn during NPC events (keeps player and NPC in the correct spot
     * during dialogue gamestate).
     * @param delta Time since last render.
     * @param x X position to draw at.
     * @param y Y position to draw at.
     * @author Michelle Vuong
     */
    public void drawDialogueAnimations(float delta, float x, float y){
        if(gameScreen.getLevel() == 3){
            mimirIdle.update(delta);
            idleLeft.update(delta);
            batch.draw(mimirIdle.getFrame(), 680, 250);
            batch.draw(idleLeft.getFrame(), 730, 230);
        }else if(gameScreen.getLevel() == 8){
            persephoneIdle.update(delta);
            idle.update(delta);
            batch.draw(persephoneIdle.getFrame(), 1125, 170);
            batch.draw(idle.getFrame(), 1050, 170);
        }else if(gameScreen.getLevel() == 9){
            luIdle.update(delta);
            mannyIdle.update(delta);
            roseIdle.update(delta);
            idle.update(delta);
            batch.draw(luIdle.getFrame(), 375, 170);
            batch.draw(mannyIdle.getFrame(), 1100, 170);
            batch.draw(idle.getFrame(), 300, 170);
            batch.draw(roseIdle.getFrame(), 1500, 170);
        }else{
            if(player.isLeft()){
                idleLeft.update(delta);
                batch.draw(idleLeft.getFrame(), x, y);
            }else{
                idle.update(delta);
                batch.draw(idle.getFrame(), x, y);
            }
        }
    }

    /**
     * Draws NPC idle animations while game is running.
     * @param delta Time since last render.
     * @author Michelle Vuong
     */
    private void drawNPCAnimations(float delta){
        if(mimir != null){
            if(gameScreen.getLevel() == 3){
                batch.draw(mimirIdle.getFrame(), mimir.getX() - 48, mimir.getY() - 52);
                mimirIdle.update(delta);
            }
        }

        if(persephone != null){
            if(gameScreen.getLevel() == 8){
                batch.draw(persephoneIdle.getFrame(), persephone.getX() - 48, persephone.getY() - 48);
                persephoneIdle.update(delta);
            }
        }

        if(rose != null){
            if(gameScreen.getLevel() == 9){
                batch.draw(roseIdle.getFrame(), rose.getX() - 48, rose.getY() - 48);
                roseIdle.update(delta);
            }
        }

        if(lu != null){
            if(gameScreen.getLevel() == 9){
                batch.draw(luIdle.getFrame(), lu.getX() - 48, lu.getY() - 48);
                luIdle.update(delta);
            }
        }

        if(manny != null){
            if(gameScreen.getLevel() == 9){
                batch.draw(mannyIdle.getFrame(), manny.getX() - 32, manny.getY() - 40);
                mannyIdle.update(delta);
            }
        }
    }

    /**
	 * Draws all animations using the GameScreen's SpriteBatch.
     * @param delta Time since last render.
     * @author Michelle Vuong
	 */
    public void drawAnimations(float delta){
        shinyRaindropAnimation.update(delta);
        mangoAnimation.update(delta);
        drawNPCAnimations(delta);
        
        for(ShinyRaindrop shinyRaindrop : shinyRaindrops){
            batch.draw(shinyRaindropAnimation.getFrame(), shinyRaindrop.getX() - 32, shinyRaindrop.getY() - 32);
        }

        for(Mango mango : mangos){
            batch.draw(mangoAnimation.getFrame(), mango.getX() - 32, mango.getY() - 32);
        }

        if(raindropDestroyed == true){
            if(raindropDestroyedAnimation.getFrameNum() == raindropDestroyedAnimation.getFrameCount()-1){
                raindropDestroyed = false;
                raindropDestroyedAnimation.setFrame(0);
            }
            batch.draw(raindropDestroyedAnimation.getFrame(), destroyedRaindrop.getX() - 64, destroyedRaindrop.getY() - 64);
            raindropDestroyedAnimation.update(delta);
        }

        if(player.isDead()){           
            if(player.isLeft()){
                player.setVelocityX(0);
                player.setVelocityY(0);
                deathLeft.update(delta);
                batch.draw(deathLeft.getFrame(), player.getX() - 64, player.getY() - 64);
            }else{
                player.setVelocityX(0);
                player.setVelocityY(0);
                death.update(delta);
                batch.draw(death.getFrame(), player.getX() - 64, player.getY() - 64);
            }
        }
        if(!player.isDead()){
            //idle animation - if player is not moving
            if(player.getVelX() == 0 && player.getVelY() == 0 && player.getDashCounter() == 0 && !player.isCarried()){ 
                if(hasBeenCarried){
                    hasBeenCarried = false;
                }
                if(player.isLeft()){
                    idleLeft.update(delta);
                    batch.draw(idleLeft.getFrame(), player.getX() - 64, player.getY() - 64);
                }else{
                    idle.update(delta);
                    batch.draw(idle.getFrame(), player.getX() - 64, player.getY() - 64);
                }
            }else{
                idle.setFrame(0);
                idleLeft.setFrame(0);
            }
            //walk animation - if player is moving
            if(player.getVelX() != 0 && player.getVelY() == 0 && player.getDashCounter() == 0 && !player.isCarried()){
                if(player.isLeft()){
                    walkLeft.update(delta);
                    batch.draw(walkLeft.getFrame(), player.getX() - 64, player.getY() - 64);
                }else{
                    walk.update(delta);
                    batch.draw(walk.getFrame(), player.getX() - 64, player.getY() - 64);
                }
            }else{
                walk.setFrame(0);
                walkLeft.setFrame(0);
            }

            //dash animation - if player is currently dashing
            if(player.getDashCounter() != 0){
                if(player.isLeft()){
                    dashLeft.update(delta);
                    batch.draw(dashLeft.getFrame(), player.getX() -92, player.getY() - 64);
                }else{
                    dash.update(delta);
                    batch.draw(dash.getFrame(), player.getX() -32, player.getY() - 64);
                }
            }else{
                dash.setFrame(0);
                dashLeft.setFrame(0);
            }

            //jump animation - if player y velocity is greater than 0 and they have not used their jumps yet
            if(player.getJumpCount() != 0 && player.getVelY() > 0){
                if(player.isCarried()){
                    hasBeenCarried = true;
                }
                
                if(player.getJumpCount() == 1 && !hasBeenCarried){
                    if(player.isLeft()){
                        jumpLeft.update(delta);
                        batch.draw(jumpLeft.getFrame(), player.getX() - 64, player.getY() - 64);
                    }else{
                        jump.update(delta);
                        batch.draw(jump.getFrame(), player.getX() - 64, player.getY() - 64);
                    }
                }else if(player.getJumpCount() == 2 && !hasBeenCarried){
                    if(player.isLeft()){
                        doubleJumpLeft.update(delta);
                        doubleJump.update(delta);
                        batch.draw(doubleJumpLeft.getFrame(), player.getX() - 64, player.getY() - 64);
                    }else{
                        doubleJump.update(delta);
                        doubleJumpLeft.update(delta);
                        batch.draw(doubleJump.getFrame(), player.getX() - 64, player.getY() - 64);
                    }
                }
            }else{
                jump.setFrame(0);
                jumpLeft.setFrame(0);
                doubleJump.setFrame(0);
                doubleJumpLeft.setFrame(0);
            }

            //fall animation - if player y velocity is less than 0
            if(player.getVelY() < 0 && !player.isCarried()){
                if(player.isLeft()){
                    fallLeft.update(delta);
                    batch.draw(fallLeft.getFrame(), player.getX()-64, player.getY()-64);
                }else{
                    fall.update(delta);
                    batch.draw(fall.getFrame(), player.getX()-64, player.getY()-64);

                }
            }else{
                fall.setFrame(0);
                fallLeft.setFrame(0);
            }

            //draws fall animation if the player recently exited a wind current without jumping
            if(player.getVelY() > 0 && !player.isCarried() && player.getJumpCount() == 0){
                if(player.isLeft()){
                    fallLeft.update(delta);
                    batch.draw(fallLeft.getFrame(), player.getX()-64, player.getY()-64);
                }else{
                    fall.update(delta);
                    batch.draw(fall.getFrame(), player.getX()-64, player.getY()-64);
                }
            }

            if(player.isCarried()){
                if(player.isLeft()){
                    umbrellaLeft.update(delta);
                    batch.draw(umbrellaLeft.getFrame(), player.getX() - 64, player.getY() - 64);
                }else{
                    umbrella.update(delta);
                    batch.draw(umbrella.getFrame(), player.getX() - 64, player.getY() - 64);
                }
            }
        }
    }
}