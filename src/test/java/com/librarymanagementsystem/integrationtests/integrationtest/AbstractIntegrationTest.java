package com.librarymanagementsystem.integrationtests.integrationtest;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.time.Duration;

@Slf4j
public abstract class AbstractIntegrationTest {

    private static final String MYSQL_DOCKER_IMAGE = "mysql:latest";
    protected static final int CONTAINER_PORT = 3306;
    protected static final int LOCAL_PORT = 50000;
    protected static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>(MYSQL_DOCKER_IMAGE)
            .withStartupTimeout(Duration.ofMinutes(5))
            .withLogConsumer(new Slf4jLogConsumer(log))
            .withUsername("mysql")
            .withPassword("mysql")
            .withDatabaseName("test")
            .withExposedPorts(CONTAINER_PORT)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(LOCAL_PORT), new ExposedPort(CONTAINER_PORT)))
            ));

    @DynamicPropertySource
    static void setMysqlProperties(DynamicPropertyRegistry registry) {
        MYSQL_CONTAINER.start();
        String host = MYSQL_CONTAINER.getHost() + ":" + MYSQL_CONTAINER.getFirstMappedPort();
        log.info("MySQL container started at {}", host);
        registry.add("spring.datasource.url", () -> "jdbc:mysql://" + host + "/test");
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
    }
}
