package com.raihanhori.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.raihanhori.ecommerce.entity.Category;
import com.raihanhori.ecommerce.entity.Product;
import com.raihanhori.ecommerce.helper.ValidationHelper;
import com.raihanhori.ecommerce.repository.CategoryRepository;
import com.raihanhori.ecommerce.repository.ProductRepository;
import com.raihanhori.ecommerce.request.CreateProductRequest;
import com.raihanhori.ecommerce.request.GetProductRequest;
import com.raihanhori.ecommerce.request.PurchaseRequest;
import com.raihanhori.ecommerce.resource.ProductResource;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ValidationHelper validationHelper;

	@Override
	public void create(CreateProductRequest request) {
		validationHelper.validate(request);
		
		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
		
		Product product = new Product();
		product.setName(request.getName());
		product.setDesctiption(request.getDesctiption());
		product.setAvailableQuantity(request.getAvailableQuantity());
		product.setPrice(request.getPrice());
		product.setCategory(category);
		
		productRepository.save(product);
	}

	@Override
	public List<ProductResource> purchase(List<PurchaseRequest> request) {
		validationHelper.validate(request);
		
		List<ProductResource> products = new ArrayList<ProductResource>();
		
		for (PurchaseRequest purchasedProduct : request) {
			Product product = productRepository.findById(purchasedProduct.getProductId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product with id " + purchasedProduct.getProductId() + " not found"));
		
			if (product.getAvailableQuantity() < purchasedProduct.getAvailableQuantity()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "too much quantity");
			}
			
			Integer newQuantity = product.getAvailableQuantity() - purchasedProduct.getAvailableQuantity();
			product.setAvailableQuantity(newQuantity);
			productRepository.save(product);
			
			products.add(toProductResource(product));
		}
		
		return products;
	}

	@Override
	public ProductResource getById(Integer id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
		
		return toProductResource(product);
	}

	@Override
	public Page<ProductResource> getAll(GetProductRequest request) {
		Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
		
		Page<Product> products = productRepository.findAll(pageable);
		List<ProductResource> listProducts = products.stream().map(product -> 
			toProductResource(product)
		).toList();
		
		return new PageImpl<ProductResource>(listProducts, pageable, products.getNumberOfElements());
	}
	
	private ProductResource toProductResource(Product product) {
		Category category = new Category();
		category.setId(product.getCategory().getId());
		category.setName(product.getCategory().getName());
		category.setDescription(product.getCategory().getDescription());
		
		return ProductResource.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDesctiption())
				.availableQuantity(product.getAvailableQuantity())
				.price(product.getPrice())
				.createdAt(product.getCreatedAt())
				.category(category)
				.build();
	}

}
