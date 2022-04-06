package com.isoterik.racken.animation.test;

import com.isoterik.racken.animation.ICondition;
import com.isoterik.racken.animation.conditions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionsTest {
    ICondition.DataSource<Float> floatDataSource1, floatDataSource2;

    @BeforeEach
    void setUp() {
        floatDataSource1 = new ICondition.DataSource<>(10f);
        floatDataSource2 = new ICondition.DataSource<>(10f);
    }

    @Test
    void testEqualCondition() {
        ICondition equalCondition = new EqualCondition<>(floatDataSource1, floatDataSource2);
        assertTrue(equalCondition.test());

        floatDataSource2.set(10.0001f);
        assertFalse(equalCondition.test());

        floatDataSource1.set(10.0001f);
        assertTrue(equalCondition.test());

        floatDataSource2.set(20f);
        assertFalse(equalCondition.test());
    }

    @Test
    void testNotEqualCondition() {
        ICondition notEqualCondition = new NotEqualCondition<>(floatDataSource1, floatDataSource2);
        assertFalse(notEqualCondition.test());

        floatDataSource2.set(10.0001f);
        assertTrue(notEqualCondition.test());

        floatDataSource1.set(10.0001f);
        assertFalse(notEqualCondition.test());

        floatDataSource2.set(20f);
        assertTrue(notEqualCondition.test());
    }

    @Test
    void testGreaterThanCondition() {
        ICondition condition = new GreaterThanCondition(floatDataSource1, floatDataSource2);
        assertFalse(condition.test());

        floatDataSource1.set(20f);
        assertTrue(condition.test());

        floatDataSource2.set(50f);
        assertFalse(condition.test());
    }

    @Test
    void testLessThanCondition() {
        ICondition condition = new LessThanCondition(floatDataSource1, floatDataSource2);
        assertFalse(condition.test());

        floatDataSource1.set(20f);
        assertFalse(condition.test());

        floatDataSource2.set(50f);
        assertTrue(condition.test());
    }

    @Test
    void testNotCondition() {
        BooleanCondition booleanCondition = new BooleanCondition(false);
        ICondition notCondition = new NotCondition(booleanCondition);
        assertTrue(notCondition.test());

        booleanCondition.dataSource.set(true);
        assertFalse(notCondition.test());
    }

    @Test
    void testAndCondition() {
        BooleanCondition booleanCondition1 = new BooleanCondition(true);
        BooleanCondition booleanCondition2 = new BooleanCondition(false);
        ICondition andCondition = new AndCondition(booleanCondition1, booleanCondition2);
        assertFalse(andCondition.test());

        booleanCondition2.dataSource.set(true);
        assertTrue(andCondition.test());

        booleanCondition1.dataSource.set(false);
        booleanCondition2.dataSource.set(false);
        assertFalse(andCondition.test());
    }

    @Test
    void testOrCondition() {
        BooleanCondition booleanCondition1 = new BooleanCondition(true);
        BooleanCondition booleanCondition2 = new BooleanCondition(false);
        ICondition orCondition = new OrCondition(booleanCondition1, booleanCondition2);
        assertTrue(orCondition.test());

        booleanCondition2.dataSource.set(true);
        assertTrue(orCondition.test());

        booleanCondition1.dataSource.set(false);
        booleanCondition2.dataSource.set(false);
        assertFalse(orCondition.test());
    }

    @Test
    void testCompoundCondition() {
        BooleanCondition booleanCondition1 = new BooleanCondition(true);
        BooleanCondition booleanCondition2 = new BooleanCondition(false);
        CompoundCondition<Boolean> compoundCondition = new CompoundCondition<>(true);
        assertTrue(compoundCondition.test());

        compoundCondition.and(booleanCondition1).or(booleanCondition2);
        assertTrue(compoundCondition.test());

        booleanCondition2.dataSource.set(true);
        assertTrue(compoundCondition.test());

        booleanCondition1.dataSource.set(false);
        assertTrue(compoundCondition.test());

        booleanCondition2.dataSource.set(false);
        assertFalse(compoundCondition.test());
    }

    @Test
    void testNumericCompoundCondition() {
        NumericCompoundCondition numericCompoundCondition = new NumericCompoundCondition(floatDataSource1);
        numericCompoundCondition.greaterThan(20).orLessThan(-20);
        assertFalse(numericCompoundCondition.test());

        floatDataSource1.set(30f);
        assertTrue(numericCompoundCondition.test());

        floatDataSource1.set(-30f);
        assertTrue(numericCompoundCondition.test());
    }
}
