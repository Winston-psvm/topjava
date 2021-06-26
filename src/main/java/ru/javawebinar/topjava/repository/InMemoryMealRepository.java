package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> mapDateBase = new ConcurrentHashMap<>();
    private final AtomicInteger atomicCounter = new AtomicInteger(0); // TODO learn more about naming convention

    {
        for (Meal meal : MealsUtil.meals) {
            save(meal);
       }
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(atomicCounter.incrementAndGet());
        }
        mapDateBase.merge(meal.getId(), meal,((meal1, meal2) -> meal2)); // TODO learn more about Map#merge
        return meal;
    }

    @Override
    public boolean deleteById(int id) {
       return mapDateBase.remove(id) != null;
    }

    @Override
    public Collection<Meal> getAll() {
        return mapDateBase.values();
    }

    @Override
    public Meal getById(int id) {
        return mapDateBase.get(id);
    }

}
