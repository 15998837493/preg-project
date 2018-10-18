
package com.mnt.preg.master.entity.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 食材uuid的对应编码表
 * 
 * @author Administrator
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-3-16 Administrator 初版
 */
@Entity
@Table(name = "mas_food_material_code")
public class FoodMaterialCode extends MappedEntity {

    private static final long serialVersionUID = -8904855642056131700L;

    // 主键
    @QueryFieldAnnotation
    private String id;

    // 食材的uuid
    @UpdateAnnotation
    @QueryFieldAnnotation
    private String uuid;

    // Excel表中食材对应的序列编码
    @UpdateAnnotation
    @QueryFieldAnnotation
    private String code;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
