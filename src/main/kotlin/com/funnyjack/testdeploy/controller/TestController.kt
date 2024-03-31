package com.funnyjack.testdeploy.controller

import com.funnyjack.testdeploy.model.TestCreationModel
import com.funnyjack.testdeploy.model.TestPatchModel
import com.funnyjack.testdeploy.model.TestViewModel
import com.funnyjack.testdeploy.model.toViewModel
import com.funnyjack.testdeploy.service.TestService
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


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

    @PostMapping
    fun createTest(
            @RequestBody creationModel: TestCreationModel
    ): Mono<TestViewModel> {
        return testService.create(creationModel).map { it.toViewModel() }
    }

    @GetMapping("{name}")
    fun getTest(
        @PathVariable name: String,
    ): Mono<TestViewModel> {
        return testService.get(name).map { it.toViewModel() }
    }

    @PatchMapping("{name}")
    fun modifyTest(
        @PathVariable name: String,
        @RequestBody patchModel: TestPatchModel
    ): Mono<TestViewModel> {
        val test = testService.get(name)
        return testService.modify(test, patchModel).map { it.toViewModel() }
    }

    @DeleteMapping("{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTest(
        @PathVariable name: String
    ): Mono<Void> {
        val test = testService.get(name)
        return testService.delete(test)
    }
}
