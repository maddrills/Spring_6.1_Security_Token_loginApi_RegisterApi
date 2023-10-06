package com.tryOne.AuthonBack.config.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.tryOne.AuthonBack.utils.RSAKeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

//security 5 oct 2023
//auth 2.0 /3
@Configuration
public class UsersSecurityConfig {

    //key 3.0 / 4
    private final RSAKeyProperties keys;

    // key 3.1
    public UsersSecurityConfig(RSAKeyProperties keys) {
        this.keys = keys;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //this check if the user is authenticated or not
    //auth 2.2
    //use the custom login details
    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        return new ProviderManager(daoProvider);
    }


    //auth 2.1
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.
                csrf(csrf -> csrf.disable())
                //permints all
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                //pass it through an http authentication form and authenticate it
                //any request should be authenticated
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/user/**").hasAnyRole("ADMIN","USER");
                    auth.anyRequest().authenticated();
                })
//                .httpBasic(Customizer.withDefaults())
                // key 3.4
                //video 1:44:00
                .oauth2ResourceServer((oauth2) ->
                        oauth2.jwt(jwtConfigurer ->{
                                System.out.println("reached the auth converter here");
                                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter());}
                        ))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


/*
    //auth 2.1
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.
                csrf(csrf -> csrf.disable())
                //permints all
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                //pass it through an http authentication form and authenticate it
                //any request should be authenticated
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.anyRequest().authenticated();
                })
//                .httpBasic(Customizer.withDefaults())
                // key 3.4
                //video 1:44:00
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
*/



    //auth 2.1
    //strategic openings but with only http basic no SSH
/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.
                csrf(csrf -> csrf.disable())
                //permints all
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                //pass it through an http authentication form and authenticate it
                //any request should be authenticated
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }
*/



    //auth 2.1
    //all post allow
/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.
                csrf(csrf -> csrf.disable())
                //permints all
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                //pass it through an http authentication form and authenticate it
                //any request should be authenticated
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }*/


    // key 3.2
    //this is going to allow us to take in some information like a token  the dicoder will decode the jwt encript
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    // key 3.3
    //take the info bundle it encrypt it using my signature and spit it out
    @Bean
    public JwtEncoder jwtEncoder(){

        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        System.out.println("Roles reached");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }

}
