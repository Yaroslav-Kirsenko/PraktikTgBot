package org.example.praktiktgbot.Services;

import org.example.praktiktgbot.Model.LessonSchedule;
import org.example.praktiktgbot.Repo.LessonScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonScheduleService {
    private final LessonScheduleRepository lessonScheduleRepository;

    public LessonScheduleService(LessonScheduleRepository lessonScheduleRepository) {
        this.lessonScheduleRepository = lessonScheduleRepository;
    }

    public String getScheduleForDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            return "Неверный формат даты. Используйте yyyy-MM-dd.";
        }

        List<LessonSchedule> lessons = lessonScheduleRepository.findAll()
                .stream()
                .filter(lesson -> lesson.getLocalDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());

        if (lessons.isEmpty()) {
            return "На выбранную дату нет расписания.";
        }

        StringBuilder response = new StringBuilder("Расписание на " + date + ":\n");
        for (LessonSchedule lesson : lessons) {
            response.append("- ").append(lesson.getNameLesson())
                    .append(" в ")
                    .append(lesson.getLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                    .append("\n");
        }
        return response.toString();
    }
}
