package com.inflearn.restfull.user;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor
public class UserJpaController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{userId}")
    public EntityModel<User> retrieveUser(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        EntityModel<User> entityModel = new EntityModel<>(user.get());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userRepository.deleteById(userId);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{userId}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        return user.get().getPosts();
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Integer userId, @RequestBody @Valid Post post) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        post.setUser(user.get());
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getUser().getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
