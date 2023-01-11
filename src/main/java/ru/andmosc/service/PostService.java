package ru.andmosc.service;

import org.springframework.stereotype.Component;
import ru.andmosc.controller.PostController;
import ru.andmosc.exception.NotFoundException;
import ru.andmosc.model.Post;
import ru.andmosc.repository.PostRepository;
import ru.andmosc.repository.PostRepositoryStubImpl;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all().stream().filter(p -> !p.isRemoved()).collect(Collectors.toList());
    }

    public Post getById(long id) throws NotFoundException {
        Post post = repository.getById(id).orElseThrow(() -> new NotFoundException(PostRepositoryStubImpl.ID_NOT_FOUND));
        if (!post.isRemoved()) {
            return post;
        } else {
            throw new NotFoundException(PostRepositoryStubImpl.ID_NOT_FOUND);
        }
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}

