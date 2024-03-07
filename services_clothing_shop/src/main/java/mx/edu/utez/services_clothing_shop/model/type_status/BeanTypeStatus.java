package mx.edu.utez.services_clothing_shop.model.type_status;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_type_status", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idTypeStatus;

    @Column(name = "name_type_status", length = 100)
    private String nameTypeStatus;

    //relacion uno a muchos con la tabla de status
    @OneToMany(mappedBy = "typeStatus")
    @JsonIgnore
    private List<BeanStatus> status;

}
