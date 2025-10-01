package app.mapper;

import app.DTO.HotelDTO;
import app.entities.Hotel;

public class HotelMapper {

    public static HotelDTO toDTO(Hotel hotel) {
        HotelDTO hotelDTO = new HotelDTO();

        hotelDTO.setId(hotel.getId());
        hotelDTO.setHotelName(hotel.getName());
        hotelDTO.setHotelAddress(hotel.getAddress());

        if(hotel.getRooms() != null){
            hotelDTO.setRooms(
                    hotel.getRooms().stream()
                            .map(room -> RoomMapper.toDto(room))
                            .toList());
        }
        return hotelDTO;
    }

    public static Hotel toEntity(HotelDTO hotelDTO){
        Hotel hotel = new Hotel();

        hotel.setAddress(hotelDTO.getHotelAddress());
        hotel.setName(hotelDTO.getHotelName());
        hotel.setId(hotelDTO.getId());

        if(hotelDTO.getRooms() != null){
            hotel.setRooms(hotelDTO.getRooms()
                    .stream()
                    .map(roomDTO -> RoomMapper.toEntity(roomDTO, hotel))
                    .toList());

            hotel.getRooms().forEach(r -> r.setHotel(hotel));
        }
        return hotel;
    }
}
