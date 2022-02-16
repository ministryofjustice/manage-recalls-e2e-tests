import {When} from "cypress-cucumber-preprocessor/steps";
import {recall, caseworker} from "../../fixtures";
import {
    getGeneratedDocFileName,
    getIsoDateForMinutesAgo,
    splitFullName
} from "../../support/utils";

When('Maria changes their caseworker band to 4+', () => {
    cy.clickLink({qaAttr: 'header-user-name'})
    cy.selectRadio('Caseworker band', 'Band 4+')
    cy.clickButton('Save')
})

When('Maria begins to assess the recall that they have just booked', () => {
    cy.clickLink('Recalls')
    cy.get('@recallId').then(recallId => {
        cy.clickButton({qaAttr: `assess-recall-${recallId}`})
    })
    cy.getText('recallStatus').should('equal', 'Assessment in progress')
})

When('Maria adds a new version of the Part A', () => {
    cy.clickLink('Change Part A recall report')
    cy.uploadPDF({field: 'document', file: 'Part A for FTR.pdf'})
    cy.fillInput('Provide more detail', 'New Part A received')
    cy.clickButton('Continue')
    cy.recallInfo('Part A recall report').should('contain', '(version 2)')
})

When('Maria starts the assessment process for the recall', () => {
    cy.clickLink('Assess recall')
})

When('Maria confirms the recall length of 28 days', () => {
    cy.selectRadio('Do you agree with the fixed term 28 day recall recommendation?', 'Yes, proceed with the recommended fixed recall type')
    cy.fillInput('Provide more detail', recall.agreeWithRecallDetail, {parent: '#conditional-agreeWithRecall'})
    cy.clickButton('Continue')
})

When('Maria submits the licence breach details', () => {
    cy.fillInput('Licence conditions breached', recall.licenceConditionsBreached)
    cy.selectCheckboxes('Reasons for recall', recall.reasonsForRecall, {findByValue: true})
    cy.fillInput('Provide more detail', recall.reasonsForRecallOtherDetail)
    cy.clickButton('Continue')
})

When('Maria submits the current prison details', () => {
    cy.get('@firstLastName').then((firstLastName) =>
        cy.selectFromAutocomplete(`Which prison is ${firstLastName} in?`, recall.currentPrisonLabel)
    )
    cy.clickButton('Continue')
})

When('Maria opens the in custody recall notification', () => {
    cy.downloadPdf('Recall notification').should('contain', 'INFORMATION ONLY – OFFENDER IS IN CUSTODY')
    cy.clickLink('Continue')
})

When('Maria opens the not in custody recall notification', () => {
    cy.downloadPdf('Recall notification').should('contain', 'RECALL NOTIFICATION')
    cy.clickLink('Continue')
})

When('Maria records the issuance of recall notification', () => {
    cy.selectCheckboxes('I have sent the email to all recipients', ['I have sent the email to all recipients'])
    const fiveMinutesAgo = getIsoDateForMinutesAgo(5)
    cy.enterDateTime(fiveMinutesAgo)
    cy.uploadEmail({field: 'recallNotificationEmailFileName', file: 'email.msg'})
    cy.clickButton('Complete assessment')
})

When('Maria can see that the recall is assessed', () => {
    cy.get('@firstLastName').then((firstLastName) => {
        cy.pageHeading().should('equal', `Recall assessed for ${firstLastName}`)
    })
})

When('Maria can see that they are unassigned from the recall', () => {
    cy.clickLink('Assess another recall')
    cy.get('@recallId').then(recallId => {
        cy.getRecallItemFromList({recallId, columnQaAttr: 'assignedTo'}).should('equal', '')
    })
})

When('Maria can see that they are assigned to the recall on the Not in custody tab', () => {
    cy.clickLink('Assess another recall')
    cy.clickLink('Not in custody')
    const {firstName, lastName} = caseworker
    cy.get('@recallId').then(recallId => {
        cy.getRecallItemFromList({recallId, columnQaAttr: 'assignedTo'}).should('equal', `${firstName} ${lastName}`)
    })
})

When('Maria confirms the details captured during assessment', () => {
    cy.get('@recallId').then(recallId => {
        cy.clickLink({qaAttr: `view-recall-${recallId}`})
    })
    cy.recallInfo('Recall type').should('equal', 'Fixed term')
    cy.recallInfo('Recall length').should('equal', '28 days') // TODO - compute from sentence length
    const {firstName, lastName} = caseworker
    cy.recallInfo('Recall booked by').should('equal', `${firstName} ${lastName}`)
    cy.recallInfo('Licence conditions breached').should('equal', recall.licenceConditionsBreached)
    recall.reasonsForRecall.forEach(reason =>
        cy.getElement({qaAttr: `reasonsForRecall-${reason}`}).should('exist')
    )
    cy.recallInfo('Recall assessed by').should('equal', `${firstName} ${lastName}`)
    cy.recallInfo('Assessment notes').should('equal', recall.agreeWithRecallDetail)
    cy.recallInfo('Custody status at booking').should('equal', 'In custody')
    cy.recallInfo('Prison held in').should('equal', recall.currentPrisonLabel)
})

When('Maria is able to see the documents generated during booking', () => {
    cy.get('@firstLastName').then((firstLastName) => {
        const {firstName, lastName} = splitFullName(firstLastName)
        const recallNotificationFileName = getGeneratedDocFileName({ firstName, lastName, bookingNumber: recall.bookingNumber, category: 'RECALL_NOTIFICATION'})
        cy.recallInfo('Recall notification').should('equal', recallNotificationFileName)
        const revocationOrderFileName = getGeneratedDocFileName({ firstName, lastName, bookingNumber: recall.bookingNumber, category: 'REVOCATION_ORDER'})
        cy.downloadPdf(revocationOrderFileName).should('contain', 'REVOCATION OF LICENCE')
    })
})

When('Maria can regenerate the revocation order and recall notification', () => {
    // recall notification
    cy.clickLink('Change Recall notification')
    cy.fillInput('Provide more detail', 'Details have changed')
    cy.clickButton('Continue')
    cy.recallInfo('Recall notification').should('contain', '(version 2)')

    // revocation order
    cy.clickLink('Change Revocation order')
    cy.fillInput('Provide more detail', 'Details have changed')
    cy.clickButton('Continue')
    cy.recallInfo('Revocation order').should('contain', '(version 2)')
    // recall notification was also recreated
    cy.recallInfo('Recall notification').should('contain', '(version 3)')
})

When('Maria adds a warrant reference number', () => {
    cy.get('@recallId').then(recallId => {
        cy.clickLink({qaAttr: `warrant-reference-${recallId}`})
    })
    cy.fillInput('What is the warrant reference number?', recall.warrantReferenceNumber)
    cy.clickButton('Continue')
    cy.getText('confirmation').should('equal', 'Warrant reference number has been added.')
    cy.clickLink('View')
    cy.recallInfo('Warrant reference number').should('equal', recall.warrantReferenceNumber)
    cy.getRecallIdFromUrl().as('notInCustodyRecallId')
})

When('Maria confirms the person is awaiting return to custody', () => {
    cy.clickLink('Recalls')
    cy.clickLink('Not in custody')
    cy.get('@notInCustodyRecallId').then(recallId => {
        cy.getRecallItemFromList({recallId, columnQaAttr: 'status'}).should('equal', 'Awaiting return to custody')
        cy.getElement({ qaAttr: `view-recall-${recallId}`}).click()
        cy.recallInfo('Custody status at assessment').should('equal', 'Not in custody')
    })
})