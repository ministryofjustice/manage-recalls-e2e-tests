import {When} from "cypress-cucumber-preprocessor/steps";
import {recall, caseworker, nomsNumber} from "../../fixtures";
import {
    booleanToYesNo,
    formatIsoDate,
    getIsoDateForToday,
} from "../../support/utils";

When('Maria changes their caseworker band to 3', () => {
    cy.clickLink({qaAttr: 'header-user-name'})
    cy.selectRadio('Caseworker band', 'Band 3')
    cy.clickButton('Save')
})

When('Maria navigates to the recall to create a dossier', () => {
    cy.clickLink('Recalls')
    cy.get('@recallId').then(recallId => {
        cy.clickButton({qaAttr: `create-dossier-${recallId}`})
    })
})
When('Maria is able to see the recall information before creating a dossier', () => {
    // TODO - check due date
    cy.getText('recallStatus').should('equal', 'Dossier in progress')
    cy.clickLink('Create dossier')
})

When('Maria submits the information for the prison letter', () => {
    cy.selectRadio('Are there additional licence conditions?', booleanToYesNo(recall.additionalLicenceConditions))
    cy.fillInput('Provide more detail', recall.additionalLicenceConditionsDetail, {parent: '#conditional-additionalLicenceConditions'})
    cy.get('@firstLastName').then((firstLastName) => {
        cy.selectRadio(`Is ${firstLastName} being held under a different NOMIS number to the one on the licence?`, booleanToYesNo(recall.differentNomsNumber))
        cy.fillInput(`NOMIS number ${firstLastName} is being held under`, recall.differentNomsNumberDetail, {parent: '#conditional-differentNomsNumber'})
    })
    cy.clickButton('Continue')
})

When('Maria has checked and created the reasons for recall document', () => {
    cy.get('@firstLastName').then((firstLastName) => {
        cy.recallInfo('Name').should('equal', firstLastName)
    })
    cy.recallInfo('NOMIS').should('equal', nomsNumber)
    cy.recallInfo('Booking number').should('equal', recall.bookingNumber)
    cy.recallInfo('Licence conditions breached').should('equal', recall.licenceConditionsBreached)
    cy.recallInfo('Recall type').should('equal', recall.recallType)
    cy.recallInfo('Recall length').should('equal', '28 days')
    cy.clickLink('Continue')
})

When('Maria can open the dossier and letter', () => {
    cy.downloadPdf('Dossier').should('contain', '28 Day FTR 12 months & over')
    cy.downloadPdf('Letter to prison').should('contain', '28 DAY FIXED TERM RECALL')
})

When('Maria has reviewed the dossier', () => {
    cy.selectCheckboxes('I have checked the dossier', ['I have checked the information in the dossier and letter is correct'])
    cy.clickButton('Continue')
})

When('Maria records that the dossier was emailed', () => {
    cy.selectCheckboxes('I have sent the email to all recipients', ['I have sent the email to all recipients'])
    cy.enterDateTime(getIsoDateForToday())
    cy.uploadFile({field: 'dossierEmailFileName', file: 'email.msg', encoding: 'binary'})
    cy.clickButton('Complete dossier creation')
})

When('Maria sees confirmation that the dossier creation is complete', () => {
    cy.get('@firstLastName').then((firstLastName) => {
        cy.pageHeading().should('equal', `Dossier created and sent for ${firstLastName}`)
    })
    cy.clickLink('Back to recalls')
    cy.clickLink('Completed')
    cy.get('@recallId').then(recallId => {
        cy.getRecallItemFromList({recallId, columnQaAttr: 'assignedTo'}).should('equal', 'Dossier issued')
        cy.clickLink({qaAttr: `view-recall-${recallId}`})
    })
})

When('Maria confirms the details captured during dossier creation', () => {
    cy.getText('recallStatus').should('equal', 'Dossier complete')
    const {firstName, lastName} = caseworker
    cy.recallInfo('Dossier created by').should('equal', `${firstName} ${lastName}`)
    cy.recallInfo('Dossier sent').should('equal', formatIsoDate(getIsoDateForToday(), {dateOnly: true}))
    // TODO - CHECK DIFFERENT NOMIS NUMBER
})

When('Maria can download the dossier email', () => {
    cy.recallInfo('Dossier email uploaded').should('equal', 'email.msg')
    cy.downloadEmail({qaAttr: 'uploadedDocument-DOSSIER_EMAIL'})
})

When('Maria can regenerate the reasons for recall and dossier', () => {
    // dossier
    cy.clickLink('Change Dossier')
    cy.fillInput('Provide more detail', 'Details have changed')
    cy.clickButton('Continue')
    cy.recallInfo('Dossier', { parent: '#generated-documents'}).should('contain', '(version 2)')
    // reasons for recall
    cy.clickLink('Change Reasons for recall')
    cy.fillInput('Provide more detail', 'Details have changed')
    cy.clickButton('Continue')
    cy.recallInfo('Reasons for recall', { parent: '#generated-documents'}).should('contain', '(version 2)')
})

When('Maria navigates to view the change history for the recall', () => {
    cy.clickLink('View change history')
    cy.clickLink({qaAttr: 'viewHistory-PREVIOUS_CONVICTIONS_SHEET'})
    cy.pageHeading().should('equal', 'Uploaded document change history')
    cy.recallInfo('Document', { parent: '[data-qa="uploaded-1-row"]'}).should('equal', 'Pre Cons.pdf')
    cy.recallInfo('Document', { parent: '[data-qa="missing-1-row"]'}).should('equal', 'Missing')
    cy.recallInfo('Details', { parent: '[data-qa="missing-1-row"]'}).should('equal', 'Chased')
    cy.recallInfo('Email uploaded', { parent: '[data-qa="missing-1-row"]'}).should('equal', 'Email')
    cy.go('back')
    cy.clickLink({qaAttr: 'viewHistory-DOSSIER'})
    cy.pageHeading().should('equal', 'Generated document change history')
})