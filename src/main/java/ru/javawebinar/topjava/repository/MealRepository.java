package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepository implements MealRepositoryInterface {
    private final Map<Integer, Meal> mapDateBase = new ConcurrentHashMap<>();
    private final AtomicInteger ATOMIC_COUNTER = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.meals) {
            save(meal);
       }
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(ATOMIC_COUNTER.incrementAndGet());
            mapDateBase.put(meal.getId(),meal);
            return meal;
        }
        return mapDateBase.merge(meal.getId(), meal,((meal1, meal2) -> meal2));
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
