package sample.ui.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

import sample.ui.booking.Hotel;
import sample.ui.model.BaseEntity;

/**
 * A JPA-based implementation of the Hotel Service. Delegates to a JPA entity
 * manager to issue data access calls against the backing repository. The
 * EntityManager reference is provided by the managing container (Spring)
 * automatically.
 */
public interface HotelRepository extends Repository<Hotel, Long> {

    List<Hotel> findAll() throws DataAccessException;

    List<Hotel> findByNameLikeOrCityLikeOrZipLikeOrAddressLike(String name, String city,
            String zip, String address);

    List<Hotel> findByNameContainingIgnoreCase(String name);

    /**
     * Retrieve an <code>Hotel</code> from the data store by id.
     *
     * @param id
     *            the id to search for
     * @return the <code>Hotel</code> if found
     * @throws org.springframework.dao.DataRetrievalFailureException
     *             if not found
     */
    public Hotel findById(Long id) throws DataAccessException;

    /**
     * Save an <code>Hotel</code> to the data store, either inserting or
     * updating it.
     *
     * @param hotel
     *            the <code>Hotel</code> to save
     * @see BaseEntity#isNew
     */
    public void save(Hotel hotel) throws DataAccessException;

    /**
     * Cancel an existing hotel.
     *
     * @param id
     *            the hotel id
     */
    public void delete(Long id);

}
