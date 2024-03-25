package mx.edu.utez.services_clothing_shop.utils.exception;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorDictionary {

    private static final Map<String, String> errorMessages = new HashMap<>();

    static {
        //error messages user
        errorMessages.put("user.email.notnull", "El email es obligatorio.");
        errorMessages.put("user.email.invalid", "El email no es válido.");
        errorMessages.put("user.password.notnull", "La contraseña es obligatoria.");
        errorMessages.put("user.email.exists", "El email no esta disponible.");


        //error messages person
        errorMessages.put("person.name.notnull", "El nombre es obligatorio.");
        errorMessages.put("person.name.pattern", "El nombre no debe contener números ni caracteres especiales.");
        errorMessages.put("person.lastName.notnull", "El primer apellido es obligatorio.");
        errorMessages.put("person.lastName.pattern", "El primer apellido no debe contener números ni caracteres especiales.");
        errorMessages.put("person.secondLastName.pattern", "El segundo apellido no debe contener números ni caracteres especiales.");
        errorMessages.put("person.phoneNumber.notnull", "El número de teléfono es obligatorio.");
        errorMessages.put("person.phoneNumber.pattern", "El número de teléfono no es válido.");
        errorMessages.put("person.birthday.notnull", "La fecha de nacimiento es obligatoria.");
        errorMessages.put("person.birthday.age", "El usuario debe ser mayor de edad.");
        errorMessages.put("person.gender.notnull", "El género es obligatorio.");

        //error messages payment card
        errorMessages.put("payment.cardholderName.notnull", "El nombre del titular de la tarjeta es obligatorio.");
        errorMessages.put("payment.cardNumber.notnull", "El número de la tarjeta es obligatorio.");
        errorMessages.put("payment.cardNumber.size", "El número de la tarjeta debe tener 16 dígitos.");
        errorMessages.put("payment.cardNumber.invalid", "El número de la tarjeta no es válido.");
        errorMessages.put("payment.expirationDate.notnull", "La fecha de expiración es obligatoria.");
        errorMessages.put("payment.expirationDate.invalid", "La fecha de expiración no es válida.");
        errorMessages.put("payment.cvv.notnull", "El código de seguridad es obligatorio.");
        errorMessages.put("payment.cvv.invalid", "El código de seguridad no es válido.");
        errorMessages.put("payment.cvv.size", "El código de seguridad debe tener 3 o 4 dígitos.");
        errorMessages.put("payment.minimum.card", "El usuario debe tener registrada al menos una tarjeta de crédito.");
        errorMessages.put("payment.card.notFound", "La tarjeta de crédito no fue encontrada.");
        errorMessages.put("payment.card.registered", "La tarjeta de crédito ya está registrada.");
        //error messages order
        errorMessages.put("order.orderDate.notnull", "La fecha de la orden es obligatoria.");

        //error messages wish list
        errorMessages.put("wishList.notFound", "La lista de deseos no fue encontrada.");
        errorMessages.put("wishList.product.notExists", "El producto no fue encontrado.");
        errorMessages.put("wishList.product.exists", "El producto ya está en la lista de deseos.");
        errorMessages.put("wishList.id.automatic", "El id de la lista de deseos es automático no manual.");
        errorMessages.put("wishList.id.notnull", "El id de la lista de deseos es obligatorio.");
        errorMessages.put("wishList.id.notfound", "La lista de deseos no fue encontrada.");
        errorMessages.put("wishList.amount.notnull", "La cantidad es obligatoria y no negativa.");
        errorMessages.put("wishList.amount.error", "La cantidad es mayor a la disponible.");
        errorMessages.put("wishList.user.notFound", "La lista de deseos del usuario no fue encontrada.");
        errorMessages.put("wishList.user.notnull", "El usuario es obligatorio.");
        errorMessages.put("wishList.product.notFound", "La lista de deseos del producto no fue encontrada.");
        errorMessages.put("wishList.product.notnull", "El producto es obligatorio.");
        errorMessages.put("wishList.delete.error", "Error al eliminar la lista de deseos.");
        errorMessages.put("wishList.update.amount.error", "Error al actualizar la misma cantidad.");


        //error messages shopping cart
        errorMessages.put("shoppingCart.id.automatic", "El id del carrito de compras es automático no manual.");
        errorMessages.put("shoppingCart.user.notnull", "El usuario es obligatorio.");
        errorMessages.put("shoppingCart.product.exists", "El producto ya está en el carrito de compras.");
        errorMessages.put("shoppingCart.notFound", "El carrito de compras no fue encontrado.");
        errorMessages.put("shoppingCart.amount.notnull", "La cantidad es obligatoria.");
        errorMessages.put("shoppingCart.amount.error", "La cantidad es mayor a la disponible.");
        errorMessages.put("shoppingCart.product.notnull","El producto es obligatorio.");
        errorMessages.put("shoppingCart.product.notFound","El producto no fue encontrado.");
        errorMessages.put("shoppingCart.save.error","Error al guardar el carrito de compras.");
        errorMessages.put("shoppingCart.delete.error","Error al eliminar el carrito de compras.");
        errorMessages.put("shoppingCart.update.error","Error al actualizar el carrito de compras.");
        errorMessages.put("shoppingCart.update.amount.error","Error al actualizar misma cantidad.");
        errorMessages.put("shoppingCart.id.notnull", "El id del carrito de compras es obligatorio.");
        errorMessages.put("shoppingCart.id.notfound", "El carrito de compras no fue encontrado.");
        //error image uploading
        errorMessages.put("image.upload.error", "Error al subir imagen.");

        //error messages category
        errorMessages.put("category.id.notnull", "El id de la categoría es obligatorio.");
        errorMessages.put("category.category.notnull", "La categoría es obligatoria.");
        errorMessages.put("category.category.size", "La categoría debe tener entre 5 y 30 caracteres.");
        errorMessages.put("category.image.notnull", "La imagen es obligatoria.");
        errorMessages.put("category.image.size", "La imagen debe tener máximo 255 caracteres.");

        //error messages subcategory
        errorMessages.put("subcategory.id.notnull", "El id de la subcategoría es obligatorio.");
        errorMessages.put("subcategory.subcategory.notnull", "La subcategoría es obligatoria.");
        errorMessages.put("subcategory.subcategory.size", "La subcategoría debe tener entre 5 y 30 caracteres.");
        errorMessages.put("subcategory.image.notnull", "La imagen es obligatoria.");
        errorMessages.put("subcategory.image.size", "La imagen debe tener máximo 255 caracteres.");
        errorMessages.put("subcategory.category.notnull", "La categoría es obligatoria.");

        //error messages product
        errorMessages.put("product.id.notnull", "El id del producto es obligatorio.");
        errorMessages.put("product.id.nonempty", "El id del producto no puede estar vacío.");
        errorMessages.put("product.name.notnull", "El nombre del producto es obligatorio.");
        errorMessages.put("product.name.size", "El nombre del producto debe tener entre 5 y 30 caracteres.");
        errorMessages.put("product.description.notnull", "La descripción del producto es obligatoria.");
        errorMessages.put("product.description.size", "La descripción del producto debe tener entre 20 y 100 caracteres.");
        errorMessages.put("product.price.min", "El precio del producto debe ser mayor a 0.");
        errorMessages.put("product.amount.min", "La cantidad del producto debe ser mayor a 0.");
        errorMessages.put("product.subcategory.notnull", "La subcategoría del producto es obligatoria.");
        errorMessages.put("product.subcategory.nonempty", "La subcategoría del producto no puede estar vacía.");
        errorMessages.put("product.user.notnull", "El usuario del producto es obligatorio.");
        errorMessages.put("product.user.nonempty", "El usuario del producto no puede estar vacío.");
        errorMessages.put("product.productGallery.nonempty", "La galería del producto no puede estar vacía.");

        //error messages address
        errorMessages.put("addresses.notfound", "No hay direcciones registradas.");
        errorMessages.put("address.notfound", "No hay direcciones registradas.");
        errorMessages.put("address.idAddress.notnull", "El id de la dirección es obligatorio.");
        errorMessages.put("address.idAddress.notfound", "La dirección no fue encontrada.");
        errorMessages.put("address.address.notnull", "La dirección es obligatoria.");
        errorMessages.put("address.address.size", "La dirección debe ser mayor a 10 caracteres y menor o igual a 100 caracteres.");
        errorMessages.put("address.referencesAddress.size", "La longitud de las referencias de la dirección debe ser mayor a 5 y menor o igual a 255 caracteres");
        errorMessages.put("address.postalCode.notnull", "El código postal es requerido");
        errorMessages.put("address.postalCode.size", "La longitud del código postal debe ser de 5 caracteres");
        errorMessages.put("address.state.notnull", "El estado es requerido");
        errorMessages.put("address.state.size", "La longitud del estado debe ser mayor a 5 caracteres y menor o igual a 100 caracteres");
        errorMessages.put("address.street.notnull", "La calle es requerida");
        errorMessages.put("address.street.size", "La longitud de la calle debe ser mayor a 5 caracteres y menor o igual a 50 caracteres");
        errorMessages.put("address.neighborhood.notnull", "El vecindario es requerido");
        errorMessages.put("address.neighborhood.size", "La longitud del vecindario debe ser mayor a 5 caracteres y menor o igual a 50 caracteres");
        errorMessages.put("address.personId.notnull", "El ID de la persona es requerido");
        errorMessages.put("address.statusId.notnull", "El ID del estado es requerido");

        //error messages return product gallery
        errorMessages.put("returnProductGallery.notfound", "No hay imágenes de devolución registradas.");
        errorMessages.put("returnProductGallery.idImage.notnull", "El id de la imagen de devolución es obligatorio.");
        errorMessages.put("returnProductGallery.idReturnProductGallery.notfound", "La imagen de devolución no fue encontrada.");
        errorMessages.put("returnProductGallery.image.notnull", "La imagen es obligatoria.");
        errorMessages.put("returnProductGallery.requestReturnProductId.notnull", "El id de la solicitud de devolución es obligatorio.");
        errorMessages.put("returnProductGallery.idRequestReturnProduct.notfound", "El id de la solicitud de devolución no fue encontrada.");

        //error messages review
        errorMessages.put("review.orderHasProductId.notnull", "El id de la orden del producto es obligatorio.");
        errorMessages.put("review.idReview.notnull", "El id de la reseña es obligatorio.");
        errorMessages.put("review.idReview.notfound", "La reseña no fue encontrada.");
        errorMessages.put("review.idOrderHasProduct.notfound", "La orden del producto no fue encontrada.");
        errorMessages.put("review.orderHasProduct.exists", "Ya existe una reseña para este producto.");
        errorMessages.put("review.comment.notnull", "El comentario es obligatorio.");
        errorMessages.put("review.comment.size", "El comentario debe tener entre 5 y 255 caracteres.");
        errorMessages.put("review.assessment.min", "La calificación debe ser mayor a 0.");
        errorMessages.put("review.assessment.max", "La calificación debe ser menor a 6.");
        errorMessages.put("review.reviewDate.notnull", "La fecha de la reseña es obligatoria.");

        //error messages data change
        errorMessages.put("dataChange.email.notnull", "El email es obligatorio.");
        errorMessages.put("dataChange.email.invalid", "El email no es válido.");
        errorMessages.put("dataChange.status.notnull", "El status es obligatorio.");
        errorMessages.put("dataChange.idRequestDataChange.notnull", "El id de la solicitud de cambio de datos es obligatorio.");
        errorMessages.put("dataChange.request.notFound", "La solicitud no fue encontrada.");
        errorMessages.put("dataChange.JSON.invalid", "El JSON no es válido.");

        //error messages request return product
        errorMessages.put("requestReturnProduct.id.notnull", "El id de la solicitud de devolución es obligatorio.");
        errorMessages.put("requestsReturnProduct.status.notnull", "El status de la solicitud de devolución es obligatorio.");
        errorMessages.put("requestsReturnProduct.rejectionReason.invalid", "El motivo de rechazo no es válido.");
        errorMessages.put("requestsReturnProduct.status.invalid", "El status no es válido.");
        errorMessages.put("requestsReturnProduct.status.notFound", "El status no fue encontrado.");

        //error de configuracion
        errorMessages.put("controller.advice.encrypter", "Algo salió mal al encriptar la información.");


        //error messages request become seller
        errorMessages.put("requestBecomeSeller.email.notnull", "El email es obligatorio.");
        errorMessages.put("requestBecomeSeller.email.invalid", "El email no es válido.");
        errorMessages.put("requestBecomeSeller.status.notnull", "El status es obligatorio.");
        errorMessages.put("requestBecomeSeller.status.invalid", "El status no es válido.");
        errorMessages.put("requestBecomeSeller.request.notFound", "La solicitud no fue encontrada.");

        errorMessages.put("requestBecomeSeller.userSellerInformation.empty", "La información del usuario vendedor es obligatoria.");
        errorMessages.put("requestBecomeSeller.JSON.invalid", "El JSON no es válido.");


        //error messages request sell product
        errorMessages.put("requestSellProduct.status.invalid", "El status no es válido.");
        errorMessages.put("requestSellProduct.rejectionReason.invalid", "El motivo de rechazo no es válido.");

        //twilio
        errorMessages.put("twilio.service.error", "Error al enviar el codigo.");

    }

    public String getErrorMessage(String errorCode) {
        return errorMessages.getOrDefault(errorCode, "Error desconocido.");
    }
}