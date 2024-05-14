package com.funnyjack.testdeploy.entity

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Entity
class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(length = 128, nullable = false)
    var name: String = "",
    @ManyToMany
    val courses: MutableSet<Course> = mutableSetOf()
)

@Repository
interface StudentRepository : PagingAndSortingRepository<Student, Long>, CrudRepository<Student, Long>,
    JpaSpecificationExecutor<Student> {
    fun findByName(name: String): Student?
    fun findAllByNameIn(names: List<String>): List<Student>
    fun existsByName(name: String): Boolean
    fun deleteByName(name: String)
}
