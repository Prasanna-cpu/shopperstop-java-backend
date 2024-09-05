package com.kumar.shopperstop.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDTO> order;
    private CartDTO cart;
}
