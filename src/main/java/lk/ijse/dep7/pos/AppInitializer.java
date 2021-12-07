package lk.ijse.dep7.pos;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class AppInitializer implements ServletContextListener {

    private static AnnotationConfigApplicationContext ctx;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ctx.close();
    }

    public static AnnotationConfigApplicationContext getContext(){
        return ctx;
    }
}
