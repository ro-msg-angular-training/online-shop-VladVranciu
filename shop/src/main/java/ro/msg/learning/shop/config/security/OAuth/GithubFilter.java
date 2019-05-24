//package ro.msg.learning.shop.config.security.OAuth;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
//import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//import org.springframework.web.filter.CompositeFilter;
//
//import javax.servlet.Filter;
//import java.util.ArrayList;
//import java.util.List;
//
//@EnableOAuth2Client
//@EnableAuthorizationServer
//public class GithubFilter extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    OAuth2ClientContext oauth2ClientContext;
//
//    @Bean
//    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//        return registration;
//    }
//
//    private Filter ssoFilter() {
//        CompositeFilter filter = new CompositeFilter();
//        List<Filter> filters = new ArrayList<>();
//        filters.add(ssoFilter(github(), "/**"));
//        filter.setFilters(filters);
//        return filter;
//    }
//
//    private Filter ssoFilter(ClientResources client, String path) {
//        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
//        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
//        filter.setRestTemplate(template);
//        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
//                client.getResource().getUserInfoUri(), client.getClient().getClientId());
//        tokenServices.setRestTemplate(template);
//        filter.setTokenServices(tokenServices);
//        return filter;
//    }
//
//    @Bean
//    @ConfigurationProperties("github")
//    public ClientResources github() {
//        return new ClientResources();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**")
//                .authorizeRequests()
//                .antMatchers("/index").permitAll()
//                .anyRequest().authenticated()
//                .and().exceptionHandling()
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"));
//    }
//}
