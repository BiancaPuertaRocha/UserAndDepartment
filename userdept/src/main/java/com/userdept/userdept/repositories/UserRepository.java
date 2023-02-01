package com.userdept.userdept.repositories;

import com.userdept.userdept.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

//                                                 tipo do obj e do id
public interface UserRepository extends JpaRepository<User, Long> {

}
