import {When} from "cypress-cucumber-preprocessor/steps";
import { recall, caseworker } from "../../fixtures";
import {formatIsoDate} from "../../support/utils";

const partBRecord = recall.partBRecords[0]

When('Maria can see on the recall information page that the part B is missing', () => {
    cy.getText('recallStatus').should('equal', 'Awaiting part B')
    cy.getText('partBDueText').should('contain', 'Part B report will be due on ')
    cy.recallInfo('Part B report').should('equal', 'Missing')
    cy.getLinkHref('Add part B report').should('contain', '/part-b?fromPage=view-recall')
})

When('Maria uploads the part B report', () => {
    cy.clickLink('Recalls')
    cy.clickLink('Awaiting part B')
    cy.get('@recallId').then(recallId => {
        cy.clickLink({qaAttr: `upload-part-b-${recallId}`})
    })

    cy.pageHeading().should('equal', 'Upload documents')
    cy.uploadPDF({field: 'partBFileName', file: 'test.pdf'})
    cy.uploadPDF({field: 'oasysFileName', file: 'test.pdf'})
    cy.fillInput('Provide more detail', partBRecord.details)
    cy.enterDateTime(partBRecord.partBReceivedDate)
    cy.uploadEmail({field: 'emailFileName'})
    cy.clickButton('Continue')
})

When('Maria views the part B details', () => {
    // confirmation banner
    cy.getText('confirmationHeading').should('equal', 'Part B added')
    cy.getText('confirmationBody').should('contain', 'Part B report and OASys uploaded.')
    cy.getText('confirmationBody').should('contain', 'Part B email and note added.')

    cy.recallInfo('OASys report').should('contain', 'OASys.pdf')

    // part B details
    cy.recallInfo('Part B report').should('contain', 'Part B.pdf')
    cy.recallInfo('Part B email received').should('equal', formatIsoDate(partBRecord.partBReceivedDate))
    cy.recallInfo('Part B uploaded by').should('equal', `${caseworker.firstName} ${caseworker.lastName}`)
    cy.recallInfo('Part B email uploaded').should('equal', 'email.msg')
})


