package service;

import entity.Admin;
import repository.AdminRepository;

public class AdminService extends UserService<Admin, AdminRepository> {

    public AdminService() {
        super(new AdminRepository());
    }


}
