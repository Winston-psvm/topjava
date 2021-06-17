package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Collections;


public interface MealRepositoryInt {
    Meal addAndUpdate(Meal meal);

    void deleteById(int id);

    Collection<Meal> getAll();

    Meal getById(int id);
}
