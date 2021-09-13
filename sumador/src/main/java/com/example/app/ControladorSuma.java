package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // localhost:9999
public class ControladorSuma {

	@Autowired
	Environment entorno;
	
	@GetMapping("/suma") // localhost:9999/suma?a={a}&b={b}
	public String sumar(@RequestParam int a, @RequestParam int b) {
		String puerto = entorno.getProperty("local.server.port");
		String retorno = String.valueOf(a) + " + " + String.valueOf(b) + " = " 
						+ String.valueOf(a + b);
		return "RESPONSE: " + retorno + "\nRESPONSE GENERATED FROM PORT: " + puerto;
	}
	
	@GetMapping("/sumaInteger") // localhost:9999/sumaInteger?a={a}&b={b}
	public Integer sumarInteger(@RequestParam int a, @RequestParam int b) {
		String puerto = entorno.getProperty("local.server.port");
		System.out.println("RESPONSE GENERATED FROM PORT: " + puerto);
		
		return a + b;
	}
}
