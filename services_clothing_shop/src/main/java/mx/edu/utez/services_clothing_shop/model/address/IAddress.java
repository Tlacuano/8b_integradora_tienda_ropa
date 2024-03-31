package mx.edu.utez.services_clothing_shop.model.address;

import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAddress extends JpaRepository<BeanAddress, UUID> {

    @Query(value = "SELECT ba.address, ba.referencesAddress, ba.postalCode, ba.state, ba.street, ba.neighborhood, ba.status.idStatus FROM BeanAddress ba")
    List<Object[]> findEssentialAddressInfo();

    Optional<BeanAddress> findByIdAddress(UUID idAddress);

    @Query("SELECT a FROM BeanAddress a WHERE a.person.user.email = :email")
    List<BeanAddress> findAllByUserEmail(@Param("email") String email);

    @Query("SELECT p FROM BeanPerson p WHERE p.user.email = :email")
    Optional<BeanPerson> findPersonByUserEmail(@Param("email") String email);



}
