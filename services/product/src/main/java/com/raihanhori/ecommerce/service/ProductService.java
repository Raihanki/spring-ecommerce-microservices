package com.raihanhori.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.raihanhori.ecommerce.request.CreateProductRequest;
import com.raihanhori.ecommerce.request.GetProductRequest;
import com.raihanhori.ecommerce.request.PurchaseRequest;
import com.raihanhori.ecommerce.resource.ProductResource;

public interface ProductService {
	
	void create(CreateProductRequest request);
	
	List<ProductResource> purchase(List<PurchaseRequest> products);
	
	ProductResource getById(Integer id);
	
	Page<ProductResource> getAll(GetProductRequest request);
	
}
