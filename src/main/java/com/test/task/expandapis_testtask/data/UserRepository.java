package com.test.task.expandapis_testtask.data;

import com.test.task.expandapis_testtask.DAO.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

//    User add(User user);

    User findByUsername(String username);
}
