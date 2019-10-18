package com.sxy.community.Service;

import com.sxy.community.DAO.Question;
import com.sxy.community.DAO.QuestionExample;
import com.sxy.community.DAO.User;
import com.sxy.community.DTO.QuestionDto;
import com.sxy.community.DTO.pageDto;
import com.sxy.community.exception.CustomizeError;
import com.sxy.community.exception.CustomizeErrorCode;
import com.sxy.community.mapper.QuestionMapper;
import com.sxy.community.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionmapper;

    @Autowired
    private UserMapper usermapper;

    public pageDto getall(Integer page, Integer size) {
        pageDto pageDto = new pageDto();
        Integer allcount = (int)questionmapper.countByExample(new QuestionExample());
        pageDto.setPagination(allcount,page,size);
        if(page>pageDto.getAllpages()){
            page=pageDto.getAllpages();
        }
        if(page<1){
            page=1;
        }
        Integer offset=size*(page-1);
        List<Question> questions = questionmapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question : questions) {
            User user=usermapper.selectByPrimaryKey(question.getCreater());
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
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(id);
        Integer allcount = (int)questionmapper.countByExample(example);
        pageDto.setPagination(allcount,page,size);
        if(page>pageDto.getAllpages()){
            page=pageDto.getAllpages();
        }
        if(page<1){
            page=1;
        }
        Integer offset=size*(page-1);
//        List<Question> questions = questionmapper.getallById(id,offset,size);
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andCreaterEqualTo(id);
        List<Question> questions = questionmapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question : questions) {
            User user=usermapper.selectByPrimaryKey(question.getCreater());
            QuestionDto questionDto = new QuestionDto();
            questionDto.setUser(user);
            BeanUtils.copyProperties(question,questionDto);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestions(questionDtoList);

        return pageDto;
    }

    public QuestionDto getById(Long id) {
        Question byId = questionmapper.selectByPrimaryKey(id);
        if(byId==null){
            throw new CustomizeError(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(byId,questionDto);
        User user = usermapper.selectByPrimaryKey(byId.getCreater());
        questionDto.setUser(user);
        return questionDto;
    }

    public void CreatOrUpdate(Question que) {
        if(que.getId()==null){
            //创建
            que.setGmtcreat(System.currentTimeMillis());
            que.setGmtmodified(que.getGmtcreat());
            questionmapper.insert(que);
        }else{
            //更新
            Question record = new Question();
            record.setGmtmodified(System.currentTimeMillis());
            record.setTag(que.getTag());
            record.setDescription(que.getDescription());
            record.setTitle(que.getTitle());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(que.getId());
            questionmapper.updateByExampleSelective(record, example);
        }
    }
}
