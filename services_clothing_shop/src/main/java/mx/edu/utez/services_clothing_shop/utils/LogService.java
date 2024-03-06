package mx.edu.utez.services_clothing_shop.utils;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "control_table")
@Entity
public class LogService {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_control", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idControl;

    @Column(name = "action", length = 100)
    private String action;

    @Column(name = "table_name", length = 100)
    private String tableName;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "user_name", length = 100)
    private String user_Name;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDate createdAt;
}
