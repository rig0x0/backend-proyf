package com.proyecto.time.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.time.entities.Order;
import com.proyecto.time.entities.Usuario;

public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByUsuario(Usuario usuario);
}
