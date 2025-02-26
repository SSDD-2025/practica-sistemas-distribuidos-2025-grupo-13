package es.grupo13.ssddgrupo13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.grupo13.ssddgrupo13.entities.Comment;
import es.grupo13.ssddgrupo13.repository.CommentRepository;

public class CommentController {
     @Autowired
    private CommentRepository commentRepository;
    
    @PostMapping("/comment-in")
    public String commentIn(@RequestParam String autor, 
                         @RequestParam String text, 
                         @RequestParam int rating,
                         Model model) {
        


        Comment comment = new Comment(autor, text, rating);
        commentRepository.save(comment);
        return "/index";
    }
    
}
