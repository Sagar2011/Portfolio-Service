package com.business.portfolio.entity;


import com.business.portfolio.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {

    @Indexed(unique = true)
    private String username;
    private String password;
    private boolean isActive;
    private Role role;
}
