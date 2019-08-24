package com.gk.university.service;

import com.gk.university.dto.PaginationDTO;
import com.gk.university.dto.QuestionDTO;
import com.gk.university.dto.QuestionQueryDTO;
import com.gk.university.exception.CustomizeErrorCode;
import com.gk.university.exception.CustomizeException;
import com.gk.university.mapper.QuestionExtMapper;
import com.gk.university.mapper.QuestionMapper;
import com.gk.university.mapper.UserMapper;
import com.gk.university.model.Question;
import com.gk.university.model.QuestionExample;
import com.gk.university.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO findPagination(String search,Integer currentPage, Integer size) {
        if(StringUtils.isNotBlank(search)) {
            String[] s = search.split(" ");
            search = StringUtils.join(s, "|");
        }

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setIndex((currentPage - 1) * size);
        questionQueryDTO.setSize(size);
        questionQueryDTO.setSearch(search);


        PaginationDTO pageInationDTO = new PaginationDTO();
        ArrayList<QuestionDTO> questionDTOList = new ArrayList<>();
        Integer index = (currentPage - 1) * size;
        List<Question> questionList = questionExtMapper.selectQuestionBySearch(questionQueryDTO);
        for (Question question : questionList) {
            QuestionDTO target = new QuestionDTO();
            BeanUtils.copyProperties(question, target);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            target.setUser(user);
            questionDTOList.add(target);
        }
        pageInationDTO.setT(questionDTOList);
        pageInationDTO.setCurrentPage(currentPage);
        pageInationDTO.setSize(size);



        Integer totalCount = (int) questionExtMapper.countQuestionBySearch(questionQueryDTO);
        pageInationDTO.setTotalCount(totalCount);

        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = (totalCount / size) + 1;
        }

        pageInationDTO.setTotalPage(totalPage);
        return pageInationDTO;
    }

    public PaginationDTO findPaginationById(Long userId, Integer currentPage, Integer size) {

        PaginationDTO<QuestionDTO> pageInationDTO = new PaginationDTO();
        ArrayList<QuestionDTO> questionDTOList = new ArrayList<>();
        if (currentPage < 0) {
            currentPage = 1;
        }
        Integer index = (currentPage - 1) * size;
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(index, size));
        for (Question question : questionList) {
            QuestionDTO target = new QuestionDTO();
            BeanUtils.copyProperties(question, target);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            target.setUser(user);
            questionDTOList.add(target);
        }
        pageInationDTO.setT(questionDTOList);
        pageInationDTO.setCurrentPage(currentPage);
        pageInationDTO.setSize(size);

        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(example1);
        pageInationDTO.setTotalCount(totalCount);

        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = (totalCount / size) + 1;
        }

        pageInationDTO.setTotalPage(totalPage);
        return pageInationDTO;
    }

    public QuestionDTO findQuestionById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO target = new QuestionDTO();
        BeanUtils.copyProperties(question, target);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        target.setUser(user);
        return target;

    }

    public void insertOrUpdateQuestion(Question question) {
        Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());
        if (dbQuestion == null) {
            //插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            int row = questionMapper.updateByPrimaryKeySelective(question);
            if (row == 0) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void invcView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<Question> relatedQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(StringUtils.replace(questionDTO.getTag(),",","|"));
        List<Question> questionList = questionExtMapper.relatedQuestion(question);
        return questionList;

    }
}
