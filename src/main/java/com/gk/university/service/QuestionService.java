package com.gk.university.service;

import com.gk.university.dto.PageInationDTO;
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

    public PageInationDTO list(Integer currentPage, Integer size) {

        PageInationDTO pageInationDTO = new PageInationDTO();
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
}
