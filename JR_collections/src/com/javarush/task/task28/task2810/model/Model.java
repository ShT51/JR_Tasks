package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.view.View;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class Model {
    View view;
    Provider[] providers;

    public Model(View view, Provider... providers) {
        if (view == null || providers == null || providers.length == 0) {
            throw new IllegalArgumentException("set wrong args to the Model's constructor");
        }
        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city) {
        List<Vacancy> result = new ArrayList<>();

        for (Provider provider : providers) {
            List<Vacancy> vacancyList = provider.getJavaVacancies(city);
            result.addAll(vacancyList);
        }
        view.update(result);
    }
}
