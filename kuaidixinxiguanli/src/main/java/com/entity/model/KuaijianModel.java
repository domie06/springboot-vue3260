package com.entity.model;

import com.entity.KuaijianEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 快递信息
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class KuaijianModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 编号
     */
    private String kuaijianUuidNumber;


    /**
     * 发件人名称
     */
    private String kuaijianName;


    /**
     * 联系方式
     */
    private String kuaijianPhone;


    /**
     * 物品图片
     */
    private String kuaijianPhoto;


    /**
     * 送件住址
     */
    private String kuaijianAddress;


    /**
     * 物品类型
     */
    private Integer jijianxinxiTypes;


    /**
     * 快递状态
     */
    private Integer kuaijianTypes;


    /**
     * 用户
     */
    private Integer yonghuId;


    /**
     * 派件员
     */
    private Integer kuaidiyuanId;


    /**
     * 发布日期
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 创建时间 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：编号
	 */
    public String getKuaijianUuidNumber() {
        return kuaijianUuidNumber;
    }


    /**
	 * 设置：编号
	 */
    public void setKuaijianUuidNumber(String kuaijianUuidNumber) {
        this.kuaijianUuidNumber = kuaijianUuidNumber;
    }
    /**
	 * 获取：发件人名称
	 */
    public String getKuaijianName() {
        return kuaijianName;
    }


    /**
	 * 设置：发件人名称
	 */
    public void setKuaijianName(String kuaijianName) {
        this.kuaijianName = kuaijianName;
    }
    /**
	 * 获取：联系方式
	 */
    public String getKuaijianPhone() {
        return kuaijianPhone;
    }


    /**
	 * 设置：联系方式
	 */
    public void setKuaijianPhone(String kuaijianPhone) {
        this.kuaijianPhone = kuaijianPhone;
    }
    /**
	 * 获取：物品图片
	 */
    public String getKuaijianPhoto() {
        return kuaijianPhoto;
    }


    /**
	 * 设置：物品图片
	 */
    public void setKuaijianPhoto(String kuaijianPhoto) {
        this.kuaijianPhoto = kuaijianPhoto;
    }
    /**
	 * 获取：送件住址
	 */
    public String getKuaijianAddress() {
        return kuaijianAddress;
    }


    /**
	 * 设置：送件住址
	 */
    public void setKuaijianAddress(String kuaijianAddress) {
        this.kuaijianAddress = kuaijianAddress;
    }
    /**
	 * 获取：物品类型
	 */
    public Integer getJijianxinxiTypes() {
        return jijianxinxiTypes;
    }


    /**
	 * 设置：物品类型
	 */
    public void setJijianxinxiTypes(Integer jijianxinxiTypes) {
        this.jijianxinxiTypes = jijianxinxiTypes;
    }
    /**
	 * 获取：快递状态
	 */
    public Integer getKuaijianTypes() {
        return kuaijianTypes;
    }


    /**
	 * 设置：快递状态
	 */
    public void setKuaijianTypes(Integer kuaijianTypes) {
        this.kuaijianTypes = kuaijianTypes;
    }
    /**
	 * 获取：用户
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }


    /**
	 * 设置：用户
	 */
    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 获取：派件员
	 */
    public Integer getKuaidiyuanId() {
        return kuaidiyuanId;
    }


    /**
	 * 设置：派件员
	 */
    public void setKuaidiyuanId(Integer kuaidiyuanId) {
        this.kuaidiyuanId = kuaidiyuanId;
    }
    /**
	 * 获取：发布日期
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 设置：发布日期
	 */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：创建时间 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间 show2 photoShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
