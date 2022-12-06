package com.lyl.config;

import com.lyl.shiro.CustomFormAuthenticationFilter;
import com.lyl.shiro.ShiroRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {

//	     验证过滤器是authc;逻辑上只要登录成功即可跳转至“/account/user/current”实际上不行；
//		主要原因是successUrl配置只是做为一种附加配置，只有session中没有用户请求地址时才会使用successUrl；
//		系统默认登录成功后首次跳转的地址为，访问系统时初次使用地址，例：如果用户首次访问的是http://****/aa.html;
	   // 那么shiro校验成功后跳转的地址即可http://****/aa/aa.html; 否则shiro将跳转到默认虚拟路径：“/”；

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager) {
		
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/doLogin");
		shiroFilterFactoryBean.setSuccessUrl("/queryAuctions");
		shiroFilterFactoryBean.setUnauthorizedUrl("/refuse.html");
		
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/register.html", "anon");
		filterChainDefinitionMap.put("/defaultKaptcha", "anon");
		filterChainDefinitionMap.put("/register", "anon");
		filterChainDefinitionMap.put("/checkUsername", "anon");
		filterChainDefinitionMap.put("/login.html", "anon");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/**", "authc");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		
		Map<String, Filter> filters = new HashMap<>();
		CustomFormAuthenticationFilter authFilter = new CustomFormAuthenticationFilter();
		filters.put("authc",authFilter);
		shiroFilterFactoryBean.setFilters(filters);
		
		return shiroFilterFactoryBean;
	}
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
	    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
	    
	    // logged in users with the 'admin' role
	    //chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");
	    
	    // logged in users with the 'document:read' permission
	    //chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");
	    
	    // all other paths require a logged in user
	    //chainDefinition.addPathDefinition("/logout", "logout");
	    //chainDefinition.addPathDefinition("/**", "authc");
	    return chainDefinition;
	}
	
	@Bean  
    public DefaultWebSecurityManager securityManager(Realm realm){
       DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
       securityManager.setRealm(realm);
//       securityManager.setRememberMeManager(rememberMeManager());
//       securityManager.setCacheManager(getEhCacheManager());
//       securityManager.setSessionManager(sessionManager());
       return securityManager;  
    }

	@Bean
	public Realm shiroRealm() {
		return new ShiroRealm();
	} 
	
//    /**
//     * 密码校验规则HashedCredentialsMatcher
//     * 这个类是为了对密码进行编码的 ,
//     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
//     * 这个类也负责对form里输入的密码进行编码
//     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
//     */
//    @Bean("hashedCredentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        //指定加密方式为MD5
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        //加密次数
//        credentialsMatcher.setHashIterations(1024);
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return credentialsMatcher;
//    }
//
//
//    @Bean("authRealm")
//    @DependsOn("lifecycleBeanPostProcessor")//可选
//    public AuthRealm authRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
//        AuthRealm authRealm = new AuthRealm();
//        authRealm.setAuthorizationCachingEnabled(false);
//        authRealm.setCredentialsMatcher(matcher);
//        return authRealm;
//    }
}
