package com.lllslll.library.wallpaper;

/**
 * Created by ISU on 2019-01-20.
 */

public abstract class LoopThread extends Thread
{
    private boolean exit = false;

    protected abstract void onLoop();

    @Override
    public synchronized void start()
    {
        this.exit = false;
        super.start();
    }

    public void exit()
    {
        this.exit = true;
    }

    @Override
    public void run()
    {
        while(!this.exit)
            this.onLoop();
    }
}
