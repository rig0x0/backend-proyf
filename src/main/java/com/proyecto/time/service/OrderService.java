package com.proyecto.time.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.time.entities.Order;
import com.proyecto.time.entities.Usuario;
import com.proyecto.time.records.OrderRequest;
import com.proyecto.time.respository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    public Order saveOrder(OrderRequest request, Usuario user) {
        System.out.println(user);
        var order = Order.builder()
            .fecha(request.fecha())
            .customerId(request.customerId())
            .total(request.total())
            .usuario(user)
            .build();
        orderRepository.save(order);
        return order;
    }

    public List<Order> obtenerRegistrosPorUsuario(Usuario usuario) {
        return orderRepository.findByUsuario(usuario);
    }
}
