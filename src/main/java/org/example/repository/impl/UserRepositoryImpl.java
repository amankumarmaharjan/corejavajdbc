package org.example.repository.impl;

import org.example.entity.User;

public class UserRepositoryImpl extends SimpleJpaRepository<User,Integer> implements UserRepository {
    public UserRepositoryImpl(Class type) {
        super(type);
    }


}
