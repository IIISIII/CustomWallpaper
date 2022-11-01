# CustomWallpaper
custom wallpaper library

void onDraw(Canvas canvas, Paint paint, int width, int height, long deltaTime)

void onUpdate(long deltaTime)

void onSurfaceSizeChanged(int width, int height)

void onSurfaceVisibilityChanged(boolean visible)

void setFrameLimit(boolean limit) //if limit is false, ignore frame rate (unrecommend)
                                  //default: true

void setFrameCount(int count) //set frame rate limit
                              //default: 60

void setUpdateForSec(int count) //update rate per second
                                //default: 40
