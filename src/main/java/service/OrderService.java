package service;

import entity.Order;
import repository.OrderRepository;

public class OrderService extends ShopService<Order, OrderRepository> {

    public OrderService() {
        super(new OrderRepository());
    }
}
