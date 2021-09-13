package com.example.app.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
	
	public static final String FILE_PATH = "src/main/java/com/example/app/server/trips.json";

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	
	// http://localhost:8080/myresource
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    //PUNTO 2: http://localhost:8080/myresource/trips
    
    @GET
    @Path("trips")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Trip> getTrips() {
    	ArrayList<Trip> trips = new ArrayList<Trip>();
    	
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader(FILE_PATH))
        {
            Object obj = jsonParser.parse(reader);
 
            JSONArray tripArray = (JSONArray) obj;
             
            for(Object trip : tripArray) {
            	trips.add(createTripFromJson((JSONObject) trip));
            }
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}	
        
        return trips;
    }
    
    public Trip createTripFromJson(JSONObject trip) {
    	Trip returnTrip = new Trip();
    	
        returnTrip.setId((String) trip.get("id"));
        returnTrip.setName((String) trip.get("name"));
        returnTrip.setDeparture((String) trip.get("departure"));
        returnTrip.setArrival((String) trip.get("arrival"));
        returnTrip.setDate((String) trip.get("date"));
        
        return returnTrip;
    }
    
    //PUNTO 3: http://localhost:8080/myresource/trips/trip?id=P101
    
    @DELETE
    @Path("trips/trip")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteTrip(@QueryParam("id") String id) {
    	boolean hasBeenDeleted = false;
    	
    	//LEER trips.json
    	
    	JSONParser jsonParser = new JSONParser();
        JSONArray newJsonArray = new JSONArray();
    	
        try (FileReader reader = new FileReader(FILE_PATH))
        {
        	JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
            JSONObject jsonObject;
            
            for(Object obj : jsonArray) {
            	jsonObject = (JSONObject) obj;
            	
            	if(id.compareTo((String) jsonObject.get("id")) != 0) {
            		newJsonArray.add(jsonObject);
            	}
            	else {
            		hasBeenDeleted = true;
            	}
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
        
        if(hasBeenDeleted)
        {
        	//BORRAR todo el contenido de trips.json
            
            File file = new File(FILE_PATH);
            
            try (PrintWriter pw = new PrintWriter(file)) {}
            catch (IOException e) {
                e.printStackTrace();
            }
            
            //ESCRIBIR en trips.json
            
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write(newJsonArray.toJSONString()); 
                writer.flush();
     
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
        
        return String.valueOf(hasBeenDeleted);
    }
    
    //PUNTO 4: http://localhost:8080/myresource/trips/trip?id=P101
    
    @PUT
    @Path("trips/trip")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String changeTripName(@QueryParam("id") String id, Trip tripWithOnlyName) {
    	boolean hasBeenChanged = false;
    	
    	//LEER trips.json
    	
    	JSONParser jsonParser = new JSONParser();
    	JSONArray jsonArray = new JSONArray();
    	
        try (FileReader reader = new FileReader(FILE_PATH))
        {
        	jsonArray = (JSONArray) jsonParser.parse(reader);
            JSONObject jsonObject;
            
            for(Object obj : jsonArray) {
            	jsonObject = (JSONObject) obj;
            	
            	if(id.compareTo((String) jsonObject.get("id")) == 0) {
            		jsonObject.replace("name", tripWithOnlyName.getName());
            		hasBeenChanged = true;
            	}
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
        
        if(hasBeenChanged)
        {
        	//BORRAR todo el contenido de trips.json
            
            File file = new File(FILE_PATH);
            
            try (PrintWriter pw = new PrintWriter(file)) {}
            catch (IOException e) {
                e.printStackTrace();
            }
            
            //ESCRIBIR en trips.json
            
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write(jsonArray.toJSONString()); 
                writer.flush();
     
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
    	
    	return String.valueOf(hasBeenChanged);
    }
    
    //PUNTO 5: http://localhost:8080/myresource/trips
    
    @POST
    @Path("trips")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Trip insertTrip(Trip trip) {
    	JSONArray jsonArray = new JSONArray();
    	JSONObject jsonTrip = new JSONObject();
    	jsonTrip.put("id", trip.getId());
    	jsonTrip.put("name", trip.getName());
    	jsonTrip.put("departure", trip.getDeparture());
    	jsonTrip.put("arrival", trip.getArrival());
    	jsonTrip.put("date", trip.getDate());
    	
		//LEER trips.json
    	
    	JSONParser jsonParser = new JSONParser();
    	
        try (FileReader reader = new FileReader(FILE_PATH))
        {
        	jsonArray = (JSONArray) jsonParser.parse(reader);
            jsonArray.add(jsonTrip);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
        
    	//BORRAR todo el contenido de trips.json
        
        File file = new File(FILE_PATH);
        
        try (PrintWriter pw = new PrintWriter(file)) {}
        catch (IOException e) {
            e.printStackTrace();
        }
        
        //ESCRIBIR en trips.json
        
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(jsonArray.toJSONString()); 
            writer.flush();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    
        return trip;
    }
}
