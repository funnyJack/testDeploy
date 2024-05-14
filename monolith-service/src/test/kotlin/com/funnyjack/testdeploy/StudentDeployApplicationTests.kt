package com.funnyjack.testdeploy

import com.funnyjack.test.TestDeploySpringBootTest
import com.funnyjack.testdeploy.model.StudentCreationModel
import com.funnyjack.testdeploy.model.StudentViewModel
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod

import kotlin.test.assertEquals

class StudentDeployApplicationTests @Autowired constructor(
    private val restTemplate: TestRestTemplate,
) : TestDeploySpringBootTest() {

    @Test
    fun create() {
        val createUrl = "/test"
        val createModel = HttpEntity(StudentCreationModel(name = "pl", course = "math"))
//      val testViewModel =  restTemplate.postForObject(createUrl,createModel,TestViewModel::class.java)!!
        val studentViewModel = restTemplate.exchange(
            createUrl,
            HttpMethod.POST,
            createModel,
            StudentViewModel::class.java
        ).body!!
        assertEquals(studentViewModel.name, "test")
    }
}
