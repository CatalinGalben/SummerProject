package com.siemens.web.config;

import com.siemens.core.config.JPAConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
@Import({JPAConfig.class})
@PropertySources({@PropertySource(value = "classpath:local/database.properties")})
public class AppLocalConfig {
   @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
   {
       return new PropertySourcesPlaceholderConfigurer();
   }
}
