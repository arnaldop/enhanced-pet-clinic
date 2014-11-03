package sample.ui.booking;


/**
 * A backing bean for the main hotel search form. Encapsulates the criteria
 * needed to perform a hotel search.
 *
 * It is expected a future milestone of Spring Web Flow 2.0 will allow
 * flow-scoped beans like this one to hold references to transient services that
 * are restored automatically when the flow is resumed on subsequent requests.
 * This would allow this SearchCriteria object to delegate to the
 * {@link BookingService} directly, for example, eliminating the need for the
 * actions in {@link MainActions}.
 */
public class SearchCriteria {

    /**
     * The user-provided search criteria for finding Hotels.
     */
    private String searchString;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
