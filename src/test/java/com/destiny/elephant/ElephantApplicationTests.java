package com.destiny.elephant;

import com.alibaba.fastjson.JSONObject;
import com.destiny.elephant.mapper.MenuMapper;
import com.destiny.elephant.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElephantApplicationTests {

    private Logger LOGGER = LoggerFactory.getLogger(ElephantApplicationTests.class);

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void contextLoads() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isShow", true);
        map.put("parentId", 0);
        long start = System.currentTimeMillis();
        List<Menu> list = menuMapper.getMenus(map);
        long end = System.currentTimeMillis();
        LOGGER.info("cost is {}", (end - start));
        LOGGER.info(JSONObject.toJSONString(list));
    }

}
