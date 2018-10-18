
package com.mnt.preg.examitem.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.preg.examitem.pojo.PregDietAnalysePojo;

/**
 * 膳食报告DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-1-14 zcq 初版
 */
@Repository
public class PregDietFoodAnalysisDAO extends HibernateTemplate {

    /**
     * 计算一天的饮食中每种营养物的含量
     * 
     * @param originId
     * @return
     * 
     */
    public List<PregDietAnalysePojo> getFoodElementSum(String foodRecordId) {
        // 灰分在食材表  -- 胡萝卜素没有  -- 胆固醇没有  modify zj 20180828
        String sql = "SELECT " +
        "(SELECT ROUND(SUM(d.food_amount * t1.E00001 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00001 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00001' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEnergy , "+
        
        "(SELECT ROUND(SUM(d.food_amount * t1.E00002 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00002 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00002' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodProtid , "+

        "(SELECT substring_index(ROUND(SUM(d.food_amount * t1.E00003 / 100),1), '.', 1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00003 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00003' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodFat , "+
        
        "(SELECT substring_index(ROUND(SUM(d.food_amount * t1.E00004 / 100),1), '.', 1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00004 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00004' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodCbr , "+
        
        "(SELECT ROUND(SUM(d.food_amount * t1.E00034 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00034 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00034' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodDf , "+

        "(SELECT ROUND(SUM(d.food_amount * t1.E00005 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00005 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00005' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodVa , "+

        "(SELECT ROUND(SUM(d.food_amount * t1.E00004 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00004 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00004' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodAshc , "+

        "(SELECT ROUND(SUM(d.food_amount * t1.E00009 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00009 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00009' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodVb1 , "+
        
        "(SELECT ROUND(SUM(d.food_amount * t1.E00010 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00010 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00010' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodVb2 , "+     
        
        "(SELECT ROUND(SUM(d.food_amount * t1.E00015 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00015 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00015' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodAf , "+          
        
        "(SELECT ROUND(SUM(d.food_amount * t1.E00018 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00018 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00018' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodVc , "+         

        "(SELECT ROUND(SUM(d.food_amount * t1.E00007 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00007 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00007' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodVe , "+   
        
        "(SELECT ROUND(SUM(d.food_amount * t1.E00019 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00019 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00019' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEca , "+    
        
        "(SELECT ROUND(SUM(d.food_amount * t1.E00020 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00020 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00020' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEp , "+  
        
        "(SELECT ROUND(SUM(d.food_amount * t1.E00021 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00021 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00021' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEk , "+  

        "(SELECT ROUND(SUM(d.food_amount * t1.E00022 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00022 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00022' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEna , "+  
        
        "(SELECT ROUND(SUM(d.food_amount * t1.E00023 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00023 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00023' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEmg , "+  

        "(SELECT ROUND(SUM(d.food_amount * t1.E00024 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00024 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00024' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEfe , "+         

        "(SELECT ROUND(SUM(d.food_amount * t1.E00025 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00025 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00025' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEzn , "+         

        "(SELECT ROUND(SUM(d.food_amount * t1.E00027 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00027 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00027' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEse , "+        

        "(SELECT ROUND(SUM(d.food_amount * t1.E00028 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00028 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00028' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEcu , "+         

        "(SELECT ROUND(SUM(d.food_amount * t1.E00030 / 100),1) FROM "+ 
        "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
        "(SELECT food_1.food_id,food_1.food_name,food_1.food_pic,ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00030 "+
        "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details WHERE details.food_record_id = :foodRecordId)) food_1 "+
        "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+
        "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' AND nutrient_id = 'E00030' AND corres_id IN ( "+
        "SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 ON fm_1.fm_id = nutrient_1.corres_id  GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id ) AS foodEmn "+   
        
        "FROM cus_food_details AS mf WHERE mf.food_record_id =  :foodRecordId  GROUP BY mf.food_record_id";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("foodRecordId", foodRecordId);
        return this.SQLQueryAlias(sql, queryParams, PregDietAnalysePojo.class);
    }

