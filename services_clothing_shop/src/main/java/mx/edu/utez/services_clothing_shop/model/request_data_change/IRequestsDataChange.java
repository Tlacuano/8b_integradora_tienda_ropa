package mx.edu.utez.services_clothing_shop.model.request_data_change;

import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
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



    @Query("SELECT s FROM BeanRequestStatus s WHERE s.status = :status")
    Optional<BeanRequestStatus> findStatusByStatusName(@Param("status") String status);


    interface RequestDataChangeStatusProjection {
        BeanRequestStatus getStatus();
    }

    @Query("SELECT r.idRequestDataChange as requestId, r.status as status, p.name as personName, p.lastName as personLastName, p.picture as personPicture " +
            "FROM BeanRequestDataChange r LEFT JOIN r.user u LEFT JOIN u.person p " +
            "WHERE (:searchTerm = '' OR CONCAT(p.name, ' ', p.lastName) LIKE %:searchTerm%)")
    Page<RequestDataChangeStatusPersonProjection> findAllStatusesWithPersonNameAndLastName(Pageable pageable, @Param("searchTerm") String searchTerm);


    interface RequestDataChangeStatusPersonProjection {
        UUID getRequestId();
        BeanRequestStatus getStatus();
        String getPersonName();
        String getPersonLastName();
        String getPersonPicture();
    }

}

