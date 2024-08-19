package repository;

import model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// Stub
public class PostRepository {
    private final ConcurrentMap<Long, Post> allPosts;
    private long nextId = 1;

    public PostRepository() {
        this.allPosts = new ConcurrentHashMap<>();
    }
    public List<Post> all() {
        return new ArrayList<>(allPosts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(allPosts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(nextId++);
        }
        allPosts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        allPosts.remove(id);
    }
}
