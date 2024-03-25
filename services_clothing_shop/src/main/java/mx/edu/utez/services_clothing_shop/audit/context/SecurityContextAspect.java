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
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();


        String user =  SecurityContextHolder.getContext().getAuthentication() == null ? "anonymous" : SecurityContextHolder.getContext().getAuthentication().getName();
        String ip = request.getRemoteAddr();


        AuditContext.setUserName(user);
        AuditContext.setIpAddress(ip);

        try {
            return joinPoint.proceed();
        } finally {
            AuditContext.clear();
        }
    }
}
