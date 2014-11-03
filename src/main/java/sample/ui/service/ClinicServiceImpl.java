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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.ui.booking.Booking;
import sample.ui.booking.Hotel;
import sample.ui.booking.SearchCriteria;
import sample.ui.booking.User;
import sample.ui.message.Message;
import sample.ui.message.MessageRepository;
import sample.ui.model.Owner;
import sample.ui.model.Pet;
import sample.ui.model.PetType;
import sample.ui.model.Vet;
import sample.ui.model.Visit;
import sample.ui.repository.BookingRepository;
import sample.ui.repository.HotelRepository;
import sample.ui.repository.OwnerRepository;
import sample.ui.repository.PetRepository;
import sample.ui.repository.PetTypeRepository;
import sample.ui.repository.VetRepository;
import sample.ui.repository.VisitRepository;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder for @Transactional
 * and @Cacheable annotations
 *
 * @author Michael Isvy
 * @author Arnaldo Piccinelli
 */
@Service("clinicService")
public class ClinicServiceImpl implements ClinicService {

    private PetRepository petRepository;
    private PetTypeRepository petTypeRepository;
    private VetRepository vetRepository;
    private OwnerRepository ownerRepository;
    private VisitRepository visitRepository;
    private MessageRepository messageRepository;
    private BookingRepository bookingRepository;
    private HotelRepository hotelRepository;

    @Autowired
    public ClinicServiceImpl(PetRepository petRepository, PetTypeRepository petTypeRepository,
            VetRepository vetRepository, OwnerRepository ownerRepository,
            VisitRepository visitRepository, MessageRepository messageRepository,
            BookingRepository bookingRepository, HotelRepository hotelRepository) {
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.messageRepository = messageRepository;
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PetType> findPetTypes() throws DataAccessException {
        return petTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findOwnerById(int id) throws DataAccessException {
        return ownerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findOwners() throws DataAccessException {
        return ownerRepository.findAll();
    }

    @Override
    @Transactional
    public void saveOwner(Owner owner) throws DataAccessException {
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void saveVisit(Visit visit) throws DataAccessException {
        visitRepository.save(visit);
    }

    @Override
    @Transactional(readOnly = true)
    public Pet findPetById(int id) throws DataAccessException {
        return petRepository.findById(id);
    }

    @Override
    @Transactional
    public void savePet(Pet pet) throws DataAccessException {
        petRepository.save(pet);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "vets")
    public Collection<Vet> findVets() throws DataAccessException {
        return vetRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Message> findMessages() throws DataAccessException {
        return messageRepository.findAll();
    }

    @Override
    @Transactional
    public Message saveMessage(Message message) throws DataAccessException {
        return messageRepository.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Message findMessage(Long id) throws DataAccessException {
        return messageRepository.findMessage(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Booking> findBookings(String userName) {
        return bookingRepository.findByUserName(userName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> findHotels(SearchCriteria criteria) {
        return hotelRepository.findByNameContainingIgnoreCase(criteria.getSearchString());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Hotel findHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Booking createBooking(Long hotelId, String userName) {
        Hotel hotel = hotelRepository.findById(hotelId);
        User user = null;
        Booking booking = new Booking(hotel, user);
        return booking;
    }

    @Override
    @Transactional
    public void persistBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void cancelBooking(Long id) {
        bookingRepository.delete(id);
    }

}
