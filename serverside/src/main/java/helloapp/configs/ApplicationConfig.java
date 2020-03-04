/*
 * @bot-written
 * 
 * WARNING AND NOTICE
 * Any access, download, storage, and/or use of this source code is subject to the terms and conditions of the
 * Full Software Licence as accepted by you before being granted access to this source code and other materials,
 * the terms of which can be accessed on the Codebots website at https://codebots.com/full-software-licence. Any
 * commercial use in contravention of the terms of the Full Software Licence may be pursued by Codebots through
 * licence termination and further legal action, and be required to indemnify Codebots for any loss or damage,
 * including interest and costs. You are deemed to have accepted the terms of the Full Software Licence on any
 * access, download, storage, and/or use of this source code.
 * 
 * BOT WARNING
 * This file is bot-written.
 * Any changes out side of "protected regions" will be lost next time the bot makes any changes.
 */
package helloapp.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

// % protected region % [Add any additional imports here] off begin
// % protected region % [Add any additional imports here] end

/**
 * Application-wide configuration to be applied only in beta and production environment.
 */
//@Profile("!test & !dev")
@Configuration
@EnableJpaAuditing
public class ApplicationConfig implements WebMvcConfigurer {

	// https://stackoverflow.com/questions/38516667/springboot-angular2-how-to-handle-html5-urls
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/docs/**")
				.addResourceLocations("classpath:/docs/")
				.resourceChain(false)
				.addResolver(new PathResourceResolver() {
					@Override
					protected Resource getResource(String resourcePath, Resource location) throws IOException {
						Resource requestedResource = location.createRelative(resourcePath);
						return requestedResource.exists() && requestedResource.isReadable()
								? requestedResource
								: new ClassPathResource("/docs/index.html");
					}
				});

		registry.addResourceHandler(
				"/{x:(?!api|graphql|voyager|altair|graphiql|swagger-ui.html|docs).*}/**"
		)
				.addResourceLocations("classpath:/static/")
				.resourceChain(true)
				.addResolver(new PathResourceResolver() {
					@Override
					protected Resource getResource(String resourcePath, Resource location) throws IOException {
						Resource requestedResource = location.createRelative(resourcePath);
						return requestedResource.exists() && requestedResource.isReadable()
								? requestedResource
								: new ClassPathResource("/static/index.html");
					}
				});

		registry.addResourceHandler("/docs/**")
				.addResourceLocations("classpath:/docs/")
				.resourceChain(true)
				.addResolver(new PathResourceResolver() {
					@Override
					protected Resource getResource(String resourcePath, Resource location) throws IOException {
						Resource requestedResource = location.createRelative(resourcePath);
						return requestedResource.exists() && requestedResource.isReadable()
								? requestedResource
								: new ClassPathResource("/docs/index.html");
					}
				});
	}

	@Override
	@Profile("dev")
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200")
				.allowedMethods("PUT", "DELETE", "GET", "POST", "OPTION")
				.allowCredentials(true);

		// % protected region % [Add any additional CORS mapping here] off begin
		// % protected region % [Add any additional CORS mapping here] end
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

		// % protected region % [Add any custom configuration for multipart resolver here] off begin
		multipartResolver.setMaxUploadSize(100000);
		// % protected region % [Add any custom configuration for multipart resolver here] end

		return multipartResolver;
	}

	// % protected region % [Add any additional application configurations here] off begin
	// % protected region % [Add any additional application configurations here] end
}
