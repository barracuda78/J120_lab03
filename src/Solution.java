package com.barracuda.tasks.politeh.labs.j120.lab03.src;


import com.barracuda.tasks.politeh.labs.j120.lab03.src.std.ConverterException;

public class Solution {
    public static void main(String[] args) {
        DataConverter dataConverter = new DataConverter();

// 1. код для тестирования метода toBinary();
//        dataConverter.toBinary("C:\\coding\\myJavaLearning\\src\\com\\barracuda\\tasks\\politeh\\labs\\j120\\lab03\\bin\\text.txt",
//                "C:\\coding\\myJavaLearning\\src\\com\\barracuda\\tasks\\politeh\\labs\\j120\\lab03\\bin\\text.bin",
//                "UTF-8");

// 2. код для тестирования метода getSum();
//        try {
//            System.out.println(dataConverter.getSum("C:\\coding\\myJavaLearning\\src\\com\\barracuda\\tasks\\politeh\\labs\\j120\\lab03\\bin\\sum.txt"));
//        } catch (ConverterException e) {
//            e.printStackTrace();
//        }


// 3. код для создания файла со значениями double БЕЗ ПРОБЕЛОВ!!! с префиксом DBL_  - для проверки метода toText();
                dataConverter.toBinaryTesting("C:\\coding\\myJavaLearning\\src\\com\\barracuda\\tasks\\politeh\\labs\\j120\\lab03\\bin\\toCreateDoublesOnly.txt",
                "C:\\coding\\myJavaLearning\\src\\com\\barracuda\\tasks\\politeh\\labs\\j120\\lab03\\bin\\for_to_text_doubles.txt",
                "UTF-8");

// 4. код для проверки метода  toText(); (как он работает с Double - используя файл for_to_text_doubles.txt
        dataConverter.toText("C:\\coding\\myJavaLearning\\src\\com\\barracuda\\tasks\\politeh\\labs\\j120\\lab03\\bin\\for_to_text_doubles.txt",
                "C:\\coding\\myJavaLearning\\src\\com\\barracuda\\tasks\\politeh\\labs\\j120\\lab03\\bin\\write_to_text_doubles.txt",
                "UTF-8");
    }
}
