package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.dto.MenuDto;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.entity.admin.RolePermissionEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;
import com.xinbo.chainblock.entity.admin.UserRoleEntity;
import com.xinbo.chainblock.mapper.PermissionMapper;
import com.xinbo.chainblock.mapper.RolePermissionMapper;
import com.xinbo.chainblock.mapper.UserMapper;
import com.xinbo.chainblock.mapper.UserRoleMapper;
import com.xinbo.chainblock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public List<Integer> findPermission(int userId) {
        //根据用户id拿到权限
        List<PermissionEntity> permission = this.getPermission(userId);
        return permission.stream().map(PermissionEntity::getCode).distinct().collect(Collectors.toList());
    }

    private List<PermissionEntity> getPermission(int userId) {

        //Step 1: 根据用户id获取到角色
        List<UserRoleEntity> roleEntityList = userRoleMapper.findByUserId(userId);

        //Step 2: 根据角色获取到权限
        List<Integer> roles = roleEntityList.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());
        List<RolePermissionEntity> rolePermissionEntityList = rolePermissionMapper.findByRoles(roles);

        List<Integer> permissions = rolePermissionEntityList.stream().map(RolePermissionEntity::getPermissionId).distinct().collect(Collectors.toList());

        return permissionMapper.findByIds(permissions);
    }


    @Override
    public List<PermissionEntity> menu(int userId) {
        //根据用户id拿到权限
        List<PermissionEntity> permission = this.getPermission(userId);

        //根据权限表path获取父级id
        List<Integer> parentIdList = new ArrayList<>();
        for (PermissionEntity entity : permission) {
            String[] split = StringUtils.split(entity.getParentPath(), ",");
            if (ObjectUtils.isEmpty(split) || split.length <= 0) {
                continue;
            }

            List<Integer> collect = Stream.of(split).map(Integer::parseInt).collect(Collectors.toList());
            parentIdList.addAll(collect);
        }
        List<Integer> ids = parentIdList.stream().distinct().collect(Collectors.toList());

        //根据id查询
        List<PermissionEntity> entityList = permissionMapper.findByIds(ids);

        //绑定层级
        List<PermissionEntity> folders = entityList.stream().filter(f -> f.getParentId().equals(0)).collect(Collectors.toList());
        for(PermissionEntity folder : folders) {
            List<PermissionEntity> pages = entityList.stream().filter(f -> f.getParentId().equals(folder.getId())).collect(Collectors.toList());
            folder.setChildren(pages);
        }
        return folders;
    }

    private List<MenuDto> format(List<PermissionEntity> menus) {

        List<MenuDto> result = new ArrayList<>();

        for (PermissionEntity entity : menus) {
            MenuDto build = MenuDto.builder()
                    .path("")

                    .build();
        }

        return result;
    }

    @Override
    public UserEntity findById(int userId) {
        return userMapper.selectById(userId);
    }

}
