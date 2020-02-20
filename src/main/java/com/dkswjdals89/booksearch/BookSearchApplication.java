package com.dkswjdals89.booksearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;

@SpringBootApplication
public class BookSearchApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookSearchApplication.class, args);
	}
}
