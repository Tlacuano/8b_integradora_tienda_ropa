package mx.edu.utez.services_clothing_shop.service.requests_return_product;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductGetByIdResponseDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductPostRequestDTO;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_has_products.IOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_status.BeanOrderStatus;
import mx.edu.utez.services_clothing_shop.model.request_return_product.BeanRequestReturnProduct;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.model.return_product_gallery.BeanReturnProductGallery;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import mx.edu.utez.services_clothing_shop.model.request_return_product.IRequestsReturnProduct;
import mx.edu.utez.services_clothing_shop.model.return_product_gallery.IReturnProductGallery;
import java.util.List;
import java.util.UUID;

import mx.edu.utez.services_clothing_shop.model.request_return_product.IRequestsReturnProduct.ReturnRequestProjection;

@Service
public class RequestsReturnProductService {

    private final IRequestsReturnProduct requestsReturnProductRepository;
    private final IRequestStatus requestStatusRepository;
    private final IReturnProductGallery returnProductGalleryRepository;
    private final EmailService emailService;
    private final IOrderHasProducts orderHasProductsRepository;

    public RequestsReturnProductService(IRequestsReturnProduct requestsReturnProductRepository, IRequestStatus requestStatusRepository, IReturnProductGallery returnProductGalleryRepository, EmailService emailService, IOrderHasProducts orderHasProductsRepository) {
        this.requestsReturnProductRepository = requestsReturnProductRepository;
        this.requestStatusRepository = requestStatusRepository;
        this.returnProductGalleryRepository = returnProductGalleryRepository;
        this.emailService = emailService;
        this.orderHasProductsRepository = orderHasProductsRepository;
    }

    @Transactional
    public RequestsReturnProductDTO putRequestReturnProduct(UUID requestId, String status, String rejectionReason, String email) {
        BeanRequestReturnProduct request = requestsReturnProductRepository.findById(requestId)
                .orElseThrow(() -> new CustomException("Request not found"));

        BeanRequestStatus requestStatus = requestsReturnProductRepository.findRequestStatusByName(status)
                .orElseThrow(() -> new CustomException("Status not found"));

        request.setStatus(requestStatus);
        request.setRejectionReason(rejectionReason);
        requestsReturnProductRepository.save(request);

        if (status.equals("Aprobado")) {
            BeanOrderStatus orderStatus = requestsReturnProductRepository.findOrderStatusByName("Reembolsado")
                    .orElseThrow(() -> new CustomException("Order status not found"));
            BeanOrderHasProducts orderHasProduct = request.getOrderHasProduct();
            orderHasProduct.setStatus(orderStatus);
            orderHasProductsRepository.save(orderHasProduct);

            emailService.sendEmail(
                    email,
                    "Solicitud de devolución aprobada",
                    "Su solicitud de devolución ha sido aprobada",
                    "Estamos procesando su reembolso. Gracias por su paciencia.",
                    "Reembolso en proceso"
            );
        } else if (status.equals("Rechazado")) {
            emailService.sendEmail(
                    email,
                    "Solicitud de devolución rechazada",
                    "Su solicitud de devolución ha sido rechazada",
                    "Motivo del rechazo: " + rejectionReason,
                    "Puede contactarnos para más detalles"
            );
        }
        return convertToDTO(request);
    }


    public RequestsReturnProductGetByIdResponseDTO getRequestReturnProductInfoById(UUID idRequestReturnProduct) {
        List<IRequestsReturnProduct.ReturnProductInfoProjection> results = requestsReturnProductRepository.findReturnProductInfoById(idRequestReturnProduct);
        if (results.isEmpty()) {
            throw new CustomException("requestReturnProduct.id.notFound");
        }
        return convertToReturnProductInfoDTO(results.get(0));
    }

    private RequestsReturnProductGetByIdResponseDTO convertToReturnProductInfoDTO(IRequestsReturnProduct.ReturnProductInfoProjection request) {
        RequestsReturnProductGetByIdResponseDTO dto = new RequestsReturnProductGetByIdResponseDTO();
        dto.setIdRequestReturnProduct(request.getIdRequestReturnProduct());
        dto.setReturnReason(request.getReturnReason());
        dto.setIdOrderProduct(request.getIdOrderProduct());
        dto.setIdOrder(request.getIdOrder());
        dto.setOrderNumber(request.getOrderNumber());
        dto.setAmount(request.getAmount());
        dto.setEmail(request.getEmail());
        dto.setImage(request.getImage());
        dto.setPrice(request.getPrice());
        dto.setProductName(request.getProductName());
        return dto;
    }

    @Transactional
    public RequestsReturnProductDTO postRequestReturnProduct(RequestsReturnProductPostRequestDTO requestDTO) {
        BeanOrderHasProducts orderHasProduct = requestsReturnProductRepository.findFirstByOrderNumber(requestDTO.getOrderNumber())
                .orElseThrow(() -> new CustomException("order.notfound"));

        boolean existsPendingRequest = requestsReturnProductRepository.existsByOrderHasProduct_Order_OrderNumberAndStatus_Status(
                requestDTO.getOrderNumber(), "Pendiente");
        if (existsPendingRequest) {
            throw new CustomException("Ya existe una solicitud de devolución pendiente para este producto.");
        }

        BeanRequestStatus requestStatus = requestStatusRepository.findByStatus("Pendiente")
                .orElseThrow(() -> new CustomException("Request status not found"));

        BeanRequestReturnProduct request = new BeanRequestReturnProduct();
        request.setOrderHasProduct(orderHasProduct);
        request.setStatus(requestStatus);
        request.setReturnReason(requestDTO.getReturnReason());

        BeanRequestReturnProduct savedRequest = requestsReturnProductRepository.save(request);
        if (requestDTO.getImage() != null && !requestDTO.getImage().isEmpty()) {
            BeanReturnProductGallery galleryImage = new BeanReturnProductGallery();
            galleryImage.setImage(requestDTO.getImage());
            galleryImage.setReturnProduct(savedRequest);
            returnProductGalleryRepository.save(galleryImage);
        }

        return convertToDTO(savedRequest);
    }


    public Page<ReturnRequestProjection> findRequestsByPageAndSearchTerm(Pageable pageable, String searchTerm) {
        return requestsReturnProductRepository.findRequestsWithOrderInfo(pageable, searchTerm);
    }


    private RequestsReturnProductDTO convertToDTO(BeanRequestReturnProduct request) {
        RequestsReturnProductDTO dto = new RequestsReturnProductDTO();
        dto.setIdRequestReturnProduct(request.getIdRequestReturnProduct());
        dto.setOrderHasProductId(request.getOrderHasProduct().getIdOrderProduct());
        dto.setStatusId(request.getStatus().getIdStatus());
        dto.setRejectionReason(request.getRejectionReason());
        return dto;
    }

    @Transactional
    public Object getRequestReturnProductByOrderProduct(UUID idOrderProduct) {
        BeanOrderHasProducts orderHasProduct = orderHasProductsRepository.findById(idOrderProduct)
                .orElseThrow(() -> new CustomException("order.notfound"));

        BeanRequestStatus requestStatus = requestStatusRepository.findByStatus("Pendiente")
                .orElseThrow(() -> new CustomException("requestStatus.notfound"));


        BeanRequestReturnProduct request = requestsReturnProductRepository.findByOrderHasProductAndStatus(orderHasProduct, requestStatus);

        return request != null;
    }
}
