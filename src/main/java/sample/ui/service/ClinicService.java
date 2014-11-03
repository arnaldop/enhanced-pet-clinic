/*
 * Copyright 2002-2014 the original author or authors.
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
package sample.ui.service;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import sample.ui.booking.Booking;
import sample.ui.booking.Hotel;
import sample.ui.booking.SearchCriteria;
import sample.ui.message.Message;
import sample.ui.model.Owner;
import sample.ui.model.Pet;
import sample.ui.model.PetType;
import sample.ui.model.Vet;
import sample.ui.model.Visit;

/**
 * Mostly used as a facade for all Petclinic controllers.
 *
 * @author Michael Isvy
 * @author Arnaldo Piccinelli
 */
public interface ClinicService {

    public Collection<PetType> findPetTypes() throws DataAccessException;

    public Owner findOwnerById(int id) throws DataAccessException;

    public Pet findPetById(int id) throws DataAccessException;

    public void savePet(Pet pet) throws DataAccessException;

    public void saveVisit(Visit visit) throws DataAccessException;

    public Collection<Vet> findVets() throws DataAccessException;

    public void saveOwner(Owner owner) throws DataAccessException;

    public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException;

    public Collection<Owner> findOwners() throws DataAccessException;

    public Collection<Message> findMessages() throws DataAccessException;

    public Message saveMessage(Message message) throws DataAccessException;

    public Message findMessage(Long id) throws DataAccessException;

    public Collection<Booking> findBookings(String username) throws DataAccessException;

    public List<Hotel> findHotels(SearchCriteria criteria) throws DataAccessException;

    public List<Hotel> findAllHotels() throws DataAccessException;

    public Hotel findHotelById(Long id) throws DataAccessException;

    public Booking createBooking(Long hotelId, String userName) throws DataAccessException;

    public void persistBooking(Booking booking) throws DataAccessException;

    public void cancelBooking(Long id) throws DataAccessException;
}
