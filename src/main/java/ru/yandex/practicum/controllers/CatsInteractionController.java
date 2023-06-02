package ru.yandex.practicum.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exception.IncorrectCountException;

import java.util.Map;

@RestController
@RequestMapping("/cats")
public class CatsInteractionController {
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        happiness++;
        return Map.of("talk", "Мяу");
    }

    @GetMapping("/pet")
    public Map<String, String> pet(@RequestParam(required = false) final Integer count) {
        if (count == null) {
            throw new IncorrectCountException("Параметр count равен null.");
        } else if (count <= 0) {
            throw new IncorrectCountException("Передан отрицательный параметр count.");
        }
        happiness += count;
        return Map.of("talk", "Муррр. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

    @ExceptionHandler
// отлавливаем исключение IllegalArgumentException
    public Map<String, String> handleIncorrectCount(final IncorrectCountException e) {
        // возвращаем сообщение об ошибке
        return Map.of("error", "Ошибка с параметром count.",
                "errorMessage", e.getMessage()
        );
    }
}