//package io.moonlight.utils.log;
//
///**
// * @author qt on 2018/1/13.
// */
//import java.lang.reflect.Method;
//import java.util.Date;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.yangjf.entity.Admin;
//import com.yangjf.entity.Log;
//import com.yangjf.service.LogService;
//
//@Aspect
//public class Log {
//
//    public Integer id = null;
//
//    @Autowired
//    LogService logService;
//
//    /**
//     * 管理员登录方法的切入点
//     */
//    @Pointcut("execution(* com.yangjf.service.*.login(..))")
//    public void loginCell() {
//    }
//
//    /**
//     * 添加业务逻辑方法切入点
//     */
//    @Pointcut("execution(* com.yangjf.service.*.save(..))")
//    public void insertCell() {
//    }
//
//    /**
//     * 修改业务逻辑方法切入点
//     */
//    @Pointcut("execution(* com.yangjf.service.*.update(..))")
//    public void updateCell() {
//    }
//
//    /**
//     * 删除业务逻辑方法切入点
//     */
//    @Pointcut("execution(* com.yangjf.service.*.delete(..))")
//    public void deleteCell() {
//    }
//
//    /**
//     * 登录操作(后置通知)
//     *
//     * @param joinPoint
//     * @param object
//     * @throws Throwable
//     */
//    @AfterReturning(value = "loginCell()", argNames = "object", returning = "object")
//    public void loginLog(JoinPoint joinPoint, Object object) throws Throwable {
//        Admin admin = (Admin) object;
//        if (admin == null) {
//            return;
//        }
//        if (joinPoint.getArgs() == null) {// 没有参数
//            return;
//        }
//        id = admin.getId();
//        // 获取方法名
//        String methodName = joinPoint.getSignature().getName();
//        // 获取操作内容
//        String opContent = optionContent(joinPoint.getArgs(), methodName);
//
//        Log log = new Log();
//        log.setContent(opContent);
//        log.setAdminId(admin.getId());
//        log.setCreateDate(new Date());
//        log.setOperation("登录");
//        logService.insertLog(log);
//    }
//
//    /**
//     * 添加操作日志(后置通知)
//     *
//     * @param joinPoint
//     * @param object
//     */
//    @AfterReturning(value = "insertCell()", argNames = "object", returning = "object")
//    public void insertLog(JoinPoint joinPoint, Object object) throws Throwable {
//        // Admin admin=(Admin)
//        // request.getSession().getAttribute("businessAdmin");
//        // 判断参数
//        if (joinPoint.getArgs() == null) {// 没有参数
//            return;
//        }
//        // 获取方法名
//        String methodName = joinPoint.getSignature().getName();
//        // 获取操作内容
//        String opContent = optionContent(joinPoint.getArgs(), methodName);
//
//        Log log = new Log();
//        log.setContent(opContent);
//        log.setAdminId(id);
//        ;
//        log.setOperation("添加");
//        log.setCreateDate(new Date());
//        logService.insertLog(log);
//    }
//
//    /**
//     * 管理员修改操作日志(后置通知)
//     *
//     * @param joinPoint
//     * @param object
//     * @throws Throwable
//     */
//    @AfterReturning(value = "updateCell()", argNames = "object", returning = "object")
//    public void updateLog(JoinPoint joinPoint, Object object) throws Throwable {
//        // Admin admin=(Admin)
//        // request.getSession().getAttribute("businessAdmin");
//
//        // 判断参数
//        if (joinPoint.getArgs() == null) {// 没有参数
//            return;
//        }
//        // 获取方法名
//        String methodName = joinPoint.getSignature().getName();
//        // 获取操作内容
//        String opContent = optionContent(joinPoint.getArgs(), methodName);
//
//        // 创建日志对象
//        Log log = new Log();
//        log.setContent(opContent);
//        log.setAdminId(id);
//        log.setOperation("修改");// 操作
//        log.setCreateDate(new Date());
//        logService.insertLog(log);
//    }
//
//    /**
//     * 删除操作
//     *
//     * @param joinPoint
//     * @param object
//     */
//    @AfterReturning(value = "deleteCell()", argNames = "object", returning = "object")
//    public void deleteLog(JoinPoint joinPoint, Object object) throws Throwable {
//        // Admin admin=(Admin)
//        // request.getSession().getAttribute("businessAdmin");
//        // 判断参数
//        if (joinPoint.getArgs() == null) {// 没有参数
//            return;
//        }
//        // 获取方法名
//        String methodName = joinPoint.getSignature().getName();
//
//        StringBuffer rs = new StringBuffer();
//        rs.append(methodName);
//        String className = null;
//        for (Object info : joinPoint.getArgs()) {
//            // 获取对象类型
//            className = info.getClass().getName();
//            className = className.substring(className.lastIndexOf(".") + 1);
//            rs.append("[参数，类型:" + className + "，值:(id:"
//                    + joinPoint.getArgs()[0] + ")");
//        }
//
//        // 创建日志对象
//        Log log = new Log();
//        log.setContent(rs.toString());
//        log.setAdminId(id);
//        log.setOperation("删除");// 操作
//        log.setCreateDate(new Date());
//        logService.insertLog(log);
//    }
//
//    /**
//     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
//     *
//     * @param args
//     * @param mName
//     * @return
//     */
//    public String optionContent(Object[] args, String mName) {
//        if (args == null) {
//            return null;
//        }
//        StringBuffer rs = new StringBuffer();
//        rs.append(mName);
//        String className = null;
//        int    index     = 1;
//        // 遍历参数对象
//        for (Object info : args) {
//            // 获取对象类型
//            className = info.getClass().getName();
//            className = className.substring(className.lastIndexOf(".") + 1);
//            rs.append("[参数" + index + "，类型:" + className + "，值:");
//            // 获取对象的所有方法
//            Method[] methods = info.getClass().getDeclaredMethods();
//            // 遍历方法，判断get方法
//            for (Method method : methods) {
//                String methodName = method.getName();
//                // 判断是不是get方法
//                if (methodName.indexOf("get") == -1) {// 不是get方法
//                    continue;// 不处理
//                }
//                Object rsValue = null;
//                try {
//                    // 调用get方法，获取返回值
//                    rsValue = method.invoke(info);
//                } catch (Exception e) {
//                    continue;
//                }
//                // 将值加入内容中
//                rs.append("(" + methodName + ":" + rsValue + ")");
//            }
//            rs.append("]");
//            index++;
//        }
//        return rs.toString();
//    }
//
//}
