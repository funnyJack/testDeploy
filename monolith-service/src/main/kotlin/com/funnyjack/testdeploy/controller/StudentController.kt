package com.funnyjack.testdeploy.controller

import com.funnyjack.testdeploy.model.*
import com.funnyjack.testdeploy.service.StudentService
import com.funnyjack.testdeploy.utils.SearchFilterCombineOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*


@Transactional(rollbackFor = [Throwable::class])
@RestController
@RequestMapping("student")
class StudentController(
    private val studentService: StudentService
) {
    @PostMapping("search")
    fun searchTests(
        @RequestBody searchFilter: StudentSearchFilter,
        @RequestParam(required = false, defaultValue = "OR")
        searchFilterCombineOperation: SearchFilterCombineOperation,
        pageable: Pageable,
    ): Page<StudentViewModel> {
        return studentService.search(searchFilter.toSpecification(searchFilterCombineOperation), pageable)
            .map { it.toViewModel() }
    }

    @PostMapping
    fun createTest(
        @RequestBody creationModel: StudentCreationModel
    ): StudentViewModel {
        return studentService.create(creationModel).toViewModel()
    }

    @GetMapping("{name}")
    fun getTest(
            @PathVariable name: String,
    ): StudentViewModel {
        return studentService.get(name).toViewModel()
    }

    @PatchMapping("{name}")
    fun modifyTest(
            @PathVariable name: String,
            @RequestBody patchModel: StudentPatchModel
    ): StudentViewModel {
        val test = studentService.get(name)
        return studentService.modify(test, patchModel).toViewModel()
    }

    @DeleteMapping("{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTest(
            @PathVariable name: String
    ) {
        val test = studentService.get(name)
        studentService.delete(test)
    }
}