    /**
     * 计算一天中每餐的供能量
     * 
     * @param mflId
     * @return
     * 
     */
    public List<PregDietAnalysePojo> getEachMealEnergy(String foodRecordId) {
        //元素修改，关联关系修改导致代码修改 modify zj 20180829        
        String sql = "SELECT d.meals_id AS mealsId, ROUND(SUM(d.food_amount * t1.E00001 / 100),1) AS foodEnergy FROM "+
                     "(SELECT *  FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
                     "(SELECT food_1.food_id, food_1.food_name, food_1.food_pic, "+
                     "ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00001 "+
                     "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details "+
                     "WHERE details.food_record_id = :foodRecordId)) food_1 "+
                     "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id "+
                     "LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+ 
                     "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' "+
                     " AND nutrient_id = 'E00001' AND corres_id IN (SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 "+
                     " ON fm_1.fm_id = nutrient_1.corres_id GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id GROUP BY d.meals_id";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("foodRecordId", foodRecordId);
        return this.SQLQueryAlias(sql, queryParams, PregDietAnalysePojo.class);
    }

    /**
     * 计算一天的饮食中优质蛋白质和非优质蛋白质的量
     * 
     * @param mflId
     * @return
     * 
     */
    public List<PregDietAnalysePojo> getFoodProtidSum(String foodRecordId) {

        String sql = "SELECT t1.fm_protid_flag AS fmProtidFlag, ROUND(SUM(d.food_amount * t1.E00002 / 100),1) AS protidAmount FROM "+
                "(SELECT * FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
                "(SELECT food_1.food_id, food_1.food_name, food_1.food_pic, fm_1.fm_fat_type, fm_1.fm_protid_flag,"+
                "ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00002 "+
                "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details "+
                "WHERE details.food_record_id = :foodRecordId)) food_1 "+
                "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id "+
                "LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+ 
                "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' "+
                " AND nutrient_id = 'E00002' AND corres_id IN (SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 "+
                " ON fm_1.fm_id = nutrient_1.corres_id GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id GROUP BY t1.fm_protid_flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("foodRecordId", foodRecordId);
        return this.SQLQueryAlias(sql, queryParams, PregDietAnalysePojo.class);
    }

    /**
     * 计算一天的饮食中脂肪类型所占的量
     * 
     * @param mflId
     * @return
     * 
     */
    public List<PregDietAnalysePojo> getFoodFatSum(String foodRecordId) {

        String sql = "SELECT t1.fm_fat_type AS fmFatType, ROUND(SUM(d.food_amount * t1.E00003 / 100),1) AS fatAmount FROM "+
                "(SELECT * FROM cus_food_details d1 WHERE d1.food_record_id = :foodRecordId)d INNER JOIN "+
                "(SELECT food_1.food_id, food_1.food_name, food_1.food_pic, fm_1.fm_fat_type,"+
                "ROUND(SUM(list_1.fml_amount * fm_1.fm_esculent * nutrient_1.nutrient_dosage / 100) / SUM(list_1.fml_amount),1) AS E00003 "+
                "FROM (SELECT * FROM mas_food f WHERE f.food_id IN (SELECT food_id FROM cus_food_details details "+
                "WHERE details.food_record_id = :foodRecordId)) food_1 "+
                "LEFT JOIN mas_food_material_list list_1 ON food_1.food_id = list_1.food_id "+
                "LEFT JOIN mas_food_material fm_1 ON list_1.fm_id = fm_1.fm_id LEFT JOIN "+ 
                "(SELECT corres_id, nutrient_id, nutrient_dosage FROM mas_nutrient_amount WHERE corres_type = 'foodMaterial' "+
                " AND nutrient_id = 'E00003' AND corres_id IN (SELECT DISTINCT fm_id FROM mas_food_material_list)) nutrient_1 "+
                " ON fm_1.fm_id = nutrient_1.corres_id GROUP BY food_1.food_id)t1 ON d.food_id = t1.food_id GROUP BY t1.fm_fat_type";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("foodRecordId", foodRecordId);
        return this.SQLQueryAlias(sql, queryParams, PregDietAnalysePojo.class);
    }

    /**
     * 计算GL值
     * 
     * @param originId
     * @return
     */
    public List<PregDietAnalysePojo> getGL(String foodRecordId) {
        String sql = "SELECT"
                + "        mf.meals_id AS mealsId,"
                + "        SUM(((mf.food_amount*fml.fml_percent/10000)*mfe.fm_cbr)*fm.fm_gi/100) AS gl"
                + "   FROM cus_food_details AS mf"
                + "        JOIN mas_food_material_list AS fml ON mf.food_id=fml.food_id"
                + "             AND mf.food_record_id=:foodRecordId"
                + "        JOIN mas_food_material AS fm ON fml.fm_id=fm.fm_id"
                + "        JOIN mas_food_material_ext AS mfe ON fm.fm_id=mfe.fm_id"
                + "   GROUP BY mf.meals_id";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("foodRecordId", foodRecordId);
        return this.SQLQueryAlias(sql, queryParams, PregDietAnalysePojo.class);
    }

}
