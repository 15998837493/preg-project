/**
 * 
 */

package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 中国区域信息表
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-7-22 mnt_zhangjing 初版
 */
@Entity
@Table(name = "mas_china_info")
public class ChinaInfo extends MappedEntity {

    private static final long serialVersionUID = 8033305822991755383L;

    /** 主键 */
    private String id;

    /** 名称 */
    @UpdateAnnotation
    private String name;

    /** 上级主键 */
    @UpdateAnnotation
    private String pid;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "pid")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "ChinaInfo [id=" + id + ", name=" + name + ", pid=" + pid + "]";
    }

}
