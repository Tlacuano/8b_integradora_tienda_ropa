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

    private final String ADMIN = "ADMIN";
    private final String SUPERADMIN = "SUPERADMIN";
    private final String BUYER = "BUYER";
    private final String SELLER = "SELLER";


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
                                //captcha
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/captcha/validate").permitAll()
                                //Modulo usuarios
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/post-account").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/delete-incomplete-account").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/exist-by-email").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/verify-email").permitAll()

                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/post-admin-account").hasAnyRole(SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/get-by-email").hasRole(SELLER)
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/users/get-page").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/get-page-by-email").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/delete-account").hasAnyRole(ADMIN, BUYER, SELLER, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/get-user-detail-by-email-admin").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/put-status").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/get-profile").hasAnyRole(ADMIN, BUYER, SELLER, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/change-password").hasAnyRole(ADMIN, BUYER, SELLER, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/resend-email-code").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/restore-password").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/delete-account-admin").hasAnyRole(ADMIN, SUPERADMIN)


                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/users/get-page-admins").hasRole(SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/users/get-page-admins-by-email").hasRole(SUPERADMIN)


                                //Modulo personas
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/person/post-personal-information").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/person/delete-personal-information-incomplete").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/person/verify-phone").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/person/resend-phone-code").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/person/get-personal-information").hasAnyRole(BUYER, SELLER, ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/person/put-personal-information").hasAnyRole(BUYER, SELLER, ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/person/put-picture").hasAnyRole(BUYER, SELLER, ADMIN, SUPERADMIN)

                                //Modulo shopping-cart
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/shopping-carts/get-shopping-cart").hasRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/shopping-carts/put-shopping-cart").hasRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/shopping-carts/post-shopping-cart").hasRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/shopping-carts/delete-shopping-cart").hasRole(BUYER)

                                //Modulo whislists
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/wishes-list/get-wish-list").hasRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/wishes-list/post-wish-list").hasRole(BUYER)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/wishes-list/put-wish-list").hasRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/wishes-list/delete-wish-list").hasRole(BUYER)

                                //Modulo requests-data-change
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-data-change/get-page").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-data-change/get-by-id-request-data-change").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-data-change/post-request-data-change").hasAnyRole(BUYER, SELLER)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-data-change/put-request-data-change").hasAnyRole(ADMIN, SUPERADMIN)

                                //Modulo requests-become-seller
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-become-seller/get-page").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-become-seller/get-page-by-user-email").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-become-seller/get-by-id-request-become-seller").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-become-seller/get-by-user-email").hasAnyRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-become-seller/post-request-become-seller").hasRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-become-seller/put-request-become-seller").hasAnyRole(ADMIN, SUPERADMIN)

                                //Modulo requests-return-product
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-return-product/get-page").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-return-product/get-by-id-request-return-product").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-return-product/post-request-return-product").hasRole(BUYER)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-return-product/put-request-return-product-status").hasAnyRole(ADMIN, SUPERADMIN)

                                //Modulo requests-sell-product
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/requests-sell-product/get-page").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-sell-product/get-by-id-request-sell-product").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-sell-product/post-request-sell-product").hasRole(SELLER)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/requests-sell-product/put-request-sell-product").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/requests-sell-product/get-page-by-user-email").hasAnyRole(ADMIN, SUPERADMIN)



                                //Modulo address
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/addresses/get-addresses").hasAnyRole(BUYER, SELLER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/addresses/get-address").hasAnyRole(BUYER, SELLER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/addresses/post-address").hasAnyRole(BUYER, SELLER)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/addresses/put-address").hasAnyRole(BUYER, SELLER)
                                .requestMatchers(HttpMethod.DELETE, "venta-ropa/api/addresses/delete-address").hasAnyRole(BUYER, SELLER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/addresses/get-addresses-by-email").hasAnyRole(BUYER, SELLER)

                                //Modulo return-product-gallery
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/return-product-galleries/get-return-product-galleries").hasAnyRole(ADMIN, BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/return-product-galleries/get-return-product-gallery").hasAnyRole(ADMIN, BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/return-product-galleries/post-return-product-gallery").hasRole(BUYER)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/return-product-galleries/put-return-product-gallery").hasAnyRole(ADMIN, BUYER)
                                .requestMatchers(HttpMethod.DELETE, "venta-ropa/api/return-product-galleries/delete-return-product-gallery").hasAnyRole(ADMIN, BUYER)

                                //Modulo reviews
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/reviews/get-reviews-by-order-product-id").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/reviews/get-reviews-by-product-id").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/reviews/post-review").hasRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/reviews/put-review").hasRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/reviews/delete-review").hasRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/reviews/get-comprobant-to-review").hasRole(BUYER)

                                //Modulo seller-information
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/sellers-information/get-sellers-information").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/sellers-information/get-seller-information").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/sellers-information/post-seller-information").hasRole(SELLER)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/sellers-information/put-seller-information").hasRole(SELLER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/sellers-information/block-sell").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/sellers-information/unblock-sell").hasAnyRole(ADMIN, SUPERADMIN)

                                //Modulo categories
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/categories/get-categories").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/categories/get-category").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/categories/post-category").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/categories/put-category").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/categories/put-status-category").hasAnyRole(ADMIN, SUPERADMIN)

                                //Modulo subcategories
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/subcategories/get-by-category").permitAll()
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/subcategories/get-subcategories").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/subcategories/get-subcategories-by-subcategory").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/subcategories/get-subcategory").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/subcategories/post-subcategory").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/subcategories/put-subcategory").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/subcategories/put-status-subcategory").hasAnyRole(ADMIN, SUPERADMIN)

                                //Modulo products
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-by-category").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-by-subcategory").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-by-search-query").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-products").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-product").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/get-products-by-user").hasAnyRole(SELLER, ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/products/post-product").hasRole(SELLER)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/products/put-product").hasRole(SELLER)
                                .requestMatchers(HttpMethod.PUT, "venta-ropa/api/products/put-status-product").hasAnyRole(SELLER, ADMIN,SUPERADMIN)

                                //Modulo cloudinary
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/images/upload-images").hasAnyRole(BUYER, SELLER, ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/images/upload-image").hasAnyRole(BUYER, SELLER, ADMIN, SUPERADMIN)

                                //Modulo payment-cards
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/payment-cards/get-payment-card-by-user-email").hasAnyRole(BUYER)

                                //Modulo orders
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/orders/get-orders").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/orders/get-orders-by-order-number").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/orders/get-orders-by-user-email").hasAnyRole(BUYER, SELLER, ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/order/get-order-details").hasAnyRole(ADMIN, SUPERADMIN)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/order/get-orders-by-user-email").hasAnyRole(BUYER, SELLER, ADMIN, SUPERADMIN)

                                //Modulo order-has-products
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/order-has-products/get-orders-has-products-by-order-id").permitAll()
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/order-has-products/get-orders-has-products-by-buyer").hasAnyRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/order-has-products/get-orders-has-products-by-seller-and-status").hasAnyRole(SELLER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/order-has-products/get-orders-has-products-by-seller-and-order-number").hasAnyRole(SELLER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/order-has-products/cancel-sell-by-seller").hasAnyRole(SELLER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/order-has-products/mark-as-sent-by-seller").hasAnyRole(SELLER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/order-has-products/put-status-order-has-products").hasAnyRole(ADMIN, SUPERADMIN)

                                //Modulo transactions
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/transactions/create-checkout-session").hasRole(BUYER)
                                .requestMatchers(HttpMethod.POST, "venta-ropa/api/transactions/webhook").permitAll()

                                //Modulo order-status
                                .requestMatchers(HttpMethod.GET, "venta-ropa/api/order-status/get-order-status").hasAnyRole(SELLER)

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
