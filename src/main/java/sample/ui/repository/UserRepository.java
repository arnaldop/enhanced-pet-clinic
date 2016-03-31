/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.ui.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

import sample.ui.model.BaseEntity;
import sample.ui.model.User;

/**
 * Repository class for <code>User</code> domain objects All method names are
 * compliant with Spring Data naming conventions so this interface can easily be
 * extended for Spring Data See here:
 * http://static.springsource.org/spring-data/
 * jpa/docs/current/reference/html/jpa
 * .repositories.html#jpa.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Arnaldo Piccinelli
 */
public interface UserRepository extends Repository<User, Long> {

	/**
	 * Retrieve <code>User</code>s from the data store by last name, returning
	 * all users whose last name <i>starts</i> with the given name.
	 *
	 * @param lastName
	 *            Value to search for
	 * @return a <code>Collection</code> of matching <code>User</code>s (or an
	 *         empty <code>Collection</code> if none found)
	 */
	Collection<User> findByNameLike(String name) throws DataAccessException;

	/**
	 * Retrieve <code>User</code> from the data store by username.
	 *
	 * @param lastName
	 *            Value to search for
	 * @return a <code>Collection</code> of matching <code>User</code>s (or an
	 *         empty <code>Collection</code> if none found)
	 */
	User findByUsername(String username) throws DataAccessException;

	/**
	 * Retrieve all <code>User</code>s from the data store.
	 *
	 * @return a <code>Collection</code> of matching <code>User</code>s (or an
	 *         empty <code>Collection</code> if none found)
	 */
	Collection<User> findAll() throws DataAccessException;

	/**
	 * Retrieve an <code>User</code> from the data store by id.
	 *
	 * @param id
	 *            the id to search for
	 * @return the <code>User</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if not found
	 */
	User findById(Long id) throws DataAccessException;

	/**
	 * Save an <code>User</code> to the data store, either inserting or updating
	 * it.
	 *
	 * @param user
	 *            the <code>User</code> to save
	 * @see BaseEntity#isNew
	 */
	User save(User user) throws DataAccessException;

	/**
	 * Save an <code>User</code> to the data store, either inserting or updating
	 * it.
	 *
	 * @param user
	 *            the <code>User</code> to save
	 * @see BaseEntity#isNew
	 */
	User delete(User user) throws DataAccessException;

	/**
	 *
	 * @param primaryKey
	 * @return
	 */
	boolean exists(Long userId) throws DataAccessException;

}
