package com.ankush.Blogvista.repositories;

import com.ankush.Blogvista.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
