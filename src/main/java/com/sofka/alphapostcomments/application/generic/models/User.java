package com.sofka.alphapostcomments.application.generic.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "User")
public class User {

    @Id
    private String userId;
    private String userName;
    private String password;
    private String email;

    @Builder.Default()
    private boolean isActive = true;
    @Builder.Default()
    private List<String> roles = new ArrayList<>();

}
