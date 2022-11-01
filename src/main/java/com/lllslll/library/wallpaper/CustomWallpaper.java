package com.lllslll.library.wallpaper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/**
 * Created by ISU on 2019-01-20.
 */

public abstract class CustomWallpaper extends WallpaperService
{
    private int frameCount = 60;

    private int updateForSec = 40;

    private boolean isFrameLimited = false;

    protected abstract void onDraw(Canvas canvas, Paint paint, int width, int height, long deltaTime);

    protected abstract void onUpdate(long deltaTime);

    protected abstract void onSurfaceSizeChanged(int width, int height);

    protected abstract void onSurfaceVisibilityChanged(boolean visible);

    @Override
    public Engine onCreateEngine() {
        return new CustomEngine();
    }

    public void setFrameLimit(boolean limit) {
        this.isFrameLimited = limit;
    }

    public void setFrameCount(int count) {
        this.frameCount = count;
    }

    public void setUpdateForSec(int count) {
        this.updateForSec = count;
        if (count > 1000)
            this.updateForSec = 1000;
    }

    private class CustomEngine extends Engine {
        private int width, height;

        private long frameStart, frameEnd, frameDeltaTime = 0;

        private long updateStart, updateEnd, updateDeltaTime = 0;

        private Canvas canvas;

        private Paint paint;

        private SurfaceHolder holder;

        private LoopThread drawThread, updateThread;

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            super.onSurfaceChanged(holder, format, w, h);

            if(this.width != w || this.height != h)
                onSurfaceSizeChanged(w, h);

            this.width = w;
            this.height = h;
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            onSurfaceVisibilityChanged(visible);

            if(visible) {
                this.updateThread = new LoopThread() {
                    @Override
                    protected void onLoop() {
                        update();
                    }
                };
                this.updateThread.start();

                this.drawThread = new LoopThread() {
                    @Override
                    protected void onLoop() {
                        drawFrame();
                    }
                };
                this.drawThread.start();
            } else {
                this.updateThread.exit();
                this.drawThread.exit();
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);

            this.updateThread.exit();
            this.drawThread.exit();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            this.updateThread.exit();
            this.drawThread.exit();
        }

        private void update()
        {
            this.updateStart = System.currentTimeMillis();

            if(updateForSec < 1000) {
                try {
                    Thread.sleep(1000 / updateForSec);
                } catch (InterruptedException err) {}
            }

            if(this.updateDeltaTime > 0)
                onUpdate(updateDeltaTime);

            this.updateEnd = System.currentTimeMillis();
            this.updateDeltaTime = updateEnd - updateStart;
        }

        private void drawFrame()
        {
            this.frameStart = System.currentTimeMillis();

            if(isFrameLimited) {
                try {
                    long max_fps = 1000 / frameCount;
                    if(this.frameDeltaTime < max_fps)
                        Thread.sleep(max_fps - this.frameDeltaTime);
                } catch (InterruptedException err) {
                }
            }

            this.holder = this.getSurfaceHolder();
            if(this.holder != null) {
                try {
                    this.canvas = this.holder.lockCanvas();

                    if(this.paint == null)
                        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    else
                        this.paint.reset();

                    if(this.canvas != null) {
                        onDraw(this.canvas, this.paint, this.width, this.height, this.frameDeltaTime);
                        this.paint.reset();
                        this.holder.unlockCanvasAndPost(this.canvas);
                    }
                } catch (Exception err) {}
            }

            this.frameEnd = System.currentTimeMillis();
            this.frameDeltaTime = this.frameEnd - this.frameStart;
        }
    }
}