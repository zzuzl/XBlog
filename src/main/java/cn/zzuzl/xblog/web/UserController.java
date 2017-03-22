package cn.zzuzl.xblog.web;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.model.*;
import cn.zzuzl.xblog.service.*;
import cn.zzuzl.xblog.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 用户相关controller
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RedisService redisService;
    @Resource
    private MailService mailService;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private DynamicService dynamicService;
    @Resource
    private MessageService messageService;
    @Resource
    private TokenService tokenService;
    private final Logger logger = LogManager.getLogger(getClass());

    /* 用户登录 */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(String email, String password, HttpServletResponse response, Model model) {
        Result result = new Result();
        User user = userService.login(email, password);
        if (user != null) {
            result.setSuccess(true);
            if (userService.resetCounts(user.getUserId()) > 0) {
                logger.debug("------------重置用户粉丝数量和关注数量-------------");
            } else {
                logger.debug("------------filed   filed  filed  -------------");
            }
            logger.debug("---------------重新设置未读消息的数量---------------");
            // resetUnreadMsgCount(session, user.getUserId());
            String token = tokenService.generateToken(user.getUserId());
            response.addCookie(new Cookie(Common.TOKEN, token));
            model.addAttribute(Common.USER,user);
        } else {
            result.setSuccess(false);
            result.setMsg("用户名或密码错误");
        }
        return result;
    }

    private void resetUnreadMsgCount(HttpSession session, int id) {
        int count = messageService.getUnreadMsgCount(id);
        session.setAttribute(Common.UNREAD_MSG_COUNT, count);
    }

    /* 查询Email是否已经注册,返回200 表示可以注册，返回411表示不可以注册 */
    @RequestMapping(value = "/exists", method = RequestMethod.GET)
    public void exists(@RequestParam(value = "email", required = true) String email,
                       HttpServletResponse response) {
        if (userService.searchUserByEmail(email) == null) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
        }
    }

    /* 根据email查询用户 */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public User searchUserByEmail(@RequestParam(value = "email", required = true) String email) {
        return userService.searchUserByEmail(email);
    }

    /* 注册 */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(@Valid @ModelAttribute("user") User user,
                           @RequestParam("password") String password,
                           @RequestParam("captcha") String captcha,
                           BindingResult bindingResult, HttpServletRequest request) {
        Result result = user.valid(password);
        if (result.isSuccess()) {
            if (!captcha.equals(captchaService.getGeneratedKey(request))) {
                result.setSuccess(false);
                result.setMsg("验证码错误");
            }
            if (userService.searchUserByEmail(user.getEmail()) != null) {
                result.setSuccess(false);
                result.setMsg("该邮箱已被注册");
            } else {
                String hash = Utils.MD5(user.getEmail());
                mailService.sendRegisterEmail(hash, user);
                redisService.addUser(hash, user, password);

                messageService.sendRegSuccess(user);
            }
        }
        return result;
    }

    /* 修改密码 */
    @RequestMapping(value = "/changePwd", method = RequestMethod.PUT)
    @ResponseBody
    public Result changePwd(@Valid @RequestParam("newPassword") String newPassword,
                            @RequestParam("originalPassword") String originalPassword,
                            HttpSession session) {
        Result result = new Result();
        result.setSuccess(true);

        User user = (User) session.getAttribute(Common.USER);
        if (user == null) {
            result.setSuccess(false);
            result.setMsg("用户未登录");
        } else {
            user = userService.login(user.getEmail(), originalPassword);
            if (user == null) {
                result.setSuccess(false);
                result.setMsg("原密码输入错误");
            } else {
                userService.changePwd(user.getUserId(), newPassword);
            }
        }

        return result;
    }

    @RequestMapping(value = "/modifyPhotoSrc", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyPhotoSrc(@RequestParam("src") String src, HttpSession session) {
        Result result = new Result(true);
        User user = (User) session.getAttribute(Common.USER);
        if (user == null) {
            result.setSuccess(false);
            result.setMsg("未登陆");
        } else {
            result = userService.changePhoto(src, user.getUserId());
            if (result.isSuccess()) {
                user.setPhotoSrc(src);
                session.setAttribute(Common.USER, user);
            }
        }

        return result;
    }

    /* 重置密码 */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.PUT)
    @ResponseBody
    public Result resetPwd(@RequestParam("password") String password,
                           @RequestParam("captcha") String captcha,
                           @RequestParam("hash") String hash, HttpServletRequest request) {
        Result result = new Result();
        if (!captcha.equalsIgnoreCase(captchaService.getGeneratedKey(request))) {
            result.setMsg("验证码错误");
        } else if (password.length() < 6 || password.length() > 32) {
            result.setMsg("密码长度为6-32位");
        } else {
            Map<String, Object> map = redisService.getLink(hash);

            if (map == null || !hash.equals(map.get("hash"))) {
                result.setMsg("操作无效，请重新发送找回密码邮件");
            } else if (Utils.getGapMinute((Long) map.get("time")) > 30) {
                result.setMsg("邮件验证码已过期,请重新发送邮件!");
            } else {
                User user = userService.searchUserByEmail((String) map.get("email"));
                if (user == null) {
                    result.setMsg("未知错误，请重试!");
                } else {
                    userService.changePwd(user.getUserId(), password);
                    result.setSuccess(true);
                    redisService.deleteLink(hash);

                    // 发送修改密码成功的站内信
                    messageService.sendChangePwdMsg(user);
                }
            }
        }

        return result;
    }

    /* 根据id查询用户信息 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User userDetail(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    /* 根据id查询用户所有粉丝 */
    @RequestMapping(value = "/fans/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Attention> fans(@PathVariable("id") Integer id) {
        return userService.getAllFans(id);
    }

    /* 根据id查询用户所有关注 */
    @RequestMapping(value = "/attention/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Attention> attentions(@PathVariable("id") Integer id) {
        return userService.getAllAttentions(id);
    }

    /* 添加关注 */
    @RequestMapping(value = "/attention", method = RequestMethod.POST)
    @ResponseBody
    public Result addAttention(@RequestParam("from") Integer from,
                               @RequestParam("to") Integer to, HttpSession session) {
        User user = (User) session.getAttribute(Common.USER);
        if (user.getUserId() == from) {
            return userService.insertAttention(from, to);
        } else {
            Result result = new Result();
            result.setMsg("用户身份错误");
            return result;
        }
    }

    /* 取消关注 */
    @RequestMapping(value = "/attention", method = RequestMethod.DELETE)
    @ResponseBody
    public Result cancelAttention(@RequestParam("from") Integer from,
                                  @RequestParam("to") Integer to, HttpSession session) {
        User user = (User) session.getAttribute(Common.USER);
        if (user.getUserId() == from) {
            return userService.deleteAttention(from, to);
        } else {
            Result result = new Result();
            result.setMsg("用户身份错误");
            return result;
        }
    }

    /* 退出 */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Result logout(HttpSession session) {
        session.removeAttribute(Common.USER);
        return null;
    }

    /* 更新用户信息 */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult, HttpSession session) {
        Result result = userService.updateUser(user);
        if (result.isSuccess()) {
            session.setAttribute(Common.USER, userService.getUserById(user.getUserId()));
        }
        return result;
    }

    /* 获取用户动态 */
    @RequestMapping(value = "/dynamics/page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Pager<Dynamic> getDynamics(@RequestParam("userId") Integer userId,
                                      @PathVariable("page") Integer page) {
        page = page == null ? 1 : page;
        return dynamicService.getDynamics(userId, page, Common.DEFAULT_ITEM_COUNT);
    }

    /* 删除用户动态 */
    @RequestMapping(value = "/dynamics/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteDynamic(@PathVariable("id") Integer id, HttpSession session) {
        Result result = new Result();

        Dynamic dynamic = dynamicService.getDynamicById(id);
        User user = (User) session.getAttribute(Common.USER);
        if (user != null && dynamic != null &&
                user.getUserId() == dynamic.getUser().getUserId()) {
            if (id == null) {
                result.setMsg("id不能为空");
            } else {
                result = dynamicService.deleteDynamic(id);
            }
        } else {
            result.setMsg("用户身份错误");
        }

        return result;
    }

    @RequestMapping("/messages")
    @ResponseBody
    public Result<Message> searchMessages(@RequestParam(value = "type", required = false, defaultValue = "-1") Integer type,
                                          @RequestParam(value = "state", required = false, defaultValue = "-1") Integer state,
                                          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                          HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Common.USER);

        Result<Message> result = new Result<Message>(false);
        if (user != null) {
            result = messageService.searchMessages(user.getUserId(), type, state, page, pageSize);
        }

        return result;
    }

    @RequestMapping("/updateMsgState")
    @ResponseBody
    public Result updateMsgState(@RequestParam(value = "id", required = false, defaultValue = "0") Integer id,
                                 @RequestParam(value = "state", required = false, defaultValue = "1") Integer state,
                                 HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Common.USER);

        Message message = messageService.getById(id);

        Result result = new Result<Message>(false);
        if (user != null && message != null && message.getTo().getUserId() == user.getUserId()) {
            result = messageService.updateMsgState(id, state);
        } else {
            result.setMsg("用户未授权或消息错误");
        }

        return result;
    }

}
