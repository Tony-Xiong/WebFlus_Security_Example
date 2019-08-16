package group.gamelife.security.reactor.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by xiongyizhou on 2019/8/13 10:44 E-mail: xiongyizhou@powerpms.com
 * subject entity / 账户实例
 * @author xiongyizhou
 */
@Entity
@Data
public class Operator  {
  @Id
  private String id;
  @Column
  private String name;
  @Column
  private int age;
  @Column
  private String password;

  @ManyToMany(targetEntity = group.gamelife.security.reactor.demo.entity.Role.class, fetch = FetchType.EAGER)
  private Set<Role> roles;
}
