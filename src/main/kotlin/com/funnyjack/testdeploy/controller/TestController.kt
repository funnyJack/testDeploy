package com.funnyjack.testdeploy.controller

import com.funnyjack.testdeploy.model.TestCreationModel
import com.funnyjack.testdeploy.model.TestPatchModel
import com.funnyjack.testdeploy.model.TestViewModel
import com.funnyjack.testdeploy.model.toViewModel
import com.funnyjack.testdeploy.service.TestService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Transactional(rollbackFor = [Throwable::class])
@RestController
@RequestMapping("test")
class TestController(
        private val testService: TestService
) {
//    @PostMapping("search")
//    fun searchTests(
//            @RequestBody searchFilter: TestSearchFilter,
//        @RequestParam(required = false, defaultValue = "OR")
//        searchFilterCombineOperation: SearchFilterCombineOperation,
//        pageable: Pageable,
//    ): Page<TestViewModel> {
//        return testService.search(searchFilter.toSpecification(searchFilterCombineOperation), pageable)
//            .map { it.toViewModel() }
//    }

    @GetMapping("search")
    suspend fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sort: String
    ): Page<TestViewModel> {
        val pageable = PageRequest.of(page, size, Sort.by(sort))
        return testService.findAll(pageable).map { it.toViewModel() }
    }

    @PostMapping
    suspend fun createTest(
            @RequestBody creationModel: TestCreationModel
    ): TestViewModel {
        return testService.create(creationModel).toViewModel()
    }

    @GetMapping("{name}")
    suspend fun getTest(
            @PathVariable name: String,
    ): TestViewModel {
        return testService.get(name).toViewModel()
    }

    @PatchMapping("{name}")
    suspend fun modifyTest(
            @PathVariable name: String,
            @RequestBody patchModel: TestPatchModel
    ): TestViewModel {
        val test = testService.get(name)
        return testService.modify(test, patchModel).toViewModel()
    }

    @DeleteMapping("{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteTest(
            @PathVariable name: String
    ) {
        val test = testService.get(name)
        testService.delete(test)
    }
}
