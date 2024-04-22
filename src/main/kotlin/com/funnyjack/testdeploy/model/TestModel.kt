package com.funnyjack.testdeploy.model

import com.funnyjack.testdeploy.entity.Test

//import com.funnyjack.testdeploy.jpaspecificationdsl.combineSpecification
//import com.funnyjack.testdeploy.jpaspecificationdsl.distinct
//import com.funnyjack.testdeploy.jpaspecificationdsl.like
//import com.funnyjack.testdeploy.utils.SearchFilterCombineOperation
//import com.funnyjack.testdeploy.utils.fullTrim
//import org.springframework.data.jpa.domain.Specification

//
//data class TestSearchFilter(
//    val nameLike: String? = null,
//    val messageLike: String? = null
//) {
//    fun toSpecification(searchFilterCombineOperation: SearchFilterCombineOperation): Specification<Test> {
//        val nameLike = nameLike?.fullTrim()?.ifBlank { null }
//        val messageLike = messageLike?.fullTrim()?.ifBlank { null }
//        return combineSpecification(
//            listOf(
//                nameLike?.let { Test::name.like("%$it%") },
//                messageLike?.let { Test::message.like("%$it%") },
//            ),
//            searchFilterCombineOperation.toOperation()
//        ).distinct()
//    }
//}

data class TestCreationModel(
    val name: String,
    val message: String
)

data class TestPatchModel(
    val name: String? = null,
    val message: String? = null
)

data class TestViewModel(
    val id: Long,
    val name: String,
    val message: String
)

fun Test.toViewModel() = TestViewModel(
    id!!, name, message
)
