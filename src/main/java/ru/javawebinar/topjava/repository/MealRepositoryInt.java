package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Collections;

                 // FIXME what is "Int"? Why?)
public interface MealRepositoryInt {
    Meal addAndUpdate(Meal meal); // FIXME it's better to just name it "save". 'addAndUpdate' implies add + update

    boolean deleteById(int id);

    Collection<Meal> getAll();

    Meal getById(int id);
}
