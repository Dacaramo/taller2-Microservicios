package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // localhost:7100
public class ControladorResta {

	@Autowired
	Environment entorno;
	
	@GetMapping("/resta") // localhost:7100/resta?a={a}&b={b}
	public String restar(@RequestParam int a, @RequestParam int b) {
		String puerto = entorno.getProperty("local.server.port");
		String retorno = String.valueOf(a) + " - " + String.valueOf(b) + " = " 
						+ String.valueOf(a - b);
		return "RESPONSE: " + retorno + "\nRESPONSE GENERATED FROM PORT: " + puerto;
	}
}
