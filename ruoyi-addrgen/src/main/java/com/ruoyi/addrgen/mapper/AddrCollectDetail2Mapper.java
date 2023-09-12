package com.ruoyi.addrgen.mapper;

import com.ruoyi.addrgen.domain.AddrCollect;
import com.ruoyi.addrgen.domain.AddrCollectDetail;
import com.ruoyi.addrgen.domain.AddrCollectDetail2;

import java.util.List;

public interface AddrCollectDetail2Mapper {
    //批量插入地址到addrcollect_detail表
    public int insertDetail2(AddrCollectDetail2 acd2);
    //查找任务list信息
    public List<AddrCollectDetail2> selectDetail2(AddrCollectDetail2 acd2);
    //删除detail2记录
    public int deleteDetail2ByUid(String taskId);
    public List<AddrCollect> selectAll();

    public List<AddrCollectDetail2> selectIps();
}
