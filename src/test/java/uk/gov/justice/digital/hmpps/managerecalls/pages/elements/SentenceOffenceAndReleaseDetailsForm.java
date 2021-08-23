package uk.gov.justice.digital.hmpps.managerecalls.pages.elements;

import org.openqa.selenium.By;

public class SentenceOffenceAndReleaseDetailsForm extends BaseForm{

    public static By SENTENCE_DAY_INPUT_FIELD = By.id("sentenceDate-sentenceDateDay");
    public static By SENTENCE_MONTH_INPUT_FIELD = By.id("sentenceDate-sentenceDateMonth");
    public static By SENTENCE_YEAR_INPUT_FIELD = By.id("sentenceDate-sentenceDateYear");

    public static By LICENCE_EXPIRY_DAY_INPUT_FIELD = By.id("licenceExpiryDate-licenceExpiryDateDay");
    public static By LICENCE_EXPIRY_MONTH_INPUT_FIELD = By.id("licenceExpiryDate-licenceExpiryDateMonth");
    public static By LICENCE_EXPIRY_YEAR_INPUT_FIELD = By.id("licenceExpiryDate-licenceExpiryDateYear");

    public static By SENTENCE_EXPIRY_DAY_INPUT_FIELD = By.id("sentenceExpiryDate-sentenceExpiryDateDay");
    public static By SENTENCE_EXPIRY_MONTH_INPUT_FIELD = By.id("sentenceExpiryDate-sentenceExpiryDateMonth");
    public static By SENTENCE_EXPIRY_YEAR_INPUT_FIELD = By.id("sentenceExpiryDate-sentenceExpiryDateYear");

    public static By SENTENCE_LENGTH_YEAR_INPUT_FIELD = By.id("sentenceLength-sentenceLengthYears");
    public static By SENTENCE_LENGTH_MONTH_INPUT_FIELD = By.id("sentenceLength-sentenceLengthMonths");
    public static By SENTENCE_LENGTH_DAY_INPUT_FIELD = By.id("sentenceLength-sentenceLengthDays");
    
    public static By SENTENCING_COURT_INPUT_FIELD = By.id("sentencingCourt");
    public static By INDEX_OFFENCE_INPUT_FIELD = By.id("indexOffence");
    public static By RELEASING_PRISON_INPUT_FIELD = By.id("lastReleasePrison");
    public static By BOOKING_NUMBER_INPUT_FIELD = By.id("bookingNumber");

    public static By LATEST_RELEASE_DAY_INPUT_FIELD = By.id("lastReleaseDate-lastReleaseDateDay");
    public static By LATEST_RELEASE_MONTH_INPUT_FIELD = By.id("lastReleaseDate-lastReleaseDateMonth");
    public static By LATEST_RELEASE_YEAR_INPUT_FIELD = By.id("lastReleaseDate-lastReleaseDateYear");

    public static By CONDITIONAL_RELEASE_DAY_INPUT_FIELD = By.id("conditionalReleaseDate-conditionalReleaseDateDay");
    public static By CONDITIONAL_RELEASE_MONTH_INPUT_FIELD = By.id("conditionalReleaseDate-conditionalReleaseDateMonth");
    public static By CONDITIONAL_RELEASE_YEAR_INPUT_FIELD = By.id("conditionalReleaseDate-conditionalReleaseDateYear");

}
