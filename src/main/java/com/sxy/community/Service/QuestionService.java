package com.sxy.community.Service;

import com.sxy.community.DAO.Question;
import com.sxy.community.DAO.User;
import com.sxy.community.DTO.QuestionDto;
import com.sxy.community.DTO.pageDto;
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

    public pageDto getall(Integer page, Integer size) {
        pageDto pageDto = new pageDto();
        Integer allcount = questionmapper.count();
        pageDto.setPagination(allcount,page,size);
        if(page>pageDto.getAllpages()){
            page=pageDto.getAllpages();
        }
        if(page<1){
            page=1;
        }
        Integer offset=size*(page-1);
        List<Question> questions = questionmapper.getall(offset,size);
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question : questions) {
            User user=usermapper.findbyID(question.getCreater());
            QuestionDto questionDto = new QuestionDto();
            questionDto.setUser(user);
            BeanUtils.copyProperties(question,questionDto);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestions(questionDtoList);

        return pageDto;
    }

    public pageDto list(Long id, Integer page, Integer size) {
        pageDto pageDto = new pageDto();
        Integer allcount = questionmapper.countById(id);
        pageDto.setPagination(allcount,page,size);
        if(page>pageDto.getAllpages()){
            page=pageDto.getAllpages();
        }
        if(page<1){
            page=1;
        }
        Integer offset=size*(page-1);
        List<Question> questions = questionmapper.getallById(id,offset,size);
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question : questions) {
            User user=usermapper.findbyID(question.getCreater());
            QuestionDto questionDto = new QuestionDto();
            questionDto.setUser(user);
            BeanUtils.copyProperties(question,questionDto);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestions(questionDtoList);

        return pageDto;
    }

    public QuestionDto getById(Integer id) {
        Question byId = questionmapper.getById(id);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(byId,questionDto);
        User user = usermapper.findbyID(byId.getCreater());
        questionDto.setUser(user);
        return questionDto;
    }
}
