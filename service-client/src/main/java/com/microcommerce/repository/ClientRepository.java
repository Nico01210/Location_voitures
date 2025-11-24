package com.microcommerce.repository;

import com.microcommerce.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    
    @Override
    @NonNull
    Optional<Client> findById(@NonNull Long id);
}
