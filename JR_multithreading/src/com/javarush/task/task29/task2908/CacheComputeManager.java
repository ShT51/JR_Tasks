package com.javarush.task.task29.task2908;

import java.util.concurrent.*;

/* Argument and Value are generic types*/
public class CacheComputeManager<Argument, Value> implements Computable<Argument, Value> {
    // поля для хранения кэшированных данных
    private final ConcurrentHashMap<Argument, Future<Value>> cache = new ConcurrentHashMap<>();
    private final Computable<Argument, Value> computable;

    // в конструктор передаем объект класса, у которо будем кэшировать результат выполнения метода compute
    public CacheComputeManager(Computable<Argument, Value> computable) {
        this.computable = computable;
    }

    // его метод compute вызывает нужный метод compute у переданного объекта
    @Override
    public Value compute(final Argument arg) throws InterruptedException {
        // 1. лезет в cache ищет по arg значение f
        Future<Value> f = cache.get(arg);
        if (f == null) {
            // если этого значения нет, то мы его должны вычислить в занести в кэш
            FutureTask<Value> ft = createFutureTaskForNewArgumentThatHasToComputeValue(arg);
            cache.putIfAbsent(arg, ft);
            f = ft;
            ft.run();
            System.out.print(arg + " will be cached  ");
        } else {
            System.out.print(arg + " taken from cache  ");
        }
        try {
            return f.get();
        } catch (CancellationException e) {
            cache.remove(arg, f);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
        return null;
    }

    public FutureTask<Value> createFutureTaskForNewArgumentThatHasToComputeValue(final Argument arg) {

        Callable<Value> task = new Callable<Value>() {
            @Override
            public Value call() throws Exception {
                return computable.compute(arg);
            }
        };

        return new FutureTask<>(task);
    }
}
