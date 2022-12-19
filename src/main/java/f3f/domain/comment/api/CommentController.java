package f3f.domain.comment.api;

import f3f.domain.comment.application.CommentService;
import f3f.domain.comment.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private final CommentService commentService;

    @RequestMapping("/save_comment")
    public void save(@ModelAttribute )



}
