
package com.example.newsfeed.repository;

import com.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByDisplayName(String displayName);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String displayName);
    Optional<User> findByEmail(String email);
    Optional<User> findByDisplayName(String displayName);
    Optional<User> findIdByEmail(String email); // 추가(고예나)

    List<User> findAllByIdIn(List<Integer> list);
}

