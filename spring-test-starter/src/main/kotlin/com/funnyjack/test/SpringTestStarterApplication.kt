package com.funnyjack.test

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

//junit test base class must be an abstract class, can't be an interface
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringJUnitConfig(initializers = [MysqlContainerInitializer::class])
@ActiveProfiles("local", "test")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
abstract class TestDeploySpringBootTest
