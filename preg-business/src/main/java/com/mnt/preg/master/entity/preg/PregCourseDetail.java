
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
 * 食物卡片
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-20 gss 初版
 */
@Entity
@Table(name = "mas_pregnancy_course_detail")
public class PregCourseDetail extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 主键 */
    private String id;

    /** 主表id */
    @UpdateAnnotation
    private String pregId;

    /** 类别名称 */
    @UpdateAnnotation
    private String pregDeName;

    /** 编码 */
    @UpdateAnnotation
    private String pregDeCode;

    /** 排序 */
    @UpdateAnnotation
    private Integer pregDeOrder;

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

    @Column(name = "preg_de_name")
    public String getPregDeName() {
        return pregDeName;
    }

    public void setPregDeName(String pregDeName) {
        this.pregDeName = pregDeName;
    }

    @Column(name = "preg_de_code")
    public String getPregDeCode() {
        return pregDeCode;
    }

    public void setPregDeCode(String pregDeCode) {
        this.pregDeCode = pregDeCode;
    }

    @Column(name = "preg_de_order")
    public Integer getPregDeOrder() {
        return pregDeOrder;
    }

    public void setPregDeOrder(Integer pregDeOrder) {
        this.pregDeOrder = pregDeOrder;
    }

}
