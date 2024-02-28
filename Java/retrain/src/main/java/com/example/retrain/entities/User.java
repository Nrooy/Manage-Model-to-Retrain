package com.example.retrain.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private Integer role; // 0: user, 1:admin

    @ManyToMany
    @JoinTable(name = "user_favor",
            joinColumns = @JoinColumn(name = "user_id", insertable = true, updatable = true),
            inverseJoinColumns = @JoinColumn(name = "favor_id", insertable = true, updatable = true)
    )
    private List<Favor> favors;

    @Column(name = "category")
    private String category;

}
