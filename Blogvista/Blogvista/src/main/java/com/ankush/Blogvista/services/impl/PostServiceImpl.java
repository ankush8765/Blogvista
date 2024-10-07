package com.ankush.Blogvista.services.impl;

import com.ankush.Blogvista.entities.Category;
import com.ankush.Blogvista.entities.Post;
import com.ankush.Blogvista.entities.User;
import com.ankush.Blogvista.exceptions.ResourceNotFoundException;
import com.ankush.Blogvista.payloads.PostDto;
import com.ankush.Blogvista.payloads.PostResponse;
import com.ankush.Blogvista.repositories.CategoryRepo;
import com.ankush.Blogvista.repositories.PostRepo;
import com.ankush.Blogvista.repositories.UserRepo;
import com.ankush.Blogvista.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto,Integer categoryId, Integer userId) {

        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserID",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
      Post post= this.modelMapper.map(postDto, Post.class);
      post.setImageName("default.png");
      post.setAddedDate(new Date());
      post.setCategory(category);
      post.setUser(user);
      Post newPost=this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
       Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());
        Post updatedPost=this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public Void deletePost(Integer postId) {
       Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
       this.postRepo.delete(post);
        return null;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryID) {
        Category cat=this.categoryRepo.findById(categoryID).orElseThrow(()->new ResourceNotFoundException("category","CategoryId",categoryID));
        List<Post> posts=this.postRepo.findByCategory(cat);
        List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }
        else{
            sort=Sort.by(sortBy).descending();
        }
        PageRequest p= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePosts=this.postRepo.findAll(p);
        List<Post> posts=pagePosts.getContent();
        List<PostDto> postDtos=posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setIsLastPage(pagePosts.isLast());

        return postResponse;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));
        List<Post> post=this.postRepo.findByUser(user);
        List<PostDto> postDtos=post.stream().map((pst)->this.modelMapper.map(pst, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyWord) {
        List<Post> posts=this.postRepo.findByTitleContaining(keyWord);
        List<PostDto> postDtos=posts.stream().map((pst)->this.modelMapper.map(pst, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
