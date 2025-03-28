package com.tui.proof.service

import com.tui.proof.controller.dto.AddressDto
import com.tui.proof.controller.dto.ClientDto
import com.tui.proof.controller.dto.OrderRequestDto
import com.tui.proof.exception.OrderNotFoundException
import com.tui.proof.exception.OrderUpdateNotAllowedException
import com.tui.proof.model.Client
import com.tui.proof.model.Order
import com.tui.proof.repository.OrderRepository
import spock.lang.Specification

import java.sql.Timestamp
import java.time.LocalDateTime

/**
 * Unit tests for the OrderService class.
 */
class OrderServiceSpec extends Specification {

    def orderRepository = Mock(OrderRepository)
    def orderService = new OrderService(orderRepository)

    /**
     * Test case for creating a new order with the given details.
     */
    def "should create a new order with the given details"() {
        given: "an order request with the specified number of pilotes, delivery address, and client details"
        def orderRequestDto = OrderRequestDto.builder()
                .pilotes("5")
                .deliveryAddress(AddressDto.builder().build())
                .client(ClientDto.builder().build())
                .build()
        orderRepository.save(*_) >> Order.builder()
                .pilotes(5)
                .orderTotal(Integer.parseInt(orderRequestDto.getPilotes()) * 1.33)
                .build()

        when: "creating a new order"
        def result = orderService.createOrder(orderRequestDto)

        then: "the order is created with the specified details"
        result.pilotes == 5
        result.orderTotal == 5 * 1.33
    }

    /**
     * Test case for updating an existing order with the new details.
     */
    def "should update an existing order with the new details"() {
        given: "an order request with the specified number of pilotes, delivery address, and client details"
        def orderRequestDto = OrderRequestDto.builder()
                .pilotes("10")
                .deliveryAddress(AddressDto.builder().build())
                .client(ClientDto.builder().build())
                .build()
        def existingOrder = Order.builder()
                .number(1L)
                .pilotes(5)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build()
        def orderToUpdate = Order.builder()
                .number(1L)
                .pilotes(10)
                .orderTotal(10 * 1.33)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build()
        orderRepository.findById(1L) >> Optional.of(existingOrder)
        orderRepository.save(*_) >> orderToUpdate

        when: "updating an existing order"
        def result = orderService.updateOrder(1L, orderRequestDto)

        then: "the order is updated with the specified details"
        result.number == 1L
        result.pilotes == 10
        result.orderTotal == 10 * 1.33
    }

    /**
     * Test case for throwing an OrderUpdateNotAllowedException when updating an order after the allowed time.
     */
    def "should throw an OrderUpdateNotAllowedException when updating an order after the allowed time"() {
        given: "an order request with the specified number of pilotes, delivery address, and client details"
        def orderRequestDto = OrderRequestDto.builder()
                .pilotes("10")
                .deliveryAddress(AddressDto.builder().build())
                .client(ClientDto.builder().build())
                .build()
        def existingOrder = Order.builder()
                .number(1L)
                .pilotes(5)
                .timestamp(Timestamp.valueOf(LocalDateTime.now().minusMinutes(10)))
                .build()
        def orderToUpdate = Order.builder()
                .number(1L)
                .pilotes(10)
                .orderTotal(10 * 1.33)
                .timestamp(Timestamp.valueOf(LocalDateTime.now().minusMinutes(10)))
                .build()
        orderRepository.findById(1L) >> Optional.of(existingOrder)
        orderRepository.save(*_) >> orderToUpdate
        when: "updating an existing order"
        orderService.updateOrder(1L, orderRequestDto)

        then: "an OrderUpdateNotAllowedException is thrown"
        OrderUpdateNotAllowedException result = thrown()
        result !== null
        result.message == "Order cannot be updated after 5 minutes of creation"
    }

    /**
     * Test case for throwing an OrderNotFoundException when the order to update is not found.
     */
    def "should throw an OrderNotFoundException when the order to update is not found"() {
        given: "an order request with the specified number of pilotes, delivery address, and client details"
        def orderRequestDto = OrderRequestDto.builder()
                .pilotes("10")
                .deliveryAddress(AddressDto.builder().build())
                .client(ClientDto.builder().build())
                .build()

        orderRepository.findById(*_) >> Optional.empty()

        when: "updating an existing order"
        orderService.updateOrder(1L, orderRequestDto)

        then: "an OrderNotFoundException is thrown"
        OrderNotFoundException result = thrown()
        result !== null
        result.message == "Order not found with ID: " + 1L
    }

    /**
     * Test case for returning orders matching the given client name.
     */
    def "should return orders matching the given client name"() {
        given: "an order request with the specified number of pilotes, delivery address, and client details"
        def order1 = Order.builder()
                .number(1L)
                .client(Client.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .build())
                .build()

        def order2 = Order.builder()
                .number(2L)
                .client(Client.builder()
                        .firstName("Jane")
                        .lastName("Doe")
                        .build())
                .build()

        orderRepository.findByClientFirstNameContainingIgnoreCaseOrClientLastNameContainingIgnoreCase(_, _) >> [order1, order2]

        when: "getting orders by client name"
        def result = orderService.getOrders("Doe")

        then: "the orders are returned"
        result.size() == 2
        result[0].number == 1
        result[1].number == 2
    }

    /**
     * Test case for throwing an OrderNotFoundException when no orders match the given client name.
     */
    def "should throw an OrderNotFoundException when no orders match the given client name"() {
        given: "an order request with the specified number of pilotes, delivery address, and client details"

        orderRepository.findByClientFirstNameContainingIgnoreCaseOrClientLastNameContainingIgnoreCase(_, _) >> []

        when: "getting orders by client name"
        orderService.getOrders("Doe")

        then: "an OrderNotFoundException is thrown"
        OrderNotFoundException result = thrown()
        result !== null
        result.message == "No orders found with filter: Doe"
    }
}
