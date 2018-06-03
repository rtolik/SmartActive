package rtolik.smartactive.config.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static rtolik.smartactive.config.oauth2.AuthorizationServerConfiguration.RESOURCE_ID;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().cacheControl().disable().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/user").permitAll()
                .antMatchers("/add").permitAll()
//                /admin
//                .antMatchers("/admin/**").hasRole(Roles.ADMIN.name().toUpperCase()) todo
//                .antMatchers(HttpMethod.GET,"/admin/**").permitAll() todo
                .antMatchers("/admin/**").permitAll()
                //ws
                .antMatchers("/web-socket-ticket").permitAll()
                .antMatchers("/web-socket-history").permitAll()
                .antMatchers("/web-socket-user-message").permitAll()
                //payment
                .antMatchers("/payment/**").permitAll()
//                res
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/file/**").permitAll()
                .antMatchers("/vendor.bundle.js").permitAll()
                .antMatchers("/inline.bundle.js").permitAll()
                .antMatchers("/styles.bundle.js").permitAll()
                .antMatchers("/main.bundle.js").permitAll()
                .antMatchers("/polyfills.bundle.js").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/").permitAll()

                .anyRequest().permitAll();
    }
}
