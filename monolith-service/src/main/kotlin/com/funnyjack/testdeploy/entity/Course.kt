package com.funnyjack.testdeploy.entity

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Entity
class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(length = 128, nullable = false)
    var name: String = "",
    @ManyToMany(mappedBy = "courses")
    val students: MutableSet<Student> = mutableSetOf()
)

@Repository
interface CourseRepository : PagingAndSortingRepository<Course, Long>, CrudRepository<Course, Long>,
    JpaSpecificationExecutor<Course> {
    fun findByName(name: String): Course?
}