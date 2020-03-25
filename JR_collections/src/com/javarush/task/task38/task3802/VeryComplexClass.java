package com.javarush.task.task38.task3802;

/* 
Проверяемые исключения (checked exception)
*/

import java.text.SimpleDateFormat;
import java.util.Date;

public class VeryComplexClass {
    public void veryComplexMethod() throws Exception {
        //напишите тут ваш код
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
        String line = "01.01.2010";

        Date date = dateFormat.parse(line);
    }

    public static void main(String[] args) {

    }
}
