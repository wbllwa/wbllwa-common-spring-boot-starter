package com.wbllwa.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wbllwa.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户是否存在
     * @param user
     * @return
     */
    default Boolean existsUser(User user)
    {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, user.getUsername());
        return this.exists(queryWrapper);
    }
}
