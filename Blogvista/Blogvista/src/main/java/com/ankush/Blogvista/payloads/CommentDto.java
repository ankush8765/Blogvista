package com.ankush.Blogvista.payloads;

import com.ankush.Blogvista.entities.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private int id;
    private String content;
}
