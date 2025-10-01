package app.routes;

import app.controllers.HotelController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class HotelRoute {

    private HotelController hotelController = new HotelController();

    public EndpointGroup getRoutes() {
        return () -> {
            get("/", hotelController::getAllHotels);
            get("/{id}", hotelController::getById);
            get("/{id}/rooms", hotelController::getRoomsForHotel);
            post("/", hotelController::createHotel);
            put("/{id}", hotelController::updateHotel);
            delete("/{id}", hotelController::deleteHotel);
        };
    }

}
