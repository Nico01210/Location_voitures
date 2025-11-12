package com.microcommerce.service_vehicules.controller;
import com.microcommerce.service_vehicules.model.Vehicle;
import com.microcommerce.service_vehicules.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VehicleController {
    private final VehicleService service; // GET /vehicles
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return service.findAll(); } // GET /vehicles/{id}
    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable String id) {
        return service.findById(id); } // POST /vehicles
    @PostMapping public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return service.save(vehicle); }

}