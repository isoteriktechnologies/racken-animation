package com.isoterik.racken.animation;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.isoterik.racken.Component;
import com.isoterik.racken.GameObject;
import com.isoterik.racken.Racken;
import com.isoterik.racken._2d.components.renderer.SpriteRenderer;

/**
 * Useful for animating 2d {@link GameObject}s using a sequence of images (sprites).
 * <p>
 * For FrameAnimation to work, the host {@link GameObject} must have a {@link SpriteRenderer} attached!
 *
 * @see Animator
 *
 * @author imranabdulmalik
 */
public class FrameAnimation extends Component implements IAnimationState<GameObject>  {
    protected final Array<? extends TextureRegion> sprites;

    protected final Animation<? extends TextureRegion> animation;

    protected float stateTime;
    private boolean shouldReset = true;

    public static final Animation.PlayMode NORMAL =
            Animation.PlayMode.NORMAL;
    public static final Animation.PlayMode REVERSED =
            Animation.PlayMode.REVERSED;
    public static final Animation.PlayMode LOOP =
            Animation.PlayMode.LOOP;
    public static final Animation.PlayMode LOOP_PINGPONG =
            Animation.PlayMode.LOOP_PINGPONG;
    public static final Animation.PlayMode LOOP_RANDOM =
            Animation.PlayMode.LOOP_RANDOM;
    public static final Animation.PlayMode LOOP_REVERSED =
            Animation.PlayMode.LOOP_REVERSED;

    /**
     * Creates a new instance with an array of sprites ({@link TextureRegion}s)
     * @param sprites array of texture regions
     * @param frameDuration the duration (in seconds) for a single frame of the animation
     */
    public FrameAnimation(float frameDuration, Array<? extends TextureRegion> sprites) {
        this.sprites = sprites;
        this.animation = new Animation<>(frameDuration,
                this.sprites);
        this.animation.setPlayMode(LOOP);
    }

    /**
     * Creates a new instance with an array of sprites ({@link TextureRegion}s)
     * @param sprites array of texture regions
     * @param frameDuration the duration (in seconds) for a single frame of the animation
     */
    public FrameAnimation(float frameDuration, TextureRegion ...sprites) {
        this(frameDuration, new Array<>(sprites));
    }

    /**
     * Creates a new instance given a 2D array of texture regions.
     * @param sprites a 2D array of texture regions
     * @param rows the number rows
     * @param cols the number of columns
     * @param frameDuration the duration (in seconds) for a single frame of the animation
     */
    public FrameAnimation(float frameDuration, TextureRegion[][] sprites,
                          int rows, int cols)
    { this(frameDuration, getSpritesFromArray(sprites, rows, cols)); }

    /**
     * Creates a new instance given a sprite sheet ({@link Texture}). The sprite sheet will be split to get the frames of the animation.
     * @param spriteSheet the sprite sheet ({@link Texture})
     * @param cols the number of columns
     * @param rows the number rows
     * @param frameDuration the duration (in seconds) for a single frame of the animation
     */
    public FrameAnimation(Texture spriteSheet, int rows, int cols,
                          float frameDuration)
    { this(frameDuration, splitSpriteSheet(spriteSheet, rows, cols), rows, cols); }

    /**
     * Sets the animation play mode
     * @param playMode the animation play mode
     */
    public void setPlayMode(Animation.PlayMode playMode)
    { animation.setPlayMode(playMode); }

    /**
     *
     * @return the current animation play mode
     */
    public Animation.PlayMode getPlayMode()
    { return animation.getPlayMode(); }

    /**
     * Creates an {@link Array} of texture regions from a 2D array
     * @param spritesArray the 2D array
     * @param rows number of rows
     * @param cols number of columns
     * @return an {@link Array} of texture regions
     */
    public static Array<TextureRegion> getSpritesFromArray(TextureRegion[][] spritesArray, int rows, int cols) {
        Array<TextureRegion> array = new Array<>();
        for(int i=0; i<rows; i++)
            for(int j=0; j<cols; j++)
                array.add(spritesArray[i][j]);

        return(array);
    }

