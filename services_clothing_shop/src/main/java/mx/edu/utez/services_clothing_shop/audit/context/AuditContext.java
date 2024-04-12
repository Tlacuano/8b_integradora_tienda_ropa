package mx.edu.utez.services_clothing_shop.audit.context;

public class AuditContext {
    private static final ThreadLocal<String> userName = new ThreadLocal<>();
    private static final ThreadLocal<String> ipAddress = new ThreadLocal<>();

    public static void setUserName(String userName) {
        AuditContext.userName.set(userName);
    }

    public static String getUserName() {
        return userName.get();
    }

    public static void setIpAddress(String ipAddress) {
        AuditContext.ipAddress.set(ipAddress);
    }

    public static String getIpAddress() {
        return ipAddress.get();
    }

    public static void clear() {
        userName.remove();
        ipAddress.remove();
    }
}
