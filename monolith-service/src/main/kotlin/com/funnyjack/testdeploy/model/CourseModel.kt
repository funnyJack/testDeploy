package com.funnyjack.testdeploy.model

import com.funnyjack.testdeploy.entity.Course

data class CoursePatchModel(
    val name: String? = null,
    val studentNames: List<String>? = null,
)

data class CourseViewModel(
    val id: Long,
    val name: String,
    val studentsName: List<String>
)

fun Course.toViewModel() = CourseViewModel(
    id!!, name, students.map { it.name }
)
