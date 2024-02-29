package mx.edu.utez.services_clothing_shop.model.people;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "people")
@Entity
public class BeanPeople {
    @Id
    @Column(name = "id_person")
    private UUID idPeople;

    //relacion uno a uno con la tabla users, usamos la misma llave primaria
    @OneToOne
    @MapsId
    @JoinColumn(name = "fk_id_user")
    private BeanUser user;



}
