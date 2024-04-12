package mx.edu.utez.services_clothing_shop.security.filter;

import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Component
@Order(1)
public class DecryptingFilter implements Filter {
    private static final String APPJSON = "application/json";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getAttribute("bodyDecrypted") != null) {
            chain.doFilter(request, response);
            return;
        }

        // Obtener el tipo de contenido de la petición
        String contentType = httpRequest.getHeader("Content-Type");

        //validar el tipo de contenido
        if ("application/x-www-form-urlencoded".equals(contentType)) {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = httpRequest.getReader()) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            String body = stringBuilder.toString();
            String decodedBody = EncryptionFunctions.decryptString(body);

            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBody.getBytes());

            HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStream() {
                        public int read() {
                            return byteArrayInputStream.read();
                        }

                        @Override
                        public boolean isFinished() {
                            return byteArrayInputStream.available() == 0;
                        }

                        @Override
                        public boolean isReady() {
                            return true;
                        }

                        @Override
                        public void setReadListener(ReadListener readListener) {
                            throw new CustomException("Not implemented");
                        }
                    };
                }

                @Override
                public int getContentLength() {
                    return decodedBody.getBytes().length;
                }

                @Override
                public BufferedReader getReader() throws IOException {
                    return new BufferedReader(new InputStreamReader(getInputStream()));
                }

                @Override
                public String getContentType() {
                    return APPJSON;
                }

                @Override
                public String getHeader(String name) {
                    if ("content-type".equalsIgnoreCase(name)) {
                        return APPJSON;
                    }
                    return super.getHeader(name);
                }

                @Override
                public Enumeration<String> getHeaders(String name) {
                    if ("content-type".equalsIgnoreCase(name)) {
                        return Collections.enumeration(List.of(APPJSON));
                    }
                    return super.getHeaders(name);
                }
            };

            wrappedRequest.setAttribute("bodyDecrypted", true);
            chain.doFilter(wrappedRequest, response);
            return;
        }
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig filterConfig) {
        // Implementation not required
    }

    @Override
    public void destroy() {
        // Implementation not required
    }
}
