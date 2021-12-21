import {Given, When} from "cypress-cucumber-preprocessor/steps";
import {recall} from "../../fixtures/recall";
import {caseworker} from "../../fixtures/caseworker";

const nomsNumber = Cypress.env('NOMS_NUMBER') || 'A1234AA'

Given('Maria enters their user details', () => {
    cy.visitPage('/user-details')
    cy.fillInput('First name', caseworker.firstName, {clearExistingText: true})
    cy.fillInput('Last name', caseworker.lastName, {clearExistingText: true})
    cy.fillInput('Email address', caseworker.email, {clearExistingText: true})
    cy.fillInput('Phone number', caseworker.phoneNumber, {clearExistingText: true})
    // cy.selectRadio('Caseworker band', caseworker.caseworkerBand, { findByValue: true })
    cy.clickButton('Save')
    cy.clickLink('Recalls')
    cy.pageHeading().should('equal', 'Recalls')
})

When('Maria searches for the environment specific NOMS number', () => {
    cy.clickLink('Find a person')
    cy.fillInput('NOMIS number', nomsNumber)
    cy.clickButton('Search')
    cy.getText('name').as('firstLastName')
})

When('Maria clicks on the Book a recall link', () => {
    cy.clickButton('Book a recall')
})

When('Maria enters the licence name', () => {
    cy.get('@firstLastName').then((firstLastName) =>
        cy.selectRadio(`How does ${firstLastName}\'s name appear on the licence?`, firstLastName)
    )
    cy.clickButton('Continue')
})

When('Maria enters the pre-convictions name', () => {
    cy.get('@firstLastName').then((firstLastName) =>
        cy.selectRadio(`How does ${firstLastName}'s name appear on the previous convictions sheet (pre-cons)?`, firstLastName)
    )
    cy.clickButton('Continue')
})

When('Maria submits the date and email of the recall request received from probation service', () => {
    cy.enterDateTimeFromRecall('recallEmailReceivedDateTime')
    cy.uploadFile({field: 'recallRequestEmailFileName', file: 'email.msg', encoding: 'binary'})
    cy.clickButton('Continue')
})

When('Maria submits the sentence, offence and release details', () => {
    cy.enterDateTimeFromRecall('sentenceDate')
    cy.enterDateTimeFromRecall('licenceExpiryDate')
    cy.enterDateTimeFromRecall('sentenceExpiryDate')
    const {years, months, days} = recall.sentenceLength
    cy.fillInputGroup({'Years': years, 'Months': months, 'Days': days}, {parent: '#sentenceLength'})
    cy.selectFromAutocomplete('Sentencing court', 'Aberdare County Court')
    cy.selectFromAutocomplete('Releasing prison', 'Ashwell (HMP)')
    cy.fillInput('Index offence', recall.indexOffence)
    cy.fillInput('Booking number', recall.bookingNumber)
    cy.enterDateTimeFromRecall('lastReleaseDate')
    cy.enterDateTimeFromRecall('conditionalReleaseDate')
    cy.clickButton('Continue')
})

When('Maria submits the police contact details', () => {
    cy.selectFromAutocomplete('What is the name of the local police force?', 'Cumbria Constabulary')
    cy.clickButton('Continue')
})

When('Maria submits any vulnerability and contraband related details for the offender', () => {
    cy.selectRadio('Are there any vulnerability issues or diversity needs?', 'Yes')
    cy.fillInput('Provide more detail', recall.vulnerabilityDiversityDetail, {parent: '#conditional-vulnerabilityDiversity'})
    cy.get('@firstLastName').then((firstLastName) =>
        cy.selectRadio(`Do you think ${firstLastName} will bring contraband into prison?`, 'No')
    )
    cy.selectFromDropdown('MAPPA level', 'Level 1')
    cy.clickButton('Continue')
})

When('Maria submits the probation officer details', () => {
    cy.fillInput('Name', recall.probationOfficerName)
    cy.fillInput('Email address', recall.probationOfficerEmail)
    cy.fillInput('Phone number', recall.probationOfficerPhoneNumber)
    cy.selectFromAutocomplete('Local Delivery Unit (LDU)', 'PS - Cumbria')
    cy.fillInput('Assistant Chief Officer (ACO) that signed-off the recall', recall.authorisingAssistantChiefOfficer)
    cy.clickButton('Continue')
})

When('Maria uploads some documents', () => {
    cy.uploadFile({field: 'documents', file: 'Licence.pdf', encoding: 'base64'})
    cy.suggestedCategoryFor('Licence.pdf').should('equal', 'LICENCE')
    cy.uploadFile({field: 'documents', file: 'Part A for FTR.pdf'})
    cy.suggestedCategoryFor('Part A for FTR.pdf').should('equal', 'PART_A_RECALL_REPORT')
    cy.clickButton('Continue')
})

When('Maria submits the reason for missing documents', () => {
    cy.uploadFile({field: 'missingDocumentsEmailFileName', file: 'email.msg', encoding: 'binary'})
    cy.fillInput('Provide more detail', 'Chased', {clearExistingText: true})
    cy.clickButton('Continue')
    cy.pageHeading().should('equal', 'Check the details before booking this recall')
})

When('Maria can check their answers', () => {
    cy.downloadPdf('Licence.pdf')
        .should('contain','REVOCATION OF LICENCE')
})