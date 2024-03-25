package mx.edu.utez.services_clothing_shop.security;


import mx.edu.utez.services_clothing_shop.security.filter.DecryptingFilter;
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
    private final DecryptingFilter decryptingFilter;

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration, DecryptingFilter decryptingFilter) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.decryptingFilter = decryptingFilter;
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
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/post-account").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/post-admin-account").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/users/get-users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/delete-account").hasAnyRole("ADMIN", "BUYER", "SELLER")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users//get-user-detaiil-by-email-admin").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/put-status").hasAnyRole("ADMIN")

                                //Modulo personas

                                //Modulo shopping-cart
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/shopping-carts/get-shopping-cart").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/shopping-carts/put-shopping-cart").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/shopping-carts/post-shopping-cart").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/shopping-carts/delete-shopping-cart").permitAll()

                                //Modulo whislists
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/wishes-list/get-wish-list").hasRole("BUYER")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/wishes-list/post-wish-list").hasRole("BUYER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/wishes-list/put-wish-list").hasRole("BUYER")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/wishes-list/delete-wish-list").hasRole("BUYER")

                                //Modulo requests-data-change
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-data-change/get-page").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-data-change/get-by-id-request-data-change").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-data-change/post-request-data-change").hasAnyRole("BUYER", "SELLER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-data-change/put-request-data-change").hasRole("ADMIN")

                                //Modulo requests-become-seller
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-become-seller/get-page").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-become-seller/get-by-email").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-become-seller/post-request-become-seller").hasRole("BUYER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-become-seller/put-request-become-seller").hasRole("ADMIN")

                                //Modulo requests-return-product
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-return-product/get-page").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-return-product/get-by-id-request-return-product").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-return-product/post-request-return-product").hasRole("BUYER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-return-product/put-request-return-product-status").hasRole("ADMIN")

                                //Modulo requests-sell-product
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-sell-product/get-page").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-sell-product/get-by-id-request-sell-product").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-sell-product/post-request-sell-product").hasRole("SELLER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-sell-product/put-request-sell-product").hasRole("ADMIN")

                                //Modulo address
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/addresses/get-addresses").hasAnyRole("BUYER", "SELLER")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/addresses/get-address").hasAnyRole("BUYER", "SELLER")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/addresses/post-address").hasAnyRole("BUYER", "SELLER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/addresses/put-address").hasAnyRole("BUYER", "SELLER")
                                .requestMatchers(HttpMethod.DELETE, "venta-ropa/api/addresses/delete-address").hasAnyRole("BUYER", "SELLER")

                                //Modulo return-product-gallery
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/return-product-galleries/get-return-product-galleries").hasAnyRole("ADMIN", "BUYER")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/return-product-galleries/get-return-product-gallery").hasAnyRole("ADMIN", "BUYER")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/return-product-galleries/post-return-product-gallery").hasRole("BUYER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/return-product-galleries/put-return-product-gallery").hasAnyRole("ADMIN", "BUYER")
                                .requestMatchers(HttpMethod.DELETE, "venta-ropa/api/return-product-galleries/delete-return-product-gallery").hasAnyRole("ADMIN", "BUYER")

                                //Modulo reviews
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/reviews/get-reviews-by-order-product-id").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/reviews/post-review").hasRole("BUYER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/reviews/put-review").hasRole("BUYER")
                                .requestMatchers(HttpMethod.DELETE, "venta-ropa/api/reviews/delete-review").hasRole("BUYER")

                                //Modulo seller-information
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/sellers-information/get-sellers-information").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/sellers-information/get-seller-information").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/sellers-information/post-seller-information").hasRole("SELLER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/sellers-information/put-seller-information").hasRole("SELLER")

                                //Modulo categories
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/categories/get-categories").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/categories/get-category").hasAnyRole("ADMIN", "SUPER_ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/categories/post-category").hasAnyRole("ADMIN", "SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/categories/put-category").hasAnyRole("ADMIN", "SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/categories/put-status-category").hasAnyRole("ADMIN", "SUPER_ADMIN")

                                //Modulo subcategories
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/subcategories/get-subcategories").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/subcategories/get-subcategory").hasAnyRole("ADMIN", "SUPER_ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/subcategories/post-subcategory").hasAnyRole("ADMIN", "SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/subcategories/put-subcategory").hasAnyRole("ADMIN", "SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/subcategories/put-status-subcategory").hasAnyRole("ADMIN", "SUPER_ADMIN")

                                //Modulo products
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-products").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-product").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-products-by-user").hasAnyRole("SELLER", "ADMIN", "SUPER_ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/post-product").hasRole("SELLER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/products/put-product").hasRole("SELLER")
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/products/put-status-product").hasAnyRole("SELLER", "ADMIN","SUPER_ADMIN")

                                //Modulo cloudinary
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/images/upload-images").hasAnyRole("BUYER", "SELLER", "ADMIN", "SUPER_ADMIN")
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/images/upload-image").hasAnyRole("BUYER", "SELLER", "ADMIN", "SUPER_ADMIN")

                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/payment-cards/get-payment-cards").permitAll()
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/payment-cards/get-payment-cards/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/payment-cards/post-payment-cards").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/payment-cards/put-payment-cards").permitAll()

                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/orders/get-orders").permitAll()
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/orders/get-orders/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/orders/post-orders").permitAll()
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/orders/put-orders").permitAll()

                                .anyRequest().authenticated())

                .addFilter(new JwtAuthenticationFilter(authenticationManager()))

                .addFilter(new JwtValidationFilter(authenticationManager()))

                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(decryptingFilter, JwtAuthenticationFilter.class)

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
