package mx.edu.utez.services_clothing_shop.utils.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PreUpdate;
import mx.edu.utez.services_clothing_shop.audit.context.AuditContext;
import mx.edu.utez.services_clothing_shop.audit.model.AuditLog;
import mx.edu.utez.services_clothing_shop.audit.service.AuditLogService;
import mx.edu.utez.services_clothing_shop.utils.Convert;
import org.springframework.context.ApplicationContext;


public class AuditEntityListener {

    private final ApplicationContext applicationContext;

    public AuditEntityListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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

        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setTableName(target.getClass().getSimpleName());
        auditLog.setNewValue(newValue);
        auditLog.setUserName(AuditContext.getUserName());
        auditLog.setIpAddress(AuditContext.getIpAddress());

        applicationContext.getBean(AuditLogService.class).recordAuditEvent(auditLog);
    }

}
