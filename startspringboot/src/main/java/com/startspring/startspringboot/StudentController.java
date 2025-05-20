package com.startspring.startspringboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping("/students")
    public Student post(@RequestBody StudentDto dto) {
        var student = toStudents(dto);
        return studentRepository.save(student);
    }

    @PostMapping("/student")
    public StudentResponseDto posnt(@RequestBody StudentDto dto) {
        var student = toStudents(dto);
        var saveStudnet =  studentRepository.save(student);
        return toStudentResponseDto(saveStudnet);
    }

    private Student toStudents(StudentDto dto) {
        var student = new Student();
        student.setFirsrName(dto.firsrName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());

        var school = new School();
        school.setId(dto.schoolID());

        student.setSchool(school);

        return student;
    }

    private StudentResponseDto toStudentResponseDto(Student student) {
        return new StudentResponseDto(
                student.getFirsrName(),
                student.getLastName(),
                student.getEmail()
        );
    }

    @GetMapping("/students")
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{student-id}")
    public Student findStudentById(@PathVariable("student-id") Integer studentId) {
        return studentRepository.findById(studentId).orElse(new Student()); // Return a default student ("null") if not found
    }

    @GetMapping("/students/search/{student-name}")
    public List<Student> findStudentByName(@PathVariable("student-name") String studentId) {
        return studentRepository.findAllByFirsrNameContaining(studentId); // Return a default student ("null") if not found
    }

    @DeleteMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("student-id") Integer studentId) {
        studentRepository.deleteById(studentId);
    }
















    @GetMapping("/hello1")
    public String sayHello() {
        return "Hello World from my first application";
    }

    @PostMapping("/post")
    public String post(
            @RequestBody String message
    ) {
        return "Hello post World from my first application " + message  ;
    }

    @PostMapping("/post-order")
    public String post(@RequestBody Order order) {
        return "Hello post World from my first application : " + order.toString()  ;
    }

    @PostMapping("/post-order-record")
    public String postRecord(@RequestBody OrderRecord order) {
        return "Hello post World from my first application : " + order.toString()  ;
    }
    // @ResponseStatus(HttpStatus.FORBIDDEN)
    /*public String sayHello2() {
        return "Hello World from my first application";
    }*/

    // http://localhost:8080/hello/Gerard
    @GetMapping("/hello/{userName}")
    public String pathVariable(
            @PathVariable String userName
            // @PathVariable use to extract the value contain into the URL
    ) {
        return "Hello World from my first application " + userName ;
    }

    // http://localhost:8080/hello/Gerard
    @GetMapping("/hello")
    public String paramVariable(
            @RequestParam("user-name") String userName,
            @RequestParam("user-lastname") String lastName
            // @RequestParam use to extract the value contain into the parameter int he URL
    ) {
        return "Hello World from my first application " + userName + "  " + lastName;
    }
}
