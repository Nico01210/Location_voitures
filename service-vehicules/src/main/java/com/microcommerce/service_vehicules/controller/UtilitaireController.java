package com.microcommerce.service_vehicules.controller;

import com.microcommerce.service_vehicules.service.UtilitaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.microcommerce.service_vehicules.model.Utilitaire;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RestController


@RequestMapping("/api/utilitaires")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UtilitaireController {

    private final UtilitaireService service;

    @GetMapping
    public List<Utilitaire> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Utilitaire getOne(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Utilitaire> create(@RequestBody Utilitaire u) {
        Utilitaire saved = service.save(u);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getRegistration())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }


    @PutMapping("/{registration}")
    public ResponseEntity<Utilitaire> update(
            @PathVariable String registration,
            @RequestBody Utilitaire updatedUtilitaire
    ) {
        try {
            Utilitaire updated = service.update(registration, updatedUtilitaire);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

