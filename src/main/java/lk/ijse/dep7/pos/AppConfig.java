package lk.ijse.dep7.pos;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(JPAConfig.class)
@ComponentScan
@Configuration
public class AppConfig {
}
