package com.tui.proof.service;

import com.tui.proof.controller.dto.OrderRequestDto;
import com.tui.proof.controller.dto.OrderResponseDto;
import com.tui.proof.exception.OrderNotFoundException;
import com.tui.proof.exception.OrderUpdateNotAllowedException;
import com.tui.proof.model.Order;
import com.tui.proof.repository.OrderRepository;
import com.tui.proof.service.mapper.OrderMapper;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.owasp.encoder.Encode;
import org.springframework.stereotype.Service;

/**
 * Service class for managing orders.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private static final OrderMapper orderMapper = OrderMapper.INSTANCE;

    /**
     * Creates a new order.
     *
     * @param orderRequestDto the order request data transfer object
     * @return the order response data transfer object
     */
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        log.info(Encode.forJava(String.format("Creating order with %s pilotes", orderRequestDto.getPilotes())));
        Order model = orderMapper.toEntity(orderRequestDto);
        model.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Order orderCreated = orderRepository.save(model);
        log.info("Order created with number: {}", orderCreated.getNumber());
        return orderMapper.toDto(orderCreated);
    }

    /**
     * Updates an existing order.
     *
     * @param id              the ID of the order to update
     * @param orderRequestDto the order request data transfer object
     * @return the order response data transfer object
     */
    public OrderResponseDto updateOrder(Long id, OrderRequestDto orderRequestDto) {
        log.info("Updating order with ID: {}", id);
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    log.error("Existing order found with id: {}", existingOrder.getNumber());
                    if (existingOrder.getTimestamp().toLocalDateTime().isBefore(LocalDateTime.now().minusMinutes(5))) {
                        log.error("Order update not allowed for order ID: {}. "
                                + "Order cannot be updated after 5 minutes of creation", id);
                        throw new OrderUpdateNotAllowedException("Order cannot be updated after 5 minutes of creation");
                    }
                    Order orderToUpdate = orderMapper.toEntity(orderRequestDto);
                    orderToUpdate.setTimestamp(new Timestamp(System.currentTimeMillis()));
                    orderToUpdate.setNumber(existingOrder.getNumber());
                    Order orderUpdated = orderRepository.save(orderToUpdate);
                    log.info("Order entity updated with id: {}", orderUpdated.getNumber());
                    return orderMapper.toDto(orderUpdated);
                })
                .orElseThrow(() -> {
                    log.error("Order not found with ID: {}", id);
                    return new OrderNotFoundException("Order not found with ID: " + id);
                });

    }

    /**
     * Retrieves a list of orders based on a filter.
     *
     * @param filter the filter criteria
     * @return a list of order response data transfer objects
     */
    public List<OrderResponseDto> getOrders(String filter) {
        List<Order> orders = orderRepository
                .findByClientFirstNameContainingIgnoreCaseOrClientLastNameContainingIgnoreCase(filter, filter);
        if (orders.isEmpty()) {
            log.error(Encode.forJava(String.format("No orders found with filter: %s", filter)));
            throw new OrderNotFoundException("No orders found with filter: " + filter);
        }
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

}
