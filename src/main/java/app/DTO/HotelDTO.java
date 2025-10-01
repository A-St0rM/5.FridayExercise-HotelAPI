package app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HotelDTO {
    private int id;
    private String hotelName;
    private String hotelAddress;
    private List<RoomDTO> rooms;
}
