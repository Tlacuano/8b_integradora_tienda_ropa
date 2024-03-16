package mx.edu.utez.services_clothing_shop.model.wish_list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Repository
public interface IWishList extends JpaRepository<BeanWishList, UUID>{
    List<BeanWishList> findAllByUser_email(String userEmail);
}
