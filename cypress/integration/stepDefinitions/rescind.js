import {When} from "cypress-cucumber-preprocessor/steps";

When('Maria goes to the view recall page', () => {
    cy.clickLink('Recalls')
    cy.clickLink('Not in custody')
    cy.get('@recallId').then(recallId => {
        cy.clickLink({qaAttr: `view-recall-${recallId}`})
    })
    cy.get('@firstLastName').then((firstLastName) =>
        cy.pageHeading().should('equal', `View the recall for ${firstLastName}`)
    )
})

When('Maria rescinds the recall', () => {
    cy.clickButton('Actions')
    cy.clickLink('Rescind recall')
    cy.pageHeading().should('equal', 'Record a rescind request')
    cy.fillInput('Provide details about the rescind request', 'blah blah blah')
    cy.clickButton('Today')
    cy.uploadEmail({ field: 'rescindRequestEmailFileName', file: 'email.msg' })
    cy.clickButton('Save and return')
})

When('Maria checks that the recall has been rescinded', () => {
    cy.clickLink('View')
    cy.getText('recallStatus').should('equal', 'Rescind in progress')
    cy.recallInfo('Rescind request details').should('equal', 'blah blah blah')
    cy.recallInfo('Rescind request email').should('equal', 'email.msg')
})
