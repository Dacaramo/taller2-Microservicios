package com.example.app.restClient;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientMain {

	public static final String MY_SERVER_URL="http://localhost:8080/myresource/";
	//public static final String RESTCOUNTRIES_SERVER_URL="http://restcountries.eu/rest/v2/";
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(MY_SERVER_URL);
		WebTarget helloWebTarget;
		Invocation.Builder invocationBuilder;
		Response response;
		String textResponse;
		
		int opc;
		
		do
		{
			Scanner sc = new Scanner(System.in);
			
			System.out.print("\n0. Salir\n"
					+ "1. (GET) Imprimir la lista de paseos disponibles en el servidor\n"
					+ "2. (DELETE) Borrar un paseo dado el id del paseo\n"
					+ "3. (PUT) Modificar el nombre de un paseo dado el id\n"
					+ "4. (POST) Insertar un nuevo paseo en el listado del servidor\n"
					+ "\tDigite una opcion -> ");
 
			opc = sc.nextInt();  
			
			switch(opc) {
				case 0:
					System.out.println("Hasta pronto!\n");
					break;
				case 1: //GET
					helloWebTarget = webTarget.path("trips");
					invocationBuilder = helloWebTarget.request(MediaType.APPLICATION_JSON);
					response = invocationBuilder.get(); 
					textResponse = response.readEntity(String.class);
					System.out.println("RESPONSE CODE FROM SERVER: " + response.getStatus());
					System.out.println("Media type: " + response.getMediaType().toString());
					System.out.println("Content: " + textResponse);
					break;
				case 2: //DELETE
					helloWebTarget = webTarget.path("trips/trip").queryParam("id", "P101"); //Para borrar el P101
					invocationBuilder = helloWebTarget.request(MediaType.TEXT_PLAIN);
					response = invocationBuilder.delete(); 
					textResponse = response.readEntity(String.class);
					System.out.println("RESPONSE CODE FROM SERVER: " + response.getStatus());
					System.out.println("Media type: " + response.getMediaType().toString());
					System.out.println("Content: " + textResponse);
					break;
				case 3: //PUT
					Trip tripWithOnlyName = new Trip();
					tripWithOnlyName.setName("NewTrip");
					
					helloWebTarget = webTarget.path("trips/trip").queryParam("id", "P102"); //Para modificar el P102
					invocationBuilder = helloWebTarget.request(MediaType.TEXT_PLAIN);
					response = invocationBuilder.put(Entity.entity(tripWithOnlyName, MediaType.APPLICATION_JSON)); 
					textResponse = response.readEntity(String.class);
					System.out.println("RESPONSE CODE FROM SERVER: " + response.getStatus());
					System.out.println("Media type: " + response.getMediaType().toString());
					System.out.println("Content: " + textResponse);
					break;
				case 4: //POST
					Trip trip = new Trip();
					trip.setId("P000");
					trip.setName("ClientGeneratedTrip");
					trip.setDeparture("departureSite");
					trip.setArrival("arrivalSite");
					trip.setDate("00-00-2021");
					
					helloWebTarget = webTarget.path("trips");
					invocationBuilder = helloWebTarget.request(MediaType.APPLICATION_JSON);
					response = invocationBuilder.post(Entity.entity(trip, MediaType.APPLICATION_JSON));
					textResponse = response.readEntity(String.class);
					System.out.println("RESPONSE CODE FROM SERVER: " + response.getStatus());
					System.out.println("Media type: " + response.getMediaType().toString());
					System.out.println("Content: " + textResponse);
					break;
				default:
					System.out.println("|!| La opcion no es valida, vuelva a intentarlo\n");
					break;
			}
		}while(opc != 0);
	}

}
