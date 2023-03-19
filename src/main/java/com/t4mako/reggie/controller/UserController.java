package com.t4mako.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.t4mako.reggie.common.BaseContext;
import com.t4mako.reggie.common.CustomException;
import com.t4mako.reggie.common.R;
import com.t4mako.reggie.entity.User;
import com.t4mako.reggie.service.UserService;
import com.t4mako.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author T4mako
 * @date 2023/3/11 16:57
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    //发送手机短信验证码
    @PostMapping("/sendMsg")
    public R<String> sendMeg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if(StringUtils.isNotBlank(phone)){
            //如果获取过验证码，且验证码没过期，不允许重新获取
            if(redisTemplate.opsForValue().get(phone) != null){
                throw new CustomException("已获取验证码，请稍后获取");
            }
            //生成随机四位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码={}",code);
            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("瑞吉外卖","",phone,code);

            //需要生成的验证码保存到Session
            /*session.setAttribute(phone,code);*/

            //将生成的验证码缓存到Redis中，并且设置有效期为5分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            return R.success("手机验证码短信发送成功");

        }
        return R.error("短信发送失败");
    }

    //移动端用户登录
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){
        //获取手机号
        String phone = map.get("phone").toString();
        //获取哦验证码
        String code = map.get("code").toString();

        //从Session中获取保存的验证码
        /*Object codeInSession = session.getAttribute(phone);*/

        //从redis中获取缓存验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            //登录成功，将id放入session中
            session.setAttribute("user",user.getId());

            //登陆成功，删除redis中缓存的验证码
            redisTemplate.delete(phone);
            return R.success(user);
        }
        return R.error("登录失败");
    }

    @PostMapping("/loginout")
    public R<String> loginOut(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        BaseContext.getCurrentId();
        return R.success("退出成功");
    }

}
