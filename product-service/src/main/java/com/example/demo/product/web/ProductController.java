package com.example.demo.product.web;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.product.domain.Product;
import com.example.demo.product.exceptions.NotFoundException;
import com.example.demo.product.repository.ProductRepository;
import com.example.demo.product.service.product.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Responsible for REST operations involving products. Reading, creating,
 * searching, etdi, etc.
 */
@Api(tags = "students")
@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	/**
	 * Get a product by ID.
	 * 
	 * @param studentId
	 * @return
	 */
	@ApiOperation("Retrieves a product by the given ID.")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Product not found."),
			@ApiResponse(code = 200, message = "OK") })
	@RequestMapping(method = RequestMethod.GET, value = "/api/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand
	public Product getProductById(
			@ApiParam(name = "product", value = "The ID of the product.", required = true) @PathVariable Integer productId) {
		return requireNotNull(productRepository.findOne(productId), productId);

	}

	/**
	 * Creates a new product.
	 * 
	 * @param product
	 *            the new product.
	 * 
	 * @return
	 */
	@ApiOperation("Creates a new product.")
	@RequestMapping(method = RequestMethod.POST, value = "/api/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> createProduct(
			@ApiParam(name = "product", value = "The product.", required = true) @Valid @RequestBody Product product) {

		Product newProduct = productService.createProduct(product);

		return ResponseEntity.created(productURI(newProduct.getId())).body(newProduct);
	}

	private static URI productURI(Integer productId) {
		return toUri("/api/products/{id}", productId);
	}

	private static URI toUri(String path, Integer productId) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path(path).buildAndExpand(productId).toUri();
	}

	private static Product requireNotNull(Product p, Integer productId) {
		return Optional.ofNullable(p)
				.orElseThrow(() -> new NotFoundException("Product with Id " + productId + " not found!"));
	}

}