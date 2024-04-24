package com.funnyjack.testdeploy.service

import com.funnyjack.testdeploy.entity.Test
import com.funnyjack.testdeploy.entity.TestRepository
import com.funnyjack.testdeploy.exception.ResourceNotFoundException
import com.funnyjack.testdeploy.model.TestCreationModel
import com.funnyjack.testdeploy.model.TestPatchModel
import com.hiczp.spring.error.BadRequestException
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TestService(
        private val testRepository: TestRepository
) {
    suspend fun get(name: String): Test =
        testRepository.findByName(name) ?: throw ResourceNotFoundException(Test::class)

    suspend fun findAll(pageable: Pageable): Page<Test> =
        PageImpl(testRepository.findAllBy(pageable).toList(), pageable, testRepository.count())

//    fun search(specification: Specification<Test>, pageable: Pageable) =
//        testRepository.findAll(specification, pageable)

    suspend fun create(creationModel: TestCreationModel): Test {
        ensureNameUnique(creationModel.name)
        return Test(
                name = creationModel.name,
                message = creationModel.message
        ).save()
    }

    suspend fun modify(test: Test, patchModel: TestPatchModel): Test {
        patchModel.name?.let {
            ensureNameUnique(it)
        }
        return test.apply {
            patchModel.name?.also { name = it }
            patchModel.message?.also { message = it }
        }.save()
    }

    suspend fun delete(test: Test) = testRepository.delete(test)

    private suspend fun ensureNameUnique(name: String) =
        testRepository.existsByName(name).let {
                if (it) {
                    throw BadRequestException("Request name: $name already exists")
                }
            }

    private suspend fun Test.save() = testRepository.save(this)
}
