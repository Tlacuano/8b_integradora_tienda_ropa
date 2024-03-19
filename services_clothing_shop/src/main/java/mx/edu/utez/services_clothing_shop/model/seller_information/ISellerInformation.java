package mx.edu.utez.services_clothing_shop.model.seller_information;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ISellerInformation extends JpaRepository<BeanSellerInformation, UUID> {
    @Query(value = "SELECT CONCAT(p.name, ' ', p.lastName) AS fullName, si.curp AS curp, si.privacyPolicyAgreement AS privacy FROM BeanSellerInformation si JOIN si.person p")
    List<Object[]> findEssentialSellerInformationInfo();

    boolean existsByIdSellerInformation(UUID idSelllerInformation);
    BeanSellerInformation findByIdSellerInformation(UUID idSellerInformation);
}
