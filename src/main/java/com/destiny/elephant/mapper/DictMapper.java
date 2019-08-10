package com.destiny.elephant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.elephant.entity.Dict;

/**
 * <p>
  * 字典表 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2017-11-26
 */
public interface DictMapper extends BaseMapper<Dict> {

    Integer selectMaxSort(Dict dict);
}