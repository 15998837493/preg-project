
package com.mnt.preg.master.entity.preg;

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
 * 孕期_课程
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-9-12 gss 初版
 */
@Entity
@Table(name = "mas_pregnancy_course")
public class PregCourse extends MappedEntity {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 主键 */
    private String id;

    /** 编码 */
    @UpdateAnnotation
    private String pregId;

    /** 名称 */
    @UpdateAnnotation
    private String pregName;

    /** 孕周数范围开始 */
    @UpdateAnnotation
    private Integer pregWeekBegin;

    /** 孕周数范围结束 */
    @UpdateAnnotation
    private Integer pregWeekEnd;

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

    @Column(name = "preg_id")
    public String getPregId() {
        return pregId;
    }

    public void setPregId(String pregId) {
        this.pregId = pregId;
    }

    @Column(name = "preg_name")
    public String getPregName() {
        return pregName;
    }

    public void setPregName(String pregName) {
        this.pregName = pregName;
    }

    @Column(name = "preg_week_begin")
    public Integer getPregWeekBegin() {
        return pregWeekBegin;
    }

    public void setPregWeekBegin(Integer pregWeekBegin) {
        this.pregWeekBegin = pregWeekBegin;
    }

    @Column(name = "preg_week_end")
    public Integer getPregWeekEnd() {
        return pregWeekEnd;
    }

    public void setPregWeekEnd(Integer pregWeekEnd) {
        this.pregWeekEnd = pregWeekEnd;
    }

}
