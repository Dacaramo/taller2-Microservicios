package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController // localhost:8888
public class ControladorCalculadora {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();	
	}
	
	//Metodo que consume uno de los sumadores disponibles para sumar
	
	@GetMapping("/calculadora/suma") // localhost:8888/calculadora/suma?a={a}&b={b}
	public String sumarCalculadora(@RequestParam int a, @RequestParam int b) {
		String retorno = restTemplate.getForObject("http://sumador/suma?a={a}&b={b}", String.class, a, b);
		return retorno;
	}
	
	//Metodo que consume uno de los multiplicadores disponibles para multiplicar
	
	@GetMapping("/calculadora/multiplicacion") // localhost:8888/calculadora/multiplicacion?a={a}&b={b}
	public String multiplicarCalculadora(@RequestParam int a, @RequestParam int b) {
		String retorno = restTemplate.getForObject("http://multiplicador/multiplicacion?a={a}&b={b}", String.class, a, b);
		return retorno;
	}
	//Metodo que consume uno de los multiplicadores disponibles para multiplicar
	
	@GetMapping("/calculadora/resta") // localhost:8888/calculadora/multiplicacion?a={a}&b={b}
	public String restarCalculadora(@RequestParam int a, @RequestParam int b) {
		String retorno = restTemplate.getForObject("http://restador/resta?a={a}&b={b}", String.class, a, b);
		return retorno;
	}
	
	//Metodo que consume uno de los multiplicadores disponibles para multiplicar
	
	@GetMapping("/calculadora/division") // localhost:8888/calculadora/multiplicacion?a={a}&b={b}
	public String dividirCalculadora(@RequestParam int a, @RequestParam int b) {
		String retorno = restTemplate.getForObject("http://divisor/division?a={a}&b={b}", String.class, a, b);
		return retorno;
	}
}
