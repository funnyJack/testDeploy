package com.funnyjack.testdeploy.controller

import com.funnyjack.testdeploy.model.CoursePatchModel
import com.funnyjack.testdeploy.model.CourseViewModel
import com.funnyjack.testdeploy.model.toViewModel
import com.funnyjack.testdeploy.service.CourseService
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Transactional(rollbackFor = [Throwable::class])
@RestController
@RequestMapping("courses")
class CourseController(
    private val courseService: CourseService
) {
    @PatchMapping("{name}")
    fun modifyTest(
        @PathVariable name: String,
        @RequestBody patchModel: CoursePatchModel
    ): CourseViewModel {
        val test = courseService.get(name)
        return courseService.modify(test!!, patchModel).toViewModel()
    }
}
