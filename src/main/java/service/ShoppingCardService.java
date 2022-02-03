package service;

import entity.ShoppingCard;
import repository.ShoppingCardRepository;

public class ShoppingCardService extends ShopService<ShoppingCard, ShoppingCardRepository> {

    public ShoppingCardService() {
        super(new ShoppingCardRepository());
    }
}
