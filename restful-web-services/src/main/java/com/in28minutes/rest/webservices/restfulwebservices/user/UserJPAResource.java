package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAResource {
	
	@Autowired
	private UserDAOService service;
	
	@Autowired
	private UserRepository userRepository;
	//GET /users
	//retriveAll
	
	@Autowired
	private PostRepository postRepository;
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	//GET /users/{id}
	//retrieveUser(int id)
	@GetMapping("/jpa/users/{id}")
	public Resource retrieveUser(@PathVariable int id){
		Optional<User> user= userRepository.findById(id);
		if(!user.isPresent()){
			throw new UserNotFoundException("id-"+id);
			
			//all-users,SERVER_PATH
			
		}
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity createUser(@Valid @RequestBody User user){
		User savedUser= userRepository.save(user);
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRepository.deleteById(id);
	}
	
	@GetMapping("jpa/users/{id}/posts/")
	public List<Post> retrieveAllUsers(@PathVariable int id){
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()){
			throw new UserNotFoundException("id-"+id);
		}
		return userOptional.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity createPost(@PathVariable Integer id,@RequestBody Post post){
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()){
			throw new UserNotFoundException("id-"+id);
		}
		User user = userOptional.get();
		post.setUser(user);
		postRepository.save(post);
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
}
