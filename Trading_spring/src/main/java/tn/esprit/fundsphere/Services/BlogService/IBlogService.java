package tn.esprit.fundsphere.Services.BlogService;

import tn.esprit.fundsphere.Entities.BlogManagement.Blog;

import java.util.List;

public interface IBlogService {
    Blog addBlog(Blog blog);
    Blog updateBlog(Blog blog);
    void deleteBlog(int idBlog);
    Blog getBlogById(int idBlog);
    List<Blog> getAllBlogs();
}
