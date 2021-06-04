package practice.controller.chap06;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample")
@Log4j
public class SampleController {
	
	@RequestMapping("")
	public void basic() {
		log.info("basic..............................");
	}
	
	@RequestMapping(value="/basic", method= {RequestMethod.GET, RequestMethod.POST})
	public void basicGet() {
		log.info("basic get..............................");
	}
	
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		//@GetMapping, @PostMapping, @DeleteMapping, @PutMapping, @FetchMapping
		log.info("basicGet2 basicOnly get URL................");
	}
	
	@GetMapping("/ex01")
//	public void ex01(SampleDTO dto) {	//반환타입이 void면 views/부른url경로까지 해서 맨 끝에 .jsp를 찾는다
	public String ex01(SampleDTO dto) {	//String 타입이 반환타입이면 views에서 해당 리턴값.jsp를 뷰로 찾는다
		log.info(""+dto);
		System.out.println(dto.getName());
		System.out.println(dto.getAge());
		
		return "ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name1, @RequestParam("age") int age1) {
		log.info("name: "+name1);
		log.info("age: "+age1);
		
		return "ex01";
	}
}
