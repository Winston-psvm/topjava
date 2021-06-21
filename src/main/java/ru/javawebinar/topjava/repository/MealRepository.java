package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepository implements MealRepositoryInt {
    private final Map<Integer, Meal> mapDateBase = new ConcurrentHashMap<>();
    private final AtomicInteger AtomicCount = new AtomicInteger(0); // FIXME naming convention violation

    {
        for (Meal meal : MealsUtil.meals) {
            addAndUpdate(meal);
       }
    }

    @Override
    public Meal addAndUpdate(Meal meal) {   // FIXME look at Map.merge()
        if (meal.getId() == null) {
            meal.setId(AtomicCount.incrementAndGet());
            mapDateBase.put(meal.getId(),meal);
            return meal;
        }
        return mapDateBase.put(meal.getId(), meal);
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
