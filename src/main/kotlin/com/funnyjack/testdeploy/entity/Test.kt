package com.funnyjack.testdeploy.entity

//import jakarta.persistence.Column
//import jakarta.persistence.Entity
//import jakarta.persistence.GeneratedValue
//import jakarta.persistence.GenerationType
import kotlinx.coroutines.flow.Flow
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

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
interface TestRepository : CoroutineCrudRepository<Test, Long> {
    suspend fun findAllBy(pageable: Pageable): Flow<Test>
    suspend fun findByName(name: String): Test?
    suspend fun existsByName(name: String): Boolean
}
