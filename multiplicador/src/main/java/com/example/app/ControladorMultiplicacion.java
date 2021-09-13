package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController // localhost:7090
public class ControladorMultiplicacion {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();	
	}
	
	//Metodo que consume uno de los sumadores disponibles para multiplicar a punta de sumas
	
	@GetMapping("/multiplicacion") // localhost:7090/multiplicacion?a={a}&b={b}
	public Integer multiplicar(@RequestParam int a, @RequestParam int b) {
		Integer resultado = 0;
		
		for(int i = 0; i < b; i++) {
			resultado += sumarMultiplicador(a, resultado);
		}
		
		return resultado;	
	}
	
	public Integer sumarMultiplicador(int a, int b) {
		Integer resultado = restTemplate.getForObject("http://sumador/sumaInteger?a={a}&b={b}", Integer.class, a, b);
		return resultado;
	}
}
