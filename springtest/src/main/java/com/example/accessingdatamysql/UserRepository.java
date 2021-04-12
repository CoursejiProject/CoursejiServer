package com.example.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

/**
 * This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 *
 * @author littlecorgi
 * @date 2021/4/12
 */
public interface UserRepository extends CrudRepository<User, Integer> {

}
