package repository;

import entity.Customer;
import entity.Order;
import entity.ShoppingCard;

import java.util.List;

public interface CustomerInterface extends UserInterface<Customer>{

    List<Order> findShoppingCardByUserId(int id);
}
