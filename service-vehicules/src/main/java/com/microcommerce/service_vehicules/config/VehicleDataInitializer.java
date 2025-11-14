package com.microcommerce.service_vehicules.config;

import com.microcommerce.service_vehicules.model.Car;
import com.microcommerce.service_vehicules.model.Motorcycle;
import com.microcommerce.service_vehicules.model.Utilitaire;
import com.microcommerce.service_vehicules.repository.VehicleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleDataInitializer {

    private final VehicleRepository vehicleRepository;

    @PostConstruct
    public void initializeVehicles() {
        // Vérifier si les véhicules existent déjà
        if (vehicleRepository.count() == 0) {
            // Créer des voitures
            Car car1 = new Car();
            car1.setRegistration("AB-123-CD");
            car1.setBrand("Toyota");
            car1.setModel("Corolla");
            car1.setColor("Noir");
            car1.setBasePrice(50.0);
            car1.setPricePerKm(0.25);
            car1.setFiscalPower(4);
            vehicleRepository.save(car1);

            Car car2 = new Car();
            car2.setRegistration("EF-456-GH");
            car2.setBrand("Renault");
            car2.setModel("Clio");
            car2.setColor("Bleu");
            car2.setBasePrice(40.0);
            car2.setPricePerKm(0.20);
            car2.setFiscalPower(3);
            vehicleRepository.save(car2);

            // Créer des motos
            Motorcycle moto1 = new Motorcycle();
            moto1.setRegistration("IJ-789-KL");
            moto1.setBrand("Honda");
            moto1.setModel("CB500");
            moto1.setColor("Rouge");
            moto1.setBasePrice(30.0);
            moto1.setPricePerKm(0.15);
            moto1.setFiscalPower(5);
            moto1.setCylinder(500);
            vehicleRepository.save(moto1);

            Motorcycle moto2 = new Motorcycle();
            moto2.setRegistration("MN-012-OP");
            moto2.setBrand("Yamaha");
            moto2.setModel("MT-07");
            moto2.setColor("Gris");
            moto2.setBasePrice(35.0);
            moto2.setPricePerKm(0.18);
            moto2.setFiscalPower(7);
            moto2.setCylinder(689);
            vehicleRepository.save(moto2);

            // Créer des utilitaires
            Utilitaire util1 = new Utilitaire();
            util1.setRegistration("QR-345-ST");
            util1.setBrand("Ford");
            util1.setModel("Transit");
            util1.setColor("Blanc");
            util1.setBasePrice(60.0);
            util1.setPricePerKm(0.30);
            util1.setFiscalPower(6);
            util1.setVolumeCargo(10);
            vehicleRepository.save(util1);

            Utilitaire util2 = new Utilitaire();
            util2.setRegistration("UV-678-WX");
            util2.setBrand("Peugeot");
            util2.setModel("Boxer");
            util2.setColor("Jaune");
            util2.setBasePrice(55.0);
            util2.setPricePerKm(0.28);
            util2.setFiscalPower(5);
            util2.setVolumeCargo(12);
            vehicleRepository.save(util2);

            System.out.println("✅ Véhicules initialisés avec succès dans la base de données H2 persistante");
        } else {
            System.out.println("✅ Base de données déjà initialisée avec " + vehicleRepository.count() + " véhicules");
        }
    }
}

