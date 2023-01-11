package ru.andmosc.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.andmosc.controller.PostController;
import ru.andmosc.exception.NotFoundException;
import ru.andmosc.model.Post;
import ru.andmosc.repository.PostRepository;

import java.util.List;
@Component
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }
    public List<Post> all() {
        return repository.all();
    }
    public Post getById(long id) throws NotFoundException {
        return repository.getById(id).orElseThrow(() -> new NotFoundException(PostController.ID_NOT_FOUND));
    }
    public Post save(Post post) {
        return repository.save(post);
    }
    public void removeById(long id) {
        repository.removeById(id);
    }
}

