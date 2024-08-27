package com.raihanhori.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.raihanhori.ecommerce.helper.PaginationData;
import com.raihanhori.ecommerce.helper.PaginationResponseHelper;
import com.raihanhori.ecommerce.helper.SuccessResponseHelper;
import com.raihanhori.ecommerce.request.CreateProductRequest;
import com.raihanhori.ecommerce.request.GetProductRequest;
import com.raihanhori.ecommerce.request.PurchaseRequest;
import com.raihanhori.ecommerce.resource.ProductResource;
import com.raihanhori.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public PaginationResponseHelper<List<ProductResource>> getAll(
			@RequestParam(defaultValue = "10", required = false) Integer limit,
			@RequestParam(defaultValue = "1", required = false) Integer page
	) {
		GetProductRequest request = new GetProductRequest();
		request.setPage(page);
		request.setLimit(limit);
		
		Page<ProductResource> products = productService.getAll(request);
	
		return PaginationResponseHelper.<List<ProductResource>>builder()
				.status(200)
				.data(products.getContent())
				.options(
					PaginationData.builder()
						.current_page(page)
						.total_data(products.getNumberOfElements())
						.total_data_per_page(products.getSize())
						.total_page(products.getTotalPages())
						.build()
				).build();
	}
	
	@GetMapping(path = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessResponseHelper<ProductResource> getById(@PathVariable Integer productId) {
		
		ProductResource product = productService.getById(productId);
	
		return SuccessResponseHelper.<ProductResource>builder()
				.status(200)
				.data(product)
				.build();
	}
	
	@PostMapping(path = "/purchase", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessResponseHelper<List<ProductResource>> purchase(@RequestBody List<PurchaseRequest> request) {
		List<ProductResource> products = productService.purchase(request);
	
		return SuccessResponseHelper.<List<ProductResource>>builder()
				.status(200)
				.data(products)
				.build();
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessResponseHelper<String> store(@RequestBody CreateProductRequest request) {
		productService.create(request);
	
		return SuccessResponseHelper.<String>builder()
				.status(201)
				.data("product successfully created")
				.build();
	}
	
}
