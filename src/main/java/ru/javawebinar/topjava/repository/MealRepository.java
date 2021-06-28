package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;


public interface MealRepository {
    Meal save(Meal meal);

    boolean deleteById(int id);

    List<Meal> getAll();

    Meal getById(int id);
}
