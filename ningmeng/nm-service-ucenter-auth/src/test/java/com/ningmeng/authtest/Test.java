package com.ningmeng.authtest;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 2020/3/10.
 */
public class Test {
    //生成一个jwt令牌
    @org.junit.Test
    private void testCreateJwt(){
//证书文件
        String key_location = "nm.keystore";
//密钥库密码
        String keystore_password ="ningmeng";
//访问证书路径
        ClassPathResource resource = new ClassPathResource(key_location);
//密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource,
                keystore_password.toCharArray());
//密钥的密码，此密码和别名要匹配3.6.3.3 验证jwt令牌
        String keypassword = "ningmeng";
//密钥别名
        String alias = "nmkey";
//密钥对（密钥和公钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias,keypassword.toCharArray());
//私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
//定义payload信息
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "123");
        tokenMap.put("name", "mrt");
        tokenMap.put("roles", "r01,r02");
        tokenMap.put("ext", "1");
//生成jwt令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(aPrivate));
//取出jwt令牌
        String token = jwt.getEncoded();
        System.out.println("token="+token);
    }
    //资源服务使用公钥验证jwt的合法性，并对jwt解码
    @org.junit.Test
    public void testVerify(){
//jwt令牌
        String token="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6Ik5tV2ViQXBwIiwic2NvcGUiOlsiYXBwIl0sIm5hbWUiOm51bGwsInV0eXBlIjpudWxsLCJpZCI6bnVsbCwiZXhwIjoxNTgzODUxMTk2LCJqdGkiOiJiMWQ3NWFjMS03NTdlLTQ4NjYtODZjYy01YTlhYWE2ZmU3MzkiLCJjbGllbnRfaWQiOiJObVdlYkFwcCJ9.es2fBA2F7kJDK3OlpQ7R_V-El7VzTTxx7YHc3_BIlPEhdgoABG8w9zo-mmr05XfLzp_EhWau_OPRM5n3ISQu9192HJzj86SrRr50PrcPaU65dGAOc39LH970njt2RKewteJWQcM2ZWedL2eGpx5yGsM7ejUckuyibgNCIG8wPmyPgmrWhfbzIHhJ61E8wJlKMnrY-_M4qD9g5t2H4jsTSP0SLu28DHN03ZIgNwvDHuDPdQC3oVY8utvP0xK2jrtOa-Gh8HJJvy1s67QzEp-otkTxMthlICjnteEc75ZJlium7IKUHELcdR_uUvk_SqHmz996BGECk-gHOUM7w5d8pw";
//公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0JWLscE2/Xz9OcQ9+H4LuP/ifrTdM7dZoga/t1xMH37GEdYOmwRLidiUYHkuTRTaWNgaTthtbyKsByVMOwTc+zpRf2nR9YAde8+ZNysk6gHjtfcEJ2qzx+Gr1SZMC27uuXKg1SktIzpvI5q+eBE+QUVtHG/nMfqEDPFtoyfasi6eSenWvw/MChc2wPEDTW/oTghzS99Jx5wfhUjf3Zf05VotyBjqOgywV6XlOpWjE/P4BV2NKj6TMs5+/gQJnoB9FmGRt7FPr7kBBHRq8YJXaOjOalvGZ9xPaL8F5uKZ571z7fgqCBLhzeHA5B+tYOdedEGx9Y47qYKyW7v+gh/+RQIDAQAB-----END PUBLIC KEY-----";
//校验jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));
//获取jwt原始内容
        String claims = jwt.getClaims();
//jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
