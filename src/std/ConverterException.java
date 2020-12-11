package com.barracuda.tasks.politeh.labs.j120.lab03.src.std;

/**
 * Класс представляет исключения, возникающие при конвертации
 * данных из одного типа в другой.
 * @author (C)Y.D.Zakovryashin, 19.01.2019
 */
public class ConverterException extends Exception {
    /** Конструктор по умолчанию. */
    public ConverterException() {
    }
    /** Конструктор с параметром, описывающим причину
     исключения.
     * @param string описание причины исключения.
     */
    public ConverterException(String string) {
        super(string);
    }
}