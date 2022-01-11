import {When} from "cypress-cucumber-preprocessor/steps";
import {recall, caseworker, nomsNumber} from "../../fixtures";

When('Maria navigates to view the change history overview for the recall', () => {
    cy.clickLink('View change history')
    cy.pageHeading().should('equal', 'Change history')
})

When('Maria navigates to view the change history details page for the fields for the recall', () => {
    cy.clickLink({qaAttr: 'viewHistory-currentPrison'})
    cy.getText('value').should('equal', 'Bedford (HMP)')
    cy.getText('dateAndTime').should('not.be.empty')
    const {firstName, lastName} = caseworker
    cy.getText('updatedByUserName').should('equal', `${firstName} ${lastName}`)
})

When('Maria navigates to view the change history details page for the documents for the recall', () => {
    cy.go('back')
    cy.get('#tab_documents').click()
    cy.clickLink({qaAttr: 'viewHistory-PREVIOUS_CONVICTIONS_SHEET'})
    cy.pageHeading().should('equal', 'Uploaded document change history')
    cy.recallInfo('Document', { parent: '[data-qa="uploaded-1-row"]'}).should('equal', 'Pre Cons.pdf')
    cy.recallInfo('Document', { parent: '[data-qa="missing-1-row"]'}).should('equal', 'Missing')
    cy.recallInfo('Details', { parent: '[data-qa="missing-1-row"]'}).should('equal', 'Chased')
    cy.recallInfo('Email uploaded', { parent: '[data-qa="missing-1-row"]'}).should('equal', 'email.msg')
    cy.go('back')
    cy.clickLink({qaAttr: 'viewHistory-DOSSIER'})
    cy.pageHeading().should('equal', 'Generated document change history')
})