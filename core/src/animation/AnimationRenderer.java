package animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import objects.gameObjects.ShinyRaindrop;
import objects.player.Player;

/**
	 * Handles all animations and when they should be played based on world events.
     * @author Michelle Vuong
	 */

public class AnimationRenderer{

    private Player player;
    private SpriteBatch batch;
    private ShinyRaindrop destroyedRaindrop;
    private Array<ShinyRaindrop> shinyRaindrops;

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

    //object animations
    private Animation shinyRaindropAnimation;
    private Animation raindropDestroyedAnimation;
    private boolean raindropDestroyed;

    
    /**
	 * Constructor of the AnimationRenderer. Creates multiple animation objects for each animation that will be used.
     * @param player The player object, passed when Animation Renderer is created. Used to observe the action the player is currently performing.
     * @param batch The SpriteBatch object. Uses the same SpriteBatch as the GameScreen class to save resources.
     * @author Michelle Vuong
	 */
    public AnimationRenderer(Player player, SpriteBatch batch){
        this.player = player;
        this.batch = batch;
        this.shinyRaindrops = new Array<ShinyRaindrop>();

        this.idle = new Animation(new TextureRegion(new Texture("animations/player/idle.png")), 4, 4f);
        this.idleLeft = new Animation(new TextureRegion(new Texture("animations/player/idleLeft.png")), 4, 4f);
        this.walk = new Animation(new TextureRegion(new Texture("animations/player/walk.png")), 4, 0.5f);
        this.walkLeft = new Animation(new TextureRegion(new Texture("animations/player/walkLeft.png")), 4, 0.5f);
        this.dash = new Animation(new TextureRegion(new Texture("animations/player/dash.png")), 14, 0.58f);
        this.dashLeft = new Animation(new TextureRegion(new Texture("animations/player/dashleft.png")), 14, 0.58f);
        this.jump = new Animation(new TextureRegion(new Texture("animations/player/jump.png")), 8, 0.47f);
        this.jumpLeft = new Animation(new TextureRegion(new Texture("animations/player/jumpLeft.png")), 8, 0.47f);
        this.doubleJump = new Animation(new TextureRegion(new Texture("animations/player/doubleJump.png")), 6, 0.75f);
        this.doubleJumpLeft = new Animation(new TextureRegion(new Texture("animations/player/doubleJumpLeft.png")), 6, 0.75f);
        this.fall = new Animation(new TextureRegion(new Texture("animations/player/fall.png")), 3, 0.42f);
        this.fallLeft = new Animation(new TextureRegion(new Texture("animations/player/fallLeft.png")), 3, 0.42f);

        this.shinyRaindropAnimation = new Animation(new TextureRegion(new Texture("animations/objects/shinyRaindrop.png")), 5, 0.71f);
        this.raindropDestroyedAnimation = new Animation(new TextureRegion(new Texture("animations/objects/shinyRaindropDestroy.png")), 7, 0.54f);
        this.raindropDestroyed = false;
    }

    public void clearRaindrops(){
        shinyRaindrops.clear();
    }
    /**
	 * Adds all game ShinyRaindrop objects into an array so that animations can be drawn for each raindrop.
     * @param shinyRaindrop The shinyRaindrop object to be added to the array.
     * @author Michelle Vuong
	 */
    public void addRaindrops(ShinyRaindrop shinyRaindrop){
        shinyRaindrops.add(shinyRaindrop);
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
	 * Draws all animations using the GameScreen's SpriteBatch.
     * @param delta Universal game time (GameScreen render method).
     * @author Michelle Vuong
	 */
    public void drawAnimations(float delta){
        shinyRaindropAnimation.update(delta);
        
        for(ShinyRaindrop shinyRaindrop : shinyRaindrops){
            batch.draw(shinyRaindropAnimation.getFrame(), shinyRaindrop.getX() - 32, shinyRaindrop.getY() - 32);
        }

        if(raindropDestroyed == true){
            if(raindropDestroyedAnimation.getFrameNum() == raindropDestroyedAnimation.getFrameCount()-1){
                raindropDestroyed = false;
                raindropDestroyedAnimation.setFrame(0);
            }
            batch.draw(raindropDestroyedAnimation.getFrame(), destroyedRaindrop.getX() - 64, destroyedRaindrop.getY() - 64);
            raindropDestroyedAnimation.update(delta);
        }
        
        //idle animation
        if(player.getVelX() == 0 && player.getVelY() == 0){
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

        if(player.getVelX() != 0 && player.getVelY() == 0){
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

        //dash animation
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

        //jump animation
        if(player.getJumpCount() != 0 && player.getVelY() > 0){
            if(player.getJumpCount() == 1){
                if(player.isLeft()){
                    jumpLeft.update(delta);
                    batch.draw(jumpLeft.getFrame(), player.getX() - 64, player.getY() - 64);
                }else{
                    jump.update(delta);
                    batch.draw(jump.getFrame(), player.getX() - 64, player.getY() - 64);
                }
            }else if(player.getJumpCount() == 2){
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

        //fall animation
        if(player.getVelY() < 0){
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
    }
}