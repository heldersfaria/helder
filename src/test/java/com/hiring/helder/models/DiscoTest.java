package com.hiring.helder.models;


import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class DiscoTest {
    @Test
    public void shouldReturnTrueSameObject() {
        assertPojoMethodsFor(Disco.class)
                .testing(Method.GETTER, Method.SETTER)
                .quickly().areWellImplemented();
    }
}