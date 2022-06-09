package app.config;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "app")
@PropertySource("classpath:application.properties")
public class BeanConfig {
}
