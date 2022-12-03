package org.example.repository.impl;

import org.example.entity.User;
import org.example.repository.UserRepository;

public class UserRepositoryImpl extends SimpleJpaRepository<User,Integer> implements UserRepository {
    public UserRepositoryImpl(Class type) {
        super(type);
    }


}
