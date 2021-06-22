package com.gehao.iotserver.dal.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author gehao
 */
@Data
@Accessors(chain = true)
public class UserDO {
    private String username;
    private String password;
    private String email;
}
