import {When} from "cypress-cucumber-preprocessor/steps";
import {recall, caseworker, nomsNumber} from "../../fixtures";
import {booleanToYesNo, formatIsoDate} from "../../support/utils";

When('Maria enters their user details', () => {
    cy.visitPage('/user-details')
    cy.fillInput('First name', caseworker.firstName, {clearExistingText: true})
    cy.fillInput('Last name', caseworker.lastName, {clearExistingText: true})
    cy.fillInput('Email address', caseworker.email, {clearExistingText: true})
    cy.fillInput('Phone number', caseworker.phoneNumber, {clearExistingText: true})
    cy.selectRadio('Caseworker band', 'Band 3')
    cy.uploadFile({field: 'signature', file: 'signature.jpg'})
    cy.clickButton('Save')
    cy.clickLink('Recalls')
    cy.pageHeading().should('equal', 'Recalls')
})

When('Maria searches for the environment specific NOMS number', () => {
    cy.clickLink('Find a person')
    cy.fillInput('NOMIS number', nomsNumber)
    cy.clickButton('Search')
    cy.getText('name').as('firstLastName')
})

When('Maria clicks on the Book a recall link', () => {
    cy.clickButton('Book a recall')
})

When('Maria enters the licence name', () => {
    cy.get('@firstLastName').then((firstLastName) =>
        cy.selectRadio(`How does ${firstLastName}\'s name appear on the licence?`, recall.licenceNameCategory, {findByValue: true})
    )
    cy.clickButton('Continue')
})

When('Maria enters the pre-convictions name', () => {
    cy.get('@firstLastName').then((firstLastName) =>
        cy.selectRadio(`How does ${firstLastName}'s name appear on the previous convictions sheet (pre-cons)?`, recall.previousConvictionMainNameCategory, {findByValue: true})
    )
    cy.clickButton('Continue')
})

When('Maria submits the date and email of the recall request received from probation service', () => {
    cy.enterDateTimeFromRecall('recallEmailReceivedDateTime')
    cy.uploadFile({field: 'recallRequestEmailFileName', file: 'email.msg', encoding: 'binary'})
    cy.clickButton('Continue')
})

When('Maria submits the sentence, offence and release details', () => {
    cy.enterDateTimeFromRecall('sentenceDate')
    cy.enterDateTimeFromRecall('licenceExpiryDate')
    cy.enterDateTimeFromRecall('sentenceExpiryDate')
    const {years, months, days} = recall.sentenceLength
    cy.fillInputGroup({'Years': years, 'Months': months, 'Days': days}, {parent: '#sentenceLength'})
    cy.selectFromAutocomplete('Sentencing court', recall.sentencingCourtLabel)
    cy.selectFromAutocomplete('Releasing prison', recall.lastReleasePrisonLabel)
    cy.fillInput('Index offence', recall.indexOffence)
    cy.fillInput('Booking number', recall.bookingNumber)
    cy.enterDateTimeFromRecall('lastReleaseDate')
    cy.enterDateTimeFromRecall('conditionalReleaseDate')
    cy.clickButton('Continue')
})

When('Maria submits the police contact details', () => {
    cy.selectFromAutocomplete('What is the name of the local police force?', recall.localPoliceForceLabel)
    cy.clickButton('Continue')
})

When('Maria submits any vulnerability and contraband related details for the offender', () => {
    cy.selectRadio('Are there any vulnerability issues or diversity needs?', booleanToYesNo(recall.vulnerabilityDiversity))
    cy.fillInput('Provide more detail', recall.vulnerabilityDiversityDetail, {parent: '#conditional-vulnerabilityDiversity'})
    cy.get('@firstLastName').then((firstLastName) =>
        cy.selectRadio(`Do you think ${firstLastName} will bring contraband into prison?`, booleanToYesNo(recall.contraband))
    )
    cy.fillInput('Provide more detail', recall.contrabandDetail, {parent: '#conditional-contraband'})
    cy.selectFromDropdown('MAPPA level', recall.mappaLevelLabel)
    cy.clickButton('Continue')
})

When('Maria submits the probation officer details', () => {
    cy.fillInput('Name', recall.probationOfficerName)
    cy.fillInput('Email address', recall.probationOfficerEmail)
    cy.fillInput('Phone number', recall.probationOfficerPhoneNumber)
    cy.selectFromAutocomplete('Local Delivery Unit (LDU)', recall.localDeliveryUnitLabel)
    cy.fillInput('Assistant Chief Officer (ACO) that signed-off the recall', recall.authorisingAssistantChiefOfficer)
    cy.clickButton('Continue')
})

When('Maria uploads some documents', () => {
    cy.uploadPDF({field: 'documents', file: 'Licence.pdf'})
    cy.suggestedCategoryFor('Licence.pdf').should('equal', 'LICENCE')
    cy.uploadPDF({field: 'documents', file: 'Part A for FTR.pdf'})
    cy.suggestedCategoryFor('Part A for FTR.pdf').should('equal', 'PART_A_RECALL_REPORT')
    cy.clickButton('Continue')
})

