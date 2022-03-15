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

When('Maria uploads the email sent to New Scotland Yard', () => {
    cy.selectCheckboxes('I have sent the email', ['I have sent the email'])
    cy.uploadEmail({field: 'nsyEmailFileName'})
    cy.clickButton('Continue')
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
    cy.get('@nomsNumber').then(nomsNumber =>
        cy.recallInfo('NOMIS').should('equal', nomsNumber)
    )
    cy.recallInfo('Booking number').should('equal', recall.bookingNumber)
    cy.recallInfo('Licence conditions breached').should('equal', recall.licenceConditionsBreached)
    cy.get('@recallType').then( recallType => {
        cy.recallInfo('Recall type').should('equal', recallType)
        if (recallType === 'Fixed term') {
            cy.recallInfo('Recall length').should('equal', '28 days')
        }
    })
    cy.clickLink('Continue')
})

When('Maria can open the dossier and letter to prison', () => {
    cy.get('@recallType').then(recallType => {
        let dossierText;
        let letterToPrisonText;
        if ('Fixed term' === recallType) {
            dossierText = '28 Day FTR 12 months & over';
            letterToPrisonText = '28 DAY FIXED TERM RECALL';
            cy.downloadPdf('Letter to prison').should('contain', letterToPrisonText) //FIXME: When letter to prison fixed for standard recalls
        } else {
            dossierText = 'Standard 255c recall review';
            letterToPrisonText = 'Dont know yet';
        }
        cy.downloadPdf('Dossier').should('contain', dossierText)
        // cy.downloadPdf('Letter to prison').should('contain', letterToPrisonText) //FIXME: When letter to prison fixed for standard recalls
    })
})

When('Maria has reviewed the dossier', () => {
    cy.selectCheckboxes('I have checked the dossier', ['I have checked the information in the dossier and letter is correct'])
    cy.clickButton('Continue')
})

When('Maria records that the dossier was emailed', () => {
    cy.selectCheckboxes('I have sent the email to all recipients', ['I have sent the email to all recipients'])
    cy.enterDateTime(getIsoDateForToday())
    cy.uploadEmail({field: 'dossierEmailFileName'})
    cy.clickButton('Complete dossier creation')
})

When('Maria sees confirmation that the dossier creation is complete', () => {
    cy.get('@firstLastName').then((firstLastName) => {
        cy.pageHeading().should('equal', `Dossier created and sent for ${firstLastName}`)
    })
})

When('Maria sees recall is in the complete tab with Dossier issued status', () => {
    cy.clickLink('Back to recalls')
    cy.clickLink('Completed')
    cy.get('@recallId').then(recallId => {
        cy.getRecallItemFromList({recallId, columnQaAttr: 'status'}).should('equal', 'Dossier issued')
    })
})

When('Maria confirms the details captured during dossier creation', () => {
    cy.get('@recallId').then(recallId => {
        cy.clickLink({qaAttr: `view-recall-${recallId}`})
    })
    cy.get('@recallType').then(recallType => {
        let expectedStatus
        if (recallType === 'Fixed term')
            expectedStatus = 'Dossier complete'
        else
            expectedStatus = 'Awaiting part B'
        cy.getText('recallStatus').should('equal', expectedStatus)
    })
    const {firstName, lastName} = caseworker
    cy.recallInfo('Dossier created by').should('equal', `${firstName} ${lastName}`)
    cy.recallInfo('Dossier sent').should('equal', formatIsoDate(getIsoDateForToday(), {dateOnly: true}))
    // TODO - CHECK DIFFERENT NOMIS NUMBER
})

When('Maria confirms recall information for the "not in custody" recall', () => {
    cy.get('@recallId').then(recallId => {
        cy.clickLink({qaAttr: `view-recall-${recallId}`})
    })
    cy.recallInfo('NSY email uploaded').should('equal', 'email.msg')
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

When('Maria can see that the recall appears on the Awaiting part B tab', () => {
    cy.clickLink('Recalls')
    cy.clickLink('Awaiting part B')
    cy.get('@recallId').then(recallId => {
        cy.getRecallItemFromList({recallId, columnQaAttr: 'assignedTo'}).should('equal', '')
    })
})

