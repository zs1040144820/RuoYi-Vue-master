package com.ruoyi.pwdcrack.crackobserver;

import com.ruoyi.pwdcrack.domain.CrackCommand;

public interface CrackObserver {
    public void update(CrackCommand cmd);
}
