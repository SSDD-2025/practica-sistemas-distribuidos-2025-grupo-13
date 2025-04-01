package es.grupo13.ssddgrupo13.dto;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import es.grupo13.ssddgrupo13.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDTO ToDTO(Comment comment);
    List<CommentDTO> ToDTOs(List<Comment> comments);
    Comment ToDomain(CommentDTO commentDTO);
    List<Comment> ToDomains(List<CommentDTO> commentDTOs);
}
