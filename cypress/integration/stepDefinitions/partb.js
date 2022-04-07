import {When} from "cypress-cucumber-preprocessor/steps";
import { recall, caseworker } from "../../fixtures";
import {booleanToYesNo, formatIsoDate} from "../../support/utils";

const partBRecord = recall.partBRecords[0]

When('Maria chases the missing part B report', () => {
    cy.getText('recallStatus').should('equal', 'Awaiting part B')
    cy.getText('partBDueText').should('contain', 'Part B report will be due on ')
    cy.recallInfo('Part B report').should('equal', 'Missing')
    cy.getLinkHref('Add part B report').should('contain', '/part-b?fromPage=view-recall')
    // chase
    cy.clickLink('Add note to this section')
    cy.uploadEmail({ field: 'missingDocumentsEmailFileName' })
    const detail = 'Chased up part B'
    cy.fillInput('Provide more detail', detail, { clearExistingText: true })
    cy.clickButton('Continue')

    cy.getText('confirmation').should('equal', 'Chase note added.')
    cy.getElement('Show 1 note').click()
    cy.getText('missingDocumentsRecordDetail-1').should('equal', detail)
    cy.getText('missingDocumentsRecordEmail-1').should('equal', 'email.msg')
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

    cy.selectRadio('Do probation support re-release?', booleanToYesNo(recall.rereleaseSupported))
    cy.clickButton('Continue')
})

When('Maria can see that the recall is on the list of recalls on the Dossier Check tab', () => {
    cy.clickLink('Recalls')
    cy.clickLink('Dossier check')
    cy.get('@recallId').then(recallId => {
        cy.getRecallItemFromList({recallId, columnQaAttr: 'assignedTo'}).should('equal', ``)
    })
})

When('Maria begins to prepare the dossier', () => {
    cy.clickLink('Recalls')
    cy.clickLink('Dossier check')
    cy.get('@recallId').then(recallId => {
        cy.clickButton({qaAttr: `prepare-dossier-${recallId}`})
    })
    cy.getText('recallStatus').should('equal', 'Preparation in progress')
})

When('Maria views the part B details', () => {
    // confirmation banner
    cy.getText('confirmationHeading').should('equal', 'Part B added')
    cy.getText('confirmationBody').should('contain', 'Part B report and OASys uploaded.')
    cy.getText('confirmationBody').should('contain', 'Part B email and note added.')
    cy.getText('confirmationBody').should(
        'contain',
        'Re-release recommendation added' //  and recall moved to Dossier team list'
    )

    cy.recallInfo('OASys report').should('contain', 'OASys.pdf')

    // part B details
    cy.recallInfo('Part B report').should('contain', 'Part B.pdf')
    cy.recallInfo('Part B email received').should('equal', formatIsoDate(partBRecord.partBReceivedDate))
    cy.recallInfo('Part B uploaded by').should('equal', `${caseworker.firstName} ${caseworker.lastName}`)
    cy.recallInfo('Part B email uploaded').should('equal', 'email.msg')
    cy.recallInfo('Re-release supported by probation').should(
        'contain',
        booleanToYesNo(recall.rereleaseSupported)
    )
})


