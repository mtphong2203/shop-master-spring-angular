package com.maiphong.shopmaster.controllers;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.maiphong.shopmaster.dtos.order.OrderCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.order.OrderMasterDTO;
import com.maiphong.shopmaster.models.OrderStatus;
import com.maiphong.shopmaster.response.CustomPageResponse;
import com.maiphong.shopmaster.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final PagedResourcesAssembler<OrderMasterDTO> pagedResource;

    public OrderController(OrderService orderService,
            PagedResourcesAssembler<OrderMasterDTO> pagedResource) {
        this.orderService = orderService;
        this.pagedResource = pagedResource;
    }

    @GetMapping
    public ResponseEntity<List<OrderMasterDTO>> getAllOrder() {
        List<OrderMasterDTO> orderMasters = orderService.getAll();
        return ResponseEntity.ok(orderMasters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderMasterDTO> getOrderById(@PathVariable UUID id) {
        OrderMasterDTO orderMaster = orderService.getById(id);
        return ResponseEntity.ok(orderMaster);
    }

    @GetMapping("/search-paginated")
    public ResponseEntity<?> searchPainatedOrder(
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false, defaultValue = "totalAmount") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String orderBy,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size) {
        Pageable pageable = null;

        if (orderBy.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        Page<OrderMasterDTO> orderMasters = orderService.searchPaginated(orderStatus, pageable);

        var pageModel = pagedResource.toModel(orderMasters);

        Collection<EntityModel<OrderMasterDTO>> data = pageModel.getContent();

        Links links = pageModel.getLinks();

        var response = new CustomPageResponse<EntityModel<OrderMasterDTO>>(data, links, pageModel.getMetadata());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderCreateUpdateDTO orderDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        OrderMasterDTO orderMaster = orderService.create(orderDTO);

        return ResponseEntity.status(201).body(orderMaster);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable UUID id,
            @Valid @RequestBody OrderCreateUpdateDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        OrderMasterDTO orderMaster = orderService.update(id, orderDTO);

        return ResponseEntity.ok(orderMaster);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = orderService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

}
