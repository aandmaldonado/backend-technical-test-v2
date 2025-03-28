package com.tui.proof.controller;

import com.tui.proof.controller.dto.OrderRequestDto;
import com.tui.proof.controller.dto.OrderResponseDto;
import com.tui.proof.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for managing orders.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param orderDto the order data transfer object
     * @return the created order data transfer object
     */
    @Operation(summary = "Create a new order.",
            description = "Operation to create a new order.")
    @ApiResponse(responseCode = "200", description = "Successfully created order")
    @ApiResponse(responseCode = "400", description = "Client error occurred while creating order")
    @ApiResponse(responseCode = "500", description = "Internal server error occurred while creating order")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

    /**
     * Updates an existing order.
     *
     * @param id       the order ID
     * @param orderDto the order data transfer object
     * @return the updated order data transfer object
     */
    @Operation(summary = "Update an existing order.",
            description = "Operation to update an existing order.")
    @ApiResponse(responseCode = "200", description = "Successfully updated order")
    @ApiResponse(responseCode = "400", description = "Client error occurred while updating order")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @ApiResponse(responseCode = "500", description = "Internal server error occurred while updating order")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDto orderDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDto));
    }

    /**
     * Retrieves a list of orders based on a filter.
     *
     * @param filter the filter criteria
     * @return the list of order data transfer objects
     */
    @Operation(summary = "Retrieve a list of orders.",
            description = "Operation to retrieve a list of orders based on a filter.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved orders")
    @ApiResponse(responseCode = "400", description = "Client error occurred while retrieving orders")
    @ApiResponse(responseCode = "401", description = "Unauthorized access while retrieving orders")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @ApiResponse(responseCode = "500", description = "Internal server error occurred while retrieving orders")
    @GetMapping("/search")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@RequestParam String filter) {
        return ResponseEntity.ok(orderService.getOrders(filter));
    }
}
