package app.mapper;

import app.DTO.RoomDTO;
import app.entities.Hotel;
import app.entities.Room;

public class RoomMapper {

    public static RoomDTO toDto(Room room){

        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(room.getId());
        roomDTO.setNumber(room.getNumber());
        roomDTO.setPrice(room.getPrice());

        roomDTO.setHotelId(room.getHotel().getId());

        return roomDTO;
    }

    public static Room toEntity(RoomDTO roomDTO, Hotel hotel){

        Room room = new Room();

        room.setId(roomDTO.getId());
        room.setNumber(roomDTO.getNumber());
        room.setPrice(roomDTO.getPrice());
        room.setHotel(hotel);

        return room;
    }

}
