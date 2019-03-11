import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.lang.reflect.Array;

public class Shiro {
    @Test
//    @RequiresRoles()
    public void test(){
    IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro1.ini");
    SecurityManager manager = factory.getInstance();
    SecurityUtils.setSecurityManager(manager);
    Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("张三","123456");
        try {
        subject.login(token);
        if(subject.isAuthenticated()){
            System.out.println("niubi");
        }
    }catch (AuthenticationException e){
        e.printStackTrace();
            System.out.println("GG");
    }
//        boolean role1 = subject.hasRole("role1");
////        subject.hasRoles(Array.as)
//        System.out.println(role1);
        System.out.println(subject.isPermittedAll("user:add"));

    }
}
