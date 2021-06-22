package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepository implements MealRepositoryInt {
    private final Map<Integer, Meal> MAP_DATE_BASE = new ConcurrentHashMap<>();
    private final AtomicInteger ATOMIC_COUNTER = new AtomicInteger(0); // FIXME naming convention violation

    {
        for (Meal meal : MealsUtil.meals) {
            addAndUpdate(meal);
       }
    }

    @Override
    public Meal addAndUpdate(Meal meal) {   // FIXME look at Map.merge()
        if (meal.getId() == null) {
            meal.setId(ATOMIC_COUNTER.incrementAndGet());
            MAP_DATE_BASE.put(meal.getId(),meal);
            return meal;
        }
        return MAP_DATE_BASE.put(meal.getId(), meal);
    }

    @Override
    public boolean deleteById(int id) {
       return MAP_DATE_BASE.remove(id) != null;

    }

    @Override
    public Collection<Meal> getAll() {
        return MAP_DATE_BASE.values();
    }

    @Override
    public Meal getById(int id) {
        return MAP_DATE_BASE.get(id);
    }

}
