package servlet;

import config.AppConfig;
import controller.PostController;
import exception.NotFoundException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private static final String API_POSTS = "/api/posts";
    private static final String API_POSTS_D = "/api/posts/\\d+";
    private static final String STR = "/";
    private final String GET_METHOD = "GET";
    private final String POST_METHOD = "POST";
    private final String DELETE_METHOD = "DELETE";
    private PostController controller;
    private String path;
    private String method;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext(AppConfig.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            path = req.getRequestURI();
            method = req.getMethod();
            if (method.equals(GET_METHOD) && path.equals(API_POSTS)) {
                controller.all(resp);
                return;
            }
            if (method.equals(GET_METHOD) && path.matches(API_POSTS_D)) {
                final long id = parseId(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST_METHOD) && path.equals(API_POSTS)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE_METHOD) && path.matches(API_POSTS_D)) {
                final long id = parseId(path);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (NotFoundException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long parseId(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf(STR) + 1));
    }
}