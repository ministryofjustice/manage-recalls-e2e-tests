import {When} from "cypress-cucumber-preprocessor/steps";
import {caseworker, recall} from "../../fixtures";
import {booleanToYesNo, formatIsoDate} from "../../support/utils";

const rescind = recall.rescindRecords[0]
const note = recall.notes[0]
const emailFixtureFileName = 'email.msg';
const wordDocFixtureFileName = 'lorem-ipsum-msword.docx';

function viewRecallAndOpenActionsMenu() {
  cy.clickLink('Recalls')
  cy.get('@recallId').then(recallId => {
      cy.clickLink({qaAttr: `view-recall-${recallId}`})
  })
  cy.get('@firstLastName').then((firstLastName) =>
      cy.pageHeading().should('equal', `View the recall for ${firstLastName}`)
  )
  cy.clickButton('Actions')
}

When('Maria rescinds the recall', () => {
  viewRecallAndOpenActionsMenu()
  cy.clickLink('Rescind recall')
  cy.pageHeading().should('equal', 'Record a rescind request')
  cy.fillInput('Provide details about the rescind request', rescind.requestDetails)
  cy.enterDateTime(rescind.requestEmailReceivedDate)
  cy.uploadEmail({ field: 'rescindRequestEmailFileName' })
  cy.clickButton('Save and return')
})

When('Maria checks that the rescind has been requested', () => {
  cy.clickLink('View')
  cy.getText('recallStatus').should('equal', 'Rescind in progress')
  cy.recallInfo('Rescind request details').should('equal', rescind.requestDetails)
  cy.recallInfo('Rescind request email').should('equal', emailFixtureFileName)
  cy.recallInfo('Rescind request received').should('equal', formatIsoDate(rescind.requestEmailReceivedDate))
})

When('Maria stops the recall', () => {
  viewRecallAndOpenActionsMenu()
  cy.clickLink('Stop recall')
  cy.pageHeading().should('equal', 'Why are you stopping this recall?')
  cy.selectFromDropdown('Why are you stopping this recall?', recall.stopReason)
  cy.clickButton('Save and return')
})

When('Maria checks that the stop decision has been recorded', () => {
  cy.clickLink('View')
  cy.getText('recallStatus').should('equal', 'Stopped')
  cy.getElement('Stop recall').should('not.exist')
  cy.getText('confirmation').should('equal', 'Recall stopped.')
  cy.clickLink('View')
  cy.recallInfo('Reason recall stopped').should('equal', 'Deceased')

  const {firstName, lastName} = caseworker
  cy.recallInfo('Recall stopped by').should('equal', `${firstName} ${lastName}`)
  cy.recallInfo('Recall stopped on').should('contain', formatIsoDate(Date.now()))
})

When('Maria updates the rescind', () => {
  cy.clickButton('Actions')
  cy.clickLink('Update rescind')
  cy.pageHeading().should('equal', 'Record the rescind decision')
  cy.selectRadio('Do you want to rescind this recall?', booleanToYesNo(rescind.approved))
  cy.fillInput('Provide details about the decision', rescind.decisionDetails)
  cy.selectCheckboxes('I have sent the email to all relevant recipients', ['I have sent the email to all relevant recipients'])
  cy.enterDateTime(rescind.decisionEmailSentDate)
  cy.uploadEmail({ field: 'rescindDecisionEmailFileName' })
  cy.clickButton('Save and return')
})

When('Maria checks that the rescind decision has been made', () => {
  cy.clickLink('View')
  cy.getText('recallStatus').should('equal', 'Stopped')
  cy.recallInfo('Recall rescinded').should('equal', booleanToYesNo(rescind.approved))
  cy.recallInfo('Rescind decision details').should('equal', rescind.decisionDetails)
  cy.recallInfo('Rescind decision email').should('equal', emailFixtureFileName)
  cy.recallInfo('Rescind decision sent').should('equal', formatIsoDate(rescind.decisionEmailSentDate))
})

When('Maria adds a note to the recall', () => {
  viewRecallAndOpenActionsMenu()
  cy.clickLink('Add a note')
  cy.pageHeading().should('equal', 'Add a note to the recall')
  cy.fillInput('Subject', note.subject)
  cy.fillInput('Details', note.details)
  cy.uploadDocx({ field: 'fileName' })
  cy.clickButton('Add note')
})

When('Maria checks that the note details have been recorded', () => {
  cy.clickLink('View')
  cy.getElement({qaAttr: `noteSubject1`}).should('contain', note.subject)
  cy.getElement({qaAttr: `noteDetails1`}).should('contain', note.details)
  const {firstName, lastName} = caseworker
  cy.recallInfo('Note made by').should('equal', `${firstName} ${lastName}`)
  cy.recallInfo('Date and time of note').should('contain', formatIsoDate(Date.now()))
  cy.recallInfo('Document').should('equal', wordDocFixtureFileName)
})
