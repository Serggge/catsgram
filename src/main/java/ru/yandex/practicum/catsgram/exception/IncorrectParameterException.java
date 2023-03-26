package ru.yandex.practicum.catsgram.exception;

public class IncorrectParameterException extends RuntimeException {

    private final String parametr;

    public IncorrectParameterException(String parametr) {
        this.parametr = parametr;
    }

    public String getParametr() {
        return parametr;
    }

}
