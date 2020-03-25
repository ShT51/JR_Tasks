package com.javarush.task.task38.task3804;

public class ExceptionFabric {

    public static <T> Throwable getException(T param) {
        if (param != null) {
            String paramClass = param.getClass().getSimpleName();
            String message = MessageHandler.getMessage(param);

            switch (paramClass) {
                case ("ApplicationExceptionMessage"):
                    return new Exception(message);
                case ("DatabaseExceptionMessage"):
                    return new RuntimeException(message);
                case ("UserExceptionMessage"):
                    return new Error(message);
            }
        }
        return new IllegalArgumentException();
    }
}
