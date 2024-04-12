package mx.edu.utez.services_clothing_shop.model.wish_list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IWishList extends JpaRepository<BeanWishList, UUID>{
    List<BeanWishList> findAllByUser_email(String userEmail);

    List<BeanWishList> findByProduct_idProductAndUser_idUser(UUID idProduct, UUID idUser);

}
