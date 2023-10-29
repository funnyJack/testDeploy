package com.funnyjack.testdeploy.service

import com.funnyjack.testdeploy.entity.Test
import com.funnyjack.testdeploy.entity.TestRepository
import com.funnyjack.testdeploy.exception.ResourceNotFoundException
import com.funnyjack.testdeploy.model.TestCreationModel
import com.funnyjack.testdeploy.model.TestPatchModel
import com.hiczp.spring.error.BadRequestException
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class TestService(
    private val testRepository: TestRepository
) {
    fun get(name: String): Test = testRepository.findById(name).orElseThrow {
        ResourceNotFoundException(Test::class)
    }

    fun search(specification: Specification<Test>, pageable: Pageable) =
        testRepository.findAll(specification, pageable)

    fun create(creationModel: TestCreationModel): Test {
        ensureNameUnique(creationModel.name)
        return Test(
            name = creationModel.name,
            message = creationModel.message
        ).save()
    }

    fun modify(test: Test, patchModel: TestPatchModel): Test {
        patchModel.name?.let {
            ensureNameUnique(it)
        }
        return test.apply {
            patchModel.name?.also { name = it }
            patchModel.message?.also { message = it }
        }.save()
    }

    fun delete(name: String) =
        testRepository.delete(get(name))

    private fun ensureNameUnique(name: String) {
        if (testRepository.existsById(name)) {
            throw BadRequestException("Request name already exists")
        }
    }

    private fun Test.save() = testRepository.save(this)
}