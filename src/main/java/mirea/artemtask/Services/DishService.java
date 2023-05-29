package mirea.artemtask.Services;

import mirea.artemtask.Entities.Dish;
import mirea.artemtask.Repositories.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DishService {
    Dish getDishById(Long id);
    Dish createDish(Dish dish);
    Dish updateDish(Dish dish);
    void deleteDish(Long id);
    List<Dish> findAll();
}

