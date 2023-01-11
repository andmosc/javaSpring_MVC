package ru.andmosc.controller;

import org.springframework.web.bind.annotation.*;
import ru.andmosc.model.Post;
import ru.andmosc.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }
    @GetMapping
    public List<Post> all()  {
        return service.all();
    }
    @GetMapping("/{id}")
    public Post getById(@PathVariable("id") long id) {
        return service.getById(id);
    }
    @PostMapping
    public Post save(@RequestBody Post post) {
        return service.save(post);
    }
    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }
}
