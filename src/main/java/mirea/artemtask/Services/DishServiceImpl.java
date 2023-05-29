package mirea.artemtask.Services;

import mirea.artemtask.Entities.Dish;
import mirea.artemtask.Repositories.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Dish getDishById(Long id) {
        return dishRepository.getById(Math.toIntExact(id));
    }

    @Override
    public Dish createDish(Dish dish) {
        // Implement the creation of a new dish
        return dishRepository.save(dish);
    }

    @Override
    public Dish updateDish(Dish dish) {
        // Implement the update of an existing dish
        return dishRepository.save(dish);
    }

    @Override
    public void deleteDish(Long id) {
        dishRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }


}
