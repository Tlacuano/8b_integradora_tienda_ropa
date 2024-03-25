package mx.edu.utez.services_clothing_shop.audit.service;

import mx.edu.utez.services_clothing_shop.audit.model.AuditLog;
import mx.edu.utez.services_clothing_shop.audit.model.IAuditLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    private final IAuditLog iAuditLog;

    public AuditLogService(IAuditLog iAuditLog) {
        this.iAuditLog = iAuditLog;
    }

    @Async
    public void recordAuditEvent(AuditLog auditLog) {
        iAuditLog.save(auditLog);
    }

}
