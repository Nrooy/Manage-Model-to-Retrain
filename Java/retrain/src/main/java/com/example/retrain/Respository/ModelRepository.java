package com.example.retrain.Respository;

import com.example.retrain.entities.Model;
import com.example.retrain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model,Integer> {
    Model findByName(String name);
    //    List<Model> findByDefaultIsTrue();
    @Query("SELECT m FROM Model m WHERE m.dafault = true")
    List<Model> findByDefaultIsTrue();

    @Query(value = "select m from Model m where (?1 is null or m.name like %?1% )")
    Page<User> doSearch(String keyword, Pageable pageable);
}
