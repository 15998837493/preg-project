package com.mnt.preg.master.service.basic;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.preg.master.dao.basic.FoodMaterialDAO;
import com.mnt.preg.master.entity.basic.FoodMaterialExt;
import com.mnt.preg.master.entity.basic.NutrientAmount;

@Service
public class ImportFmDataService {

	private static final String corresType = "foodMaterial";

	private static final String insid = "C000000";

	private static final String updateUserId = "update";

	@Autowired
	private FoodMaterialDAO foodMaterialDAO;

	@Transactional
	public void importFmData() {

		// 1:查询出所有数据到list
		List<FoodMaterialExt> list = foodMaterialDAO.queryFoodMaterialExt();

		// 2:数据转换、保存
		for (FoodMaterialExt pojo : list) {

			// 能量
			if (pojo.getFmEnergy() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00001");
				nutrientAmount.setNutrientDosage(pojo.getFmEnergy());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}
			// 蛋白质
			if (pojo.getFmProtid() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00002");
				nutrientAmount.setNutrientDosage(pojo.getFmProtid());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}
			// 脂肪
			if (pojo.getFmFat() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00003");
				nutrientAmount.setNutrientDosage(pojo.getFmFat());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}
			// 碳水化合物
			if (pojo.getFmCbr() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00004");
				nutrientAmount.setNutrientDosage(pojo.getFmCbr());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 维生素A
			if (pojo.getFmVitaminA() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00005");
				nutrientAmount.setNutrientDosage(pojo.getFmVitaminA());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}
			// 维生素D
			// if (pojo.getFmEnergy() != null) {
			// nutrientAmount.setNutrientId("E00006");
			// nutrientAmount.setNutrientDosage(pojo.getFmEnergy());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }
			// 维生素E
			if (pojo.getFmVitaminE() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00007");
				nutrientAmount.setNutrientDosage(pojo.getFmVitaminE());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}
			// 维生素K
			// if (pojo.getFmElementCa() != null) {
			// nutrientAmount.setNutrientId("E00008");
			// nutrientAmount.setNutrientDosage(pojo.getFmElementCa());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }
			// 维生素B1
			// if (pojo.getFmEnergy() != null) {
			// nutrientAmount.setNutrientId("E00009");
			// nutrientAmount.setNutrientDosage(pojo.getFmEnergy());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }
			// 维生素B2
			// if (pojo.getFmEnergy() != null) {
			// nutrientAmount.setNutrientId("E000010");
			// nutrientAmount.setNutrientDosage(pojo.getFmEnergy());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }
			// 维生素B6
			if (pojo.getFmVitaminB6() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00011");
				nutrientAmount.setNutrientDosage(pojo.getFmVitaminB6());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 维生素B12
			if (pojo.getFmVitaminB12() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00012");
				nutrientAmount.setNutrientDosage(pojo.getFmVitaminB12());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 泛酸
			// if (pojo.getFmProtid() != null) {
			// nutrientAmount.setNutrientId("E00013");
			// nutrientAmount.setNutrientDosage(pojo.getFmProtid());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }

			// 叶酸
			if (pojo.getFmFolate() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00014");
				nutrientAmount.setNutrientDosage(pojo.getFmFolate());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 烟酸
			if (pojo.getFmNiacin() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00015");
				nutrientAmount.setNutrientDosage(pojo.getFmNiacin());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 胆碱
			// if (pojo.getFmVitaminA() != null) {
			// nutrientAmount.setNutrientId("E00016");
			// nutrientAmount.setNutrientDosage(pojo.getFmVitaminA());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }
			// 生物素
			// if (pojo.getFmEnergy() != null) {
			// nutrientAmount.setNutrientId("E000017");
			// nutrientAmount.setNutrientDosage(pojo.getFmFat());
			// getNutrientAmount(nutrientAmount, pojo);
			// foodMaterialDAO.save(nutrientAmount);
			// }
			// 维生素C
			if (pojo.getFmVitaminC() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00018");
				nutrientAmount.setNutrientDosage(pojo.getFmVitaminC());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}
			// 钙
			if (pojo.getFmElementCa() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00019");
				nutrientAmount.setNutrientDosage(pojo.getFmElementCa());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}
			// 磷
			if (pojo.getFmElementP() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00020");
				nutrientAmount.setNutrientDosage(pojo.getFmElementP());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 钾
			if (pojo.getFmElementK() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00021");
				nutrientAmount.setNutrientDosage(pojo.getFmElementK());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}
			// 钠
			if (pojo.getFmElementNa() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00022");
				nutrientAmount.setNutrientDosage(pojo.getFmElementNa());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 镁
			if (pojo.getFmElementMg() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00023");
				nutrientAmount.setNutrientDosage(pojo.getFmElementMg());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 铁
			if (pojo.getFmElementFe() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00024");
				nutrientAmount.setNutrientDosage(pojo.getFmElementFe());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 碘
			if (pojo.getFmElementI() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00025");
				nutrientAmount.setNutrientDosage(pojo.getFmElementI());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 锌
			if (pojo.getFmElementZn() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00026");
				nutrientAmount.setNutrientDosage(pojo.getFmElementZn());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 硒
			if (pojo.getFmElementSe() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00027");
				nutrientAmount.setNutrientDosage(pojo.getFmElementSe());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}

			// 铜
			if (pojo.getFmElementCu() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00028");
				nutrientAmount.setNutrientDosage(pojo.getFmElementCu());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}
			// 铬
			// if (pojo.getFmVitaminE() != null) {
			// nutrientAmount.setNutrientId("E00029");
			// nutrientAmount.setNutrientDosage(pojo.getFmVitaminE());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }
			// 锰
			if (pojo.getFmElementMn() != null) {
				NutrientAmount nutrientAmount = new NutrientAmount();
				nutrientAmount.setNutrientId("E00030");
				nutrientAmount.setNutrientDosage(pojo.getFmElementMn());
				getNutrientAmount(nutrientAmount, pojo);
				foodMaterialDAO.save(nutrientAmount);
			}
			// 钼
			// if (pojo.getFmEnergy() != null) {
			// nutrientAmount.setNutrientId("E00031");
			// nutrientAmount.setNutrientDosage(pojo.getFmEnergy());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }
			// a-亚麻酸
			// if (pojo.getFmEnergy() != null) {
			// nutrientAmount.setNutrientId("E000032");
			// nutrientAmount.setNutrientDosage(pojo.getFmEnergy());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }
			// DHA
			// if (pojo.getFmVitaminB6() != null) {
			// nutrientAmount.setNutrientId("E00033");
			// nutrientAmount.setNutrientDosage(pojo.getFmVitaminB6());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }

			// 膳食纤维
			// if (pojo.getFmEnergy() != null) {
			// nutrientAmount.setNutrientId("E00034");
			// nutrientAmount.setNutrientDosage(pojo.getFmEnergy());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }

			// 牛磺酸
			// if (pojo.getFmProtid() != null) {
			// nutrientAmount.setNutrientId("E00035");
			// nutrientAmount.setNutrientDosage(pojo.getFmProtid());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }

			// EPA
			// if (pojo.getFmFat() != null) {
			// nutrientAmount.setNutrientId("E00036");
			// nutrientAmount.setNutrientDosage(pojo.getFmFat());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }

			// 氯
			// if (pojo.getFmCbr() != null) {
			// nutrientAmount.setNutrientId("E00037");
			// nutrientAmount.setNutrientDosage(pojo.getFmCbr());
			// nutrientAmount.setCorresType(corresType);
			// foodMaterialDAO.save(nutrientAmount);
			// }

		}

	}

	public NutrientAmount getNutrientAmount(NutrientAmount nutrientAmount, FoodMaterialExt foodMaterialExt) {
		nutrientAmount.setCorresId(foodMaterialExt.getFmId());
		nutrientAmount.setCorresType(corresType);
		nutrientAmount.setCreateUserId("import");
		nutrientAmount.setCreateInsId(insid);
		nutrientAmount.setUpdateUserId(updateUserId);
		nutrientAmount.setFlag(1);
		return nutrientAmount;
	}

}
