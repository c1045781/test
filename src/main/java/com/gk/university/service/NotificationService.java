package com.gk.university.service;

import com.gk.university.dto.NotificationDTO;
import com.gk.university.dto.PaginationDTO;
import com.gk.university.enums.NotificationStatusEnum;
import com.gk.university.enums.NotificationTypeEnum;
import com.gk.university.exception.CustomizeErrorCode;
import com.gk.university.exception.CustomizeException;
import com.gk.university.mapper.NotificationMapper;
import com.gk.university.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO findPaginationById(Long id, Integer currentPage, Integer size) {
        PaginationDTO<NotificationDTO> pageInationDTO = new PaginationDTO();
        ArrayList<NotificationDTO> notificationDTOS = new ArrayList<>();
        if (currentPage < 0) {
            currentPage = 1;
        }
        Integer index = (currentPage - 1) * size;
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(id);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(index, size));

        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        pageInationDTO.setT(notificationDTOS);
        pageInationDTO.setCurrentPage(currentPage);
        pageInationDTO.setSize(size);

        Integer totalCount = (int)notificationMapper.countByExample(example);
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

    public int unreadCount(Long id) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andStatusEqualTo(0).andReceiverEqualTo(id);
        int i = (int)notificationMapper.countByExample(example);
        return i;
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);

        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        return notificationDTO;
    }
}
