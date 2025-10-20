package tn.esprit.fundsphere.Repositories.BlogRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.fundsphere.Entities.BlogManagement.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
}
