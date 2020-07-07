package com.lxd.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @ClassName FastClientImporter
 * @Author lxd
 * @Date 2020/7/6 22:39
 * @Description TODO
 */

@Configuration
@Import(FdfsClientConfig.class)
/**
 * @author li
 * 解决jmx重复注册bean的问题
 */
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastClientImporter {
}
