package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
      private static final Logger log = getLogger(MealServlet.class);
      private MealRepository repository;

    @Override
    public void init() throws ServletException {
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.info("POST: {}", req);

        String id = req.getParameter("id");

        Meal meal = new Meal(id == null || id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                                req.getParameter("description"),
                                Integer.parseInt(req.getParameter("calories")));
        repository.save(meal);
        log.debug("Send redirect to /meals");

        resp.sendRedirect("meals");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("GET: {}", req);

        String action = req.getParameter("action");

        switch (action == null ? "getAll" : action) {
            case "create":
            case "update":
                MealTo mealTo;
                if ("create".equals(action)) {
                    mealTo = new MealTo(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0,false);
                } else {
                    Meal meal =
                            repository.getById(Integer.parseInt(Objects.requireNonNull(req.getParameter("id"))));
                    mealTo = MealsUtil.createTo(meal,false);
                    // FIXME if not found?
                }
                log.debug("Forward to /mealCreate.jsp");

                req.setAttribute("meal", mealTo);
                req.getRequestDispatcher("/mealCreate.jsp").forward(req, resp);
                break;

            case "delete":
                log.info("delete");
                int id = Integer.parseInt(Objects.requireNonNull(req.getParameter("id")));
                repository.deleteById(id); // FIXME not deleted?
                resp.sendRedirect("meals");
                break;

            case "getAll":
            default:
                req.setAttribute("meals", MealsUtil.getFilteredTOs(repository.getAll(), MealsUtil.CALORIES_PER_DAY));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }
    }
}
