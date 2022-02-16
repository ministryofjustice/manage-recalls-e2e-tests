import {When} from "cypress-cucumber-preprocessor/steps";
import {recall} from "../../fixtures";
import {booleanToYesNo, formatIsoDate} from "../../support/utils";

const rescind = recall.rescindRecords[0]
const emailFileName = 'email.msg';

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
  cy.fillInput('Provide details about the rescind request', rescind.requestDetails)
  cy.enterDateTime(rescind.requestEmailReceivedDate)
  cy.uploadEmail({ field: 'rescindRequestEmailFileName', file: emailFileName })
  cy.clickButton('Save and return')
})

When('Maria checks that the rescind has been requested', () => {
  cy.clickLink('View')
  cy.getText('recallStatus').should('equal', 'Rescind in progress')
  cy.recallInfo('Rescind request details').should('equal', rescind.requestDetails)
  cy.recallInfo('Rescind request email').should('equal', emailFileName)
  cy.recallInfo('Rescind request received').should('equal', formatIsoDate(rescind.requestEmailReceivedDate))
})

When('Maria updates the rescind', () => {
  cy.clickButton('Actions')
  cy.clickLink('Update rescind')
  cy.pageHeading().should('equal', 'Record the rescind decision')
  cy.selectRadio('Do you want to rescind this recall?', booleanToYesNo(rescind.approved))
  cy.fillInput('Provide details about the decision', rescind.decisionDetails)
  cy.selectCheckboxes('I have sent the email to all relevant recipients', ['I have sent the email to all relevant recipients'])
  cy.enterDateTime(rescind.decisionEmailSentDate)
  cy.uploadEmail({ field: 'rescindDecisionEmailFileName', file: emailFileName })
  cy.clickButton('Save and return')
})

When('Maria checks that the rescind decision has been made', () => {
  cy.clickLink('View')
  cy.getText('recallStatus').should('equal', 'Stopped')
  cy.recallInfo('Recall rescinded').should('equal', booleanToYesNo(rescind.approved))
  cy.recallInfo('Rescind decision details').should('equal', rescind.decisionDetails)
  cy.recallInfo('Rescind decision email').should('equal', emailFileName)
  cy.recallInfo('Rescind decision sent').should('equal', formatIsoDate(rescind.decisionEmailSentDate))
})
