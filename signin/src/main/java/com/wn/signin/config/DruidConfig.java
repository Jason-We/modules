package com.wn.signin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: shebaocard_transfer
 * @description: 配置druid监控
 * @author: Jason-Wei
 * @create: 2020-10-22 11:16
 **/
@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid(){
        return new DruidDataSource();
    }

    //配置servlet   监控IP： http://localhost:8890/druid/login.html
    @Bean
    public ServletRegistrationBean druidServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> initParams = new HashMap<String,String>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123");
        initParams.put("allow","");// 如果不写就是默认允许所有
        initParams.put("deny","127.0.0.1");
        //是否能够重置数据.
        bean.addInitParameter("resetEnable","false");
        bean.setInitParameters(initParams);
        return bean;
    }

    //配置filter
    @Bean
    public FilterRegistrationBean myfilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.gif,*.jpg,*.css,/druid/*");

        bean.setInitParameters(initParams);

        bean.addUrlPatterns("/*");
        return bean;
    }

}
