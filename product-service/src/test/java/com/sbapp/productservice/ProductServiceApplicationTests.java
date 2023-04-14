package com.sbapp.productservice;

import com.mongodb.assertions.Assertions;
import com.sbapp.productservice.dto.ProductRequest;
import com.sbapp.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	//INTEGRATION TEST

	@Container // Junit5 understands this is a mongoDB container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	// first the integration test will start the mongodb 4.4.2 container
	//then it will take the replica url and add to the spring.data.mongodb.uri property
	// this is because we defined spring.data.mongodb.uri manually in application.properties

	@DynamicPropertySource // add property dynamically
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry){
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired //To make request to the controller
	private MockMvc mockMvc;
	@Autowired // To convert the object to a string POJO to JSON and JSON to POJO
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
					.contentType(MediaType.APPLICATION_JSON)
					.content(productRequestString))
				.andExpect(status().isCreated());
		Assertions.assertTrue(productRepository.findAll().size() == 1);

	}

	private ProductRequest getProductRequest(){
		return ProductRequest.builder()
				.name("Iphone 14")
				.description("Iphone 14")
				.price(BigDecimal.valueOf(1500))
				.build();
	}

	@Test
	void shouldReturnProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")).andExpect(status().is2xxSuccessful());
	}

}
