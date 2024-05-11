package com.ecommerce.Service;

import com.ecommerce.Model.Orders;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    Orders findOrderByID(Long orderID);

    List<Orders> getAllOrders();
    Orders updateOrder(Orders orders);
    Orders createAnOrder(Long UserId , Double totalAmount);
}
