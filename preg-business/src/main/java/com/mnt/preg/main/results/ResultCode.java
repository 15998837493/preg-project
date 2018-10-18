
package com.mnt.preg.main.results;

/**
 * 
 * 定义规则：
 * 
 * 1、所有成功统一定义为0
 * 2、所有错误统一定义为5位数字，例如10001密码不正确
 * 3、第一部分90到99由系统级占用其中90到91为公共错误码定义
 * 4、99999统一代表未知异常
 * 5、健康管理平台--11=用户模块、12=客户模块、13=基础信息模块、14=报表模块、80=系统管理模块、90=系统级错误
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-16 zy 初版
 */
public class ResultCode {

    /** 操作成功 */
    public static int SUC = 0;

    /** 操作失败 */
    public static int FAIL = -1;

    /** 会员用户不存在 */
    public static String ERROR_10001 = "10001";

    /** 会员用户密码错误 */
    public static String ERROR_10002 = "10002";

    /** 会员不存在或密码错误 */
    public static String ERROR_10003 = "10003";

    /** 该会员尚未激活 */
    public static String ERROR_10004 = "10004";

    /** 会员未干预 */
    public static String ERROR_10005 = "10005";

    /** 已存在相同的会员信息，当前会员编号无法使用 */
    public static String ERROR_10006 = "10006";

    /** 已存在相同的会员信息，当前手机号码无法使用 */
    public static String ERROR_10007 = "10007";

    /** 已存在相同的会员信息，当前电子邮件无法使用 */
    public static String ERROR_10008 = "10008";

    /** 链接已失效 */
    public static String ERROR_10009 = "10009";

    /** 已存在相同的会员信息，当前会员身份证号无法使用 */
    public static String ERROR_10010 = "10010";

    /** 该日期已预约！ */
    public static String ERROR_10011 = "10011";

    /** 该人体成分数据已完成同步！ */
    public static String ERROR_10012 = "10012";

    /** 没有检索到摄入量明细信息 */
    public static String ERROR_50001 = "50001";

    /** 没有找到符合要求的膳食结构 */
    public static String ERROR_50002 = "50002";

    /** 所选疾病对应的膳食结构交集为空 */
    public static String ERROR_50003 = "50003";

    /** 没有检索到摄入量模版信息 */
    public static String ERROR_50004 = "50004";

    /** 疾病编码不存在 */
    public static String ERROR_50005 = "50005";

    /** 请先删除类型下的疾病信息 */
    public static String ERROR_50006 = "50006";

    /** 未选择要干预的疾病！ */
    public static String ERROR_50007 = "50007";

    /** 没有查询到符合要求的膳食类型！ */
    public static String ERROR_50008 = "50008";

    /** 没有查询到符合要求的一日饮食清单！ */
    public static String ERROR_50009 = "50009";

    /** 没有查询到干预重点！ */
    public static String ERROR_50010 = "50010";

    /** 报告生成HTML失败 */
    public static String ERROR_60001 = "60001";

    /** PDF报告文件不存在 */
    public static String ERROR_60002 = "60002";

    /** 生成PDF报告错误 */
    public static String ERROR_60003 = "60003";

    /** 报告编码重复生成次数过大 */
    public static String ERROR_60004 = "60004";

    /** 未做干预方案 */
    public static String ERROR_60005 = "60005";

    /** 未做项目检测 */
    public static String ERROR_60006 = "60006";

    /** 已存在相同的系统用户信息，当前用户编号无法使用 */
    public static String ERROR_80001 = "80001";

    /** 已存在相同的用户信息，当前用户手机号无法使用 */
    public static String ERROR_80002 = "80002";

    /** 已存在相同的用户信息，当前用户邮件无法使用 */
    public static String ERROR_80003 = "80003";

    /** 用户编码不能为空 */
    public static String ERROR_80004 = "80004";

    /** 请设置用户所属机构 */
    public static String ERROR_80005 = "80005";

    /** 用户输入密码不正确 */
    public static String ERROR_80006 = "80006";

    /** 用户不存在 */
    public static String ERROR_80007 = "80007";

    /** 用户名或密码不正确 */
    public static String ERROR_80008 = "80008";

    /** 机构不存在 */
    public static String ERROR_80009 = "80009";

    /** 机构未启用 */
    public static String ERROR_80010 = "80010";

    /** 机构已过期 */
    public static String ERROR_80011 = "80011";

    /** 该指标已存在 */
    public static String ERROR_80012 = "80012";

    /** 该指标不存在 */
    public static String ERROR_80013 = "80013";

    /** 该助理未配置医生 */
    public static String ERROR_80014 = "80014";

    /** 查询结果为空 */
    public static String ERROR_90001 = "90001";

    /** 主键重复 */
    public static String ERROR_90002 = "90002";

    /** 输入参数错误 */
    public static String ERROR_90003 = "90003";

    /** 密码MD5加密异常 */
    public static String ERROR_90004 = "90004";

    /** copyProperties异常 */
    public static String ERROR_90005 = "90005";

    /** 未登陆状态，接口不能调用 */
    public static String ERROR_90006 = "90006";

    /** HQL通过更新方法异常 */
    public static String ERROR_90007 = "90007";

    /** 令牌不正确 */
    public static String ERROR_90009 = "90009";

    /** 令牌已过期 */
    public static String ERROR_90010 = "90010";

    /** 主键生成异常 */
    public static String ERROR_90012 = "90012";

    /** 输入参数为空 */
    public static String ERROR_90013 = "90013";

    /** 内容重复，请检查核对 */
    public static String ERROR_90014 = "90014";

    /** 接口服务层异常 */
    public static String ERROR_99997 = "99997";

    /** 数据库层异常 */
    public static String ERROR_99998 = "99998";

    /** 非法异常 */
    public static String ERROR_99999 = "99999";

}
