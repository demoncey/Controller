package com.controller.massena.tasks;

import android.os.AsyncTask;
import android.os.Handler;

/**
 * Created by massena on 2017-06-03.
 */

public class ExtendedAsyncTask extends AsyncTask  {
    private final Handler handler;
    private boolean running=false;


    public void setRunning(boolean flag){
        running=flag;


    }

    public boolean isRunning(){
        return this.running;
    }

    public ExtendedAsyncTask(Handler handler){
        this.handler=handler;
    }

    public ExtendedAsyncTask(){
        this.handler=null;
    }

    public void exec (){
        //now several task in parallel are working
        this.setRunning(true);
        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    @Override
    protected Object doInBackground(Object[] params) {
        //something like run put your code here
        while(isRunning()){
            doIt();
        }
        return null;
    }

    public void doIt(){


    }

    public Handler getHandler(){
        return this.handler;
    }
}