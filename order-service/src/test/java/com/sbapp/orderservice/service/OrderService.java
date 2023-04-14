package com.sbapp.orderservice.service;

import com.sbapp.orderservice.dto.OrderLineItemsDto;
import com.sbapp.orderservice.dto.OrderRequest;
import com.sbapp.orderservice.model.Order;
import com.sbapp.orderservice.model.OrderLineItems;
import com.sbapp.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    //Injecting order repository into order service
    private final OrderRepository orderRepository;

    //1. Receives order request from the client to the controller
    // 2. Controller is passing the order request to the order service
    // 3. Inside order service we are mapping order request to order object
    // 4. Finally saving the order into order repository.
    // 5. Call the order service from order controller classs.
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto)) //method reference this::mapToDto
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
