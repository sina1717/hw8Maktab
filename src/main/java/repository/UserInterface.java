package repository;

import entity.User;

public interface UserInterface<T> extends BaseRepository<T>{

    T login(String username,String password);
}
