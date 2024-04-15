package org.archer.game;

public class Thread_manager {

    boolean signal = false;
    public void do_wait() {
        synchronized(this){
            try {
                while (!signal){
                    this.wait();
                }
                signal = false;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void do_notify(){
        synchronized (this){
            signal = true;
            this.notify();
        }
    }
}
