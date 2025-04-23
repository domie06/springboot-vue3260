
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
 * 快递员
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/kuaidiyuan")
public class KuaidiyuanController {
    private static final Logger logger = LoggerFactory.getLogger(KuaidiyuanController.class);

    @Autowired
    private KuaidiyuanService kuaidiyuanService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service

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
        PageUtils page = kuaidiyuanService.queryPage(params);

        //字典表数据转换
        List<KuaidiyuanView> list =(List<KuaidiyuanView>)page.getList();
        for(KuaidiyuanView c:list){
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
        KuaidiyuanEntity kuaidiyuan = kuaidiyuanService.selectById(id);
        if(kuaidiyuan !=null){
            //entity转view
            KuaidiyuanView view = new KuaidiyuanView();
            BeanUtils.copyProperties( kuaidiyuan , view );//把实体数据重构到view中

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
    public R save(@RequestBody KuaidiyuanEntity kuaidiyuan, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,kuaidiyuan:{}",this.getClass().getName(),kuaidiyuan.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<KuaidiyuanEntity> queryWrapper = new EntityWrapper<KuaidiyuanEntity>()
            .eq("username", kuaidiyuan.getUsername())
            .or()
            .eq("kuaidiyuan_phone", kuaidiyuan.getKuaidiyuanPhone())
            .or()
            .eq("kuaidiyuan_id_number", kuaidiyuan.getKuaidiyuanIdNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        KuaidiyuanEntity kuaidiyuanEntity = kuaidiyuanService.selectOne(queryWrapper);
        if(kuaidiyuanEntity==null){
            kuaidiyuan.setCreateTime(new Date());
            kuaidiyuan.setPassword("123456");
            kuaidiyuanService.insert(kuaidiyuan);
            return R.ok();
        }else {
            return R.error(511,"账户或者快递员手机号或者快递员身份证号已经被使用");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody KuaidiyuanEntity kuaidiyuan, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,kuaidiyuan:{}",this.getClass().getName(),kuaidiyuan.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        //根据字段查询是否有相同数据
        Wrapper<KuaidiyuanEntity> queryWrapper = new EntityWrapper<KuaidiyuanEntity>()
            .notIn("id",kuaidiyuan.getId())
            .andNew()
            .eq("username", kuaidiyuan.getUsername())
            .or()
            .eq("kuaidiyuan_phone", kuaidiyuan.getKuaidiyuanPhone())
            .or()
            .eq("kuaidiyuan_id_number", kuaidiyuan.getKuaidiyuanIdNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        KuaidiyuanEntity kuaidiyuanEntity = kuaidiyuanService.selectOne(queryWrapper);
        if("".equals(kuaidiyuan.getKuaidiyuanPhoto()) || "null".equals(kuaidiyuan.getKuaidiyuanPhoto())){
                kuaidiyuan.setKuaidiyuanPhoto(null);
        }
        if(kuaidiyuanEntity==null){
            kuaidiyuanService.updateById(kuaidiyuan);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"账户或者快递员手机号或者快递员身份证号已经被使用");
        }
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        kuaidiyuanService.deleteBatchIds(Arrays.asList(ids));
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
            List<KuaidiyuanEntity> kuaidiyuanList = new ArrayList<>();//上传的东西
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
                            KuaidiyuanEntity kuaidiyuanEntity = new KuaidiyuanEntity();
//                            kuaidiyuanEntity.setUsername(data.get(0));                    //账户 要改的
//                            //kuaidiyuanEntity.setPassword("123456");//密码
//                            kuaidiyuanEntity.setKuaidiyuanName(data.get(0));                    //快递员姓名 要改的
//                            kuaidiyuanEntity.setKuaidiyuanPhone(data.get(0));                    //快递员手机号 要改的
//                            kuaidiyuanEntity.setKuaidiyuanIdNumber(data.get(0));                    //快递员身份证号 要改的
//                            kuaidiyuanEntity.setKuaidiyuanPhoto("");//详情和图片
//                            kuaidiyuanEntity.setSexTypes(Integer.valueOf(data.get(0)));   //性别 要改的
//                            kuaidiyuanEntity.setKuaidiyuanEmail(data.get(0));                    //电子邮箱 要改的
//                            kuaidiyuanEntity.setKuaidiyuanNumber(Integer.valueOf(data.get(0)));   //派件数量 要改的
//                            kuaidiyuanEntity.setCreateTime(date);//时间
                            kuaidiyuanList.add(kuaidiyuanEntity);


                            //把要查询是否重复的字段放入map中
                                //账户
                                if(seachFields.containsKey("username")){
                                    List<String> username = seachFields.get("username");
                                    username.add(data.get(0));//要改的
                                }else{
                                    List<String> username = new ArrayList<>();
                                    username.add(data.get(0));//要改的
                                    seachFields.put("username",username);
                                }
                                //快递员手机号
                                if(seachFields.containsKey("kuaidiyuanPhone")){
                                    List<String> kuaidiyuanPhone = seachFields.get("kuaidiyuanPhone");
                                    kuaidiyuanPhone.add(data.get(0));//要改的
                                }else{
                                    List<String> kuaidiyuanPhone = new ArrayList<>();
                                    kuaidiyuanPhone.add(data.get(0));//要改的
                                    seachFields.put("kuaidiyuanPhone",kuaidiyuanPhone);
                                }
                                //快递员身份证号
                                if(seachFields.containsKey("kuaidiyuanIdNumber")){
                                    List<String> kuaidiyuanIdNumber = seachFields.get("kuaidiyuanIdNumber");
                                    kuaidiyuanIdNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> kuaidiyuanIdNumber = new ArrayList<>();
                                    kuaidiyuanIdNumber.add(data.get(0));//要改的
                                    seachFields.put("kuaidiyuanIdNumber",kuaidiyuanIdNumber);
                                }
                        }

                        //查询是否重复
                         //账户
                        List<KuaidiyuanEntity> kuaidiyuanEntities_username = kuaidiyuanService.selectList(new EntityWrapper<KuaidiyuanEntity>().in("username", seachFields.get("username")));
                        if(kuaidiyuanEntities_username.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(KuaidiyuanEntity s:kuaidiyuanEntities_username){
                                repeatFields.add(s.getUsername());
                            }
                            return R.error(511,"数据库的该表中的 [账户] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //快递员手机号
                        List<KuaidiyuanEntity> kuaidiyuanEntities_kuaidiyuanPhone = kuaidiyuanService.selectList(new EntityWrapper<KuaidiyuanEntity>().in("kuaidiyuan_phone", seachFields.get("kuaidiyuanPhone")));
                        if(kuaidiyuanEntities_kuaidiyuanPhone.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(KuaidiyuanEntity s:kuaidiyuanEntities_kuaidiyuanPhone){
                                repeatFields.add(s.getKuaidiyuanPhone());
                            }
                            return R.error(511,"数据库的该表中的 [快递员手机号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //快递员身份证号
                        List<KuaidiyuanEntity> kuaidiyuanEntities_kuaidiyuanIdNumber = kuaidiyuanService.selectList(new EntityWrapper<KuaidiyuanEntity>().in("kuaidiyuan_id_number", seachFields.get("kuaidiyuanIdNumber")));
                        if(kuaidiyuanEntities_kuaidiyuanIdNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(KuaidiyuanEntity s:kuaidiyuanEntities_kuaidiyuanIdNumber){
                                repeatFields.add(s.getKuaidiyuanIdNumber());
                            }
                            return R.error(511,"数据库的该表中的 [快递员身份证号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        kuaidiyuanService.insertBatch(kuaidiyuanList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }


    /**
    * 登录
    */
    @IgnoreAuth
    @RequestMapping(value = "/login")
    public R login(String username, String password, String captcha, HttpServletRequest request) {
        KuaidiyuanEntity kuaidiyuan = kuaidiyuanService.selectOne(new EntityWrapper<KuaidiyuanEntity>().eq("username", username));
        if(kuaidiyuan==null || !kuaidiyuan.getPassword().equals(password))
            return R.error("账号或密码不正确");
        //  // 获取监听器中的字典表
        // ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        // Map<String, Map<Integer, String>> dictionaryMap= (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
        // Map<Integer, String> role_types = dictionaryMap.get("role_types");
        // role_types.get(.getRoleTypes());
        String token = tokenService.generateToken(kuaidiyuan.getId(),username, "kuaidiyuan", "快递员");
        R r = R.ok();
        r.put("token", token);
        r.put("role","快递员");
        r.put("username",kuaidiyuan.getKuaidiyuanName());
        r.put("tableName","kuaidiyuan");
        r.put("userId",kuaidiyuan.getId());
        return r;
    }

    /**
    * 注册
    */
    @IgnoreAuth
    @PostMapping(value = "/register")
    public R register(@RequestBody KuaidiyuanEntity kuaidiyuan){
//    	ValidatorUtils.validateEntity(user);
        Wrapper<KuaidiyuanEntity> queryWrapper = new EntityWrapper<KuaidiyuanEntity>()
            .eq("username", kuaidiyuan.getUsername())
            .or()
            .eq("kuaidiyuan_phone", kuaidiyuan.getKuaidiyuanPhone())
            .or()
            .eq("kuaidiyuan_id_number", kuaidiyuan.getKuaidiyuanIdNumber())
            ;
        KuaidiyuanEntity kuaidiyuanEntity = kuaidiyuanService.selectOne(queryWrapper);
        if(kuaidiyuanEntity != null)
            return R.error("账户或者快递员手机号或者快递员身份证号已经被使用");
        kuaidiyuan.setKuaidiyuanNumber(0);
        kuaidiyuan.setCreateTime(new Date());
        kuaidiyuanService.insert(kuaidiyuan);
        return R.ok();
    }

    /**
     * 重置密码
     */
    @GetMapping(value = "/resetPassword")
    public R resetPassword(Integer  id){
        KuaidiyuanEntity kuaidiyuan = new KuaidiyuanEntity();
        kuaidiyuan.setPassword("123456");
        kuaidiyuan.setId(id);
        kuaidiyuanService.updateById(kuaidiyuan);
        return R.ok();
    }


    /**
     * 忘记密码
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request) {
        KuaidiyuanEntity kuaidiyuan = kuaidiyuanService.selectOne(new EntityWrapper<KuaidiyuanEntity>().eq("username", username));
        if(kuaidiyuan!=null){
            kuaidiyuan.setPassword("123456");
            boolean b = kuaidiyuanService.updateById(kuaidiyuan);
            if(!b){
               return R.error();
            }
        }else{
           return R.error("账号不存在");
        }
        return R.ok();
    }


    /**
    * 获取用户的session用户信息
    */
    @RequestMapping("/session")
    public R getCurrKuaidiyuan(HttpServletRequest request){
        Integer id = (Integer)request.getSession().getAttribute("userId");
        KuaidiyuanEntity kuaidiyuan = kuaidiyuanService.selectById(id);
        if(kuaidiyuan !=null){
            //entity转view
            KuaidiyuanView view = new KuaidiyuanView();
            BeanUtils.copyProperties( kuaidiyuan , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }
    }


    /**
    * 退出
    */
    @GetMapping(value = "logout")
    public R logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return R.ok("退出成功");
    }





}
