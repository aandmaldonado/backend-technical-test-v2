package com.tui.proof.controller

import com.tui.proof.controller.dto.OrderRequestDto
import com.tui.proof.controller.dto.OrderResponseDto
import com.tui.proof.service.OrderService
import spock.lang.Specification

/**
 * Unit tests for the OrderController class.
 */
class OrderControllerSpec extends Specification {
    def orderService = Mock(OrderService.class)
    def orderController = new OrderController(orderService)

    /**
     * Test case for creating an order successfully.
     */
    def "should create an order successfully"() {
        given: "an order request dto and an order response dto"
        def orderRequestDto = new OrderRequestDto()
        def orderResponseDto = new OrderResponseDto()
        orderService.createOrder(orderRequestDto) >> orderResponseDto

        when: "creating an order"
        def response = orderController.createOrder(orderRequestDto)

        then: "the order is created successfully"
        response.statusCodeValue == 200
        response.body == orderResponseDto
    }

    /**
     * Test case for updating an order successfully.
     */
    def "should update an order successfully"() {
        given: "an order id, an order request dto, and an order response dto"
        def orderRequestDto = new OrderRequestDto()
        def orderResponseDto = new OrderResponseDto()
        def orderId = 1L
        orderService.updateOrder(orderId, orderRequestDto) >> orderResponseDto

        when: "updating an order"
        def response = orderController.updateOrder(orderId, orderRequestDto)

        then: "the order is updated successfully"
        response.statusCodeValue == 200
        response.body == orderResponseDto
    }

    /**
     * Test case for getting orders successfully.
     */
    def "should get orders successfully"() {
        given: "a filter and a list of order response dtos"
        def filter = "someFilter"
        def orderResponseDtoList = [new OrderResponseDto()]
        orderService.getOrders(filter) >> orderResponseDtoList

        when: "getting orders"
        def response = orderController.getOrders(filter)

        then: "the orders are retrieved successfully"
        response.statusCodeValue == 200
        response.body == orderResponseDtoList
    }
}
