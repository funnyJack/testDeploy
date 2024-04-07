package com.funnyjack.test

import com.hkmci.web.bms2.test.initializer.MysqlContainerInitializer
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

//junit test base class must be an abstract class, can't be an interface
@SpringBootTest
@SpringJUnitConfig(initializers = [MysqlContainerInitializer::class])
@AutoConfigureWebTestClient
@ActiveProfiles("local", "test")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
abstract class TestDeploySpringBootTest
