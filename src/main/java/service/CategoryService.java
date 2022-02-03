package service;

import entity.Category;
import repository.CategoryRepository;

public class CategoryService extends ShopService<Category, CategoryRepository> {

    public CategoryService() {
        super(new CategoryRepository());
    }

}
