package com.ankush.Blogvista.services;

import com.ankush.Blogvista.payloads.CommentDto;
import org.springframework.stereotype.Service;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,int postId);

    void deleteComment(Integer commentId);
}
