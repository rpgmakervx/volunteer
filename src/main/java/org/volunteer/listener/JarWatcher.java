package org.volunteer.listener;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午10:54
 */

import net.contentobjects.jnotify.JNotify;
import org.volunteer.constant.Const;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午10:54
 */

public class JarWatcher {

    private static final int mask = JNotify.FILE_CREATED |
                                    JNotify.FILE_DELETED |
                                    JNotify.FILE_MODIFIED |
                                    JNotify.FILE_RENAMED;
    private boolean watchSubtree = true;

    public synchronized void watchJarFolder(){
        try {
            int watchID = JNotify.addWatch(Const.COMPILE_PATH, mask, watchSubtree, new Listener());
            System.out.println(watchID+"  正在监听目录："+Const.COMPILE_PATH);
            wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JarWatcher().watchJarFolder();
    }
}
