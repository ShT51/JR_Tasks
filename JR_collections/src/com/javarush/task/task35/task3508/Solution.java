package com.javarush.task.task35.task3508;

import java.util.List;

/* 
extends vs super
*/
public abstract class Solution {
    // должен работать с одним и тем же типом;
    public abstract <T> void one(List<T> destination, List<T> source);

    // должен добавлять любых наследников типа T в список, умеющий хранить только тип T;
    public abstract <T> void two(List<T> destination, List<? extends T> source);

    // должен добавлять объекты типа T в любой список, параметризированный любым родительским классом;
    public abstract <T> void three(List<? super T> destination, List<T> source);

    // должен добавлять любых наследников типа T в список, параметризированный любым родительским классом;
    public abstract <T> void four(List<? super T> destination, List<? extends T> source);
}
