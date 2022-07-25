package com.rulebased848.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@org.springframework.boot.test.context.SpringBootTest
public class AppTest {
    @Autowired
    private com.rulebased848.puzzlehub.web.LoginController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }
}