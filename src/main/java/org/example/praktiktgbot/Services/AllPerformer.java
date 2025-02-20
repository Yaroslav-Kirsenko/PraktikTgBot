package org.example.praktiktgbot.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllPerformer {

    @Autowired
    private Player player;

    @Autowired
    private Singer singer;

    @Autowired
    private Teacher teacher;

    public String perform() {
        return player.perform() + " \n" + singer.perform() + " \n" + teacher.perform();
    }

    public void ShowAllPerformer(){
        System.out.println(player.perform());
        System.out.println(singer.perform());
        System.out.println(teacher.perform());
    }

}
