package com.ruoyi.addrgen.mapper;

import com.ruoyi.addrgen.domain.AddrCollect;
import com.ruoyi.addrgen.domain.AddrgenSeedfileHandle;

import java.util.List;

public interface AddrCollectMapper {
    //查询TaskList中所有任务情况
    public List<AddrCollect> selectCollectList(AddrCollect addrcollect);
    //在执行收集任务时向TaskList中插入一条记录
    public int insertIntoTaskList(AddrCollect addrcollect);
    //完成收集任务时更新任务状态
    public int updateTaskList(AddrCollect addrcollect);
    //删除一条任务记录
    public int deleteTaskByUid(String uid);
    //根据uid查找一条记录
    public AddrCollect selectByID(String uid);
    //临时更新控制台的值
    public void updateCom(AddrCollect addrcollect);
}