When('Maria submits the reason for missing documents', () => {
    cy.uploadFile({field: 'missingDocumentsEmailFileName', file: 'email.msg', encoding: 'binary'})
    cy.fillInput('Provide more detail', 'Chased', {clearExistingText: true})
    cy.clickButton('Continue')
    cy.pageHeading().should('equal', 'Check the details before booking this recall')
})

When('Maria can check their answers', () => {
    cy.get('@firstLastName').then((firstLastName) => {
        cy.recallInfo('Name', {parent: '#personalDetails'}).should('equal', firstLastName)
        cy.recallInfo('Name on pre-cons').should('equal', firstLastName)
    })
    cy.recallInfo('NOMIS').should('equal', nomsNumber)

    // Recall details
    cy.recallInfo('Recall email received').should('equal', formatIsoDate(recall.recallEmailReceivedDateTime))
    cy.recallInfo('Recall email uploaded').should('equal', 'email.msg')

    // Sentence, offence and release details
    cy.recallInfo('Sentence type').should('equal', 'Determinate')
    cy.recallInfo('Date of sentence').should('equal', formatIsoDate(recall.sentenceDate))
    cy.recallInfo('Licence expiry date').should('equal', formatIsoDate(recall.licenceExpiryDate))
    cy.recallInfo('Sentence expiry date').should('equal', formatIsoDate(recall.sentenceExpiryDate))
    cy.recallInfo('Length of sentence').should('equal', '2 years 3 months') // TODO - format from recall value
    cy.recallInfo('Sentencing court').should('equal', recall.sentencingCourtLabel)
    cy.recallInfo('Index offence').should('equal', recall.indexOffence)
    cy.recallInfo('Releasing prison').should('equal', recall.lastReleasePrisonLabel)
    cy.recallInfo('Booking number').should('equal', recall.bookingNumber)
    cy.recallInfo('Latest release date').should('equal', formatIsoDate(recall.lastReleaseDate, {dateOnly: true}))
    cy.recallInfo('Conditional release date').should('equal', formatIsoDate(recall.conditionalReleaseDate, {dateOnly: true}))

    // Local police force
    cy.recallInfo('Name', {parent: '#police'}).should('equal', recall.localPoliceForceLabel)

    // Issues or needs
    cy.recallInfo('Vulnerability and diversity').should('equal', recall.vulnerabilityDiversityDetail)
    cy.recallInfo('Contraband').should('equal', recall.contrabandDetail)
    cy.recallInfo('MAPPA level').should('equal', recall.mappaLevelLabel)

    // Probation details
    cy.getText('probationOfficerName').should('equal', recall.probationOfficerName)
    cy.getText('probationOfficerEmail').should('equal', recall.probationOfficerEmail)
    cy.getText('probationOfficerPhoneNumber').should('equal', recall.probationOfficerPhoneNumber)
    cy.recallInfo('Local Delivery Unit').should('equal', recall.localDeliveryUnitLabel)
    cy.recallInfo('ACO').should('equal', recall.authorisingAssistantChiefOfficer)

    // Uploaded documents
    cy.recallInfo('Part A recall report').should('equal', 'Part A.pdf')
    cy.recallInfo('Licence').should('equal', 'Licence.pdf')

    // Missing documents
    cy.recallInfo('Previous convictions sheet').should('equal', 'Missing')
    cy.recallInfo('OASys report').should('equal', 'Missing')
    cy.recallInfo('Details', {parent: '#missing-documents'}).should('equal', 'Chased')
    cy.recallInfo('Email uploaded', {parent: '#missing-documents'}).should('equal', 'email.msg')
})

When('Maria uploads missing documents', () => {
    cy.clickLink('Add Previous convictions sheet')
    cy.pageHeading().should('equal', 'Upload documents')
    cy.uploadPDF({field: 'documents', file: 'Pre cons.pdf'})
    cy.suggestedCategoryFor('Pre cons.pdf').should('equal', 'PREVIOUS_CONVICTIONS_SHEET')
    cy.uploadPDF({field: 'documents', file: 'OASys.pdf'})
    cy.suggestedCategoryFor('OASys.pdf').should('equal', 'OASYS_RISK_ASSESSMENT')
    cy.clickButton('Continue')
    cy.recallInfo('Previous convictions sheet').should('equal', 'Pre Cons.pdf')
    cy.recallInfo('OASys report').should('equal', 'OASys.pdf')
})

When('Maria downloads the documents', () => {
    cy.downloadPdf('Part A.pdf').should('contain', 'PART A: Recall Report')
    cy.downloadPdf('Licence.pdf').should('contain', 'REVOCATION OF LICENCE')
    cy.downloadPdf('Pre Cons.pdf').should('contain', 'Pre cons')
    cy.downloadPdf('OASys.pdf').should('contain', 'OASys')
})

When('Maria completes the booking', () => {
    cy.clickButton('Complete booking')
    cy.get('@firstLastName').then((firstLastName) => {
        cy.pageHeading().should('equal', `Recall booked for ${firstLastName}`)
    })
    cy.getRecallIdFromUrl().as('recallId')
})

When('Maria confirms they can\'t assess the recall as a band 3', () => {
    cy.clickLink('Recalls')
    cy.get('@recallId').then(recallId => {
        cy.getElement({qaAttr: `recall-id-${recallId}`}).should('not.exist')
    })
})
