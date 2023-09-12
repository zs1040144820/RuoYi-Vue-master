package com.ruoyi.addrgen.service;

import com.ruoyi.addrgen.domain.AddrCollect;
import com.ruoyi.addrgen.domain.AddrCollectDetail;
import com.ruoyi.addrgen.domain.AddrCollectDetail2;
import com.ruoyi.addrgen.domain.AddrCollectTade;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface IAddrCollectService {
    public void StartCollecting(AddrCollect addrcollect);
    public List<AddrCollect> selectCollectList(AddrCollect addrcollect);
    public void inserTask(AddrCollect addrcollect);
    public int deleteTaskByUid(String taskId);
    public List<AddrCollectTade> selectTadeList(AddrCollectTade act);
    public String getCommand(String uid);
    public List<String> getDetail(String uid);
    public int deleteDetailByUid(String uid);
    public String showTask(String uid);
    public void inserTask2(AddrCollect addrcollect);
    public void StartCollecting2(AddrCollect addrcollect);
    public List<AddrCollectDetail2> getCommand2(String uid);
    public List<String> getDetail2(String uid);
    public String getTempcom(String uid);
    public void terminateTask(String uid);
    public void terminateTask2(String uid);
    public List<AddrCollect> selectDeList2();
    public List<AddrCollect> selectCollectList1(AddrCollect addrcollect);
    public List<AddrCollect> selectCollectList2(AddrCollect addrcollect);
    public List<String> selectIps();
    public List<String> selectIps2();
}
