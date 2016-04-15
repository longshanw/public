package com.ehaoyao.logistics.jd.task.thread;

public class MyThread extends Thread {

	 @Override
    public void run() {
        System.out.println("线程:" + Thread.currentThread().getName());
        Integer num = DealSchedule.getLine();
        System.out.println("startline = " +(num-1000)+",endline = " + num);
    }

}
