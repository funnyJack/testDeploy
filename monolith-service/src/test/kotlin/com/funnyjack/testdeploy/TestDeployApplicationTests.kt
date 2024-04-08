package com.funnyjack.testdeploy

import com.funnyjack.test.TestDeploySpringBootTest
import com.funnyjack.testdeploy.model.TestCreationModel
import com.funnyjack.testdeploy.model.TestViewModel
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod

import kotlin.test.assertEquals

class TestDeployApplicationTests @Autowired constructor(
    private val restTemplate: TestRestTemplate,
) : TestDeploySpringBootTest() {

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
