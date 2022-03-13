package com.company;

public class MyVector {

    public double x1;
    public double x2;
    public double fx;

    MyVector(double x1,double x2){
        this.x1 = x1;
        this.x2 = x2;
        reWrightFX();
    }

    MyVector(double x1,double x2, double fx){
        this.x1 = x1;
        this.x2 = x2;
        this.fx = -1;
    }

    public void reWrightFX(){
        this.fx = 5d * this.x1 * this.x1 - 4d * this.x1 * this.x2 + 5d * this.x2 * this.x2 - this.x1 - this.x2;
    }

    @Override
    public String toString() {
        return "x1 = " + this.x1 + ", x2 = " + this.x2 + ", f(x) = " + this.fx;
    }
}
