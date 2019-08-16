package group.gamelife.security.reactor.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by xiongyizhou on 2019/8/13 10:46 E-mail: xiongyizhou@powerpms.com
 * role entity / 角色实例
 * @author xiongyizhou
 */
@Entity
@Data
public class Role {
  @Id
  private Integer roleId;
  @Column
  private String roleName;
}
