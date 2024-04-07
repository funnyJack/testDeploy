package com.funnyjack.testdeploy.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Entity
class Test(
    @Id
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(length = 128, nullable = false)
    var name: String = "",
    @Column(length = 128, nullable = false)
    var message: String = ""
)

@Repository
interface TestRepository : PagingAndSortingRepository<Test, Long>, CrudRepository<Test, Long>,
    JpaSpecificationExecutor<Test> {
    fun findByName(name: String): Test?
    fun existsByName(name: String): Boolean
    fun deleteByName(name: String)
}
