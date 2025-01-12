package com.proyecto.time.records;

import java.time.LocalDateTime;
import java.sql.Date;

import com.proyecto.time.entities.Usuario;

public record OrderRequest(
    //LocalDateTime checkInTime,
    //LocalDateTime checkOutTime,
    Date fecha,
    Long customerId,
    float total
) {

}
