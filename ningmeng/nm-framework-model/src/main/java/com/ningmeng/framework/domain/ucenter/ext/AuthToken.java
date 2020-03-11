package com.ningmeng.framework.domain.ucenter.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
public class AuthToken {
    /*身份令牌：用于校验用户是否认证
    刷新令牌：jwt令牌快过期时执行刷新令牌
    jwt令牌：用于授权*/
    String access_token;//访问token
    String refresh_token;//刷新token
    String jwt_token;//jwt令牌
}
