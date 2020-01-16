package com.sxy.community.Controller;

import com.sxy.community.DTO.CommentDto;
import com.sxy.community.DTO.QuestionDto;
import com.sxy.community.Service.CommentService;
import com.sxy.community.Service.QuestionService;
import com.sxy.community.enums.CommentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        QuestionDto questionDto=questionService.getById(id);
        questionService.incView(id);
        List<CommentDto> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        return "question";
    }

}
