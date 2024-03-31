package com.funnyjack.testdeploy.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Entity
class Test(
    @Id
    @jakarta.persistence.Id
    @Column(length = 128, nullable = false)
    var name: String = "",
    @Column(length = 128, nullable = false)
    var message: String = ""
)

@Repository
interface TestRepository : R2dbcRepository<Test, String>
