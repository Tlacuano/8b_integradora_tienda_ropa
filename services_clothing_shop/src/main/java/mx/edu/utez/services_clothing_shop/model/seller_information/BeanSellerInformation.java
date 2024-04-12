package mx.edu.utez.services_clothing_shop.model.seller_information;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sellers_information")
@Entity
@EntityListeners(AuditEntityListener.class)
public class BeanSellerInformation {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID idSellerInformation;

    @Column(name = "tax_identification_number", length = 20)
    private String taxIdentificationNumber;

    @Column(name = "secondary_phone_number", length = 30)
    private String secondaryPhoneNumber;

    @Column(name = "privacy_policy_agreement")
    private boolean privacyPolicyAgreement;

    @Column(name = "image_identification", length = 100)
    private String imageIdentification;

    @Column(name = "curp", length = 18)
    private String curp;

    @Column(name = "block_sell", columnDefinition = "TINYINT(0)")
    private boolean blockSell;

    //relacion uno a uno con la tabla people
    @OneToOne
    @MapsId
    @JoinColumn(name = "fk_id_user")
    @JsonIgnore
    private BeanPerson person;
}
