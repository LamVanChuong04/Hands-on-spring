package com.elearning.demo.Dto.Response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class UserResponse {
    private String username;
    private List<PostResponse> posts;
}
