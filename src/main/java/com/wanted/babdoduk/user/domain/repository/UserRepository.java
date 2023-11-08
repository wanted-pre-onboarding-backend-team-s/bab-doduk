package com.wanted.babdoduk.user.domain.repository;

import com.wanted.babdoduk.user.domain.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    List<User> findUsersByLunchPushApprovedIsTrue();

}
