import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;

public class UserRealm extends AuthorizingRealm {
    @Override
    public String getName() {
        return "userRealm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String s = principalCollection.getPrimaryPrincipal().toString();
        ArrayList<String> list = new ArrayList<>();
        list.add("user:add");
        list.add("user:update");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (String s1:list){
        info.addStringPermission(s1);
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        System.out.println(principal);
        String password="123456";
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,password,getName());
        return info;
    }
}
