package mx.edu.utez.services_clothing_shop.security;

import mx.edu.utez.services_clothing_shop.security.filter.JwtAuthenticationFilter;
import mx.edu.utez.services_clothing_shop.security.filter.JwtValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SpringSecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(auth ->
                        auth
                                //Modulo usuarios
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/post-account").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/post-admin-account").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/users/get-users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/delete-account").hasAnyRole("ADMIN", "BUYER", "SELLER")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/get-by-email").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/put-status").hasAnyRole("ADMIN")


                                //Modulo personas

                                //Modulo shopping-cart

                                //Modulo whislists
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/wishes-list/get-wish-list").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/wishes-list/post-wish-list").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/wishes-list/put-wish-list").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/wishes-list/delete-wish-list").permitAll()
                                //Modulo requests-data-change

                                //Modulo requests-become-seller

                                //Modulo requests-return-product

                                //Modulo requests-sell-product

                                //Modulo address

                                //Modulo return-product-gallery

                                //Modulo reviews

                                //Modulo seller-information

                                //Modulo categories

                                //Modulo subcategories

                                //Modulo products

                                //Modulo cloudinary

                                .requestMatchers(HttpMethod.POST, "/login").permitAll()

                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/categories/get-categories").permitAll()
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/subcategories/get-subcategories").permitAll()
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/products/get-products").permitAll()
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/reviews/get-reviews").permitAll()

                                //Apartir de aqui es necesaria la autenticacion del rol usuario
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/reviews/post-review").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/reviews/get-review/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/reviews/put-review").permitAll()


                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/shopping-carts/get-shopping-cart").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/shopping-carts/put-shopping-cart").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/shopping-carts/post-shopping-cart").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "venta-ropa/api/shopping-carts/delete-shopping-cart").permitAll()

                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/addresses/get-address").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/addresses/post-address").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/addresses/put-address").permitAll()

                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/payment-cards/get-payment-cards").permitAll()
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/payment-cards/get-payment-cards/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/payment-cards/post-payment-cards").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/payment-cards/put-payment-cards").permitAll()

                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/orders/get-orders").permitAll()
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/orders/get-orders/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/orders/post-orders").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/orders/put-orders").permitAll()

                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-data-change/get-request-data-change").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-data-change/post-request-data-change").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-data-change/put-request-data-change").permitAll()

                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-become-seller/get-request-become-seller/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-become-seller/post-request-become-seller").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-become-seller/put-request-become-seller").permitAll()


                                //Apartir de aqui es necesaria la autenticacion del rol vendedor
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-products").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-products-by-user").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-product").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/post-product").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/products/put-product").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/products/put-status-product").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/sellers-information/get-seller-information").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/sellers-information/put-seller-information").permitAll()

                                //Apartir de aqui es necesaria la autenticacion del rol administrador

                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-become-seller/get-requests-become-seller").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-become-seller/put-status-request-become-seller/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-sell-product/get-requests-sell-product").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-sell-product/put-status-request-sell-product/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-data-change/get-requests-data-change").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-data-change/put-status-request-data-change/**").permitAll()


                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-data-change/get-status-request-return-product/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-data-change/put-status-request-return-product/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/categories/get-categories").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/categories/get-category").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/categories/post-category").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/categories/put-category").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/categories/put-status-category").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/subcategories/get-subcategories").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/subcategories/get-subcategory").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/subcategories/post-subcategory").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/subcategories/put-subcategory").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/subcategories/put-status-subcategory").hasRole("ADMIN")

                                .anyRequest().authenticated())

                .addFilter(new JwtAuthenticationFilter(authenticationManager()))

                .addFilter(new JwtValidationFilter(authenticationManager()))

                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
