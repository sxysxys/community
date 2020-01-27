package com.sxy.community.Service;

import com.sxy.community.DAO.Question;
import com.sxy.community.DAO.QuestionExample;
import com.sxy.community.DAO.User;
import com.sxy.community.DTO.QuestionDto;
import com.sxy.community.DTO.pageDto;
import com.sxy.community.exception.CustomizeException;
import com.sxy.community.exception.CustomizeErrorCode;
import com.sxy.community.mapper.QuestionExtMapper;
import com.sxy.community.mapper.QuestionMapper;
import com.sxy.community.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionmapper;

    @Autowired
    private UserMapper usermapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

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
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmtcreat desc");
        List<Question> questions = questionmapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
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
        example.createCriteria().andCreaterEqualTo(id);
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
        example1.setOrderByClause("gmtcreat desc");
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
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
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
            questionmapper.insertSelective(que);
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

    //每次插入的时候+1个阅读量
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
//取出相关的问题
    public List<QuestionDto> getRelated(QuestionDto queryDto) {
        String[] split = queryDto.getTag().split(",");
        //拼接成正则的形式
        String regexpTag = Arrays.stream(split).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDto.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDto> collect = questions.stream().map(q -> {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q, questionDto);
            return questionDto;
        }).collect(Collectors.toList());
        return collect;
    }
}
