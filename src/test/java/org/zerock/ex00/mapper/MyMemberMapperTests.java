package com.spring5212.mypro00.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spring5212.mypro00.domain.MyAuthorityVO;
import com.spring5212.mypro00.domain.MyMemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/security-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Log4j
public class MyMemberMapperTests {

    //사용자 패스워드 암호화 
    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder pwencoder;
    
    
    
    @Setter(onMethod_ = @Autowired)
    private MyMemberMapper myMemberMapper;

//
//    //회원 등록 테스트 1
//    @Test
//    public void testInsertMyMember() {
//    	
//        MyMemberVO myMember = new MyMemberVO();
//        
//        for(int i = 0; i < 100; i++) {
//
//            myMember.setUserpw(pwencoder.encode("pw" + i));
//
//            if(i < 80) {
//                myMember.setUserid("user" + i);
//                myMember.setUserName("일반사용자" + i);
//            
//            } else if (i < 90) {
//                myMember.setUserid("manager" + i);
//                myMember.setUserName("운영자" + i);
//                
//            } else {
//                myMember.setUserid("admin" + i);
//                myMember.setUserName("관리자" + i);
//            }
//            
//            log.info(myMember);            
//            myMemberMapper.insertMyMember(myMember);
//        } //for-End
//    }
    
//    //회원 등록 테스트2 - 기존 사용자 등록 
//    @Test
//    public void testInsertMyMember2() {
//    	
//        MyMemberVO myMember = new MyMemberVO();
//        
//        for(int i = 0; i < 10; i++) {
//        	myMember.setUserpw(pwencoder.encode("pw" + i));
//            myMember.setUserid("test" + i);
//            myMember.setUserName("테스트사용자" + i);
//            log.info(myMember);            
//            myMemberMapper.insertMyMember(myMember);
//        } //for-End
//    }
    
//    //회원 등록 테스트3 - 기존 사용자 등록 
//    @Test
//    public void testInsertMyMember3() {
//    	
//        MyMemberVO myMember = new MyMemberVO();
//        
//        	myMember.setUserpw(pwencoder.encode("pw99"));
//            myMember.setUserid("test99");
//            myMember.setUserName("테스트사용자99");
//            log.info(myMember);            
//            myMemberMapper.insertMyMember(myMember);
//        
//    }
    
  //회원 권한 테스트2 - 기존 사용자 등록 
  @Test
  public void testInsertMyMemAuthority2() {
  	
  	MyAuthorityVO myAuthority = new MyAuthorityVO();
      
      	myAuthority.setUserid("test99");
      	myAuthority.setAuthority("ROLE_USER");
          log.info(myAuthority);            
          myMemberMapper.insertMyMemAuthority(myAuthority);
   
  }
    
//    //회원 권한 테스트2 - 기존 사용자 등록 
//    @Test
//    public void testInsertMyMemAuthority2() {
//    	
//    	MyAuthorityVO myAuthority = new MyAuthorityVO();
//        
//        for(int i = 0; i < 10; i++) {
//        	myAuthority.setUserid("test" + i);
//        	myAuthority.setAuthority("ROLE_USER");
//            log.info(myAuthority);            
//            myMemberMapper.insertMyMemAuthority(myAuthority);
//        } //for-End
//    }
//    
//    //회원 권한 등록 테스트
//    @Test
//    public void testInsertMyMemAuthority() {
//		
//        MyAuthorityVO myAuthority = new MyAuthorityVO();
//        
//        for(int i = 0; i < 100; i++) {
//
//            if(i < 80) {
//                myAuthority.setUserid("user" + i);
//                myAuthority.setAuthority("ROLE_USER");
//                
//            } else if (i < 90) {
//                myAuthority.setUserid("manager" + i);
//                myAuthority.setAuthority("ROLE_MEMBER");
//                
//            } else {
//                myAuthority.setUserid("admin" + i);
//                myAuthority.setAuthority("ROLE_ADMIN");
//                
//            }
//            log.info(myAuthority);
//            myMemberMapper.insertMyMemAuthority(myAuthority);
//        } //for-End
//        
//        myAuthority.setUserid("admin99");
//        myAuthority.setAuthority("ROLE_MEMBER");
//        myMemberMapper.insertMyMemAuthority(myAuthority);
//        
//        myAuthority.setUserid("admin99");
//        myAuthority.setAuthority("ROLE_USER");
//        myMemberMapper.insertMyMemAuthority(myAuthority);
//        
//        myAuthority.setUserid("admin91");
//        myAuthority.setAuthority("ROLE_MEMBER");
//        myMemberMapper.insertMyMemAuthority(myAuthority);
//        
//        
//    }
//    
//    //회원 정보 조회 테스트    
//    @Test
//    public void testRead() {
//        MyMemberVO myMember = myMemberMapper.selectUser("admin99");
//        log.info(myMember);
//        
//        myMember.getAuthorityList().forEach(authorityVO -> log.info(authorityVO));
//    }
}


/*
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring5212.mypro00.common.security.MyMemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/mybatis-context.xml")
@Log4j
public class MyMemberMapperTests {

    @Setter(onMethod_ = @Autowired)
    private MyMemberMapper myMemberMapper;
	  
	  
    @Test
    public void testRead() {
        MyMemberVO myMember = myMemberMapper.selectUser("admin99");
        log.info(myMember);
        
        myMember.getAuthorityList().forEach(authVO -> log.info(authVO));
    }

}
*/
