package com.ruoyi.vuln.commandsubject;

import com.ruoyi.vuln.commandobserver.CnnvdExecutorObserver;
import com.ruoyi.vuln.commandobserver.CommandExecutorObserver;


import java.util.ArrayList;

public interface CnnvdExecutorSubject {
    public ArrayList<CnnvdExecutorObserver> observers = new ArrayList<CnnvdExecutorObserver>();
    public void addObserver(CnnvdExecutorObserver observer) ;
    public void removeObserver(CnnvdExecutorObserver observer);
    public void notifyObserves(String rate);
}
