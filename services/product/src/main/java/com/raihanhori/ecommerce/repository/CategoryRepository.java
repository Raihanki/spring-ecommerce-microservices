package com.raihanhori.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raihanhori.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
