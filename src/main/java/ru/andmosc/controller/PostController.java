package ru.andmosc.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.andmosc.model.Post;
import ru.andmosc.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    public static final String ID_NOT_FOUND = "ID not found or list is empty";
    private final PostService service;
    private final Gson gson = new Gson();

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
