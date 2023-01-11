package ru.andmosc.repository;

import org.springframework.stereotype.Component;
import ru.andmosc.controller.PostController;
import ru.andmosc.exception.NotFoundException;
import ru.andmosc.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PostRepositoryStubImpl implements PostRepository {
    private final ConcurrentHashMap<Long, Post> data = new ConcurrentHashMap<>();
    public static final String ID_NOT_FOUND = "ID not found or list is empty";
    private final AtomicLong counter = new AtomicLong();

    public List<Post> all() {
        return new ArrayList<>(data.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(data.get(id));
    }

    /**
     * Сохраняет полученный пост в map: если id=0 то это новый пост, id увеличивается на единицу.
     * Если пост с таким номером существует и он не помечен как удален, то информация в map обновляется
     * в противном случае выбрасывается исключение NotFoundException со статусом NOT_FOUND 404.
     * @param post сохраняемый пост
     * @return новый пост
     */
    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(counter.addAndGet(1));
            data.putIfAbsent(post.getId(), post);
            return post;
        } else {
            if (post.getId() > 0) {
                getById(post.getId()).ifPresent(e -> {
                    if (!e.isRemoved()) {
                        e.setContent(post.getContent());
                    } else {
                        throw new NotFoundException(ID_NOT_FOUND);
                    }
                });
                return data.get(post.getId()) != null &&
                        data.get(post.getId()).getContent().equals(post.getContent()) ? post : null;
            }
        }
        return null;
    }

    public void removeById(long id) {
        if (id < 0 || id > counter.get() || !data.containsKey(id)) {
            throw new NotFoundException(ID_NOT_FOUND);
        }
        data.get(id).setRemoved(true);
    }
}