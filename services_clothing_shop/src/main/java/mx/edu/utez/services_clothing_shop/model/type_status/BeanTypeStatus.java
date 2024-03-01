package mx.edu.utez.services_clothing_shop.model.type_status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "types_status")
public class BeanTypeStatus {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_type_status", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_type_status;

    //relacion uno a muchos con la tabla de status
    @OneToMany(mappedBy = "type_status")
    private List<BeanStatus> status;

}
