package org.example.config;

import org.example.entity.User;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.repository.impl.CrudRepository;

final public class RepositoryFactory {

    public static <T> CrudRepository getInstanceOfCrudRepository(Class<T> o) {

        if (o.getName().equals("org.example.entity.User"))
            return new UserRepositoryImpl(o);
        return null;
    }
}
