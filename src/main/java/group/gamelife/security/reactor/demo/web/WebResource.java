package group.gamelife.security.reactor.demo.web;

import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.stream.Collector;

/**
 * Created by xiongyizhou on 2019/8/16 15:33
 * E-mail: xiongyizhou@powerpms.com
 *
 * @author xiongyizhou
 */
@Controller
@Log
public class WebResource {

    @GetMapping("")
    public Rendering defaultPage(ServerWebExchange webExchange){
        return Rendering.redirectTo("/index").build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/index")
    @ResponseBody
    public Mono<ResponseEntity<String>> index(Principal principal){
        String username = principal!=null?principal.getName():"null";
        Mono<Collection<? extends GrantedAuthority>> authorities = ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities).flatMap(Mono::just);
        return authorities.map(temp->ResponseEntity.ok("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Index</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>welcome</h1>\n"
                + "<h2>you are already login the system!</h2>\n"
                + "<h3>Good!</h3>\n"
                + "<h4>"
                + "username:"
                + username
                + "</h4>\n"
                + "<p>"
                + temp
                + "</p>"
                + "<form action=\"/logout\" method=\"POST\">\n"
                + "    <button type=\"submit\" >logout</button>\n"
                + "</form>"
                + "</body>\n"
                + "</html>")
        );
    }

    @GetMapping("/login")
    @ResponseBody
    public String loginPage(){
        return "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Login</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "\n"
                + "<h1>Login Page</h1>\n"
                + "<h3> default admin account: admin/admin </h3>"
                + "<h3> default user account: user1/123456 </h3>"
                + "<form action=\"/login\" method=\"post\">\n"
                + "    <div><p>username</p><input name=\"username\" type=\"text\"></div>\n"
                + "    <div><p>password</p><input name=\"password\" type=\"text\"></div>\n"
                + "    <button type=\"submit\">login</button>\n"
                + "</form>\n"
                + "\n"
                + "</body>\n"
                + "</html>";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    @ResponseBody
    public Mono<ResponseEntity<String>> adminPage(){

        return Mono.just(ResponseEntity.ok("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Login</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "\n"
                + "<h1>admin Page</h1>\n"
                + "<a href=\"/index\">return</a>"
                + "\n"
                + "</body>\n"
                + "</html>"));
    }
}
