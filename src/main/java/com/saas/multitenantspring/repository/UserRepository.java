package com.saas.multitenantspring.repository;

import com.saas.multitenantspring.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
