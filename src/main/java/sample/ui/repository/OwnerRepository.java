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
import sample.ui.model.Owner;

/**
 * Repository class for <code>Owner</code> domain objects All method names are
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
public interface OwnerRepository extends Repository<Owner, Long> {

	/**
	 * Retrieve <code>Owner</code>s from the data store by last name, returning
	 * all owners whose last name <i>starts</i> with the given name.
	 *
	 * @param lastName
	 *            Value to search for
	 * @return a <code>Collection</code> of matching <code>Owner</code>s (or an
	 *         empty <code>Collection</code> if none found)
	 */
	Collection<Owner> findByLastNameStartingWithIgnoreCase(String lastName) throws DataAccessException;

	/**
	 * Retrieve all <code>Owner</code>s from the data store.
	 *
	 * @return a <code>Collection</code> of matching <code>Owner</code>s (or an
	 *         empty <code>Collection</code> if none found)
	 */
	Collection<Owner> findAll() throws DataAccessException;

	/**
	 * Retrieve an <code>Owner</code> from the data store by id.
	 *
	 * @param id
	 *            the id to search for
	 * @return the <code>Owner</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if not found
	 */
	Owner findById(Long id) throws DataAccessException;

	/**
	 * Save an <code>Owner</code> to the data store, either inserting or
	 * updating it.
	 *
	 * @param owner
	 *            the <code>Owner</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Owner owner) throws DataAccessException;

}
