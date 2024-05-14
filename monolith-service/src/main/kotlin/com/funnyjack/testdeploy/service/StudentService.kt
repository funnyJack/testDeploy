package com.funnyjack.testdeploy.service

import com.funnyjack.testdeploy.entity.Course
import com.funnyjack.testdeploy.entity.Student
import com.funnyjack.testdeploy.entity.StudentRepository
import com.funnyjack.testdeploy.exception.ResourceNotFoundException
import com.funnyjack.testdeploy.model.StudentCreationModel
import com.funnyjack.testdeploy.model.StudentPatchModel
import com.hiczp.spring.error.BadRequestException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {
    fun get(name: String): Student =
        studentRepository.findByName(name) ?: throw ResourceNotFoundException(Student::class)

    fun search(specification: Specification<Student>, pageable: Pageable): Page<Student> =
        studentRepository.findAll(specification, pageable)

    fun create(creationModel: StudentCreationModel): Student {
        ensureNameUnique(creationModel.name)
        return Student(
            name = creationModel.name
        ).save()
    }

    fun modify(student: Student, patchModel: StudentPatchModel): Student {
        patchModel.name?.let {
            ensureNameUnique(it)
        }
        return student.apply {
            patchModel.name?.also { name = it }
            patchModel.course?.also { courses.add(Course(name = it)) }
        }.save()
    }

    fun delete(student: Student) = studentRepository.delete(student)

    private fun ensureNameUnique(name: String) =
        studentRepository.existsByName(name).let {
            if (it) {
                throw BadRequestException("Request name: $name already exists")
            }
        }

    private fun Student.save() = studentRepository.save(this)
}
