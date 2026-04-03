package com.elearning.demo.Dto.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements Serializable {
    private String postTitle;
    private String postContent;
    private Long userId;
}
