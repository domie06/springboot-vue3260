package com.entity.vo;

import com.entity.KuaijianEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 快递信息
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("kuaijian")
public class KuaijianVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 编号
     */

    @TableField(value = "kuaijian_uuid_number")
    private String kuaijianUuidNumber;


    /**
     * 发件人名称
     */

    @TableField(value = "kuaijian_name")
    private String kuaijianName;


    /**
     * 联系方式
     */

    @TableField(value = "kuaijian_phone")
    private String kuaijianPhone;


    /**
     * 物品图片
     */

    @TableField(value = "kuaijian_photo")
    private String kuaijianPhoto;


    /**
     * 送件住址
     */

    @TableField(value = "kuaijian_address")
    private String kuaijianAddress;


    /**
     * 物品类型
     */

    @TableField(value = "jijianxinxi_types")
    private Integer jijianxinxiTypes;


    /**
     * 快递状态
     */

    @TableField(value = "kuaijian_types")
    private Integer kuaijianTypes;


    /**
     * 用户
     */

    @TableField(value = "yonghu_id")
    private Integer yonghuId;


    /**
     * 派件员
     */

    @TableField(value = "kuaidiyuan_id")
    private Integer kuaidiyuanId;


    /**
     * 发布日期
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


    /**
     * 创建时间 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：编号
	 */
    public String getKuaijianUuidNumber() {
        return kuaijianUuidNumber;
    }


    /**
	 * 获取：编号
	 */

    public void setKuaijianUuidNumber(String kuaijianUuidNumber) {
        this.kuaijianUuidNumber = kuaijianUuidNumber;
    }
    /**
	 * 设置：发件人名称
	 */
    public String getKuaijianName() {
        return kuaijianName;
    }


    /**
	 * 获取：发件人名称
	 */

    public void setKuaijianName(String kuaijianName) {
        this.kuaijianName = kuaijianName;
    }
    /**
	 * 设置：联系方式
	 */
    public String getKuaijianPhone() {
        return kuaijianPhone;
    }


    /**
	 * 获取：联系方式
	 */

    public void setKuaijianPhone(String kuaijianPhone) {
        this.kuaijianPhone = kuaijianPhone;
    }
    /**
	 * 设置：物品图片
	 */
    public String getKuaijianPhoto() {
        return kuaijianPhoto;
    }


    /**
	 * 获取：物品图片
	 */

    public void setKuaijianPhoto(String kuaijianPhoto) {
        this.kuaijianPhoto = kuaijianPhoto;
    }
    /**
	 * 设置：送件住址
	 */
    public String getKuaijianAddress() {
        return kuaijianAddress;
    }


    /**
	 * 获取：送件住址
	 */

    public void setKuaijianAddress(String kuaijianAddress) {
        this.kuaijianAddress = kuaijianAddress;
    }
    /**
	 * 设置：物品类型
	 */
    public Integer getJijianxinxiTypes() {
        return jijianxinxiTypes;
    }


    /**
	 * 获取：物品类型
	 */

    public void setJijianxinxiTypes(Integer jijianxinxiTypes) {
        this.jijianxinxiTypes = jijianxinxiTypes;
    }
    /**
	 * 设置：快递状态
	 */
    public Integer getKuaijianTypes() {
        return kuaijianTypes;
    }


    /**
	 * 获取：快递状态
	 */

    public void setKuaijianTypes(Integer kuaijianTypes) {
        this.kuaijianTypes = kuaijianTypes;
    }
    /**
	 * 设置：用户
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }


    /**
	 * 获取：用户
	 */

    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 设置：派件员
	 */
    public Integer getKuaidiyuanId() {
        return kuaidiyuanId;
    }


    /**
	 * 获取：派件员
	 */

    public void setKuaidiyuanId(Integer kuaidiyuanId) {
        this.kuaidiyuanId = kuaidiyuanId;
    }
    /**
	 * 设置：发布日期
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：发布日期
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：创建时间 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间 show2 photoShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
