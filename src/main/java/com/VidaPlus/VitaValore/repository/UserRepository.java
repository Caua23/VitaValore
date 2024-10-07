package com.VidaPlus.VitaValore.repository;

import com.VidaPlus.VitaValore.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

}
