package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestDate {
    public static final int MEAL_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 1;

    public static final Meal meal1 = new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0),
            "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0),
            "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0),
            "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0),
            "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0),
            "Завтрак", 1000);
    public static final Meal meal6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0),
            "Обед", 500);
    public static final Meal meal7 = new Meal(MEAL_ID + 6, LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0),
            "Ужин", 410);

    public static final List<Meal> meals = Arrays.asList(meal7, meal6, meal5, meal4, meal3, meal2, meal1);


    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.JULY, 1, 10, 0),
                "Новая еда", 300);
    }

    public static Meal getUpdate() {
        return new Meal(MEAL_ID, LocalDateTime.of(2021, Month.APRIL, 11, 10, 0),
                "Обновлённая еда", 122);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}







