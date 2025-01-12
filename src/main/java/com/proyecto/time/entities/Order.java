package com.proyecto.time.entities;

import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "table_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long trackingId;
    private Long orderId;

    @Column(nullable = false)
    private Date fecha;
    //private LocalDateTime checkInTime;

    //private LocalDateTime checkOutTime;

    private Long customerId;

    private float total;

    @ManyToOne
    @JoinColumn(name = "employe_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    // Campo calculado
    @JsonProperty("employe_Id")
    public Long getEmployeId() {
        return usuario != null ? usuario.getId() : null;
    }
}