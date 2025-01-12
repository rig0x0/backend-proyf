package com.proyecto.time.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.time.entities.Order;
import com.proyecto.time.entities.Usuario;
import com.proyecto.time.records.OrderRequest;
import com.proyecto.time.respository.OrderRepository;
import com.proyecto.time.respository.UsuarioRepository;
import com.proyecto.time.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UsuarioRepository usuarioRepository;

    public OrderController(OrderRepository orderRepository, UsuarioRepository usuarioRepository,
            OrderService orderService) {
        this.orderRepository = orderRepository;
        this.usuarioRepository = usuarioRepository;
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> obtenerTodos() {
        return orderRepository.findAll();
    }

    @PostMapping("/{usuarioId}")
    public Order registrarPedido(@PathVariable Long usuarioId, @RequestBody OrderRequest order) {
        Usuario user = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                System.out.println(user);
        return orderService.saveOrder(order, user);
    }

    // Obtener un registro específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> obtenerPorId(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar un registro existente
    @PutMapping("/{id}")
    public ResponseEntity<Order> actualizarPedido(@PathVariable Long id,
            @RequestBody Order detallesActualizados) {
        return orderRepository.findById(id).map(order -> {
            order.setFecha(detallesActualizados.getFecha());
            order.setCustomerId(detallesActualizados.getCustomerId());
            order.setTotal(detallesActualizados.getTotal());
            Order actualizado = orderRepository.save(order);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un registro por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarPedido(@PathVariable Long id) {
        return orderRepository.findById(id).map(order -> {
            orderRepository.delete(order);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Obtener todos los registros de time tracking de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Map<String, Object>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return usuarioRepository.findById(usuarioId).map(usuario -> {
            List<Order> registros = orderService.obtenerRegistrosPorUsuario(usuario);
            Map<String, Object> response = new HashMap<>();
            response.put("times", registros);
            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }
}
