package org.example;

import org.example.config.RepositoryFactory;
import org.example.entity.User;
import org.example.repository.impl.UserRepository;
import org.w3c.dom.ls.LSOutput;

import java.util.Optional;

public class Main {

    public static UserRepository userRepository = (UserRepository) RepositoryFactory.getInstanceOfCrudRepository(User.class);

    public static void main(String[] args) {
//        saveOperation();
//        deleteOperation();
//        displayOperation();
//        findById();
//        findExistById();
        getCount();
    }

    private static void getCount() {
        System.out.println( userRepository.count());
    }

    private static void findById() {
        Optional<User> user = userRepository.findById(1);
        user.ifPresentOrElse(System.out::println, () -> System.out.println("Id not found:"));
    }

    private static void findExistById() {
       if( userRepository.existsById(1))
           System.out.println("User exists");
    }

    private static void deleteOperation() {
        User user = new User();
        user.setName("aman");
        user.setLname("maharjan");
        userRepository.delete(user);
    }

    private static void saveOperation() {
        User user = new User();
        user.setId(4);
        user.setName("aman");
        user.setLname("maharjan");
        User user1 = userRepository.save(user);
    }

    private static void displayOperation() {
        Iterable<User> l = userRepository.findAll();
        l.forEach(System.out::println);
    }
}