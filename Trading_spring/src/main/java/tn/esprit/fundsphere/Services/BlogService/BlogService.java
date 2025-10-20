package tn.esprit.fundsphere.Services.BlogService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.fundsphere.Entities.BlogManagement.Blog;
import tn.esprit.fundsphere.Repositories.BlogRepository.BlogRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService implements IBlogService {

    private final BlogRepository blogRepository;

    @Override
    public Blog addBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Blog blog) {
        if (blogRepository.existsById((long) blog.getIdBlog())) {
            return blogRepository.save(blog);
        }
        throw new IllegalArgumentException("Blog with ID " + blog.getIdBlog() + " not found.");
    }

    @Override
    public void deleteBlog(int idBlog) {
        if (blogRepository.existsById((long) idBlog)) {
            blogRepository.deleteById((long) idBlog);
        } else {
            throw new IllegalArgumentException("Blog with ID " + idBlog + " not found.");
        }
    }

    @Override
    public Blog getBlogById(int idBlog) {
        return blogRepository.findById((long) idBlog)
                .orElseThrow(() -> new IllegalArgumentException("Blog with ID " + idBlog + " not found."));
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }
}
