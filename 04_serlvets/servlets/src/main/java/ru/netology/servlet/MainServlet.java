package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.SpringConfig;
import ru.netology.controller.PostController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
  public static final String API_POSTS = "/api/posts";
  public static final String API_POSTS_D = "/api/posts/\\d+";
  public static final String STR = "/";
  private PostController controller;

  @Override
  public void init() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    controller = context.getBean ("postController",PostController.class);
    context.close();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    final var path = req.getRequestURI();

    if (path.equals(API_POSTS)) {
      controller.all(resp);
      return;
    }
    if (path.matches(API_POSTS_D)) {
      final var id = Long.parseLong(path.substring(path.lastIndexOf(STR)));
      controller.getById(id, resp);
      return;
    }
    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    final var path = req.getRequestURI();
    if (path.equals(API_POSTS)) {
      controller.save(req.getReader(), resp);
      return;
    }
    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    final var path = req.getRequestURI();
    if (path.matches(API_POSTS_D)) {
      final var id = Long.parseLong(path.substring(path.lastIndexOf(STR)));
      controller.removeById(id, resp);
      return;
    }
    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
  }
}

