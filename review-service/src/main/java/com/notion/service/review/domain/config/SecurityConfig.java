package com.notion.service.review.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http)  {
		http
				.authorizeExchange()
				//.pathMatchers("/v1/products/**").hasAuthority("ROLE_USER")
				.pathMatchers("/**").permitAll()
				.anyExchange().authenticated()
				.and()
				.exceptionHandling()
				.accessDeniedHandler(new HttpStatusServerAccessDeniedHandler(HttpStatus.BAD_REQUEST))
				.and()
				.oauth2ResourceServer()
				.jwt()
				.jwtAuthenticationConverter(grantedAuthoritiesExtractor());
		return http.build();
	}

	@Bean
	public Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
		GrantedAuthoritiesExtractor extractor = new GrantedAuthoritiesExtractor();
		return new ReactiveJwtAuthenticationConverterAdapter(extractor);
	}

	static class GrantedAuthoritiesExtractor extends JwtAuthenticationConverter {
		@Override
		protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
			Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
			return ((List<String>) realmAccess.get("roles")).stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		}
	}
}
