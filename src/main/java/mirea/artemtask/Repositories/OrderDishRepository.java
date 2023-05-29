package mirea.artemtask.Repositories;

import mirea.artemtask.Entities.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {
    // Add any custom query methods if needed
}
