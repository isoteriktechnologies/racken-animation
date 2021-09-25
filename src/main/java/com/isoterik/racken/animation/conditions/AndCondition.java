package com.isoterik.racken.animation.conditions;

import com.badlogic.gdx.utils.Array;
import com.isoterik.racken.animation.ICondition;

/**
 * A condition that evaluates to true if and only if all its child conditions evaluates to true.
 *
 * @author imranabdulmalik
 */
public class AndCondition extends GroupCondition {
    /**
     * Creates a new instance given the child conditions to evaluate.
     * @param conditions the child conditions
     */
    public AndCondition(Array<ICondition> conditions) {
        super(conditions);
    }

    /**
     * Creates a new instance given one or more child conditions.
     * @param condition a child condition
     * @param conditions more child conditions
     */
    public AndCondition(ICondition condition, ICondition... conditions) {
        super(condition, conditions);
    }

    @Override
    public boolean test() {
        if (conditions.isEmpty())
            return false;

        for (ICondition condition : conditions) {
            if (!condition.test())
                return false;
        }

        return true;
    }
}
