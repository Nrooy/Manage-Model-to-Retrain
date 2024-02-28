package com.example.retrain.Respository;

import com.example.retrain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query(value = "select u from User u where (?1 is null or u.userName like %?1% or u.email like %?1%)")
    List<User> doSearch(String keyword);

    @Query(value = "select u from User u where (?1 is null or u.userName like %?1% or u.email like %?1% or u.age = ?1 or u.gender like %?1%)")
    Page<User> doSearch1(String keyword, Pageable pageable);
}