    /**
     * Splits a sprite sheet into frames.
     * @param spriteSheet the sprite sheet
     * @param rows number of rows
     * @param cols number of columns
     * @return a 2D array of texture regions
     */
    public static TextureRegion[][] splitSpriteSheet(Texture spriteSheet, int rows, int cols) {
        int frameWidth = spriteSheet.getWidth()/cols;
        int frameHeight = spriteSheet.getHeight()/rows;
        return(TextureRegion.split(spriteSheet, frameWidth,
                frameHeight));
    }

    /**
     * Updates the animation.
     * @param deltaTime the time difference between now and the previous frame
     * @return the next frame
     */
    public TextureRegion updateAnimation(float deltaTime) {
        stateTime += deltaTime;
        return animation.getKeyFrame(stateTime);
    }

    /**
     *
     * @return whether the animation is finished
     */
    public boolean isAnimationFinished()
    { return(animation.isAnimationFinished(stateTime)); }

    /**
     *
     * @return the internal {@link Animation} instance used
     */
    public Animation<? extends TextureRegion> getAnimation()
    { return animation; }

    /**
     * Sets the state time of the animation
     * @param stateTime the state time
     */
    public void setStateTime (float stateTime)
    { this.stateTime = stateTime; }

    /**
     * Resets the animation
     */
    public void reset()
    { setStateTime(0); }

    /**
     * @return whether the animation should be reset during transition
     */
    public boolean shouldReset() {
        return shouldReset;
    }

    /**
     * Sets whether the animation should be reset during transition
     * @param shouldReset whether the animation should be reset during transition
     */
    public void setShouldReset(boolean shouldReset) {
        this.shouldReset = shouldReset;
    }

    /**
     *
     * @return the total animation duration in seconds
     */
    public float getAnimationDuration()
    { return animation.getAnimationDuration(); }

    /**
     *
     * @return the duration (in seconds) for a single frame of the animation
     */
    public float getFrameDuration()
    { return animation.getFrameDuration(); }

    /**
     *
     * @param stateTime the current time of animation
     * @return the current frame
     */
    public TextureRegion getKeyFrame(float stateTime)
    { return animation.getKeyFrame(stateTime); }

    /**
     *
     * @return the current frame
     */
    public TextureRegion getCurrentFrame()
    { return animation.getKeyFrame(stateTime); }

    /**
     *
     * @param stateTime the current time of animation
     * @return the current frame number
     */
    public int getKeyFrameIndex(float stateTime)
    { return animation.getKeyFrameIndex(stateTime); }

    /**
     *
     * @return the frames for this animation
     */
    public Array<? extends TextureRegion> getKeyFrames()
    { return sprites; }

    /**
     * Sets the duration (in seconds) for a single frame of the animation
     * @param frameDuration the duration (in seconds) for a single frame of the animation
     */
    public void setFrameDuration(float frameDuration)
    { animation.setFrameDuration(frameDuration); }


    /* State implementations */

    @Override
    public void enter(GameObject go) {
        /*
         * Render the first frame if the host game object has a SpriteRenderer attached
         */
        setStateTime(0);

        SpriteRenderer sr = go.getComponent(SpriteRenderer.class);
        if (sr != null) {
            sr.setSprite(getCurrentFrame());
        }
    }

    @Override
    public void update(GameObject go) {
        /*
         * Render the next frame if the host game object has a SpriteRenderer attached
         */
        SpriteRenderer sr = go.getComponent(SpriteRenderer.class);
        if (sr != null) {
            sr.setSprite(updateAnimation(Racken.instance().getDeltaTime()));
        }
    }

    @Override
    public void exit(GameObject go)
    { /* Nothing to do here */ }

    @Override
    public boolean onMessage(GameObject go, Telegram tel){
        // This is not used
        return false;
    }

    @Override
    public void attach() {
        enter(gameObject);
    }

    @Override
    public void update(float deltaTime) {
        update(gameObject);
    }
}
