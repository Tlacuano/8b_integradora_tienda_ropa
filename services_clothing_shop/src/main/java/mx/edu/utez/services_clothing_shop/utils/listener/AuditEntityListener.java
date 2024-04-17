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


    private final ThreadLocal<String> oldValue = new ThreadLocal<>();

    @PreUpdate
    private void beforeUpdate(Object target) {
        String oldValue = Convert.toJSON(target);
        String oldValueEmbeddedRemoved = Convert.removeEmbedded(oldValue);

        this.oldValue.set(oldValueEmbeddedRemoved);
    }

    @PostPersist
    private void afterCreate(Object target) {
        String newValue = Convert.toJSON(target);

        recordAuditAction(target, "CREATE", newValue, null);
    }

    @PostRemove
    private void afterDelete(Object target) {
        recordAuditAction(target, "DELETE", null, null);
    }

    @PostUpdate
    private void postUpdate(Object target) {
        String newValue = Convert.toJSON(target);
        String oldVal = oldValue.get();

        recordAuditAction(target, "UPDATE",  newValue, oldVal);

        oldValue.remove();
    }

    private void recordAuditAction(Object target, String action, String newValue, String oldValue) {
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

        // set Velues

        String newValueCode = newValue != null ? Convert.toJSON(newValue) : newValue;
        String newValueEmbeddedRemoved = Convert.removeEmbedded(newValueCode);

        auditLog.setOldValue(oldValue);
        auditLog.setNewValue(newValueEmbeddedRemoved);


        // Encrypt the user and IP address
        auditLog.setUserName(user);

        auditLog.setIpAddress(ip);

        ApplicationContextProvider.getApplicationContext().getBean(AuditLogService.class).recordAuditEvent(auditLog);
    }

}
