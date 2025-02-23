//package org.example.praktiktgbot.Controller;
//
//import org.example.praktiktgbot.Services.Player;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/player")
//public class PlayerController {
//
//    @Autowired
//    private final  Player player;
//
//    public PlayerController(Player player) {
//        this.player = player;
//    }
//
//
//    @GetMapping("/perform")
//    public String perform() {
//        return player.perform();
//    }
//}
