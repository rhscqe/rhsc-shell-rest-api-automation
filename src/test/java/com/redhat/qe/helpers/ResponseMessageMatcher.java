package com.redhat.qe.helpers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import com.redhat.qe.exceptions.UnexpectedReponseException;


public class ResponseMessageMatcher implements Matcher<UnexpectedReponseException>{
	private String expected;

	public ResponseMessageMatcher(String expected){
		this.expected = expected;
	}

	public void describeTo(Description description) {
		description.appendText("Response message");
	}

	public boolean matches(Object item) {
		return ((UnexpectedReponseException)item).getResponse().contains(expected);
	}

	public void describeMismatch(Object item, Description mismatchDescription) {
		String actual = ((UnexpectedReponseException)item).getResponse().toString();
		mismatchDescription.appendText(String.format("response mismatch. response did not contain expected.\\n expected:%s\\nactual: %s", expected, actual));
	}

	@Deprecated
	public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
		// TODO Auto-generated method stub
		
	}



}
