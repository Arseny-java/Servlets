package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    Scanner scanner = new Scanner(System.in);
    private final Map<Long, Post> listOfAllPosts;
    private final AtomicLong generateId;


    public PostRepository() {
        this.listOfAllPosts = new ConcurrentHashMap<>();
        generateId = new AtomicLong(0);
    }

    public Map<Long, Post> all() {
        return listOfAllPosts;
    }


    public Optional<Post> getById(long id) {
        return Optional.ofNullable(listOfAllPosts.get(id));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Post save(Post post) {
        long inboundPostId = post.getId();
        if (inboundPostId == 0) {
            inboundPostId = generateId.incrementAndGet();
            listOfAllPosts.put(inboundPostId, post);
            return post;
        } else {
            System.out.println("Данный пост уже существует. Обновить? Да/Нет");
            String answer = scanner.nextLine();
            if (answer.equals("Да")) {
                Optional<Post> newPost = getById(inboundPostId);
                Post refreshPost = newPost.get();
                refreshPost.setContent(post.getContent());
                removeById(inboundPostId);
                refreshPost.setId(generateId.incrementAndGet());
                listOfAllPosts.put(generateId.incrementAndGet(),refreshPost);
                return refreshPost;
            } else throw new NotFoundException("Ок, хорошего вам дня!");
        }
    }


    public void removeById(long id) {
        listOfAllPosts.remove(id);
    }
}
