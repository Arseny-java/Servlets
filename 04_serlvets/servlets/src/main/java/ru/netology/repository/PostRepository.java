package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
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

    public Optional<Long> save(Post post) {
        long inboundPostId = post.getId();
        if (inboundPostId == 0) {
            inboundPostId = generateId.incrementAndGet();
            listOfAllPosts.put(inboundPostId, post);
        } else {
            if (listOfAllPosts.get(post.getId()) != null) {
                listOfAllPosts.put(post.getId(), post);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(inboundPostId);
    }



    public void removeById(long id) {
        listOfAllPosts.remove(id);
    }
}
