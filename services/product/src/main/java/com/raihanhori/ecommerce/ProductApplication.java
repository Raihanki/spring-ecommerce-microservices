package com.raihanhori.ecommerce;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.raihanhori.ecommerce.entity.Category;
import com.raihanhori.ecommerce.repository.CategoryRepository;

@EnableJpaAuditing
@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Autowired
	private CategoryRepository categoryRepository;

	@Bean
	CommandLineRunner storeCategories() {
		return args -> {
			List<Category> checkCategory = categoryRepository.findAll();
			if (checkCategory.isEmpty()) {
				List<Category> categories = new ArrayList<Category>();
				categories.add(Category.builder().name("Sport").build());
				categories.add(Category.builder().name("Kitchen").build());
				categories.add(Category.builder().name("Music").build());
				categories.add(Category.builder().name("Game").build());
				categories.add(Category.builder().name("Cloth").build());

				categoryRepository.saveAll(categories);

				System.out.println("Successfully inject categories");
			}
		};
	}

}
