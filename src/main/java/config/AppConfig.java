package config;

import controller.PostController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.PostRepository;
import service.PostService;

@Configuration
public class AppConfig {
    @Bean
    public PostController postController() {
        return new PostController(PostService());
    }
    @Bean
    public PostService PostService() {
        return new PostService(PostRepository());
    }
    @Bean
    public PostRepository PostRepository() {
        return new PostRepository();
    }
}
