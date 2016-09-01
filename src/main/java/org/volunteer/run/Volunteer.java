package org.volunteer.run;/**
 * Description : 
 * Created by YangZH on 16-8-28
 *  上午1:40
 */

/**
 * Description :
 * Created by YangZH on 16-8-28
 * 上午1:40
 */

public class Volunteer {

    public static void main(String[] args) {
//        HttpServer server = new HttpServer(Const.DEFAULT_CONFIGPATH);
//        server.startup();
        Task t1 = new Task();
        Thread th1 = new Thread(t1,"thread 1");
        Thread th2 = new Thread(t1,"thread 2");
        th1.start();
        th2.start();
    }
}

class Task implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" before sleep");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" after sleep");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
