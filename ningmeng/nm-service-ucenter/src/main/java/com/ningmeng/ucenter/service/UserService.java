package com.ningmeng.ucenter.service;


import com.ningmeng.framework.domain.ucenter.NmCompanyUser;
import com.ningmeng.framework.domain.ucenter.NmMenu;
import com.ningmeng.framework.domain.ucenter.NmUser;
import com.ningmeng.framework.domain.ucenter.ext.NmUserExt;
import com.ningmeng.ucenter.dao.NmCompanyUserRepository;
import com.ningmeng.ucenter.dao.NmMenuMapper;
import com.ningmeng.ucenter.dao.NmUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lenovo on 2020/3/11.
 */
@Service
public class UserService {
    @Autowired
    private NmUserRepository nmUserRepository;
    @Autowired
    private NmCompanyUserRepository nmCompanyUserRepository;
    @Autowired
    private NmMenuMapper nmMenuMapper;
    //根据用户账号查询用户信息
    public NmUser findNmUserByUsername(String username){
        return nmUserRepository.findNmUserByUsername(username);
    }
    //根据账号查询用户的信息，返回用户扩展信息
    public NmUserExt getUserExt(String username){
        NmUser nmUser = this.findNmUserByUsername(username);
        if(nmUser == null){
            return null;
        }
        NmUserExt nmUserExt = new NmUserExt();
        BeanUtils.copyProperties(nmUser,nmUserExt);
        //用户id
        String userId = nmUserExt.getId();
        //查询用户所属公司
        NmCompanyUser nmCompanyUser = nmCompanyUserRepository.findByUserId(userId);
        if(nmCompanyUser!=null){
            String companyId = nmCompanyUser.getCompanyId();
            nmUserExt.setCompanyId(companyId);
        }
        //根据用户id查询用户权限
        List<NmMenu> nmMenuList = nmMenuMapper.selectPermissionByUserId(userId);
        //用户的权限
        nmUserExt.setPermissions(nmMenuList);
        return nmUserExt;
    }
}
