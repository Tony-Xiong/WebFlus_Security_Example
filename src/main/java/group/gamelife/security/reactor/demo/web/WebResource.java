package group.gamelife.security.reactor.demo.web;

import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Collection;

/**
 * Created by xiongyizhou on 2019/8/16 15:33
 * E-mail: xiongyizhou@powerpms.com
 *
 * @author xiongyizhou
 */
@RestController
@Log
public class WebResource {

    @GetMapping("")
    public void defaultPage(){}

    @GetMapping("/index")
    public String index(Principal principal){
        String username = principal!=null?principal.getName():"null";
        Mono<SecurityContext> context = ReactiveSecurityContextHolder.getContext();
        context.subscribe(e->{
            Collection<? extends GrantedAuthority> authorities = e.getAuthentication().getAuthorities();
            System.out.println(authorities);
        });
        //Authentication auths = SecurityContextHolder.getContext().getAuthentication();
        //Collection<? extends GrantedAuthority> authorities = auths.getAuthorities();
        return "<!DOCTYPE html>\n"
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
                + ""
                + "</p>"
                + "<form action=\"/logout\" method=\"POST\">\n"
                + "    <button type=\"submit\" >logout</button>\n"
                + "</form>"
                + "</body>\n"
                + "</html>";
    }

    @GetMapping("/login")
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

    @GetMapping("/admin")
    public ResponseEntity<String> adminPage(){

        return ResponseEntity.ok("<!DOCTYPE html>\n"
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
                + "</html>");
    }
}
