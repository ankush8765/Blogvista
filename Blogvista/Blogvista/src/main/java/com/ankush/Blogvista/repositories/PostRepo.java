package com.ankush.Blogvista.repositories;

import com.ankush.Blogvista.entities.Category;
import com.ankush.Blogvista.entities.Post;
import com.ankush.Blogvista.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String Title);
}
