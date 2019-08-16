package group.gamelife.security.reactor.demo.repository;

import group.gamelife.security.reactor.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xiongyizhou on 2019/8/13 11:26 E-mail: xiongyizhou@powerpms.com
 * role repository / 角色的repository层
 * @author xiongyizhou
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {}
