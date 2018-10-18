/*
 * QuestionConstant.java    1.0  2016-3-8
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.util;

/**
 * 
 * 问卷相关常量
 *
 * @author mnt_zhangjing
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2016-3-8 mnt_zhangjing 初版
 */
public class QuestionConstant {

    /** 有子节点 */
    public static final int HAS_CHILDREN = 1;

    /** 无子节点 */
    public static final int NOT_HAS_CHILDREN = 0;
    
    /** 使用男或女性别 */
    public static final String ALL = "all";
    
    /** 问卷状态 常量：1=完成 */
    public static final int QUESTION_STATUS_FINISH = 1;
    
    /** 问卷状态 常量：2=过期 */
    public static final int QUESTION_STATUS_OVER = 2;
    
    /** 问卷状态 常量：3=作废 */
    public static final int QUESTION_STATUS_CANCEL = 3;

    /** 问卷状态 常量：9=失败 */
    public static final int QUESTION_STATUS_FAIL = 9;
}
