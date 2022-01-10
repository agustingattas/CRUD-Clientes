
package com.clienteweb.app;

import com.clienteweb.app.util.LoginSuccessMessage;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Autowired
    private LoginSuccessMessage successM;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/","/css/**","/js/**","/img/**").permitAll()
//                .antMatchers("/cliente").hasAnyRole("USER")
//                .antMatchers("/cliente/registrar").hasAnyRole("ADMIN")
//                .antMatchers("/cliente/guardar").hasAnyRole("ADMIN")
//                .antMatchers("/cliente/edit/**").hasAnyRole("ADMIN")
//                .antMatchers("/cliente/eliminar/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successM)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll();
    }
    
    
    
    @Autowired
    public void configurerSecurityGlobal(AuthenticationManagerBuilder builder) throws Exception{
        
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passEncoder)
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT u.username, r.rol FROM roles r INNER JOIN users u ON r.user_id=u.id WHERE u.username=?");
        
    }
    
}
