package service;

import entity.Customer;
import entity.Order;
import entity.ShoppingCard;
import repository.CustomerRepository;

import java.util.List;

public class CustomerService extends UserService<Customer, CustomerRepository> {

    private CustomerRepository customerRepository;

    public CustomerService() {
        super(new CustomerRepository());
        this.customerRepository = new CustomerRepository();
    }

    public void findShoppingCardByUserId(int id) {
        List<Order> shoppingCardByUserId = customerRepository.findShoppingCardByUserId(id);
        for (Order shoppingCard : shoppingCardByUserId) {
            System.out.println(shoppingCard.toString());
        }
    }
}
