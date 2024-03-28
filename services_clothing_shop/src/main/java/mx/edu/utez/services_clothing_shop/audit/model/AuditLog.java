package mx.edu.utez.services_clothing_shop.audit.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "control_table")
@Entity
@Data
public class AuditLog {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_control", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idControl;

    @Column(name = "action", length = 100)
    private String action;

    @Column(name = "table_name", length = 100)
    private String tableName;

    @Column(name = "new_value", columnDefinition = "JSON")
    private String newValue;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "ip_address", length = 100)
    private String ipAddress;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
