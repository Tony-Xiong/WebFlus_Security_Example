package group.gamelife.security.reactor.demo.config;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;

/**
 * Created by xiongyizhou on 2019/8/16 13:50
 * E-mail: xiongyizhou@powerpms.com
 * webFlux Security config
 * @author xiongyizhou
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
@Log
public class WebFluxSecurityConfig {
    /**
     * Password Encoder
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(rawPassword.toString());
            }
        };
    }

    /**
     * config spring security web filter chain
     * @param http
     * @return
     */
    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http) {

        http.formLogin()
                .loginPage("/login")
                .authenticationSuccessHandler((exchange, authentication) -> {
                    log.info("login success！！！！！！！！！！！！！！");
                    ServerHttpResponse res = new ServerHttpResponseDecorator(exchange.getExchange().getResponse());
                    res.getHeaders().add("Location", "/index");
                    res.setStatusCode(HttpStatus.FOUND);
                    return exchange.getChain().filter(exchange.getExchange().mutate().request(req -> req.method(HttpMethod.GET).build()).response(res).build());
                })
                .authenticationFailureHandler((exchange, exception) -> {
                    log.info("something wrong！！！！！！！");
                    ServerHttpResponse res = new ServerHttpResponseDecorator(exchange.getExchange().getResponse());
                    res.getHeaders().add("Location", "/login");
                    res.setStatusCode(HttpStatus.FOUND);
                    return exchange.getChain().filter(exchange.getExchange().mutate().request(req -> req.method(HttpMethod.GET).build()).response(res).build());
                })
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((webFilterExchange, authentication) -> {
                    log.info("logout success！！！！！！！！");
                    ServerHttpResponse res = webFilterExchange.getExchange().getResponse();
                    res.getHeaders().add("Location", "/login");
                    res.setStatusCode(HttpStatus.FOUND);
                    return webFilterExchange.getChain().filter(webFilterExchange.getExchange().mutate().response(res).build());
                })
                .and()
                .authorizeExchange()
                .pathMatchers("/static/**", "/h2/**", "/login", "/favicon.ico")
                .permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN);
        return http.build();
    }

}
