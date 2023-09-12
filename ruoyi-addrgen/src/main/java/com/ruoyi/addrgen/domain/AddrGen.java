package com.ruoyi.addrgen.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * @Description: TODO
 * @author: lzh
 * @date: 2022年11月01日 10:07
 */
public class AddrGen{
    private String fileID;
    private String fileName;
    private String ismul;
    private boolean activity;
    private boolean stability;
    private int genNum;

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIsmul() {
        return ismul;
    }

    public void setIsmul(String ismul) {
        this.ismul = ismul;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public boolean isStability() {
        return stability;
    }

    public void setStability(boolean stability) {
        this.stability = stability;
    }

    public int getGenNum() {
        return genNum;
    }

    public void setGenNum(int genNum) {
        this.genNum = genNum;
    }

    @Override
    public String toString() {
        return "AddrGen{" +
                "fileID='" + fileID + '\'' +
                ", fileName='" + fileName + '\'' +
                ", ismul='" + ismul + '\'' +
                ", activity=" + activity +
                ", stability=" + stability +
                ", genNum=" + genNum +
                '}';
    }
}
