package group.gamelife.security.reactor.demo.service;

import group.gamelife.security.reactor.demo.entity.Operator;
import group.gamelife.security.reactor.demo.entity.Role;
import group.gamelife.security.reactor.demo.repository.OperatorRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by xiongyizhou on 2019/8/15 10:57
 * E-mail: xiongyizhou@powerpms.com
 * implement ReactiveUserDetailsService to find user in db
 * @author xiongyizhou
 */
@Service("userDetailsService")
@Log
public class IUserDetailService implements ReactiveUserDetailsService {

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {

        Optional<Operator> result = operatorRepository.findOperatorByName(username);
        if(result.isPresent()){
            Operator account = result.get();
            Set<Role> roles = account.getRoles();

           // Collection<GrantedAuthority> authorities = new HashSet<>();

            Collection<GrantedAuthority> authorities = roles.stream().collect(HashSet::new,(temp, role)-> temp.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName().toUpperCase())),HashSet::addAll);
            log.info("username:"+account.getName());
            log.info("roles:"+authorities);
            //return new User(account.getName(),account.getPassword(), authorities);
            return Mono.just(new User(account.getName(),account.getPassword(), authorities));
        }
        else {
            throw new UsernameNotFoundException("can not find this user / 查找用户失败");
        }
    }

}
