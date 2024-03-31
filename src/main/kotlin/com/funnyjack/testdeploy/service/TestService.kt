package com.funnyjack.testdeploy.service

import com.funnyjack.testdeploy.entity.Test
import com.funnyjack.testdeploy.entity.TestRepository
import com.funnyjack.testdeploy.model.TestCreationModel
import com.funnyjack.testdeploy.model.TestPatchModel
import com.hiczp.spring.error.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.web.ErrorResponse
import reactor.core.publisher.Mono

@Service
class TestService(
    private val testRepository: TestRepository
) {
    fun get(name: String): Mono<Test> = testRepository.findById(name)

//    fun search(specification: Specification<Test>, pageable: Pageable) =
//        testRepository.findAll(specification, pageable)

    fun create(creationModel: TestCreationModel): Mono<Test> {
        ensureNameUnique(creationModel.name)
        return Test(
            name = creationModel.name,
            message = creationModel.message
        ).save()
    }

    fun modify(testMono: Mono<Test>, patchModel: TestPatchModel): Mono<Test> {
        patchModel.name?.let {
            ensureNameUnique(it)
        }
        return testMono.flatMap { test ->
            test.apply {
                patchModel.name?.also { name = it }
                patchModel.message?.also { message = it }
            }.save()
        }
    }

    fun delete(test: Mono<Test>) =
            test.flatMap { testRepository.delete(it) }

    private fun ensureNameUnique(name: String) {
        testRepository.existsById(name).map {
            if (it) {
                Mono.error<ErrorResponse>(BadRequestException("Request name already exists"))
            }
        }
        if (testRepository.existsById(name).block()!!) {
            throw BadRequestException("Request name already exists")
        }
    }

    private fun Test.save() = testRepository.save(this)
}
