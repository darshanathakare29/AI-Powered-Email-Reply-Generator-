//package com.example.demo.emailGeneratorController;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.Model.EmailRequest;
//import com.example.demo.Service.EmailService;
//
//import lombok.AllArgsConstructor;
//
//@RestController
//@RequestMapping("/api/email")
//
//public class EmailGeneratorController {
//
//	@Autowired
//	private EmailService es;
//	
//	@PostMapping("/generate")
//	public ResponseEntity<String> generateEmail(@RequestBody EmailRequest er){
//	String res=es.generateEmailReply(er);
//		return ResponseEntity.ok(res);
//	}
//}


package com.example.demo.emailGeneratorController;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.EmailRequest;
import com.example.demo.Service.EmailService;

import lombok.AllArgsConstructor;




@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
@CrossOrigin(origins="*")
public class EmailGeneratorController {
	
@Autowired
	private final EmailService es;

public EmailGeneratorController(EmailService es) {
	this.es=es;
}

	@PostMapping("/generate")
	public String generateEmail(@RequestBody EmailRequest req) {
        return es.generateEmailReply(req);
    }
}
