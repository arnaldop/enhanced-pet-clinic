package sample.ui.web;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sample.ui.booking.Booking;
import sample.ui.booking.Hotel;
import sample.ui.booking.SearchCriteria;
import sample.ui.service.ClinicService;

@Controller
public class HotelsController {

    private static Log logger = LogFactory.getLog(HotelsController.class);

    @Autowired
    private ClinicService clinicService;

    @RequestMapping(value = "/hotels/search", method = RequestMethod.GET)
    public void search(SearchCriteria searchCriteria, Principal currentUser, Model model) {
        if (currentUser != null) {
            Collection<Booking> booking = this.clinicService.findBookings(currentUser.getName());
            model.addAttribute(booking);
        }
    }

    @RequestMapping(value = "/hotels/searchForm", method = RequestMethod.GET)
    public String initFindForm(Model model) {
        model.addAttribute("searchCriteria", new SearchCriteria());
        return "hotels/searchForm";
    }

    @RequestMapping(value = "/hotels", method = RequestMethod.GET)
    public String list(SearchCriteria criteria, Model model) {
        List<Hotel> hotels = this.clinicService.findHotels(criteria);
//        List<Hotel> hotels = this.clinicService.findAllHotels();
        logger.info("hotels = " + hotels);
        model.addAttribute(hotels);
        return "hotels/list";
    }

    @RequestMapping(value = "/hotels/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute(this.clinicService.findHotelById(id));
        return "hotels/show";
    }

    @RequestMapping(value = "/bookings/{id}", method = RequestMethod.DELETE)
    public String deleteBooking(@PathVariable Long id) {
        this.clinicService.cancelBooking(id);
        return "redirect:../hotels/search";
    }

}
