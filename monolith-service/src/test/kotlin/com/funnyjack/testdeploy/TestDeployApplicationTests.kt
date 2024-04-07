package com.funnyjack.testdeploy

import com.funnyjack.test.TestDeploySpringBootTest
import com.funnyjack.testdeploy.model.TestCreationModel
import com.funnyjack.testdeploy.model.TestViewModel
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals


class TestDeployApplicationTests @Autowired constructor(
    private val webTestClient: WebTestClient,
) : TestDeploySpringBootTest() {
    @Test
    fun create() {
        val testViewModel = webTestClient.post().uri("/test")
            .bodyValue(TestCreationModel(name = "test", message = "testCreate"))
            .exchange().expectStatus().isOk
            .expectBody(TestViewModel::class.java)
            .returnResult().responseBody!!
        assertEquals(testViewModel.name, "test")
        assertEquals(testViewModel.name, "testCreate")
    }
}
