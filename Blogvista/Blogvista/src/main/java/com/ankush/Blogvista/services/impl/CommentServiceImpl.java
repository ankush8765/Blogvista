package com.ankush.Blogvista.services.impl;

import com.ankush.Blogvista.entities.Comment;
import com.ankush.Blogvista.entities.Post;
import com.ankush.Blogvista.exceptions.ResourceNotFoundException;
import com.ankush.Blogvista.payloads.CommentDto;
import com.ankush.Blogvista.repositories.CommentRepo;
import com.ankush.Blogvista.repositories.PostRepo;
import com.ankush.Blogvista.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentDto commentDto, int postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
        Comment comment=this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment=this.commentRepo.save(comment);
        CommentDto commentDto1=this.modelMapper.map(savedComment,CommentDto.class);
        return commentDto1;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","CommentId",commentId));
        this.commentRepo.delete(comment);

    }
}
