package pringboot1st.springboot1.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pringboot1st.springboot1.user.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
