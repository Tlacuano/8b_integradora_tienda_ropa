package mx.edu.utez.services_clothing_shop.audit.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAuditLog extends JpaRepository<AuditLog, UUID> {
}
