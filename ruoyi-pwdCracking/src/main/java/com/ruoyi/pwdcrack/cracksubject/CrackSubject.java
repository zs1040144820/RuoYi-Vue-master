package com.ruoyi.pwdcrack.cracksubject;

import com.ruoyi.pwdcrack.crackobserver.CrackObserver;
import com.ruoyi.pwdcrack.domain.CrackCommand;

import java.util.ArrayList;

public interface CrackSubject {
    //发布者
    public ArrayList<CrackObserver> observers = new ArrayList<CrackObserver>();
    public void addObserver(CrackObserver observer) ;
    public void removeObserver(CrackObserver observer);
    public void notifyObserves(CrackCommand cmd);
}
