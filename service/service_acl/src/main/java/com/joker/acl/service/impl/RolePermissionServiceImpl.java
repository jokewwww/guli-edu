package com.joker.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.acl.entity.RolePermission;
import com.joker.acl.mapper.RolePermissionMapper;
import com.joker.acl.service.RolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}
