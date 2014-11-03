package sample.ui.booking;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookingDateRangeValidator implements ConstraintValidator<BookingDateRange, Booking> {

    @Override
    public void initialize(BookingDateRange bookingDateRange) {
    }

    @Override
    public boolean isValid(Booking booking, ConstraintValidatorContext context) {
        if ((booking.getCheckinDate() != null) && (booking.getCheckoutDate() != null)
                && booking.getCheckoutDate().before(booking.getCheckinDate())) {
            return false;
        }
        return true;
    }

}
