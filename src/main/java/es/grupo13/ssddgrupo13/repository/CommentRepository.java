package es.grupo13.ssddgrupo13.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.grupo13.ssddgrupo13.entities.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    Optional<Comment> findById(long id);
}
