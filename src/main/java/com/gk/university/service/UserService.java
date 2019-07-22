package com.gk.university.service;

import com.gk.university.mapper.UserMapper;
import com.gk.university.model.User;
import com.gk.university.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void insertOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);

        if (users.size() == 0) {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新
            user.setGmtModified(System.currentTimeMillis());
            user.setId(users.get(0).getId());
            userMapper.updateByPrimaryKeySelective(user);
        }
    }
}
