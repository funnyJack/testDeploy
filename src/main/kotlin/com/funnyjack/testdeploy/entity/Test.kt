package com.funnyjack.testdeploy.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Entity
class Test(
    @Id
    var name: String = "",
    @Column(length = 128, nullable = false)
    var message: String = ""
)

@Repository
interface TestRepository : CrudRepository<Test, String>, PagingAndSortingRepository<Test, String>,
    JpaSpecificationExecutor<Test>