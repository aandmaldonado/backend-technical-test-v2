package com.tui.proof.service.mapper;

import com.tui.proof.controller.dto.OrderRequestDto;
import com.tui.proof.controller.dto.OrderResponseDto;
import com.tui.proof.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity Order and its DTO.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    static final Double PRICE_PER_PILOTE = 1.33;

    @Mapping(target = "deliveryAddress.id", ignore = true)
    @Mapping(target = "deliveryAddress.city", source = "deliveryAddress.city")
    @Mapping(target = "deliveryAddress.country", source = "deliveryAddress.country")
    @Mapping(target = "deliveryAddress.street", source = "deliveryAddress.street")
    @Mapping(target = "deliveryAddress.postcode", source = "deliveryAddress.postcode")
    @Mapping(target = "client.id", ignore = true)
    @Mapping(target = "client.firstName", source = "client.firstName")
    @Mapping(target = "client.lastName", source = "client.lastName")
    @Mapping(target = "client.telephone", source = "client.telephone")
    @Mapping(target = "client.orders", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "orderTotal", source = "pilotes", qualifiedByName = "calculateOrderTotal")
    @Mapping(target = "timestamp", ignore = true)
    Order toEntity(OrderRequestDto orderRequestDto);

    @Mapping(target = "deliveryAddress.city", source = "deliveryAddress.city")
    @Mapping(target = "deliveryAddress.country", source = "deliveryAddress.country")
    @Mapping(target = "deliveryAddress.street", source = "deliveryAddress.street")
    @Mapping(target = "deliveryAddress.postcode", source = "deliveryAddress.postcode")
    @Mapping(target = "client.firstName", source = "client.firstName")
    @Mapping(target = "client.lastName", source = "client.lastName")
    @Mapping(target = "client.telephone", source = "client.telephone")
    OrderResponseDto toDto(Order order);

    @Named("calculateOrderTotal")
    default Double calculateOrderTotal(Integer pilotes) {
        return pilotes * PRICE_PER_PILOTE;
    }
}
