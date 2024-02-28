package com.example.retrain.Seviece;

import com.example.retrain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User create(User user);

    User retrieve(Integer id);

    void update(Integer id, User user);

    void delete(Integer id);

    List<User> findAll();

    Page<User> findAll1(Pageable pageable);

    List<User> doSearch(String keyword);

    Page<User> doSearch1(String keyword, Pageable pageable);

    User findByEmail(String email);
}
