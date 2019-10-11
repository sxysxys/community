package com.sxy.community.Service;

import com.sxy.community.DAO.Question;
import com.sxy.community.DAO.User;
import com.sxy.community.DTO.QuestionDto;
import com.sxy.community.mapper.Questionmapper;
import com.sxy.community.mapper.Usermapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private Questionmapper questionmapper;
    @Autowired
    private Usermapper usermapper;

    public List<QuestionDto> getall() {
        List<Question> questions = questionmapper.getall();
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question : questions) {
            User user=usermapper.findbyID(question.getCreater());
            QuestionDto questionDto = new QuestionDto();
            questionDto.setUser(user);
            BeanUtils.copyProperties(question,questionDto);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
}
