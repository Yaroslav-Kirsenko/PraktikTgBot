//package org.example.praktiktgbot.Controller;
//
//
//import org.example.praktiktgbot.Services.AllPerformer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.beans.ConstructorProperties;
//
//@RestController
//@RequestMapping("/all")
//public class AllPerformerController {
//
//    @Autowired
//    private final AllPerformer allPerformer;
//
//
//    public AllPerformerController(AllPerformer allPerformer) {
//        this.allPerformer = allPerformer;
//    }
//
//    @GetMapping("/perform")
//    public String perform() {
//        return allPerformer.perform();
//    }
//
//}
