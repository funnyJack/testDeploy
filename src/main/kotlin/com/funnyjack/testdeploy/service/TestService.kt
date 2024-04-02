package com.funnyjack.testdeploy.service

import com.funnyjack.testdeploy.entity.Test
import com.funnyjack.testdeploy.entity.TestRepository
import com.funnyjack.testdeploy.exception.ResourceNotFoundException
import com.funnyjack.testdeploy.model.TestCreationModel
import com.funnyjack.testdeploy.model.TestPatchModel
import com.hiczp.spring.error.BadRequestException
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class TestService(
        private val testRepository: TestRepository
) {
    suspend fun get(name: String): Test = testRepository.findByName(name).awaitFirstOrElse {
        throw ResourceNotFoundException(Test::class)
    }


//    fun search(specification: Specification<Test>, pageable: Pageable) =
//        testRepository.findAll(specification, pageable)

    suspend fun create(creationModel: TestCreationModel): Test {
        ensureNameUnique(creationModel.name)
        return Test(
                name = creationModel.name,
                message = creationModel.message
        ).save().awaitSingle()
    }

    suspend fun modify(test: Test, patchModel: TestPatchModel): Test {
        patchModel.name?.let {
            ensureNameUnique(it)
        }
        return test.apply {
            patchModel.name?.also { name = it }
            patchModel.message?.also { message = it }
        }.save().awaitSingle()
    }

    suspend fun delete(test: Test): Void? = testRepository.delete(test).awaitSingleOrNull()

    private suspend fun ensureNameUnique(name: String) =
            testRepository.existsByName(name).awaitSingle().let {
                if (it) {
                    throw BadRequestException("Request name: $name already exists")
                }
            }

    private fun Test.save() = testRepository.save(this)
}
