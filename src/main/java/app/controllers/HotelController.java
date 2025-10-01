package app.controllers;

import app.DAO.HotelDAO;
import app.DTO.HotelDTO;
import app.DTO.RoomDTO;
import app.config.HibernateConfig;
import app.entities.Hotel;
import app.mapper.HotelMapper;
import app.mapper.RoomMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;


public class HotelController {

    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final HotelDAO dao = new HotelDAO(emf);

    public void getAllHotels(Context ctx) {
        List<HotelDTO> hotels = dao.getAll()
                .stream()
                .map(HotelMapper::toDTO)
                .collect(Collectors.toList());
        ctx.status(HttpStatus.OK).json(hotels);
    }

    public void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("hotelId"));
        Hotel hotel = dao.getById(id);
        if (hotel != null) {
            ctx.status(200);
            ctx.json(HotelMapper.toDTO(hotel));
            logger.info("Fetched hotel with id: " + id);
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.result("Hotel not found");
            logger.warn("Hotel with id " + id + " not found");
        }
    }

    public void createHotel(Context ctx) {
        HotelDTO dto = ctx.bodyAsClass(HotelDTO.class);
        Hotel created = dao.create(HotelMapper.toEntity(dto));
        ctx.status(HttpStatus.CREATED).json(HotelMapper.toDTO(created));
    }

    public void updateHotel(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("hotelId"));
        HotelDTO dto = ctx.bodyAsClass(HotelDTO.class);

        Hotel hotel = dao.getById(id);
        if (hotel == null) {
            ctx.status(HttpStatus.NOT_FOUND).result("Hotel not found");
            return;
        }
        hotel.setName(dto.getHotelName());
        hotel.setAddress(dto.getHotelAddress());

        Hotel updated = dao.update(hotel);
        ctx.status(HttpStatus.OK).json(HotelMapper.toDTO(updated));
    }

    public void deleteHotel(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("hotelId"));
        boolean deleted = dao.delete(id);
        if (deleted) {
            ctx.status(HttpStatus.OK).result("Hotel deleted");
        } else {
            ctx.status(HttpStatus.NOT_FOUND).result("Hotel not found");
        }
    }

    public void getRoomsForHotel(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("hotelId"));
        Hotel hotel = dao.getById(id);
        if (hotel == null) {
            ctx.status(HttpStatus.NOT_FOUND).result("Hotel not found");
            return;
        }
        List<RoomDTO> rooms = dao.getRoomsForHotel(hotel)
                .stream()
                .map(RoomMapper::toDto)
                .collect(Collectors.toList());
        ctx.status(HttpStatus.OK).json(rooms);
    }
}
