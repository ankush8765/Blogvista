package com.ankush.Blogvista.controllers;

import com.ankush.Blogvista.config.AppConstants;
import com.ankush.Blogvista.entities.Post;
import com.ankush.Blogvista.payloads.ApiResponse;
import com.ankush.Blogvista.payloads.PostDto;
import com.ankush.Blogvista.payloads.PostResponse;
import com.ankush.Blogvista.services.FileService;
import com.ankush.Blogvista.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

@Value("${project.image}")
    private String path;

    @PostMapping("/category/{categoryId}/user/{userId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer categoryId,@PathVariable Integer userId){
        PostDto post=this.postService.createPost(postDto,categoryId,userId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtoList=this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> postDtoList=this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam (value = "pageNumber",defaultValue = AppConstants.pageNumber,required = false) Integer pageNumber,
                                                    @RequestParam (value="pageSize",defaultValue =AppConstants.pageSize,required = false) Integer pageSize,
                                                    @RequestParam (value = "sortBy",defaultValue = AppConstants.sortBy,required = false) String sortBy,
                                                    @RequestParam (value = "sortDir",defaultValue = AppConstants.sortDir,required = false) String sortDir){
        PostResponse postResponse=this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto=this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto postDto1=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(postDto1,HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyWord}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("keyWord") String keyWord){
        List<PostDto> postDtos=this.postService.searchPosts(keyWord);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
        PostDto postDto=this.postService.getPostById(postId);
        String fileName=this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatedPost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void showImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
