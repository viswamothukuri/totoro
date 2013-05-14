package com.railinc.totoro.cucumber;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.runtime.PendingException;


public class BasicStepdefs {
	
	public BasicStepdefs() {
		System.out.println("StepDefs CTOR");
	}

	@Given("^a User '(.+)'$")
	public void a_User_sdtxs(String userId) throws Throwable {
		System.out.println("Create user object " + userId);
	}

	@Given("^User has the '(.+)' role$")
	public void addUserRole(String roleId) throws Throwable {
		System.out.println("Add " + roleId + " to user");
	}

	@Then("^User cannot '(.+)'$")
	public void userCannotPerform(String action) throws Throwable {
		System.out.println("check with policy manager that hte user cannot do " + action);
	}

	@Then("^User can '(.+)'$")
	public void userCanPerform(String action) throws Throwable {
		System.out.println("check with policy manager that hte user can do " + action);
	}

}
