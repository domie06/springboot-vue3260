
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 快递信息
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/kuaijian")
public class KuaijianController {
    private static final Logger logger = LoggerFactory.getLogger(KuaijianController.class);

    @Autowired
    private KuaijianService kuaijianService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private KuaidiyuanService kuaidiyuanService;
    @Autowired
    private YonghuService yonghuService;



    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("快递员".equals(role))
            params.put("kuaidiyuanId",request.getSession().getAttribute("userId"));
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = kuaijianService.queryPage(params);

        //字典表数据转换
        List<KuaijianView> list =(List<KuaijianView>)page.getList();
        for(KuaijianView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        KuaijianEntity kuaijian = kuaijianService.selectById(id);
        if(kuaijian !=null){
            //entity转view
            KuaijianView view = new KuaijianView();
            BeanUtils.copyProperties( kuaijian , view );//把实体数据重构到view中

                //级联表
                KuaidiyuanEntity kuaidiyuan = kuaidiyuanService.selectById(kuaijian.getKuaidiyuanId());
                if(kuaidiyuan != null){
                    BeanUtils.copyProperties( kuaidiyuan , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setKuaidiyuanId(kuaidiyuan.getId());
                }
                //级联表
                YonghuEntity yonghu = yonghuService.selectById(kuaijian.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody KuaijianEntity kuaijian, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,kuaijian:{}",this.getClass().getName(),kuaijian.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("用户".equals(role))
            kuaijian.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        else if("快递员".equals(role))
            kuaijian.setKuaidiyuanId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<KuaijianEntity> queryWrapper = new EntityWrapper<KuaijianEntity>()
            .eq("kuaijian_uuid_number", kuaijian.getKuaijianUuidNumber())
            .eq("kuaijian_name", kuaijian.getKuaijianName())
            .eq("kuaijian_phone", kuaijian.getKuaijianPhone())
            .eq("kuaijian_address", kuaijian.getKuaijianAddress())
            .eq("jijianxinxi_types", kuaijian.getJijianxinxiTypes())
            .eq("kuaijian_types", kuaijian.getKuaijianTypes())
            .eq("yonghu_id", kuaijian.getYonghuId())
            .eq("kuaidiyuan_id", kuaijian.getKuaidiyuanId())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        KuaijianEntity kuaijianEntity = kuaijianService.selectOne(queryWrapper);
        if(kuaijianEntity==null){
            kuaijian.setInsertTime(new Date());
            kuaijian.setCreateTime(new Date());
            kuaijianService.insert(kuaijian);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody KuaijianEntity kuaijian, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,kuaijian:{}",this.getClass().getName(),kuaijian.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            kuaijian.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
//        else if("快递员".equals(role))
//            kuaijian.setKuaidiyuanId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<KuaijianEntity> queryWrapper = new EntityWrapper<KuaijianEntity>()
            .notIn("id",kuaijian.getId())
            .andNew()
            .eq("kuaijian_uuid_number", kuaijian.getKuaijianUuidNumber())
            .eq("kuaijian_name", kuaijian.getKuaijianName())
            .eq("kuaijian_phone", kuaijian.getKuaijianPhone())
            .eq("kuaijian_address", kuaijian.getKuaijianAddress())
            .eq("jijianxinxi_types", kuaijian.getJijianxinxiTypes())
            .eq("kuaijian_types", kuaijian.getKuaijianTypes())
            .eq("yonghu_id", kuaijian.getYonghuId())
            .eq("kuaidiyuan_id", kuaijian.getKuaidiyuanId())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        KuaijianEntity kuaijianEntity = kuaijianService.selectOne(queryWrapper);
        if("".equals(kuaijian.getKuaijianPhoto()) || "null".equals(kuaijian.getKuaijianPhoto())){
                kuaijian.setKuaijianPhoto(null);
        }
        if(kuaijianEntity==null){
            if(kuaijian.getKuaidiyuanId() != null && kuaijian.getKuaijianTypes() == 3){
                KuaidiyuanEntity kuaidiyuanEntity = kuaidiyuanService.selectById(kuaijian.getKuaidiyuanId());
                kuaidiyuanEntity.setKuaidiyuanNumber(kuaidiyuanEntity.getKuaidiyuanNumber()+1);
                kuaidiyuanService.updateById(kuaidiyuanEntity);
            }
            kuaijianService.updateById(kuaijian);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        kuaijianService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<KuaijianEntity> kuaijianList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("../../upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            KuaijianEntity kuaijianEntity = new KuaijianEntity();
//                            kuaijianEntity.setKuaijianUuidNumber(data.get(0));                    //编号 要改的
//                            kuaijianEntity.setKuaijianName(data.get(0));                    //发件人名称 要改的
//                            kuaijianEntity.setKuaijianPhone(data.get(0));                    //联系方式 要改的
//                            kuaijianEntity.setKuaijianPhoto("");//详情和图片
//                            kuaijianEntity.setKuaijianAddress(data.get(0));                    //送件住址 要改的
//                            kuaijianEntity.setJijianxinxiTypes(Integer.valueOf(data.get(0)));   //物品类型 要改的
//                            kuaijianEntity.setKuaijianTypes(Integer.valueOf(data.get(0)));   //快递状态 要改的
//                            kuaijianEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            kuaijianEntity.setKuaidiyuanId(Integer.valueOf(data.get(0)));   //派件员 要改的
//                            kuaijianEntity.setInsertTime(date);//时间
//                            kuaijianEntity.setCreateTime(date);//时间
                            kuaijianList.add(kuaijianEntity);


                            //把要查询是否重复的字段放入map中
                                //编号
                                if(seachFields.containsKey("kuaijianUuidNumber")){
                                    List<String> kuaijianUuidNumber = seachFields.get("kuaijianUuidNumber");
                                    kuaijianUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> kuaijianUuidNumber = new ArrayList<>();
                                    kuaijianUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("kuaijianUuidNumber",kuaijianUuidNumber);
                                }
                                //联系方式
                                if(seachFields.containsKey("kuaijianPhone")){
                                    List<String> kuaijianPhone = seachFields.get("kuaijianPhone");
                                    kuaijianPhone.add(data.get(0));//要改的
                                }else{
                                    List<String> kuaijianPhone = new ArrayList<>();
                                    kuaijianPhone.add(data.get(0));//要改的
                                    seachFields.put("kuaijianPhone",kuaijianPhone);
                                }
                        }

                        //查询是否重复
                         //编号
                        List<KuaijianEntity> kuaijianEntities_kuaijianUuidNumber = kuaijianService.selectList(new EntityWrapper<KuaijianEntity>().in("kuaijian_uuid_number", seachFields.get("kuaijianUuidNumber")));
                        if(kuaijianEntities_kuaijianUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(KuaijianEntity s:kuaijianEntities_kuaijianUuidNumber){
                                repeatFields.add(s.getKuaijianUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //联系方式
                        List<KuaijianEntity> kuaijianEntities_kuaijianPhone = kuaijianService.selectList(new EntityWrapper<KuaijianEntity>().in("kuaijian_phone", seachFields.get("kuaijianPhone")));
                        if(kuaijianEntities_kuaijianPhone.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(KuaijianEntity s:kuaijianEntities_kuaijianPhone){
                                repeatFields.add(s.getKuaijianPhone());
                            }
                            return R.error(511,"数据库的该表中的 [联系方式] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        kuaijianService.insertBatch(kuaijianList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }






}
