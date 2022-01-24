import {When} from "cypress-cucumber-preprocessor/steps";
import {caseworker} from "../../fixtures";

When('Maria navigates to view the change history overview for the recall', () => {
    cy.clickLink('View change history')
    cy.pageHeading().should('equal', 'Change history')
    cy.getRowValuesFromTable({ parent: '#table-info-entered', rowQaAttr: 'currentPrison' }).then(rowValues => {
        expect(rowValues[0]).to.equal('Prison held in')
        expect(rowValues[1]).to.not.be.empty // updated date
        expect(rowValues[2]).to.equal('Maria Badger')
    })
})

When('Maria navigates to view the change history details page for the current prison field for the recall', () => {
    cy.clickLink({qaAttr: 'viewHistory-currentPrison'})
    cy.getText('value').should('equal', 'Bedford (HMP)')
    cy.getText('dateAndTime').should('not.be.empty')
    const {firstName, lastName} = caseworker
    cy.getText('updatedByUserName').should('equal', `${firstName} ${lastName}`)
})

When('Maria navigates to view the change history details page for the documents for the recall', () => {
    cy.go('back')
    cy.clickLink('Documents')
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