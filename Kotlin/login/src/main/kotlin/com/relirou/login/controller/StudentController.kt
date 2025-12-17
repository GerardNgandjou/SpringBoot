package com.relirou.login.model

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.security.web.csrf.CsrfToken
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/auth")
class StudentController {

    val allStudents = ArrayList<Student>().apply {
        add(Student(1, "John Doe", 85.5))
        add(Student(2, "Rou", 80.0))
        add(Student(3, "Reli", 90.0))
    }

    @GetMapping("/")
    fun home(): String {
        return "API is working!"
    }

    @GetMapping("/student")
    fun getStudent(): Student {
        return allStudents[2]
    }

  @GetMapping("/csrf")
    fun getCsrf(request: HttpServletRequest): Map<String, String> {
        val token = request.getAttribute(CsrfToken::class.java.name) as CsrfToken
        return mapOf(
            "headerName" to token.headerName,
            "parameterName" to token.parameterName,
            "token" to token.token
        )
    }


    @PostMapping("/students")
    fun addStudent(@RequestBody student: Student): List<Student> {
        allStudents.add(student)
        return allStudents
    }
}
