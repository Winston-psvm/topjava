package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer,Map<Integer, Meal>> mealsBase = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);


    {
//        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        Map<Integer, Meal> repository = mealsBase.computeIfAbsent(userId, integer -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        Map<Integer, Meal> repository = mealsBase.get(userId);
        return repository != null && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        Map<Integer, Meal> repository = mealsBase.get(userId);
        return repository == null ? null : repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        Map<Integer, Meal> repository = mealsBase.get(userId);
        return repository.values();
    }
}

