package org.cisiondata.modules.auth.service.impl;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IAuthService;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBAuthorizingServiceImpl extends AuthorizingRealm {

    private Logger LOG = LoggerFactory.getLogger(DBAuthorizingServiceImpl.class);
    
    //认证授权服务
    private IAuthService authService = null;

    public void setAuthService(IAuthService authService) {
		this.authService = authService;
	}

	/**
     * 验证当前登录的Subject 
     * 该方法的调用时机为LoginController.login()方法中执行Subject.login()时 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        if (StringUtils.isBlank(username)) throw new AuthenticationException("用户名不能为空");
        String usernameText = username.toLowerCase().trim();
        char[] password = usernamePasswordToken.getPassword();
        String passwordText = null == password || password.length == 0 ? "" : new String(password);
        String passwordCipherText = EndecryptUtils.encryptPassword(usernameText, passwordText);
        LOG.info("username:{} {} password: {}", username, usernameText, passwordCipherText);
        User user = authService.readUserByAccountAndPassword(usernameText, passwordCipherText);
        if (null == user) {
        	throw new AuthenticationException("用户名密码错误");
        }
        return new SimpleAuthenticationInfo(username, password, getName());
    }

    /**
     * 为当前登录的Subject授予角色和权限,该方法的调用时机为需授权资源被访问时
     * 并且每次访问需授权资源时都会执行该方法中的逻辑,默认并未启用AuthorizationCache 
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = (String) principals.getPrimaryPrincipal();
        User user = authService.readUserByAccount(account);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = authService.readRoleIdentitiesByUserId(user.getId());
        simpleAuthorizationInfo.setRoles(roles);
        LOG.info("authorization account {} roles: {}", user.getAccount(), roles);
        Set<String> permissions = authService.readPermissionIdentitiesByUserId(user.getId());
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
    
    @Override
    protected void assertCredentialsMatch(AuthenticationToken authenticationToken, 
    		AuthenticationInfo authenticationInfo) throws AuthenticationException {
    	UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
    	String tokenPassword = new String(usernamePasswordToken.getPassword());
    	SimpleAuthenticationInfo simpleAuthenticationInfo = (SimpleAuthenticationInfo) authenticationInfo;
    	String infoPassword = new String((char[]) simpleAuthenticationInfo.getCredentials());
    	LOG.info("tokenPassword:{} infoPassword: {}", tokenPassword, infoPassword);
    	if (!tokenPassword.equals(infoPassword)) {
    		throw new AuthenticationException("密码不匹配");
    	}
    }
    
    private static final String OR_OPERATOR = " or ";
    private static final String AND_OPERATOR = " and ";
    private static final String NOT_OPERATOR = " not ";

    /**
     * 支持or and not 关键词  不支持and or混用
     * @param principals
     * @param permission
     * @return
     */
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if (permission.contains(OR_OPERATOR)) {
            String[] permissions = permission.split(OR_OPERATOR);
            for (String orPermission : permissions) {
                if (isPermittedWithNotOperator(principals, orPermission)) {
                    return true;
                }
            }
            return false;
        } else if (permission.contains(AND_OPERATOR)) {
            String[] permissions = permission.split(AND_OPERATOR);
            for (String orPermission : permissions) {
                if (!isPermittedWithNotOperator(principals, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            return isPermittedWithNotOperator(principals, permission);
        }
    }

    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        if (permission.startsWith(NOT_OPERATOR)) {
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }

}
