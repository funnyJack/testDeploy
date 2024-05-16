package com.funnyjack.testdeploy

import com.funnyjack.testdeploy.model.TestCreationModel
import com.funnyjack.testdeploy.model.TestViewModel
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TestDeployApplicationTests @Autowired constructor(
    private val restTemplate: TestRestTemplate,
) {

    companion object {
        @Container
        @ServiceConnection
        val mysqlContainer = MySQLContainer("mysql:8").withDatabaseName("testDeploy")
    }

    @Test
    fun create() {
        val createUrl = "/test"
        val createModel = HttpEntity(TestCreationModel(name = "test", message = "testCreate"))
//      val testViewModel =  restTemplate.postForObject(createUrl,createModel,TestViewModel::class.java)!!
        val testViewModel = restTemplate.exchange(
            createUrl,
            HttpMethod.POST,
            createModel,
            TestViewModel::class.java
        ).body!!
        assertEquals(testViewModel.name, "test")
        assertEquals(testViewModel.message, "testCreate")
    }
}
