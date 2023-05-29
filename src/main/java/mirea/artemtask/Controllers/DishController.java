package mirea.artemtask.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mirea.artemtask.Controllers.dto.DishDTO;
import mirea.artemtask.Entities.Dish;
import mirea.artemtask.Entities.User;
import mirea.artemtask.Repositories.DishRepository;
import mirea.artemtask.Repositories.UserRepository;
import mirea.artemtask.Services.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/dishes")
public class DishController {
    private final DishService dishService;
    private final DishRepository dishRepository;
    UserRepository userRepository;
    AuthenticationController authenticationController;
    private final ObjectMapper objectMapper;

    public DishController(DishService dishService, DishRepository dishRepository,
                          UserRepository userRepository, AuthenticationController authenticationController,
                          ObjectMapper objectMapper) {
        this.dishService = dishService;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.authenticationController = authenticationController;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getDishById(@PathVariable int id) {
        try {
        Dish dish = dishRepository.getById(id);
        DishDTO dishDTO = convertToDTO(dish);
        return ResponseEntity.ok(dishDTO.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no dish with this id");
        }
    }
    @GetMapping("/menu")
    public ResponseEntity<String> getAllDishes() throws JsonProcessingException {
        if (dishService.findAll().size() == 0)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no dished in restaurant");
        return ResponseEntity.status(HttpStatus.FOUND).body(objectMapper.writeValueAsString(dishService.findAll()));
    }
    @PostMapping
    public ResponseEntity<String> createDish(@RequestHeader("Authorization") String authorizationHeader,
                                              @RequestBody DishDTO dishDTO) {
        User authenticatedUser = authenticationController.getUserByToken(authorizationHeader);
        try {
            System.out.println(authenticatedUser.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        }
        if (!Objects.equals(authenticatedUser.getRole(), "manager")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not manager user");
        }
        Dish dish = convertToEntity(dishDTO);
        Dish createdDish = dishService.createDish(dish);
        DishDTO createdDishDTO = convertToDTO(createdDish);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDishDTO.toString());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDish(@RequestHeader("Authorization") String authorizationHeader,
                                           @PathVariable int id) {
        User authenticatedUser = authenticationController.getUserByToken(authorizationHeader);
        try {
            System.out.println(authenticatedUser.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        }
        if (!Objects.equals(authenticatedUser.getRole(), "manager")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not manager user");
        }
        try {
        dishRepository.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no dish with this id");
        }
        return ResponseEntity.ok("deleted successfully");
    }

    // Helper method to convert Dish entity to DishDTO
    private DishDTO convertToDTO(Dish dish) {
        // Convert the fields of Dish to DishDTO
        DishDTO dishDTO = new DishDTO();
        dishDTO.setId(dish.getId());
        dishDTO.setName(dish.getName());
        dishDTO.setDescription(dish.getDescription());
        dishDTO.setPrice(dish.getPrice());
        dishDTO.setQuantity(dish.getQuantity());
        dishDTO.setAvailable(dish.isAvailable());
        return dishDTO;
    }
    private Dish convertToEntity(DishDTO dishDTO) {
        // Convert the fields of DishDTO to Dish
        Dish dish = new Dish();
        dish.setName(dishDTO.getName());
        dish.setDescription(dishDTO.getDescription());
        dish.setPrice(dishDTO.getPrice());
        dish.setQuantity(dishDTO.getQuantity());
        dish.setAvailable(dishDTO.isAvailable());
        dish.setCreatedAt(dishDTO.getCreatedAt());
        dish.setUpdatedAt(dishDTO.getUpdatedAt());
        return dish;
    }
}
