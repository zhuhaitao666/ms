package com.mujiwulian.ms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mujiwulian.ms.entity.*;
import com.mujiwulian.ms.rabbitMQ.Sender;
import com.mujiwulian.ms.service.GoodsService;
import com.mujiwulian.ms.service.SecOrderService;
import com.mujiwulian.ms.service.SeckillService;
import com.mujiwulian.ms.service.UserService;
import com.mujiwulian.ms.utils.MD5Util;
import com.mujiwulian.ms.utils.RedisUtil;
import com.mujiwulian.ms.vo.GoodsVO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class Test implements InitializingBean {
    @Autowired
    private UserService userService;
    @Autowired
    private SecOrderService secOrderService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private Sender sender;

    /**实现InitializingBean接口，启动ioc容器后，该类初始化后自动调用该方法*/
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> seckillGoods=  goodsService.selectGoodsList();
        if(seckillGoods!=null){
           seckillGoods.forEach(e->{
               redisUtil.set("secGoodsStock:"+e.getId(),e.getStockCount());
           });
        }else{
            System.out.println("查询商品库存失败");
        }
    }

    @RequestMapping("/seckill/{id}")
    public String doSecKill( @PathVariable("id") Integer id,HttpSession session,Model model){
        //1.判断用户是否登录
        Integer userId= (Integer) session.getAttribute("userId");
        if(userId==null){
            return "login";
        }else{
//            //2.每个用户只能秒杀一件商品，判断该用户是否秒杀到该商品
//            SecOrder secOrder=secOrderService.selectSecOrderByUserIdAndGoodsId(userId,id);
//            if (secOrder!=null){
//                model.addAttribute("msg","您已经秒杀成功过该商品，请勿重复秒杀!");
//                return "seckillFail";
//            }
//            else{
//                //3.预减库存，下订单，写入秒杀订单
//                Order order =seckillService.seckill(userId,id);
//                if(order!=null){
//                    model.addAttribute("order",order);
//                    return "goodsList";
//                }else{
//                    model.addAttribute("msg","秒杀失败,库存不足!");
//                    return "seckillFail";
//                }
//            }
            // 1.通过redis预减库存
            long stock=redisUtil.decr("secGoodsStock:"+id,1);

            if(stock<0){
                model.addAttribute("msg","秒杀失败,库存不足!");
            }else{
                SecOrder secOrder=secOrderService.selectSecOrderByUserIdAndGoodsId(userId,id);
                if (secOrder!=null){
                    model.addAttribute("msg",
                            "您已经秒杀成功过该商品，请勿重复秒杀!");
                }
                //异步通知
                sender.sendOrder(userId,id);
                model.addAttribute("msg","排队中。。。");
            }
            return "seckillFail";
        }
    }
    @RequestMapping("/login")
    public String toLoginPage(){
        return "login";
    }

    /**进行页面缓存*/
    @RequestMapping(value = "/doLogin",produces = "text/html")
    @ResponseBody
    public String doLogin(@RequestParam String phone, HttpSession session,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam String password, Model model){
        //1.查询数据库是否有此人
        QueryWrapper wrapper=  new QueryWrapper<User>();
        wrapper.eq("phone",phone);
        User temp=new User();
        temp.setPhone(phone);
        User user= userService.getOne(wrapper);
        if(user!=null){
            String passForm=MD5Util.inputPassForm(password);
            if(passForm.equals(user.getPassword())){
                session.setAttribute("phone",phone);
                session.setAttribute("userId",user.getId());
                String html= (String) redisUtil.get("secGoodsList");
                if(html!=null){
                    return html;
                }else{
                    //手动渲染页面
                    List goods=goodsService.selectGoodsList();
                    model.addAttribute("goods",goods);
                    WebContext webContext=new WebContext(request,response,request.getServletContext(),
                            request.getLocale(),model.asMap());
                    html=thymeleafViewResolver.getTemplateEngine().process("main",webContext);
                    if(html!=null){
                        redisUtil.set("secGoodsList",html,60);
                    }
                    return html;
                }
            }else{
                return "login";
            }
        }else{
            return "login";
        }
    }
    /**进行页面url缓存*/
    @RequestMapping(value = "/detail/{id}",produces = "text/html")
    @ResponseBody
    public String getSecGoodsDeatil(@PathVariable("id") Integer id,
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    Model model){
        String html = (String) redisUtil.get("goodsDetail:"+id);
        if(html!=null){
            return html;
        }

        GoodsVO goods=goodsService.getSecGoodsById(id);
        model.addAttribute("good",goods);
        //当前时间
        long nowDate=System.currentTimeMillis();
        int seconds;
        //秒杀未开始
        if(nowDate < goods.getStartTime().getTime()){
            seconds= (int) (nowDate-goods.getStartTime().getTime())/1000;
        //秒杀已经结束
        }else if(nowDate > goods.getEndTime().getTime()){
            seconds=0;
        }else{
            seconds= (int) (goods.getEndTime().getTime()-nowDate)/1000;
        }
        model.addAttribute("seconds",seconds);

        WebContext webContext=new WebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap());
        html=thymeleafViewResolver.getTemplateEngine().process("detail",webContext);
        if(html!=null){
            redisUtil.set("goodsDetail:"+id,html,60);
            return html;
        }else{
            return "404";
        }
    }

    @RequestMapping("/test")
    public String test(@RequestParam("password") String password){
        List<User> users=userService.list();
        //排序
        users.sort((x,y)-> y.getAge() - x.getAge());
        //输出
        users.forEach(System.out::println);
        return "test";
    }

}
