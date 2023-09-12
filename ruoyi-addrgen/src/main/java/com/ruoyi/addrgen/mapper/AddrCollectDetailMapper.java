package com.ruoyi.addrgen.mapper;

import com.ruoyi.addrgen.domain.AddrCollectDetail;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;

import java.util.List;

public interface AddrCollectDetailMapper {
    //批量插入地址到addrcollect_detail表
    public int batchInsertDetail(List<AddrCollectDetail> iplist);
    //查找任务收集到的所有IP地址
    public List<AddrCollectDetail> selectDetail(String uid);
    public int deleteDetailByUid(String uid);
    public List<AddrCollectDetail> selectIps2();
}
