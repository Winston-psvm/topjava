package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UsersUtil {
    public static List<User> sortedList(Collection<User> users){
        return users.stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }
}
