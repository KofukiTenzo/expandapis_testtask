package com.test.task.expandapis_testtask.data;

import com.test.task.expandapis_testtask.Entitys.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
