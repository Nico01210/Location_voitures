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


    @PostMapping
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return service.save(vehicle); }

    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable String id, @RequestBody Vehicle vehicle) {
        Vehicle existing = service.findById(id);
        existing.setBrand(vehicle.getBrand());
        existing.setModel(vehicle.getModel());
        existing.setColor(vehicle.getColor());
        existing.setBasePrice(vehicle.getBasePrice());
        existing.setPricePerKm(vehicle.getPricePerKm());
        existing.setFiscalPower(vehicle.getFiscalPower());
        return service.save(existing);
    }


}