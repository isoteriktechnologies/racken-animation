package com.isoterik.racken.animation;

import com.badlogic.gdx.ai.fsm.State;

public interface IAnimationState<E> extends State<E> {
    /**
     * Resets the state
     */
    void reset();

    /**
     * @return whether the state should be reset during transition
     */
    boolean shouldReset();
}
