package cars;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("cars")
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {

	@Inject
	CarService carService;
	
	@GET
	public JsonArray getCars() {
		return carService.getCars().stream().map( c ->
			Json.createObjectBuilder()
				.add("pono", c.getPono())
				.add("color", c.getColor())
				.add("type", c.getType())
				.add("year", c.getYear()).build())
			.collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add).build();
	}
	
	@POST
	public void create(@Valid Car car) {
		carService.createCar(car);
	}
}
