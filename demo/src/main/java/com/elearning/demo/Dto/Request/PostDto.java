package com.elearning.demo.Dto.Request;


import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    @NotNull
    @Length(min = 1, max = 100)
    private String postTitle;

    private String postContent;
    private Long userId;

    @Version
    private Long version;
}
