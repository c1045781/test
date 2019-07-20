package com.gk.university.service;

import com.gk.university.dto.PaginationDTO;
import com.gk.university.dto.QuestionDTO;
import com.gk.university.mapper.QuestionMapper;
import com.gk.university.mapper.UserMapper;
import com.gk.university.model.Question;
import com.gk.university.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO findPagination(Integer currentPage, Integer size) {

        PaginationDTO pageInationDTO = new PaginationDTO();
        ArrayList<QuestionDTO> questionDTOList = new ArrayList<>();
        Integer index = (currentPage-1)*size;
        List<Question> questionList = questionMapper.list(index,size);
        for (Question question : questionList) {
            QuestionDTO target = new QuestionDTO();
            BeanUtils.copyProperties(question, target);
            User user = userMapper.findById(question.getCreator());
            target.setUser(user);
            questionDTOList.add(target);
        }
        pageInationDTO.setQuestionDTOList(questionDTOList);
        pageInationDTO.setCurrentPage(currentPage);
        pageInationDTO.setSize(size);

        Integer totalCount = questionMapper.count();
        pageInationDTO.setTotalCount(totalCount);

        Integer totalPage;
        if(totalCount%size == 0) {
            totalPage = totalCount/size;
        }else{
            totalPage = (totalCount/size)+1;
        }

        pageInationDTO.setTotalPage(totalPage);
        return pageInationDTO;
    }

    public PaginationDTO findPaginationById(Integer userId, Integer currentPage, Integer size) {

        PaginationDTO pageInationDTO = new PaginationDTO();
        ArrayList<QuestionDTO> questionDTOList = new ArrayList<>();
        if(currentPage<0){
            currentPage=1;
        }
        Integer index = (currentPage-1)*size;
        List<Question> questionList = questionMapper.listByUser(userId,index,size);
        for (Question question : questionList) {
            QuestionDTO target = new QuestionDTO();
            BeanUtils.copyProperties(question, target);
            User user = userMapper.findById(question.getCreator());
            target.setUser(user);
            questionDTOList.add(target);
        }
        pageInationDTO.setQuestionDTOList(questionDTOList);
        pageInationDTO.setCurrentPage(currentPage);
        pageInationDTO.setSize(size);

        Integer totalCount = questionMapper.countByUser(userId);
        pageInationDTO.setTotalCount(totalCount);

        Integer totalPage;
        if(totalCount%size == 0) {
            totalPage = totalCount/size;
        }else{
            totalPage = (totalCount/size)+1;
        }

        pageInationDTO.setTotalPage(totalPage);
        return pageInationDTO;
    }

    public QuestionDTO findQuestionById(Integer id) {
        Question question = questionMapper.questionById(id);
        QuestionDTO target = new QuestionDTO();
        BeanUtils.copyProperties(question, target);
        User user = userMapper.findById(question.getCreator());
        target.setUser(user);
        return target;

    }

    public void insertOrUpdateQuestion(Question question) {
        Question dbQuestion = questionMapper.questionById(question.getId());
        if(dbQuestion == null){
            //插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertQuestion(question);
        }else{
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.updateQuestion(question);
        }
    }
}
