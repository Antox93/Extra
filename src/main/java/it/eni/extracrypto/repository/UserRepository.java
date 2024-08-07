package it.eni.extracrypto.repository;

import it.eni.extracrypto.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
