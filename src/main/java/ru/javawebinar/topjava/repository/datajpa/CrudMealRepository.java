package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    int deleteByIdAndUserId(int id, int userId);

    Meal findMealsByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    List<Meal> findAllByUserIdAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc(int user, LocalDateTime start, LocalDateTime end);
}
