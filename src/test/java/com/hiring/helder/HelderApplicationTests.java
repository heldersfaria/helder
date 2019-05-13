package com.hiring.helder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelderApplicationTests {
    @Test
    public void contextLoads() {
        assertTrue(HelderApplication.class.isAnnotationPresent(SpringBootApplication.class));
    }
}
