package controller;

import com.google.gson.Gson;
import exception.NotFoundException;
import model.Post;
import org.springframework.stereotype.Controller;
import service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    private final Gson gson;
    private List<Post> data;
    private Post post;

    public PostController(PostService service) {
        this.service = service;
        gson = new Gson();
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        data = service.all();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException, NotFoundException {
        response.setContentType(APPLICATION_JSON);
        post = service.getById(id);
        response.getWriter().print(gson.toJson(post));
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        post = gson.fromJson(body, Post.class);
        data = (List<Post>) service.save(post);
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        data = (List<Post>) service.getById(id);
        service.removeById(id);
        response.getWriter().print(gson.toJson(data));
    }
}
