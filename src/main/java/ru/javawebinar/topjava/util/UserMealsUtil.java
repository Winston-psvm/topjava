package ru.javawebinar.topjava.util;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

//        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

        test(); // FIXME: try to execute
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
                                                            LocalTime endTime, int caloriesPerDay) {
        if (meals == null) return new ArrayList<>(); // FIXME: 1. 4to bi 4to? 2. may produce NullPointerException

        Map<LocalDate, Integer> mapCalories = new HashMap<>();
        for (UserMeal meal : meals) {
            mapCalories.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> mealExcess = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {   // FIXME: see ru.javawebinar.topjava.util.TimeUtil#isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime)  { //
                mealExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesPerDay < mapCalories.get(meal.getDateTime().toLocalDate())));
            }
        }
        return mealExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime,
                                                             LocalTime endTime, int caloriesPerDay) {
        if (meals == null) return new ArrayList<>(); // FIXME: 1. 4to bi 4to? 2. may produce NullPointerException

        Map<LocalDate, Integer> mapCalories = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        mapCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static void test() {
        try {
            filteredByCyclesTest();
            filteredByStreamsTest();
        } catch (InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void filteredByCyclesTest() throws InterruptedException {
        System.out.println("\n============= filteredByCyclesTest ============\n");

        filteredByCycles(
                Collections.emptyList(),
                LocalTime.of(7, 0),
                LocalTime.of(12, 0), 2000
        )
                .forEach(System.out::println);

        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println("OUTPUT:");
        mealsTo.forEach(System.out::println);

        final UserMealWithExcess expected = new UserMealWithExcess(
                meals.get(0).getDateTime(),
                meals.get(0).getDescription(),
                meals.get(0).getCalories(),
                false);

        assertEquals(mealsTo.get(0), expected);
    }

    public static void filteredByStreamsTest() throws InterruptedException {
        System.out.println("\n============= filteredByStreamsTest ============\n");

        filteredByStreams(
                Collections.emptyList(),
                LocalTime.of(7, 0),
                LocalTime.of(12, 0), 2000
        )
                .forEach(System.out::println);

        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println("OUTPUT:");
        mealsTo.forEach(System.out::println);

        final UserMealWithExcess expected = new UserMealWithExcess(
                meals.get(0).getDateTime(),
                meals.get(0).getDescription(),
                meals.get(0).getCalories(),
                false);

        assertEquals(mealsTo.get(0), expected);
    }

    private static void assertEquals(UserMealWithExcess actual, UserMealWithExcess expected) {
        System.out.printf("%nAssert equals:%nActual: %s%nExpected: %s%n%n", actual, expected);

        final Field[] fields = UserMealWithExcess.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                final Object actualValue = field.get(actual);
                final Object expectedValue = field.get(expected);
                if (!actualValue.equals(expectedValue)) {
                    throw new AssertionError(
                            String.format("%nActual: %1$s=%2$s%nExpected: %1$s=%3$s",
                                    field.getName(), actualValue.toString(), expectedValue.toString())
                    );
                }
            }
        } catch (IllegalAccessException | AssertionError e) {
            e.printStackTrace();
            System.err.flush();
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

}
