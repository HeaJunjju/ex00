package org.zerock.ex00.datasource;

import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/mybatis-context.xml")
//Java 자바설정을 사용하는 경우 
//@ContextConfiguration(classes= {RootConfig.class})
@Log4j
public class DataSourceTests {

  @Setter(onMethod_ = { @Autowired })
  private DataSource dataSource;

  @Setter(onMethod_ = { @Autowired })
  private SqlSessionFactory sqlSessionFactory;

  @Test
  public void testMyBatis() {

    try (SqlSession session = sqlSessionFactory.openSession();
    	 Connection con = session.getConnection();
    ){	log.info(session);
    	log.info(con);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  
//  @Test
//  public void testConnection() {
//    
//    try (Connection con = dataSource.getConnection()){
//
//      log.info(con);
//    }catch(Exception e) {
//      fail(e.getMessage());
//    }
//  }
}
//Hikari: 직접 설정
//INFO : org.springframework.test.context.support.DefaultTestContextBootstrapper - Loaded default TestExecutionListener class names from location [META-INF/spring.factories]: [org.springframework.test.context.web.ServletTestExecutionListener, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]
//INFO : org.springframework.test.context.support.DefaultTestContextBootstrapper - Using TestExecutionListeners: [org.springframework.test.context.web.ServletTestExecutionListener@528931cf, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@ea1a8d5, org.springframework.test.context.support.DependencyInjectionTestExecutionListener@1563da5, org.springframework.test.context.support.DirtiesContextTestExecutionListener@2bbf4b8b, org.springframework.test.context.transaction.TransactionalTestExecutionListener@30a3107a, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener@33c7e1bb, org.springframework.test.context.event.EventPublishingTestExecutionListener@34c4973]
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
//INFO : com.spring5212.mypro00.jdbcCP.DataSourceTests - org.apache.ibatis.session.defaults.DefaultSqlSession@32c726ee
//INFO : com.spring5212.mypro00.jdbcCP.DataSourceTests - HikariProxyConnection@211090736 wrapping net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@36328d33
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown completed.


//Hikari: properties 파일 사용 시
//INFO : org.springframework.test.context.support.DefaultTestContextBootstrapper - Loaded default TestExecutionListener class names from location [META-INF/spring.factories]: [org.springframework.test.context.web.ServletTestExecutionListener, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]
//INFO : org.springframework.test.context.support.DefaultTestContextBootstrapper - Using TestExecutionListeners: [org.springframework.test.context.web.ServletTestExecutionListener@528931cf, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@ea1a8d5, org.springframework.test.context.support.DependencyInjectionTestExecutionListener@1563da5, org.springframework.test.context.support.DirtiesContextTestExecutionListener@2bbf4b8b, org.springframework.test.context.transaction.TransactionalTestExecutionListener@30a3107a, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener@33c7e1bb, org.springframework.test.context.event.EventPublishingTestExecutionListener@34c4973]
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
//INFO : com.spring5212.mypro00.jdbcCP.DataSourceTests - org.apache.ibatis.session.defaults.DefaultSqlSession@10cf09e8
//INFO : com.spring5212.mypro00.jdbcCP.DataSourceTests - HikariProxyConnection@2131960182 wrapping net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@6bca7e0d
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown completed.


//Mybatis: 직접 설정
//INFO : org.springframework.test.context.support.DefaultTestContextBootstrapper - Loaded default TestExecutionListener class names from location [META-INF/spring.factories]: [org.springframework.test.context.web.ServletTestExecutionListener, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]
//INFO : org.springframework.test.context.support.DefaultTestContextBootstrapper - Using TestExecutionListeners: [org.springframework.test.context.web.ServletTestExecutionListener@528931cf, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@ea1a8d5, org.springframework.test.context.support.DependencyInjectionTestExecutionListener@1563da5, org.springframework.test.context.support.DirtiesContextTestExecutionListener@2bbf4b8b, org.springframework.test.context.transaction.TransactionalTestExecutionListener@30a3107a, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener@33c7e1bb, org.springframework.test.context.event.EventPublishingTestExecutionListener@34c4973]
//INFO : com.spring5212.mypro00.jdbcCP.DataSourceTests - org.apache.ibatis.session.defaults.DefaultSqlSession@401f7633
//INFO : com.spring5212.mypro00.jdbcCP.DataSourceTests - net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@56673b2c


//Mybatis: properties 파일 사용 시
//INFO : org.springframework.test.context.support.DefaultTestContextBootstrapper - Loaded default TestExecutionListener class names from location [META-INF/spring.factories]: [org.springframework.test.context.web.ServletTestExecutionListener, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]
//INFO : org.springframework.test.context.support.DefaultTestContextBootstrapper - Using TestExecutionListeners: [org.springframework.test.context.web.ServletTestExecutionListener@528931cf, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@ea1a8d5, org.springframework.test.context.support.DependencyInjectionTestExecutionListener@1563da5, org.springframework.test.context.support.DirtiesContextTestExecutionListener@2bbf4b8b, org.springframework.test.context.transaction.TransactionalTestExecutionListener@30a3107a, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener@33c7e1bb, org.springframework.test.context.event.EventPublishingTestExecutionListener@34c4973]
//INFO : com.spring5212.mypro00.jdbcCP.DataSourceTests - org.apache.ibatis.session.defaults.DefaultSqlSession@673be18f
//INFO : com.spring5212.mypro00.jdbcCP.DataSourceTests - net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@332796d3




//HikariCp에서의 주의점
//driverClassName에 oracle.jdbc.driver.OracleDriver를 사용하는 경우 아래의 경고 발생.
//INFO : org.springframework.test.context.support.DefaultTestContextBootstrapper - Loaded default TestExecutionListener class names from location [META-INF/spring.factories]: [org.springframework.test.context.web.ServletTestExecutionListener, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]
//INFO : org.springframework.test.context.support.DefaultTestContextBootstrapper - Using TestExecutionListeners: [org.springframework.test.context.web.ServletTestExecutionListener@528931cf, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@ea1a8d5, org.springframework.test.context.support.DependencyInjectionTestExecutionListener@1563da5, org.springframework.test.context.support.DirtiesContextTestExecutionListener@2bbf4b8b, org.springframework.test.context.transaction.TransactionalTestExecutionListener@30a3107a, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener@33c7e1bb, org.springframework.test.context.event.EventPublishingTestExecutionListener@34c4973]
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
//WARN : com.zaxxer.hikari.util.DriverDataSource - Registered driver with driverClassName=oracle.jdbc.driver.OracleDriver was not found, trying direct instantiation.
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
//INFO : com.spring5212.mypro00.jdbcCP.DataSourceTests - HikariProxyConnection@1825419935 wrapping oracle.jdbc.driver.T4CConnection@1b6e1eff
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
//INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown completed.

//driverClassName에 oracle.jdbc.OracleDriver를 사용하는 경우 경고 없음.


