package com.raihanhori.ecommerce.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.raihanhori.ecommerce.entity.Address;
import com.raihanhori.ecommerce.entity.Customer;
import com.raihanhori.ecommerce.helper.ValidationHelper;
import com.raihanhori.ecommerce.repository.AddressRepository;
import com.raihanhori.ecommerce.repository.CustomerRepository;
import com.raihanhori.ecommerce.request.CreateCustomerRequest;
import com.raihanhori.ecommerce.request.GetCustomerRequest;
import com.raihanhori.ecommerce.request.UpdateCustomerRequest;
import com.raihanhori.ecommerce.resource.CustomerResource;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ValidationHelper validationHelper;

	@Transactional
	@Override
	public void create(CreateCustomerRequest request) {
		validationHelper.validate(request);
		
		Customer checkEmail = customerRepository.findByEmail(request.getEmail()).orElse(null);
		if (Objects.nonNull(checkEmail)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is already exist");
		}

		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customerRepository.save(customer);

		Address address = new Address();
		address.setStreet(request.getStreet());
		address.setHouseNumber(request.getHouseNumber());
		address.setZipCode(request.getZipCode());
		address.setCustomer(customer);
		addressRepository.save(address);
	}

	@Transactional
	@Override
	public void update(UpdateCustomerRequest request) {
		validationHelper.validate(request);
		
		Customer customer = customerRepository.findById(request.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
		
		if (!request.getEmail().equals(customer.getEmail())) {
			Customer checkEmail = customerRepository.findByEmail(request.getEmail()).orElse(null);
			if (Objects.nonNull(checkEmail)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is already exist");
			}
		}
		
		Address address = addressRepository.findByCustomer_Id(customer.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "something wrong with your account"));

		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customerRepository.save(customer);

		address.setStreet(request.getStreet());
		address.setHouseNumber(request.getHouseNumber());
		address.setZipCode(request.getZipCode());
		address.setCustomer(customer);
		addressRepository.save(address);
	}

	@Override
	public Page<CustomerResource> getAll(GetCustomerRequest request) {
		Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
		
		Page<Customer> customers = customerRepository.findAll(pageable);
		List<CustomerResource> customerResources = customers.stream().map(customer -> 
			toCustomerResource(customer)
		).toList();
		
		return new PageImpl<CustomerResource>(customerResources, pageable, customers.getTotalElements());
	}

	@Override
	public boolean existsById(Integer id) {
		return customerRepository.findById(id).isPresent();
	}

	@Override
	public CustomerResource getById(Integer id) {
		Customer customer =  customerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
		
		return toCustomerResource(customer);
	}
	
	@Transactional
	@Override
	public void delete(Integer id) {
		Customer customer =  customerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
		
		customerRepository.delete(customer);
	}
	
	private CustomerResource toCustomerResource(Customer customer) {
		Address address = Address.builder()
				.id(customer.getAddress().getId())
				.street(customer.getAddress().getStreet())
				.houseNumber(customer.getAddress().getHouseNumber())
				.zipCode(customer.getAddress().getZipCode())
				.build();
				
		return CustomerResource.builder()
				.id(customer.getId())
				.firstName(customer.getFirstName())
				.lastName(customer.getLastName())
				.email(customer.getEmail())
				.address(address)
				.build();
	}

}
