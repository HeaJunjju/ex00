package com.spring5212.mypro00.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/security-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class MemberTests {

    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder pwencoder;
  
    @Setter(onMethod_ = @Autowired)
    private DataSource dataSource;
 
//    @Setter(onMethod_ = { @Autowired })
//    private SqlSessionFactory sqlSessionFactory;
//
//    @Test
//    public void testMyBatis() {
//
//        try ( SqlSession session = sqlSessionFactory.openSession();
//              Connection con = session.getConnection(); ){
//   
//            log.info(session);
//    	      log.info(con);
//        } catch (Exception e) {
//            fail(e.getMessage());
//        }
//    }
//  
//    @Test
//    public void testConnection() {
//
//        try (Connection con = dataSource.getConnection()){
//
//            log.info(con);
//        } catch(Exception e) {
//        	fail(e.getMessage());
//        }
//    }
//  
//  
//
//    //member 데이터 입력
//    @Test
//    public void testInsertMember()  {
//
//        String sql = " INSERT INTO book_ex.tbl_mymember "
//                    + " VALUES (?,?,?, DEFAULT, DEFAULT, DEFAULT, DEFAULT)";
//
//        for(int i = 100; i < 200; i++) {
//            Connection con = null;
//            PreparedStatement pstmt = null;
//
//            try {
//                con = dataSource.getConnection();
//                pstmt = con.prepareStatement(sql);
//                pstmt.setString(2, pwencoder.encode("pw" + i));
//
//                if(i <180) {
//                    pstmt.setString(1, "user"+i);
//                    pstmt.setString(3,"일반사용자"+i);
//
//                } else if (i <190) {
//                    pstmt.setString(1, "manager"+i);
//                    pstmt.setString(3,"운영자"+i);
//
//                } else {
//                    pstmt.setString(1, "admin"+i);
//                    pstmt.setString(3,"관리자"+i);
//                }
//                pstmt.executeUpdate();
//
//            } catch(Exception e) {
//                e.printStackTrace();
//
//            } finally {
//                if(pstmt != null) { try { pstmt.close();  } catch(Exception e) {} }
//                if(con != null) { try { con.close();  } catch(Exception e) {} }
//            }
//        }//end for
//    }
//
    //권한데이터 입력  
    @Test
    public void testInsertAuth() {

        String sql = "INSERT INTO book_ex.tbl_mymember_authorities VALUES (?,?)";
    
        for(int i = 100; i < 200; i++) {
      
            Connection con = null;
            PreparedStatement pstmt = null;
      
            try {
                con = dataSource.getConnection();
                pstmt = con.prepareStatement(sql);
        
                if(i <180) {
                    pstmt.setString(1, "user"+i);
                    pstmt.setString(2,"ROLE_USER");
          
                } else if (i <190) {
                    pstmt.setString(1, "manager"+i);
                    pstmt.setString(2,"ROLE_MEMBER");
          
                } else {
                    pstmt.setString(1, "admin"+i);
                    pstmt.setString(2,"ROLE_ADMIN");
          
                }
                    pstmt.executeUpdate();
        
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if(pstmt != null) { try { pstmt.close();  } catch(Exception e) {} }
                if(con != null) { try { con.close();  } catch(Exception e) {} }
            }
        }//end for
    }
}


