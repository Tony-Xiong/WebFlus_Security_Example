package group.gamelife.security.reactor.demo.config;

import group.gamelife.security.reactor.demo.service.IUserDetailService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Created by xiongyizhou on 2019/8/16 13:50
 * E-mail: xiongyizhou@powerpms.com
 *
 * @author xiongyizhou
 */
@EnableWebFluxSecurity
@Configuration
@Log
public class WebFluxSecurityConfig {

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

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http){

    http.formLogin()
        .loginPage("/login")
        .authenticationSuccessHandler((exchange, authentication) -> {
            log.info("登陆成功！！！！！！！！！！！！！！");
            ServerHttpResponse res = exchange.getExchange().getResponse();
            res.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
            return exchange.getChain().filter(exchange.getExchange().mutate().request(req->req.path("/index").method(HttpMethod.GET).build()).response(res).build());
            }).authenticationFailureHandler((exchange,exception) -> {
                log.info("出错啦！！！！！！！");
                ServerHttpResponse res = exchange.getExchange().getResponse();
                res.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
                return exchange.getChain().filter(exchange.getExchange().mutate().request(req->req.method(HttpMethod.GET).path("/login").build()).response(res).build());
            })
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler((webFilterExchange,authentication)-> {
                log.info("登出！！！！！！！！");
                ServerHttpResponse res = webFilterExchange.getExchange().getResponse();
                res.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
                return webFilterExchange.getChain().filter(webFilterExchange.getExchange().mutate().request(req->req.method(HttpMethod.GET).path("/login").build()).response(res).build());
            })
            .and()
            .authorizeExchange()
            .pathMatchers("/static/**","/h2/**","/login","/favicon.ico")
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
