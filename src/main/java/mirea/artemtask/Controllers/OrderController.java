package mirea.artemtask.Controllers;

import mirea.artemtask.Controllers.dto.CreateOrderDTO;
import mirea.artemtask.Controllers.dto.OrderDTO;
import mirea.artemtask.Entities.User;
import mirea.artemtask.Repositories.UserRepository;
import mirea.artemtask.Services.OrderService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mirea.artemtask.Entities.Order;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final AuthenticationController authenticationController;

    public OrderController(OrderService orderService, UserRepository userRepository, AuthenticationController authenticationController) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.authenticationController = authenticationController;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestBody CreateOrderDTO createOrderDTO, HttpServletRequest request) {
        User authenticatedUser = authenticationController.getUserByToken(authorizationHeader);
        try {
            Order order = new Order();
            order.setUser(authenticatedUser);
            order.setStatus(createOrderDTO.getStatus());
            order.setSpecialRequests(createOrderDTO.getSpecialRequests());

            // Save the order
            Order savedOrder = orderService.createOrder(order);

            return ResponseEntity.ok(savedOrder.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

    }
    @GetMapping("/{orderId}")
    public ResponseEntity<String> getOrderById(@RequestHeader("Authorization") String authorizationHeader,
                                               @PathVariable int orderId){
        User authenticatedUser = authenticationController.getUserByToken(authorizationHeader);
        try {
            System.out.println(authenticatedUser.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        }
        Order order = null;
        try {
            order = orderService.getOrderById(orderId);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no order with this id");
        }

        OrderDTO orderDTO = new OrderDTO(order.getId(), order.getStatus());
        return ResponseEntity.ok(orderDTO.toString());
    }
}
