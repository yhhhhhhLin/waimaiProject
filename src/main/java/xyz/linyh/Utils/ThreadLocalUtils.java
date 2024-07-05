package xyz.linyh.Utils;

/**
 * 在每次http请求中，每次都会创建一个新了进程，利用threadLocal可以存储这个线程的数据
 */
public class ThreadLocalUtils {
    private static ThreadLocal<Long> threadLocal= new ThreadLocal<Long>();


    public static void addUid(Long uId){
        threadLocal.set(uId);
    }

    public static long getUid(){
        return threadLocal.get();
    }

}
