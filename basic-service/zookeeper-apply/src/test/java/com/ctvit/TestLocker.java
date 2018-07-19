package com.ctvit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ctvit.DitributeLocker;

public class TestLocker {
	
	private  CountDownLatch countDownLatch = new CountDownLatch(1);
	
	public static void main(String[] args) {
		TestLocker testLocker = new TestLocker();
		CountDownLatch countDownLatch = testLocker.getCountDownLatch();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<10 ;i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.submit(testLocker.getLockerThread());
        }
        countDownLatch.countDown();

	}
	public CountDownLatch getCountDownLatch(){
		return this.countDownLatch;
	}
	
	public LockerThread getLockerThread(){
		return new LockerThread();
	}
	
	class LockerThread extends Thread{
		@Override
	    public void run() {
			try{
				countDownLatch.await();
				boolean isGet = DitributeLocker.getInstance().getExclusiveLock(TestLocker.class);
				System.out.println("Thread:"+Thread.currentThread().getName()+",time: "+System.currentTimeMillis()+",isGet:"+isGet);			
			}catch(Exception e){
				e.printStackTrace();
			}
	    }
	}

}
