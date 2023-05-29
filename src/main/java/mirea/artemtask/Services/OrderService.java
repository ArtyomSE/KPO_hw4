package mirea.artemtask.Services;

import mirea.artemtask.Entities.Dish;
import mirea.artemtask.Entities.Order;
import mirea.artemtask.Entities.OrderDish;
import mirea.artemtask.Entities.User;
import mirea.artemtask.Repositories.DishRepository;
import mirea.artemtask.Repositories.OrderDishRepository;
import mirea.artemtask.Repositories.OrderRepository;
import mirea.artemtask.Repositories.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        // Set the created_at and updated_at timestamps
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Save the order in the database
        return orderRepository.save(order);
    }
    public Order getOrderById(int orderId) throws ChangeSetPersister.NotFoundException {
        // Retrieve the order by its ID from the database
        return orderRepository.findById(Math.toIntExact(orderId))
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }
}
