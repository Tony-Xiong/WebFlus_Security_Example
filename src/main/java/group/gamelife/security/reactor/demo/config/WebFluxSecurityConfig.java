package group.gamelife.security.reactor.demo.config;

import group.gamelife.security.reactor.demo.service.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import reactor.core.publisher.Mono;

/**
 * Created by xiongyizhou on 2019/8/16 13:50
 * E-mail: xiongyizhou@powerpms.com
 *
 * @author xiongyizhou
 */
@EnableWebFluxSecurity
@Configuration
public class WebFluxSecurityConfig {

//    @Autowired
//    private ReactiveUserDetailsService userDetailsService;
//
//    @Bean
//    public ReactiveUserDetailsService userDetailsService (){
//        return userDetailsService;
//    }

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
        .authenticationSuccessHandler((exchange, authentication) -> exchange.getChain().filter(exchange.getExchange()).then())
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler((webFilterExchange,authentication)->Mono.empty())
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
