package mx.edu.utez.services_clothing_shop.model.request_become_seller;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface IRequestsBecomeSeller extends JpaRepository<BeanRequestsBecomeSeller, UUID> {
    @Query(nativeQuery = true, value = "CALL insert_request_become_seller(:email, :userSellerInformation)")
    void insertRequestBecomeSeller(@Param("email") String email, @Param("userSellerInformation") String userSellerInformation);

    @Query(nativeQuery = true, value = "CALL update_request_become_seller(:requestId, :status, :rejectionReason)")
    void updateRequestBecomeSeller(
            @Param("requestId") UUID requestId,
            @Param("status") String status,
            @Param("rejectionReason") String rejectionReason
    );


    Optional<BeanRequestsBecomeSeller> findByUserEmail(String email);

    public interface StatusProjection {
        BeanRequestStatus getStatus();
    }

    @Query("SELECT r.status FROM BeanRequestsBecomeSeller r")
    Page<StatusProjection> findAllStatuses(Pageable pageable);
}