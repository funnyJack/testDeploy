package com.funnyjack.testdeploy.service

import com.funnyjack.testdeploy.entity.Course
import com.funnyjack.testdeploy.entity.CourseRepository
import com.funnyjack.testdeploy.entity.Student
import com.funnyjack.testdeploy.entity.StudentRepository
import com.funnyjack.testdeploy.model.CoursePatchModel
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service


@Service
class CourseService(
    @Qualifier("entityManagerFactory")
    private val entityManager: EntityManager,
    private val courseRepository: CourseRepository,
    private val studentRepository: StudentRepository,
) {
    fun get(name: String) = courseRepository.findByName(name)

    fun modify(course: Course, coursePatchModel: CoursePatchModel): Course {
        course.apply {
            coursePatchModel.name?.also { name = it }
            coursePatchModel.studentNames?.also { names ->
                students.onEach { it.courses.remove(course) }
                studentRepository.findAllByNameIn(names).onEach { it.courses.add(course) }
            }
        }.save()

        entityManager.flush()
        entityManager.clear()
//        val a = 1/0
//        entityManager.unwrap(Session::class.java).persist(savedCourse)
//        entityManager.refresh(course)
        return get(course.name)!!
    }

    private fun Course.save() = courseRepository.save(this)
    private fun Iterable<Student>.saveAll() = studentRepository.saveAll(this)
}