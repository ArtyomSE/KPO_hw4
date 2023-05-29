package mirea.artemtask.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mirea.artemtask.Entities.Order;
import mirea.artemtask.Repositories.OrderRepository;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@ManagedResource //jconsole
public class OrderHandler{
    private final OrderRepository orderRepository;

    private Boolean isEmpty(final File file) {
        return (file.isDirectory() && (file.list().length > 0));
    }

    @ManagedOperation(description = "backup")
    @Scheduled(cron = "*/3 * * * *")
    public void doScheduledTask() throws IOException {
        List<Order> ordersWork = orderRepository.findAllByStatus("Work");
        for(Order order : ordersWork) {
            order.setStatus("Completed");
        }
        List<Order> ordersPending = orderRepository.findAllByStatus("Pending");
        for(Order order : ordersPending) {
            order.setStatus("Work");
        }
        File authorFile = new File("src/main/resources/data/GameAuthors.txt");
    }
}