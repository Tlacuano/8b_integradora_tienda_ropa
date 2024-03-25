package mx.edu.utez.services_clothing_shop.model.request_data_change;

import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRequestsDataChange extends JpaRepository<BeanRequestDataChange, UUID> {
    @Query(nativeQuery = true, value = "CALL insert_request_data_change(:email, :newUserInformation)")
    void insertRequestDataChange(@Param("email") String email, @Param("newUserInformation") String newUserInformation);

    @Query(nativeQuery = true, value="CALL update_request_data_change(:requestId, :status, :rejectionReason)")
    void updateRequestDataChange(
            @Param("requestId") UUID requestId,
            @Param("status") String status,
            @Param("rejectionReason") String rejectionReason
    );

    @Query("SELECT r.user FROM BeanRequestDataChange r WHERE r.user.email = :email")
    Optional<BeanUser> findUserByEmail(@Param("email") String email);

    @Query("SELECT s FROM BeanRequestStatus s WHERE s.status = :status")
    Optional<BeanRequestStatus> findStatusByStatusName(@Param("status") String status);


    interface RequestDataChangeStatusProjection {
        BeanRequestStatus getStatus();
    }

    @Query("SELECT r.status FROM BeanRequestDataChange r")
     Page<RequestDataChangeStatusProjection> findAllStatuses(Pageable pageable);

    @Query("SELECT r.idRequestDataChange as requestId, r.status as status, p.name as personName, p.lastName as personLastName FROM BeanRequestDataChange r LEFT JOIN r.user u LEFT JOIN u.person p")
    Page<RequestDataChangeStatusPersonProjection> findAllStatusesWithPersonNameAndLastName(Pageable pageable);

    @Query("SELECT r.user.person.idPerson FROM BeanRequestDataChange r WHERE r.idRequestDataChange = :requestId")
    UUID findPersonIdByRequestId(@Param("requestId") UUID requestId);

    interface RequestDataChangeStatusPersonProjection {
        UUID getRequestId();
        BeanRequestStatus getStatus();
        String getPersonName();
        String getPersonLastName();
    }

}

