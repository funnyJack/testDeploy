package com.funnyjack.testdeploy.model

import com.funnyjack.testdeploy.entity.Student
import com.funnyjack.testdeploy.jpaspecificationdsl.combineSpecification
import com.funnyjack.testdeploy.jpaspecificationdsl.distinct
import com.funnyjack.testdeploy.jpaspecificationdsl.like
import com.funnyjack.testdeploy.utils.SearchFilterCombineOperation
import com.funnyjack.testdeploy.utils.fullTrim
import org.springframework.data.jpa.domain.Specification

data class StudentSearchFilter(
    val nameLike: String? = null
) {
    fun toSpecification(searchFilterCombineOperation: SearchFilterCombineOperation): Specification<Student> {
        val nameLike = nameLike?.fullTrim()?.ifBlank { null }
        return combineSpecification(
            listOf(
                nameLike?.let { Student::name.like("%$it%") },
            ),
            searchFilterCombineOperation.toOperation()
        ).distinct()
    }
}

data class StudentCreationModel(
    val name: String,
    val course: String
)

data class StudentPatchModel(
    val name: String? = null,
    val course: String? = null
)

data class StudentViewModel(
    val name: String,
    val courseName: List<String>
)

fun Student.toViewModel() = StudentViewModel(
    name, courses.map { it.name }
)
