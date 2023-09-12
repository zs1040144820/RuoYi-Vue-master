package com.ruoyi.vuln.domain;

import java.util.List;

/**
 * @Description: TODO
 * @author: lzh
 * @date: 2022年09月29日 14:46
 */
public class NmapCommand {
    private String uuid;
    private List<String> scriptSelect;
    private boolean checked;
    private String srcIP;
    private String desIP;
    private String output;
    private boolean useFile;//是否用文件输入
    private String addrFileName;
    private String startPort;
    private String endPort;
    private boolean databaseSwitch;
    private boolean osSwitch;
    private String rate;
    private String status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<String> getScriptSelect() {
        return scriptSelect;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getSrcIP() {
        return srcIP;
    }

    public String getDesIP() {
        return desIP;
    }

    public void setScriptSelect(List<String> scriptSelect) {
        this.scriptSelect = scriptSelect;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setSrcIP(String srcIP) {
        this.srcIP = srcIP;
    }

    public void setDesIP(String desIP) {
        this.desIP = desIP;
    }

    public boolean isUseFile() {
        return useFile;
    }

    public void setUseFile(boolean useFile) {
        this.useFile = useFile;
    }

    public String getAddrFileName() {
        return addrFileName;
    }

    public void setAddrFileName(String addrFileName) {
        this.addrFileName = addrFileName;
    }

    public String getStartPort() {
        return startPort;
    }

    public void setStartPort(String startPort) {
        this.startPort = startPort;
    }

    public String getEndPort() {
        return endPort;
    }

    public void setEndPort(String endPort) {
        this.endPort = endPort;
    }

    public boolean isDatabaseSwitch() {
        return databaseSwitch;
    }

    public void setDatabaseSwitch(boolean databaseSwitch) {
        this.databaseSwitch = databaseSwitch;
    }

    public boolean isOsSwitch() {
        return osSwitch;
    }

    public void setOsSwitch(boolean osSwitch) {
        this.osSwitch = osSwitch;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NmapCommand{" +
                "uuid='" + uuid + '\'' +
                ", scriptSelect=" + scriptSelect +
                ", checked=" + checked +
                ", srcIP='" + srcIP + '\'' +
                ", desIP='" + desIP + '\'' +
                ", output='" + output + '\'' +
                ", useFile=" + useFile +
                ", addrFileName='" + addrFileName + '\'' +
                ", startPort='" + startPort + '\'' +
                ", endPort='" + endPort + '\'' +
                ", databaseSwitch=" + databaseSwitch +
                ", osSwitch=" + osSwitch +
                ", rate='" + rate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
