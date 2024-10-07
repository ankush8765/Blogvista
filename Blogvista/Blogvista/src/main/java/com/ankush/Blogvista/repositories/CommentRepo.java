package com.ankush.Blogvista.repositories;

import com.ankush.Blogvista.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
