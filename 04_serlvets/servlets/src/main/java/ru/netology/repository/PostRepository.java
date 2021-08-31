package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;

// Stub
public class PostRepository {
    Scanner scanner = new Scanner(System.in);
    private final List<Post> listOfAllPosts;
    private long generateId;

    public PostRepository() {
        this.listOfAllPosts = new Vector<>();
        generateId = 0;
    }

    public List<Post> all() {
        return listOfAllPosts;
    }


    public Optional<Post> getById(long id) {
        for (Post post : listOfAllPosts) {
            if (id == post.getId()) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Post save(Post post) {
        long inboundPostId = post.getId();
        if (inboundPostId == 0) {
            post.setId(generateId++);
            listOfAllPosts.add(post);
            return post;
        } else {
            System.out.println("Данный пост уже существует. Обновить? Да/Нет");
            String answer = scanner.nextLine();
            if (answer.equals("Да")) {
                Optional<Post> newPost = getById(inboundPostId);
                Post refreshPost = newPost.get();
                refreshPost.setContent(post.getContent());
                removeById(inboundPostId);
                refreshPost.setId(generateId++);
                listOfAllPosts.add(refreshPost);
                return refreshPost;
            } else throw new NotFoundException("Ок, хорошего вам дня!");
        }
    }


    public void removeById(long id) {
        listOfAllPosts.removeIf(post -> id == post.getId());
    }
}
