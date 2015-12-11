package sample.ui.flow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.execution.repository.NoSuchFlowExecutionException;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

public class UsersFlowHandler extends AbstractFlowHandler {

	private static final String DEFAULT_URL = "/";

	@Override
	public String handleExecutionOutcome(FlowExecutionOutcome outcome, HttpServletRequest request,
			HttpServletResponse response) {
		return DEFAULT_URL;
	}

	@Override
	public String handleException(FlowException e, HttpServletRequest request, HttpServletResponse response) {
		if (e instanceof NoSuchFlowExecutionException) {
			return DEFAULT_URL;
		} else {
			// ModelAndView mav = new ModelAndView();
			// request.mav.addObject("exception", e);
			// mav.addObject("timestamp", new Date());
			// mav.addObject("url", req.getRequestURL());
			// mav.setViewName("exception");
			// return mav;

			throw e;
		}
	}
}
