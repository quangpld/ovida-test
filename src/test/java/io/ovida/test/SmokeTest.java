package io.ovida.test;

import io.ovida.test.controller.AdminController;
import io.ovida.test.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private AdminController adminController;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() throws Exception {
        assertThat(adminController).isNotNull();
        assertThat(userController).isNotNull();
    }
}
