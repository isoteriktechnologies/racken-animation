package io.github.isoteriktech.xgdx.animation.test;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.isoterik.racken.GameObject;
import com.isoterik.racken.Scene;
import com.isoterik.racken.animation.Animator;
import com.isoterik.racken.animation.FrameAnimation;
import com.isoterik.racken.animation.conditions.BooleanCondition;
import com.isoterik.racken.animation.conditions.CompoundCondition;
import com.isoterik.racken.asset.GameAssetsLoader;
import com.isoterik.racken.input.KeyTrigger;

public class AnimatorTest extends Scene {
    public AnimatorTest() {
        setBackgroundColor(Color.BLACK);

        GameAssetsLoader assets = racken.assets;

        FrameAnimation bunnyStand = new FrameAnimation(1f,
                assets.regionForTexture("sprites/bunny_stand.png", true));
        FrameAnimation bunnyJump = new FrameAnimation(1f,
                assets.regionForTexture("sprites/bunny_jump.png", true));
        FrameAnimation bunnyWalk = new FrameAnimation(.25f,
                assets.regionForTexture("sprites/bunny_walk1.png", true),
                assets.regionForTexture("sprites/bunny_walk2.png", true));

        Animator<FrameAnimation> animator = new Animator<>(bunnyStand, bunnyJump, bunnyWalk);

        GameObject bunny = newSpriteObject(bunnyStand.getCurrentFrame());
        bunny.addComponent(animator);
        addGameObject(bunny);

        BooleanCondition standCondition = new BooleanCondition(true);
        BooleanCondition jumpCondition = new BooleanCondition(false);
        CompoundCondition<Boolean> walkCondition = new CompoundCondition<>(true)
                .not(standCondition).not(jumpCondition);

        animator
            .addTransition(bunnyJump, bunnyStand, standCondition)
            .addTransition(bunnyStand, bunnyWalk, true, walkCondition)
            .addTransition(bunnyJump, jumpCondition);

        input.addKeyListener(KeyTrigger.keyDownTrigger(Input.Keys.UP).setPolled(true), (name, evt) -> {
            jumpCondition.dataSource.set(true);
            standCondition.dataSource.set(false);
        });
        input.addKeyListener(KeyTrigger.keyUpTrigger(Input.Keys.UP), (name, evt) -> {
            jumpCondition.dataSource.set(false);
            standCondition.dataSource.set(true);
        });
        input.addKeyListener(KeyTrigger.keyDownTrigger(Input.Keys.RIGHT).setPolled(true), (name, evt) -> {
            standCondition.dataSource.set(false);
        });
        input.addKeyListener(KeyTrigger.keyUpTrigger(Input.Keys.RIGHT), (name, evt) -> {
            standCondition.dataSource.set(true);
        });
    }
}









