package com.ruoyi.addrgen.mapper;

import com.ruoyi.addrgen.domain.AddrCollect;
import com.ruoyi.addrgen.domain.AddrCollectTade;

import java.util.List;

public interface AddrCollectTadeMapper {
    //查看地址详情时显示本表
    public List<AddrCollectTade> selectTadeList(AddrCollectTade act);
    //在插入任务列表时将副本插入到本表
    public int insertIntoTade(AddrCollectTade act);
    //删除任务的Tade信息
    public int deleteTadeByUid(String uid);
    //查找一条tade信息
    public AddrCollectTade selectTade(String uid);
}
