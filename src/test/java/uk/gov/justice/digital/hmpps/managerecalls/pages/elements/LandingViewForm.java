package uk.gov.justice.digital.hmpps.managerecalls.pages.elements;

import org.openqa.selenium.By;

public class LandingViewForm extends BaseForm{

    public static final String TITLE = TITLE_ROOT + "To do";

    public static By FIND_AN_OFFENDER_LINK = By.xpath("//*[@data-qa='navLinkFindSomeone']");
}