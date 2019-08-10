package com.destiny.elephant.freemark;

import com.destiny.elephant.service.UserService;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangl on 2018/1/23.
 * todo: 根据用户ID获取用户详情
 */
@Component
public class SysUserTemplateModel implements TemplateMethodModelEx {
    @Autowired
    private UserService userService;
    @Override
    public Object exec(@SuppressWarnings("rawtypes")List list) {
        if(list == null || list.size() == 0){
            throw new RuntimeException("参数为空");
        }
        SimpleNumber simpleNumber = (SimpleNumber) list.get(0);
        if(simpleNumber == null){
            return null;
        }
        Long userId = simpleNumber.getAsNumber().longValue();
        return userService.findUserById(userId);
    }
}
