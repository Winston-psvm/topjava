package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

public class MealsUtil {
    public static final List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static final int CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {}


    public static List<MealTo> filterMealList(List<Meal> meals, int caloriesPerDay) {
        Map<LocalDate, Integer> mapMeals = packMeal(meals);
        return meals.stream()
                .map(meal -> createTo(meal, mapMeals.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealTo> getFilteredTOs(List<Meal> meals, int caloriesPerDay) {
        List<MealTo> list = filterMealList(meals, caloriesPerDay);
        list.sort(Comparator.comparing(MealTo::getDateTime).reversed());
        return list;
    }


    public static Map<LocalDate, Integer> packMeal(List<Meal> meals) {
        return meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );
    }

    public static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

}
