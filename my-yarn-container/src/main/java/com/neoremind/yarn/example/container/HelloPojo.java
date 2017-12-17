package com.neoremind.yarn.example.container;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.fs.FsShell;
import org.springframework.yarn.annotation.OnContainerStart;
import org.springframework.yarn.annotation.YarnComponent;

@YarnComponent
public class HelloPojo {

    private static final Log log = LogFactory.getLog(HelloPojo.class);

    @Autowired
    private Configuration configuration;

    @Value("${my.container.arg1}")
    private String arg1;

    @OnContainerStart
    public void publicVoidNoArgsMethod() throws Exception {
        log.info("Hello from HelloPojo");
        log.info("Container arg1 value is " + arg1);
        log.info("About to list from hdfs root content");
        Thread.sleep(60000);
        FsShell shell = new FsShell(configuration);
        for (FileStatus s : shell.ls(false, "/")) {
            log.info(s);
        }
        shell.close();
    }

}
