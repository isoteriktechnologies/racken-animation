package io.github.isoteriktech.xgdx.animation.test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.isoterik.racken.GameObject;
import com.isoterik.racken.Scene;
import com.isoterik.racken.animation.FrameAnimation;
import com.isoterik.racken.utils.SpriteUtils;

public class FrameAnimationTest extends Scene {
    public FrameAnimationTest(){
        setBackgroundColor(Color.BLACK);

        Array<TextureRegion> frames = SpriteUtils.getSpriteSequence("sprites/wingMan", ".png",
                1, 5, 1);
        FrameAnimation frameAnimation = new FrameAnimation(frames, .25f);

        GameObject wingMan = newSpriteObject(frames.first());
        wingMan.addComponent(frameAnimation);
        addGameObject(wingMan);

        wingMan.transform.setPosition((gameWorldUnits.getWorldWidth() - wingMan.transform.getWidth())/2f,
                (gameWorldUnits.getWorldHeight() - wingMan.transform.getHeight())/2f);
    }
}
