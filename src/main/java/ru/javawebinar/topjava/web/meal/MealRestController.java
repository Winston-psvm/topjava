package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Integer mokUserId = SecurityUtil.authUserId(); // FIXME typo
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(mokUserId), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, mokUserId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(mokUserId, meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, mokUserId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(mokUserId, meal);
    }

    public List<MealTo> getFilterMealTos(LocalTime startTime, LocalTime endTime,
                                         LocalDate startDate, LocalDate endDate){
        log.info("getFilterMealTos");
        return MealsUtil.getFilteredTos(service.getFilterMeal(startDate, endDate, mokUserId),
                SecurityUtil.authUserCaloriesPerDay(),startTime,endTime);
        }

}