
package com.mnt.preg.examitem.util.life;

/**
 * 
 * 膳食及生活方式问题
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-6 mnt_zhangjing 初版
 */
public class Problem {

    // 问题编号
    private String problemId;

    // 问题名称
    private String problemName;

    // 问题类型 1 单选 2 多选
    private String problemType;

    public Problem(String problemId, String problemName, String problemType) {
        super();
        this.problemId = problemId;
        this.problemName = problemName;
        this.problemType = problemType;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

}
