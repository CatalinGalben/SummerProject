package com.siemens.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.siemens.core.repository", "com.siemens.core.service", "com.siemens.core.ui"})
public class CatalogConfig {


}
