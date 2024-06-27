package tw.team1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import static javax.management.Query.and;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


	//允许URI中包含非ASCII字符
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		DefaultHttpFirewall firewall = new DefaultHttpFirewall();
		return firewall;
	}


    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
//				.requestMatchers("/page/*").permitAll()
//				.requestMatchers("/css/*").permitAll()
//				.requestMatchers("/js/*").permitAll()
//				.requestMatchers("/partials/*").permitAll()
//				.requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/**").permitAll()
//				.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
//				.requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("USER", "ADMIN")
//				.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
//				.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
				.anyRequest()
				.authenticated())
				.rememberMe().tokenValiditySeconds(86400)
				.key("rememberMe-key")
				.and()
				.csrf().disable().cors().disable()
				.formLogin()
	            .loginPage("/login")
	            .permitAll()
				.defaultSuccessUrl("/welcome")
				.permitAll()
				.and()
				//允許Security 響應 'X-Frame-Options'   (Setting X-Frame-Options header)
				.headers()
				.frameOptions()
				.sameOrigin();

		return http.build();
	}

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .cors().disable()
//            .csrf().disable()
//            .authorizeHttpRequests()
//                .requestMatchers("/index", "/login").permitAll()
//                .requestMatchers("/**").permitAll()
////                .requestMatchers("/**").hasAnyRole("USER","ADMIN")
//                .anyRequest().authenticated()
//            .and()
//            .httpBasic();
//
//        return http.build();
//    }
}