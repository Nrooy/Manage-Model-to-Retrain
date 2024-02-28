package com.example.retrain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Entity
@Table(name = "favor")
@AllArgsConstructor
@NoArgsConstructor
public class Favor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "favors")
    private List<User> users;

    public void removeUser(User user){
        this.users.remove(user);
        user.getFavors().remove(this);
    }

}

