package com.mysite.kyobo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Bean
	SecurityFilterChain fiterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
					.requestMatchers(new AntPathRequestMatcher("/**"))
					.permitAll())
			.csrf((csrf) -> csrf
					.disable()) // 개발 중만 사용. 운영 환경에서는 주의!
			.formLogin((formLogin) -> formLogin
					.loginPage("/login")                 // 로그인 페이지 경로
	                .defaultSuccessUrl("/", true)        // 로그인 성공 시 이동
	                .failureUrl("/login?error=true")  	// 실패 시 다시 로그인 페이지로
	                .permitAll())
			.logout((logout) -> logout
					.logoutUrl("/logout")                // 로그아웃 경로 (POST 방식 권장)
	                .logoutSuccessUrl("/")               // 로그아웃 성공 시 이동
	                .invalidateHttpSession(true)         // 세션 종료
	                .deleteCookies("JSESSIONID")         // 쿠키 제거 (선택)
	                .permitAll());
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncdoer() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
