package com.gxx.back.config.security;

import com.gxx.back.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailInfo implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.gxx.back.bean.User user = userDao.getUserLoginInfo(username);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        UserDetails userDetails = new User(user.getUserName(),user.getUserPassWord(),
                true,true,true,true,getAuthorities(user.getLevel()));

        return userDetails;
    }

    public Collection<GrantedAuthority> getAuthorities(Integer access){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //所有用户默认拥有ROLE_USER权限
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(access.compareTo(0) == 0){
            //如果access为0 则拥有ROLE_ADMIN权限
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }
}
