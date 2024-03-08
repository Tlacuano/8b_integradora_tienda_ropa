package mx.edu.utez.services_clothing_shop.model.request_status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_data_change.BeanRequestDataChange;
import mx.edu.utez.services_clothing_shop.model.request_return_product.BeanRequestReturnProduct;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.BeanRequestSellProduct;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "request_status")
public class BeanRequestStatus {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_status", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idStatus;

    @Column(name = "status", length = 50)
    private String status;

    //relacion uno a muchos con la tabla request sell product
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanRequestSellProduct> requestSellProduct;

    //relacion uno a muchos con la tabla de request data change
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanRequestDataChange> requestDataChange;

    //relacion uno a muchos con la tabla de request return product
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanRequestReturnProduct> requestReturnProduct;

    //relacion uno a muchos con la tabla de request become seller
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanRequestBecomeSeller> requestBecomeSeller;
}
