package app.routes;

import app.controllers.HotelController;
import app.controllers.RoomController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class HotelRoute {

    private HotelController hotelController = new HotelController();
    private RoomController roomController = new RoomController();

    public EndpointGroup getRoutes() {
        return () -> {
            get("/", hotelController::getAllHotels);
            get("/{hotelId}", hotelController::getById);
            get("/{hotelId}/rooms", hotelController::getRoomsForHotel);
            post("/", hotelController::createHotel);
            put("/{hotelId}", hotelController::updateHotel);
            delete("/{hotelId}", hotelController::deleteHotel);

            // Room endpoints under et hotel
            path("/{hotelId}/rooms", () -> {
                post(roomController::addRoomToHotel);
                delete("/{roomId}", roomController::removeRoomFromHotel);
            });
        };
    }

}
