package de.rewe.demo.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Oauth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		http.authorizeRequests().
				antMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN").
			and().
		    	authorizeRequests().anyRequest().permitAll();
		// @formatter:on
	}

	//todo - endpoint?
	@Bean
	@ConfigurationProperties("tokenservice")
	RemoteTokenServices remoteTokenServices() {
		return new RemoteTokenServices();
	}
}