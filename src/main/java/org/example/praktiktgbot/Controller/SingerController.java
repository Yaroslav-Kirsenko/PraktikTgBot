//package org.example.praktiktgbot.Controller;
//
//
//import org.example.praktiktgbot.Services.Player;
//import org.example.praktiktgbot.Services.Singer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/singer")
//public class SingerController {
//
//    @Autowired
//    private final Singer singer;
//
//    public SingerController(Singer singer) {
//        this.singer = singer;
//    }
//
//
//    @GetMapping("/perform")
//    public String perform() {
//        return singer.perform();
//    }
//}
