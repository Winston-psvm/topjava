package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> mapDateBase = new ConcurrentHashMap<>();

    private final AtomicInteger atomicCounter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.meals) {
            save(meal);
        }
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(atomicCounter.incrementAndGet());
            mapDateBase.put(meal.getId(), meal);
            return meal;
        }
        Meal mealSearch = mapDateBase.get(meal.getId());
        return mapDateBase.merge(meal.getId(),mealSearch,((a,b) -> b = meal));
    }

    @Override
    public boolean deleteById(int id) {
        return mapDateBase.remove(id) != null;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mapDateBase.values());
    }

    @Override
    public Meal getById(int id) {
        return mapDateBase.get(id);
    }

}
