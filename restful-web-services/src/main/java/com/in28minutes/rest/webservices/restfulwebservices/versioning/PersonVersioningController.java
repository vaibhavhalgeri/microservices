package com.in28minutes.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {
	
	@GetMapping("v1/person")
	public PersonV1 personV1(){
		return new PersonV1("Bob Marley");
	}
	
	@GetMapping("v2/person")
	public PersonV2 personV2(){
		return new PersonV2(new Name("Bob","Marley"));
	}
	
	@GetMapping(value="/person/param",params="version=1")
	public PersonV1 personparamV1(){
		return new PersonV1("Bob Marley");
	}
	
	@GetMapping(value="/person/param",params="version=2")
	public PersonV2 personV2param(){
		return new PersonV2(new Name("Bob","Marley"));
	}
	
	@GetMapping(value="/person/header",headers="X-API-VERSION=1")
	public PersonV1 personheaderV1(){
		return new PersonV1("Bob Marley");
	}
	
	@GetMapping(value="/person/header",headers="X-API-VERSION=2")
	public PersonV2 personheaderV2(){
		return new PersonV2(new Name("Bob","Marley"));
	}
}
