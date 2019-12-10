package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Configurable
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private UserDetailsService JhUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);

//        clients.inMemory()
//                .withClient("order-client")
//                .secret(passwordEncoder.encode("order-secret-8888"))
//                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
//                .accessTokenValiditySeconds(3600)
//                .scopes("all")
//                .and()
//                .withClient("user-client")
//                .secret(passwordEncoder.encode("user-secret-8888"))
//                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
//                .accessTokenValiditySeconds(3600)
//                .scopes("all");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许客户端访问 OAuth2 授权接口，否则请求 token 会返回 401
        security.allowFormAuthenticationForClients();
        //允许已授权用户访问 checkToken 接口和获取 token 接口
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         * 普通 jwt 模式
         */
//         endpoints.tokenStore(jwtTokenStore)
//                .accessTokenConverter(jwtAccessTokenConverter)
//                .userDetailsService(kiteUserDetailsService)
//                /**
//                 * 支持 password 模式
//                 */
//                .authenticationManager(authenticationManager);

        /**
         * jwt 增强模式
         */
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);
        endpoints.tokenStore(jwtTokenStore)
                .userDetailsService(JhUserDetailsService)
                /**
                 * 支持 password 模式
                 */
                .authenticationManager(authenticationManager)
                .tokenEnhancer(enhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter);

        /**
         * redis token 方式
         */
//        endpoints.authenticationManager(authenticationManager)
//                .tokenStore(redisTokenStore)
//                .userDetailsService(kiteUserDetailsService);


    }


}



