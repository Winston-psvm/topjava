package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
      private static final Logger log = getLogger(MealServlet.class);

      private final MealRepository repository = new MealRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(req.getParameter("id"));

        Meal meal = new Meal(id, LocalDateTime.parse(req.getParameter("gateTime")),
                                req.getParameter("description"),
                                Integer.parseInt(req.getParameter("calories")));
        repository.addAndUpdate(meal);
        resp.sendRedirect("meals");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("redirect to meals");
//        req.setAttribute("meals", MealsUtil.sorted(MealsUtil.meals,MealsUtil.CALORIES_PER_DAY));
//        req.getRequestDispatcher("/meals.jsp").forward(req,resp);

        String action = req.getParameter("action");

        switch (action == null ? "getAll" : action) {
            case "create":
            case "update":
                Meal meal;
                if ("create".equals(action)) {
                    meal = new Meal(LocalDateTime.now(), "", 1);
                } else meal = repository.getById(Integer.parseInt(req.getParameter("id")));

                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/mealCreate").forward(req, resp);
                break;

            case "delete":
                int id = Integer.parseInt(req.getParameter("id"));
                repository.deleteById(id);
                resp.sendRedirect("meals");
                break;

            case "getAll":
            default:
                req.setAttribute("meals", MealsUtil.sorted(MealsUtil.meals, MealsUtil.CALORIES_PER_DAY));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }





    }

}
