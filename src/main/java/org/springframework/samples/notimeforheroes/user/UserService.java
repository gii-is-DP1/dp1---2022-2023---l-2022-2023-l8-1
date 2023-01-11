/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.notimeforheroes.user;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
@AllArgsConstructor
public class UserService {

	private UserRepository userRepository;

	@Autowired
	private AuthoritiesService authoritiesService;


	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void saveUser(User user) throws DataAccessException {
		user.setEnabled(true);
		userRepository.save(user);
		authoritiesService.saveAuthorities(user.getId(), "user");
	}

	public Page<User> getAll(Pageable pageable){
		return userRepository.findAll(pageable);
	}
	
	@Transactional
	public Optional<User> findUser(int id) {
		return userRepository.findById(id);
	}

	@Transactional
	public User findUserByUsername(String username) {
		return userRepository.getByUsername(username);
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public Collection<User> findUsers(){
		return (Collection<User>) userRepository.findAll();
	}
	

	public void delete(User user) {
		userRepository.delete(user);
	}
	
	@Transactional
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

	public List<User> findByUsernameContaining(String username){
		return userRepository.findByUsernameStartsWith(username);
	}
}
