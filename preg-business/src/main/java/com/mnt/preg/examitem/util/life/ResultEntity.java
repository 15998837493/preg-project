
package com.mnt.preg.examitem.util.life;

public class ResultEntity {

    // 比例
    private double ratio;

    // 食物总量
    private double weight;

    public ResultEntity(double ratio, double weight) {
        super();
        this.ratio = ratio;
        this.weight = weight;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
