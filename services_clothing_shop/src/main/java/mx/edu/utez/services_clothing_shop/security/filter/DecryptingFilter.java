package mx.edu.utez.services_clothing_shop.security.filter;

import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.*;

@Component
@Order(1)
public class DecryptingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Obtener el cuerpo de la petición
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = httpRequest.getReader();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // Decodificar el cuerpo de la petición
        String body = stringBuilder.toString();
        String decodedBody = EncryptionFunctions.decryptString(body);
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBody.getBytes());
        chain.doFilter(new HttpServletRequestWrapper(httpRequest) {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new ServletInputStream() {
                    public int read() throws IOException {
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
                        // No implementado
                    }
                };
            }

            @Override
            public int getContentLength() {
                return decodedBody.getBytes().length;
            }

        }, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
