package br.unicesumar.time05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    
    @Autowired
    InterceptadorDeRequisicoes inteceptador;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(inteceptador);
    }
    
}
