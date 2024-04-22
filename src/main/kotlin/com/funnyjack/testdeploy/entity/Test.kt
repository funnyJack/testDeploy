package com.funnyjack.testdeploy.entity

//import jakarta.persistence.Column
//import jakarta.persistence.Entity
//import jakarta.persistence.GeneratedValue
//import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

//@Entity
class Test(
    @Id
//    @jakarta.persistence.Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
//    @Column(length = 128, nullable = false)
    var name: String = "",
//    @Column(length = 128, nullable = false)
    var message: String = ""
)

@Repository
interface TestRepository : R2dbcRepository<Test, String> {
    fun findByName(name: String): Mono<Test>
    fun existsByName(name: String): Mono<Boolean>
}
