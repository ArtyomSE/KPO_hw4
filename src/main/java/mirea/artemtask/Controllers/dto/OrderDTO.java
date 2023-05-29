package mirea.artemtask.Controllers.dto;

public class OrderDTO {
    private int orderId;
    private String status;

    public OrderDTO(int orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", status='" + status + '\'' +
                '}';
    }

    // Getters and Setters
}
