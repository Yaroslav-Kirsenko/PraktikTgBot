//package org.example.praktiktgbot.Controller;
//
//import org.example.praktiktgbot.Services.Singer;
//import org.example.praktiktgbot.Services.Teacher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/teacher")
//public class TeacherController {
//
//    @Autowired
//    private final Teacher teacher;
//
//    public TeacherController(Teacher teacher) {
//        this.teacher = teacher;
//    }
//
//
//    @GetMapping("/perform")
//    public String perform() {
//        return teacher.perform();
//    }
//}
