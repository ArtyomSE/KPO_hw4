package mirea.artemtask.Repositories;

import mirea.artemtask.Entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Override
    List<Dish> findAll();
}
