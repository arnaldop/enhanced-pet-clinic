package sample.ui.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import sample.ui.booking.Booking;
import sample.ui.model.BaseEntity;

/**
 * A JPA-based implementation of the Booking Service. Delegates to a JPA entity
 * manager to issue data access calls against the backing repository. The
 * EntityManager reference is provided by the managing container (Spring)
 * automatically.
 */
@Service("bookingService")
public interface BookingRepository extends Repository<Booking, Long> {

    Collection<Booking> findByUserName(String userName);

    /**
     * Retrieve an <code>Booking</code> from the data store by id.
     *
     * @param id
     *            the id to search for
     * @return the <code>Booking</code> if found
     * @throws org.springframework.dao.DataRetrievalFailureException
     *             if not found
     */
    public Booking findById(Long id) throws DataAccessException;

    /**
     * Save an <code>Booking</code> to the data store, either inserting or
     * updating it.
     *
     * @param booking
     *            the <code>Booking</code> to save
     * @see BaseEntity#isNew
     */
    public void save(Booking booking) throws DataAccessException;

    /**
     * Cancel an existing booking.
     *
     * @param id
     *            the booking id
     */
    public void delete(Long id);

}
