package com.dkswjdals89.krakensearch.config.security;

import com.dkswjdals89.krakensearch.config.security.jwt.JwtRequestFilter;
import com.dkswjdals89.krakensearch.config.security.jwt.JwtTokenProvider;
import com.dkswjdals89.krakensearch.dto.ErrorResponseDto;
import com.dkswjdals89.krakensearch.exception.ServiceError;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Environment environment;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 인증 오류 핸들링
     * - 인증 처리중 오류가 발생하는 경우, Json 형식으로 오류를 반환한다.
     * - 인증 단계 중 오류는 Controller에 요청이 전달되기 전에 발생하므로, @ControllerAdvice 에서 공통으로 처리할 수 없다.
     *
     * @param request   ServletRequest Object
     * @param response  ServletResponse Object
     * @param ex    RuntimeException
     * @throws IOException
     */
    private void authExceptionHandler(HttpServletRequest request, HttpServletResponse response, RuntimeException ex) throws IOException {
        ErrorResponseDto responseDto = new ErrorResponseDto(ServiceError.UNAUTHORIZED, ex.getMessage());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), responseDto);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // develop 환경에 대해 h2-console 권한 허용
        if (Arrays.asList(environment.getActiveProfiles()).contains("develop")){
            http.authorizeRequests()
                    .antMatchers("/h2-console/**")
                    .permitAll();
        }
        // 기본 인증 및 세션 비활성화
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable();
        http
                .authorizeRequests()
                .antMatchers("/v1/account/signin", "/v1/account/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(this::authExceptionHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this::authExceptionHandler)
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
