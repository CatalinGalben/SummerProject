package com.siemens.web.config;

import com.siemens.core.config.JPAConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
@ComponentScan({"com.siemens.core"})
@Import({JPAConfig.class})
@PropertySources({@PropertySource(value = "classpath:local/db.properties")})
public class AppLocalConfig {
   @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
   {
       return new PropertySourcesPlaceholderConfigurer();
   }
}
