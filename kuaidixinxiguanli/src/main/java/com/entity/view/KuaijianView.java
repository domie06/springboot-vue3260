package com.entity.view;

import com.entity.KuaijianEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 快递信息
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("kuaijian")
public class KuaijianView extends KuaijianEntity implements Serializable {
    private static final long serialVersionUID = 1L;

		/**
		* 物品类型的值
		*/
		private String jijianxinxiValue;
		/**
		* 快递状态的值
		*/
		private String kuaijianValue;



		//级联表 kuaidiyuan
			/**
			* 快递员姓名
			*/
			private String kuaidiyuanName;
			/**
			* 快递员手机号
			*/
			private String kuaidiyuanPhone;
			/**
			* 快递员身份证号
			*/
			private String kuaidiyuanIdNumber;
			/**
			* 快递员头像
			*/
			private String kuaidiyuanPhoto;
			/**
			* 电子邮箱
			*/
			private String kuaidiyuanEmail;
			/**
			* 派件数量
			*/
			private Integer kuaidiyuanNumber;

		//级联表 yonghu
			/**
			* 用户姓名
			*/
			private String yonghuName;
			/**
			* 头像
			*/
			private String yonghuPhoto;
			/**
			* 联系方式
			*/
			private String yonghuPhone;
			/**
			* 邮箱
			*/
			private String yonghuEmail;
			/**
			* 假删
			*/
			private Integer yonghuDelete;

	public KuaijianView() {

	}

	public KuaijianView(KuaijianEntity kuaijianEntity) {
		try {
			BeanUtils.copyProperties(this, kuaijianEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



			/**
			* 获取： 物品类型的值
			*/
			public String getJijianxinxiValue() {
				return jijianxinxiValue;
			}
			/**
			* 设置： 物品类型的值
			*/
			public void setJijianxinxiValue(String jijianxinxiValue) {
				this.jijianxinxiValue = jijianxinxiValue;
			}
			/**
			* 获取： 快递状态的值
			*/
			public String getKuaijianValue() {
				return kuaijianValue;
			}
			/**
			* 设置： 快递状态的值
			*/
			public void setKuaijianValue(String kuaijianValue) {
				this.kuaijianValue = kuaijianValue;
			}








				//级联表的get和set kuaidiyuan

					/**
					* 获取： 快递员姓名
					*/
					public String getKuaidiyuanName() {
						return kuaidiyuanName;
					}
					/**
					* 设置： 快递员姓名
					*/
					public void setKuaidiyuanName(String kuaidiyuanName) {
						this.kuaidiyuanName = kuaidiyuanName;
					}

					/**
					* 获取： 快递员手机号
					*/
					public String getKuaidiyuanPhone() {
						return kuaidiyuanPhone;
					}
					/**
					* 设置： 快递员手机号
					*/
					public void setKuaidiyuanPhone(String kuaidiyuanPhone) {
						this.kuaidiyuanPhone = kuaidiyuanPhone;
					}

					/**
					* 获取： 快递员身份证号
					*/
					public String getKuaidiyuanIdNumber() {
						return kuaidiyuanIdNumber;
					}
					/**
					* 设置： 快递员身份证号
					*/
					public void setKuaidiyuanIdNumber(String kuaidiyuanIdNumber) {
						this.kuaidiyuanIdNumber = kuaidiyuanIdNumber;
					}

					/**
					* 获取： 快递员头像
					*/
					public String getKuaidiyuanPhoto() {
						return kuaidiyuanPhoto;
					}
					/**
					* 设置： 快递员头像
					*/
					public void setKuaidiyuanPhoto(String kuaidiyuanPhoto) {
						this.kuaidiyuanPhoto = kuaidiyuanPhoto;
					}

					/**
					* 获取： 电子邮箱
					*/
					public String getKuaidiyuanEmail() {
						return kuaidiyuanEmail;
					}
					/**
					* 设置： 电子邮箱
					*/
					public void setKuaidiyuanEmail(String kuaidiyuanEmail) {
						this.kuaidiyuanEmail = kuaidiyuanEmail;
					}

					/**
					* 获取： 派件数量
					*/
					public Integer getKuaidiyuanNumber() {
						return kuaidiyuanNumber;
					}
					/**
					* 设置： 派件数量
					*/
					public void setKuaidiyuanNumber(Integer kuaidiyuanNumber) {
						this.kuaidiyuanNumber = kuaidiyuanNumber;
					}











				//级联表的get和set yonghu

					/**
					* 获取： 用户姓名
					*/
					public String getYonghuName() {
						return yonghuName;
					}
					/**
					* 设置： 用户姓名
					*/
					public void setYonghuName(String yonghuName) {
						this.yonghuName = yonghuName;
					}

					/**
					* 获取： 头像
					*/
					public String getYonghuPhoto() {
						return yonghuPhoto;
					}
					/**
					* 设置： 头像
					*/
					public void setYonghuPhoto(String yonghuPhoto) {
						this.yonghuPhoto = yonghuPhoto;
					}

					/**
					* 获取： 联系方式
					*/
					public String getYonghuPhone() {
						return yonghuPhone;
					}
					/**
					* 设置： 联系方式
					*/
					public void setYonghuPhone(String yonghuPhone) {
						this.yonghuPhone = yonghuPhone;
					}

					/**
					* 获取： 邮箱
					*/
					public String getYonghuEmail() {
						return yonghuEmail;
					}
					/**
					* 设置： 邮箱
					*/
					public void setYonghuEmail(String yonghuEmail) {
						this.yonghuEmail = yonghuEmail;
					}

					/**
					* 获取： 假删
					*/
					public Integer getYonghuDelete() {
						return yonghuDelete;
					}
					/**
					* 设置： 假删
					*/
					public void setYonghuDelete(Integer yonghuDelete) {
						this.yonghuDelete = yonghuDelete;
					}




}
