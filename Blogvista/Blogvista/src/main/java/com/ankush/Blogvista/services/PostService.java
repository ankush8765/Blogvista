package com.ankush.Blogvista.services;

import com.ankush.Blogvista.entities.Post;
import com.ankush.Blogvista.payloads.PostDto;
import com.ankush.Blogvista.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    PostDto updatePost(PostDto postDto ,Integer postId);

    Void deletePost(Integer postId);

    PostDto getPostById(Integer postId);

    List<PostDto> getPostsByCategory(Integer categoryID);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    List<PostDto> getPostsByUser(Integer userId);

    List<PostDto> searchPosts(String keyWord);


}
