package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer,Map<Integer, Meal>> mealsBase = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
      private static final Logger log = getLogger(InMemoryMealRepository.class);


    {
//     MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        log.info("save {}", meal);
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
        log.info("delete {}", id);
        Map<Integer, Meal> repository = mealsBase.get(userId);
        return repository != null && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        log.info("get {}", id);
        Map<Integer, Meal> repository = mealsBase.get(userId);
        return repository == null ? null : repository.get(id);
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        log.info("getAll");
        return filterPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getFilterMeal(LocalDate startDate, LocalDate endDate, Integer userId) {
        return filterPredicate(userId,meal ->
                DateTimeUtil.isBetweenHalfOpenDate(meal.getDateTime().toLocalDate(),
                        startDate, endDate));
    }

    private List<Meal> filterPredicate(Integer userId, Predicate<Meal> filter){
        Map<Integer, Meal> repository = mealsBase.get(userId);
        if (repository == null || repository.values().isEmpty()) return Collections.emptyList();

        return repository.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

