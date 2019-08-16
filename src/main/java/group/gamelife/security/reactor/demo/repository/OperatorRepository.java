package group.gamelife.security.reactor.demo.repository;

import group.gamelife.security.reactor.demo.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by xiongyizhou on 2019/8/13 10:58 E-mail: xiongyizhou@powerpms.com
 * operator repository / 账号的repository层
 * @author xiongyizhou
 */
public interface OperatorRepository extends JpaRepository<Operator, String> {
  Optional<Operator> findOperatorByName(String name);
}
