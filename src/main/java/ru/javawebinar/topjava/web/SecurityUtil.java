package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    public static final int MOCK_USER_ID = 1;
    public static final int MOCK_ADMIN_ID = 2;
    public static int userId = 1;

    public static int authUserId() {
        return userId ;
    }

    public static void setAuthUserId(int userId) {
        SecurityUtil.userId = userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}