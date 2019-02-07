package pl.artelaguna.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.artelaguna.backend.request.SessionRequestInterceptor;

@Configuration
public class ArteLagunaConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SessionRequestInterceptor sessionRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionRequestInterceptor);
    }
}
