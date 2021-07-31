package lk.ubiquitouslibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckBookingDTO {

    private Long book;

    private Long video;

    private Integer availableQty;

    private LocalDate bookingStart;

    private LocalDate bookingEnd;

}
