package ru.netology.service;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.Map;

public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Map<Long, Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public long save(Post post) {
        if (post.getId() == 0) {
            post.setId(repository.save(post).get());
            return post.getId();
        } else {
            return repository.save(post).orElseThrow(NotFoundException::new);
        }
    }


    public void removeById(long id) {
        repository.removeById(id);
    }
}

