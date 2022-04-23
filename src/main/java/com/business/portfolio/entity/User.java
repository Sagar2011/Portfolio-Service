package com.business.portfolio.entity;


import com.business.portfolio.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("user")
public class User {

    @Id
    private String userName;
    private String password;
    private boolean isActive;
    private Role role;
}
