package com.isoterik.racken.animation.conditions;

import com.badlogic.gdx.utils.Array;
import com.isoterik.racken.animation.ICondition;

/**
 * A condition that evaluates to true if any of its child conditions evaluates to true.
 *
 * @author imranabdulmalik
 */
public class OrCondition extends GroupCondition {
    /**
     * Creates a new instance given the child conditions to evaluate.
     * @param conditions the child conditions
     */
    public OrCondition(Array<ICondition> conditions) {
        super(conditions);
    }

    /**
     * Creates a new instance given one or more child conditions.
     * @param condition a child condition
     * @param conditions more child conditions
     */
    public OrCondition(ICondition condition, ICondition... conditions) {
        super(condition, conditions);
    }

    @Override
    public boolean test() {
        for (ICondition condition : conditions) {
            if (condition.test())
                return true;
        }

        return false;
    }
}
