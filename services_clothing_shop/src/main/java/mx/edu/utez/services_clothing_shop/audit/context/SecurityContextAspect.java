package mx.edu.utez.services_clothing_shop.audit.context;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
public class SecurityContextAspect {

    @Pointcut("execution(* mx.edu.utez.services_clothing_shop.service..*.*(..))")
    public void serviceLayer() {}

    @Around("serviceLayer()")
    public Object captureUserAndIp(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        String user = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : "anonymous";
        String ip = request != null ? request.getRemoteAddr() : "unknown";

        AuditContext.setUserName(user);
        AuditContext.setIpAddress(ip);

        try {
            return joinPoint.proceed();
        } finally {
            AuditContext.clear();
        }
    }
}
