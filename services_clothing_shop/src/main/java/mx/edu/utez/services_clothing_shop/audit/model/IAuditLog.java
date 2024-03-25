package mx.edu.utez.services_clothing_shop.audit.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAuditLog extends JpaRepository<AuditLog, UUID> {
}
