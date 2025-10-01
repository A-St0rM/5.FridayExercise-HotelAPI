package app.controllers;

import app.DAO.HotelDAO;
import app.DAO.RoomDAO;
import app.DTO.RoomDTO;
import app.config.HibernateConfig;
import app.entities.Hotel;
import app.entities.Room;
import app.mapper.RoomMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;

public class RoomController {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final HotelDAO hotelDAO = new HotelDAO(emf);
    private final RoomDAO roomDAO = new RoomDAO(emf);

    public void addRoomToHotel(Context ctx) {
        int hotelId = Integer.parseInt(ctx.pathParam("hotelId"));
        RoomDTO dto = ctx.bodyAsClass(RoomDTO.class);

        Hotel hotel = hotelDAO.getById(hotelId);
        if (hotel == null) {
            ctx.status(HttpStatus.NOT_FOUND).result("Hotel not found");
            return;
        }
        Room room = RoomMapper.toEntity(dto, hotel);
        hotelDAO.addRoom(hotel, room);
        ctx.status(HttpStatus.CREATED).json(RoomMapper.toDto(room));
    }

    public void removeRoomFromHotel(Context ctx) {
        int hotelId = Integer.parseInt(ctx.pathParam("hotelId"));
        int roomId = Integer.parseInt(ctx.pathParam("roomId"));

        Hotel hotel = hotelDAO.getById(hotelId);
        Room room = roomDAO.getById(roomId);

        if (hotel == null || room == null || room.getHotel().getId() != hotelId) {
            ctx.status(HttpStatus.NOT_FOUND).result("Room not found in this hotel");
            return;
        }

        boolean deleted = roomDAO.delete(roomId);
        if (deleted) {
            ctx.status(HttpStatus.OK).json(RoomMapper.toDto(room));
        } else {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Failed to delete room");
        }
    }
}
