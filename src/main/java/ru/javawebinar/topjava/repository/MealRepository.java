package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepository implements MealRepositoryInt {
    private final Map<Integer, Meal> mapDateBase = new ConcurrentHashMap<>();
    private final AtomicInteger AtomicCount = new AtomicInteger();

    @Override
    public Meal addAndUpdate(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(AtomicCount.incrementAndGet());
            mapDateBase.put(meal.getId(),meal);
            return meal;
        }
        return mapDateBase.put(meal.getId(), meal);
    }

    @Override
    public void deleteById(int id) {
        mapDateBase.remove(id);

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
