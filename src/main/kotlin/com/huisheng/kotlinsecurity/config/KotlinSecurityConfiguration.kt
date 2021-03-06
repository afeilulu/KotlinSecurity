package com.huisheng.kotlinsecurity.config

import com.huisheng.kotlinsecurity.security.CustomDBUserDetailsService
import com.huisheng.kotlinsecurity.security.LoginFilter
import com.huisheng.kotlinsecurity.security.MyDeniedHandler
import com.huisheng.kotlinsecurity.security.MyEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@EnableWebSecurity
class KotlinSecurityConfiguration(
    private val customDBUserDetailsService: CustomDBUserDetailsService,
    private val loginFilter: LoginFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {

        // 关闭csrf和frameOptions，如果不关闭会影响前端请求接口（这里不展开细讲了，感兴趣的自行搜索，不难）
        http.csrf().disable()
        http.headers().frameOptions().disable()
        // 开启跨域以便前端调用接口
        http.cors()

        http.authorizeRequests() // 注意这里，是允许前端跨域联调的一个必要配置
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // 指定某些接口不需要通过验证即可访问。像登陆、注册接口肯定是不需要认证的
            .antMatchers("/auth/login", "/auth/register").permitAll() // 这里意思是其它所有接口需要认证才能访问
            .antMatchers("/api/**", "/test/**").authenticated() // 指定认证错误处理器
            .and().exceptionHandling().authenticationEntryPoint(MyEntryPoint()).accessDeniedHandler(MyDeniedHandler())

        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // 将我们自定义的认证过滤器替换掉默认的认证过滤器
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter::class.java)
//        http.addFilterBefore(authFilter, FilterSecurityInterceptor::class.java)


//        http {
//            httpBasic {}
//            authorizeRequests {
//                authorize("/test/**", hasAuthority("ROLE_ADMIN"))
//                authorize("/greetings/**", hasAuthority("ROLE_ADMIN"))
//                authorize("/**", permitAll)
//            }
//        }


    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers(HttpMethod.POST, "/auth/**")
            .antMatchers(HttpMethod.POST, "/api/public/**")
            .antMatchers(HttpMethod.GET, "/register.html")
    }

    // Used by spring security if CORS is enabled.
    @Bean
    fun corsFilter(): CorsFilter? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        // 指定UserDetailService和加密器
        auth.userDetailsService(customDBUserDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager? {
        return super.authenticationManager()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}