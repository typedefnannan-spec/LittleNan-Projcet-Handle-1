package com.WM.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//信息来自application.yml
@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {

    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
