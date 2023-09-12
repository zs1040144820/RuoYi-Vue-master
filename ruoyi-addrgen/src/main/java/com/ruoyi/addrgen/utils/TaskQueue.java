package com.ruoyi.addrgen.utils;

public class TaskQueue {
    private String[] arr;
    private int front;
    private int rear;

    public TaskQueue(int capacity) {
        arr = new String[capacity];
        front = -1;
        rear = -1;
    }

    public void enqueue(String item) {
        if (isFull()) {
            throw new RuntimeException("当前任务执行中！");
        }
        if (isEmpty()) {
            front = 0;
        }
        rear++;
        arr[rear] = item;
    }

    public String dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("任务异常！请重新执行");
        }
        String item = arr[front];
        arr[front] = null;
        front++;
        if (front > rear) {
            front = -1;
            rear = -1;
        }
        return item;
    }

    public boolean isEmpty() {
        return front == -1;
    }

    public boolean isFull() {
        return rear == arr.length - 1;
    }

    public String getFront() {
        if (isEmpty()) {
            throw new RuntimeException("任务异常！请重新执行");
        }
        return arr[front];
    }
}