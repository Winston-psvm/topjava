package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalTime;
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
//        MealsUtil.meals.forEach(this::save);
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

//    Фильтрацию по датам сделать в репозитории т.к.
//    из базы будем брать сразу отфильтрованные по дням записи.
//    Следите чтобы первый и последний день не были обрезаны,
//    иначе сумма калорий будет неверная.
    @Override
    public List<Meal> getAll(Integer userId) {
        log.info("getAll");
        Map<Integer, Meal> repository = mealsBase.get(userId);
        if (repository.values().isEmpty()) return Collections.emptyList();

        List<Meal> meals = new ArrayList<>(repository.values());
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return filterPredicate(meals, meal -> true);
    }

//    public static List<MealTo> getFilterTos(List<Meal> meals,
//                                            LocalTime startTime, LocalTime endTime) {
//        return filterPredicate(meals,
//                meal -> DateTimeUtil.isBetweenHalfOpen(Meal::getTime, startTime, endTime));
//    }

    private static List<Meal> filterPredicate(List<Meal> meals, Predicate<Meal> filter){
        return meals.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}

