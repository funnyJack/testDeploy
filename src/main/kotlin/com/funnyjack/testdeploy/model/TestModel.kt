package com.funnyjack.testdeploy.model

import com.funnyjack.testdeploy.entity.Test
import com.funnyjack.testdeploy.jpaspecificationdsl.combineSpecification
import com.funnyjack.testdeploy.jpaspecificationdsl.distinct
import com.funnyjack.testdeploy.jpaspecificationdsl.like
import com.funnyjack.testdeploy.utils.SearchFilterCombineOperation
import com.funnyjack.testdeploy.utils.fullTrim
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.springframework.data.jpa.domain.Specification

//import javax.validation.constraints.NotBlank
//import org.hibernate.validator.constraints.Length
//
data class TestSearchFilter(
    val nameLike: String? = null,
    val messageLike: String? = null
) {
    fun toSpecification(searchFilterCombineOperation: SearchFilterCombineOperation): Specification<Test> {
        val nameLike = nameLike?.fullTrim()?.ifBlank { null }
        val messageLike = messageLike?.fullTrim()?.ifBlank { null }
        return combineSpecification(
            listOf(
                nameLike?.let { Test::name.like("%$it%") },
                messageLike?.let { Test::message.like("%$it%") },
            ),
            searchFilterCombineOperation.toOperation()
        ).distinct()
    }
}

data class TestCreationModel(
    @field:Length(max = 128)
    @field:NotBlank
    val name: String,
    @field:Length(max = 128)
    @field:NotBlank
    val message: String
)

data class TestPatchModel(
    @field:Length(max = 128)
    val name: String? = null,
    @field:Length(max = 128)
    val message: String? = null
)

data class TestViewModel(
    val name: String,
    val message: String
)

fun Test.toViewModel() = TestViewModel(
    name, message
)