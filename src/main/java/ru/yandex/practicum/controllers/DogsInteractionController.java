package ru.yandex.practicum.controllers;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.exception.IncorrectCountException;

import java.util.Map;

@RestController
@RequestMapping("/dogs")
public class DogsInteractionController {
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        happiness += 2;
        return Map.of("talk", "Гав!");
    }

    @GetMapping("/pet")
    public Map<String, String> pet(@RequestParam(required = false) final Integer count) {
        if (count == null) {
            throw new IncorrectCountException("Параметр count равен null.");
        } else if (count <= 0) {
            throw new IncorrectCountException("Параметр count имеет отрицательное значение.");
        }


        happiness += count;
        return Map.of("action", "Вильнул хвостом. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

    @GetMapping("/feed")
    public Map<String, Integer> feed() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод /feed ещё не реализован.");
    }

    // замените возвращаемый объект
    // добавьте код ответа
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(final IncorrectCountException e) {
        return new ResponseEntity<>(new ErrorResponse("error", "Ошибка с параметром count."), HttpStatus.BAD_REQUEST);
    }

    @Data
    static class ErrorResponse {
        private final String error;
        private final String description;
    }
}

// добавьте сюда класс ErrorResponse