package mx.edu.utez.services_clothing_shop.utils.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreUpdate;
import jakarta.servlet.http.HttpServletRequest;
import mx.edu.utez.services_clothing_shop.audit.context.ApplicationContextProvider;
import mx.edu.utez.services_clothing_shop.audit.model.AuditLog;
import mx.edu.utez.services_clothing_shop.audit.service.AuditLogService;
import mx.edu.utez.services_clothing_shop.utils.Convert;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuditEntityListener {

    @PostPersist
    private void afterCreate(Object target) {
        String newValue = Convert.toJSON(target);

        recordAuditAction(target, "CREATE", newValue);
    }

    @PostRemove
    private void afterDelete(Object target) {
        recordAuditAction(target, "DELETE", null);
    }

    @PreUpdate
    private void beforeUpdate(Object target) {

        String newValue = Convert.toJSON(target);

        recordAuditAction(target, "UPDATE", newValue);
    }

    private void recordAuditAction(Object target, String action, String newValue) {
        // Get the user and IP address from the SecurityContextAspect
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        String user = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : "anonymous";
        String ip = request != null ? request.getRemoteAddr() : "unknown";


        // Instantiate the AuditLog object and set the values
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setTableName(target.getClass().getSimpleName());

        // Encrypt the values
        String newValueCode = newValue != null ? EncryptionFunctions.encryptString(newValue) : newValue;
        auditLog.setNewValue(newValueCode);


        // Encrypt the user and IP address
        String userNameCode = user != null ? EncryptionFunctions.encryptString(user) : user;
        auditLog.setUserName(userNameCode);

        String ipAddressCode = ip != null ? EncryptionFunctions.encryptString(ip) : ip;
        auditLog.setIpAddress(ipAddressCode);

        ApplicationContextProvider.getApplicationContext().getBean(AuditLogService.class).recordAuditEvent(auditLog);
    }

}
