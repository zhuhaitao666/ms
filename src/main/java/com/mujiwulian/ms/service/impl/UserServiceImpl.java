package com.mujiwulian.ms.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mujiwulian.ms.mapper.UserMapper;
import com.mujiwulian.ms.entity.User;
import com.mujiwulian.ms.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

