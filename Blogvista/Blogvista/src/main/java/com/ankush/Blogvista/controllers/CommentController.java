package com.ankush.Blogvista.controllers;

import com.ankush.Blogvista.payloads.ApiResponse;
import com.ankush.Blogvista.payloads.CommentDto;
import com.ankush.Blogvista.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable int postId){
        CommentDto commentDto1=this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
    }
}
