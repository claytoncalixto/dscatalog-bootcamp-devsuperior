package com.claytoncalixto.dscatalog;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.claytoncalixto.dscatalog.dto.ProductDTO;
import com.claytoncalixto.dscatalog.resources.ProductResource;
import com.claytoncalixto.dscatalog.services.ProductService;
import com.claytoncalixto.dscatalog.services.exceptions.ResourceNotFoundException;
import com.claytoncalixto.dscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {

	@Autowired
	private MockMvc mocMvc;

	@MockBean
	private ProductService service;

	@Autowired
	private ObjectMapper objectMapper;

	private Long existsId;
	private Long nonExistsId;
	private ProductDTO productDTO;
	private PageImpl<ProductDTO> page;

	@BeforeEach
	void setUp() throws Exception {

		existsId = 1L;
		nonExistsId = 1000L;

		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDTO));

		when(service.findAllPaged(any())).thenReturn(page);

		when(service.findById(existsId)).thenReturn(productDTO);
		when(service.findById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

		when(service.update(eq(existsId), any())).thenReturn(productDTO);
		when(service.update(eq(nonExistsId), any())).thenThrow(ResourceNotFoundException.class);
	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(productDTO);

		ResultActions result = 
				mocMvc.perform(put("/products/{id}", existsId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());

	}

	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenDoesNotIdExists() throws Exception {
	
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = 
				mocMvc.perform(put("/products/{id}", nonExistsId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
		
	}

	@Test
	public void findAllShouldReturnPage() throws Exception {
		ResultActions result = mocMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	public void findByIdShouldReturnProductWhenIdExists() throws Exception {

		ResultActions result = mocMvc.perform(get("/products/{id}", existsId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
	}

	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenDoesNotIdExists() throws Exception {

		ResultActions result = mocMvc.perform(get("/products/{id}", nonExistsId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound());
	}

}
