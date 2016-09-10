package org.volunteer.run;/**
 * Description : 
 * Created by YangZH on 16-8-28
 *  上午1:40
 */

import org.volunteer.constant.Const;
import org.volunteer.server.HttpServer;

/**
 * Description :
 * Created by YangZH on 16-8-28
 * 上午1:40
 */

public class Volunteer {

    public static void main(String[] args) {
        HttpServer server = new HttpServer(Const.DEFAULT_CONFIGPATH);
        server.startup();
    }
}
