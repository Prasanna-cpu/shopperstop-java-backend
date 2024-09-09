package com.kumar.shopperstop.Model.Role;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kumar.shopperstop.Model.Cart.Cart;
import com.kumar.shopperstop.Model.Order.Order;
import com.kumar.shopperstop.Model.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role(String name) {
        this.name=name;
    }


    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<User> users = new HashSet<>();

}
