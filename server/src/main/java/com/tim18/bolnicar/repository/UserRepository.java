package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmailAddress(String email);
}
