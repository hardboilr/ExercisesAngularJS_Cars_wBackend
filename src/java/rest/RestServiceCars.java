package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Car;
import facade.CarController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("car")
public class RestServiceCars {

    @Context
    private UriInfo context;

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
    private final CarController ctrl = new CarController(emf);
    private Gson gson = new Gson();

    public RestServiceCars() {
    }

    @GET
    @Produces("application/json")
    public Response getCars() {
        List<Car> cars;
        try {
            cars = ctrl.getCars();
            return Response.ok(gson.toJson(cars)).build();
        } catch (Exception ex) {
            JsonObject jo = new JsonObject();
            jo.addProperty("Message", ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(jo.toString()).build();
        }
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response createCar(String c) {
        Car car = ctrl.createCar(gson.fromJson(c, Car.class));
        return Response.ok(gson.toJson(car)).build();
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response editCar(String c) {
        Car car;
        try {
            car = ctrl.editCar(gson.fromJson(c, Car.class));
            return Response.ok(gson.toJson(car)).build();
        } catch (Exception ex) {
            JsonObject jo = new JsonObject();
            jo.addProperty("Message", ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(jo.toString()).build();
        }
    }
    

}
