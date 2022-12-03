package org.example.config;

import org.example.repository.CrudRepository;
import org.example.repository.impl.UserRepositoryImpl;

final public class RepositoryFactory {

    public static <T> CrudRepository getInstanceOfCrudRepository(Class<T> o) {

        if (o.getName().equals("org.example.entity.User"))
            return new UserRepositoryImpl(o);
        return null;
    }
}
