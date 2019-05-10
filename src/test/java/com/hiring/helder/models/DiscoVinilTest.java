package com.hiring.helder.models;


import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class DiscoVinilTest {
    @Test
    public void shouldReturnTrueSameObject() {
        assertPojoMethodsFor(DiscoVinil.class)
                .testing(Method.GETTER, Method.SETTER)
                .quickly().areWellImplemented();
    }
}