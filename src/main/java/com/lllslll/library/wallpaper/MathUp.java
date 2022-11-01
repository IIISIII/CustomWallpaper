package com.lllslll.library.wallpaper;

/**
 * Created by ISU on 2022-06-19.
 */

public class MathUp
{
    public class Vector3
    {
        private float x, y, z;

        public Vector3(float x, float y, float z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Vector3()
        {
            this(0, 0, 0);
        }

        public void set(float x, float y, float z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void plus(Vector3 v)
        {
            this.x += v.x;
            this.y += v.y;
            this.z += v.z;
        }

        public Vector3 plus2(Vector3 v)
        {
            return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
        }

        public void minus(Vector3 v)
        {
            this.x -= v.x;
            this.y -= v.y;
            this.z -= v.z;
        }

        public Vector3 minus2(Vector3 v)
        {
            return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
        }

        public void multiply(float n)
        {
            this.x *= n;
            this.y *= n;
            this.z *= n;
        }

        public Vector3 multiply2(float n)
        {
            return new Vector3(this.x * n, this.y * n, this.z * n);
        }

        public float getLength()
        {
            return (float)Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
        }

        public void normalize()
        {
            float length = this.getLength();
            if(length == 0)
                return;
            this.x /= length;
            this.y /= length;
            this.z /= length;
        }

        public Vector3 copy()
        {
            return new Vector3(this.x, this.y, this.z);
        }
    }

    public static int random(int min, int max)
    {
        return (int)(Math.random() * (max - min)) + min;
    }

    public static float random(float min, float max)
    {
        return (float)(Math.random() * (max - min)) + min;
    }

    public static int map(int value, int in_min, int in_max, int out_min, int out_max) //value, input_min, input_max, output_min, output_max
    {
        return ((value - in_min) / (in_max - in_min) * (out_max - out_min)) + out_min;
    }

    public static float map(float value, float in_min, float in_max, float out_min, float out_max)
    {
        return ((value - in_min) / (in_max - in_min) * (out_max - out_min)) + out_min;
    }

    public static float getForwardX(float x, float degree, float length)
    {
        return x + (float) Math.sin(degree / 180 * Math.PI) * length;
    }

    public static float getForwardY(float y, float degree, float length)
    {
        return y - (float) Math.cos(degree / 180 * Math.PI) * length;
    }
}
