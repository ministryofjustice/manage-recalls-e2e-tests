import {splitIsoDateToParts} from "./utils";
import {recall} from "../fixtures/recall";

const userName = Cypress.env('USERNAME') || 'PPUD_USER'
const password = Cypress.env('PASSWORD') || 'password123456'


// =============================== PAGE ===============================

Cypress.Commands.add('visitPage', (url) => {
    cy.visit(url)
    cy.get('#username').type(userName)
    cy.get('#password').type(password)
    cy.get('#submit').click()
})

Cypress.Commands.add('pageHeading', () =>
    cy.get('h1').invoke('text').then(text => text.trim())
)

// cy.getIdsFromUrl().then(({ nomsNumber, recallId })=> {
//     cy.log(`NOMS number: ${nomsNumber}`)
//     cy.log(`Recall ID: ${recallId}`)
// })
Cypress.Commands.add('getIdsFromUrl', () =>
    cy.location('pathname').then(path => {
        const re = /\/persons\/(?<nomsNumber>[A-Z0-9]+)\/recalls\/(?<recallId>[^\/]+)/
        const {groups} = path.match(re)
        return {
            nomsNumber: groups.nomsNumber,
            recallId: groups.recallId
        }
    })
)


// =============================== NAVIGATE ===============================

Cypress.Commands.add('clickButton', (label, opts = {parent: 'body'}) =>
    cy.get(opts.parent).find('button').contains(label).click()
)

Cypress.Commands.add('clickLink', (label, opts = {parent: 'body'}) =>
    cy.get(opts.parent).find('a').contains(label).click()
)


// =============================== GET TEXT ===============================

Cypress.Commands.add('getText', (qaAttr) =>
    cy.get(`[data-qa="${qaAttr}"]`).invoke('text')
)

// ============================ GET ELEMENT ===============================

Cypress.Commands.add('getElement', ({qaAttr}) =>
    cy.get(`[data-qa="${qaAttr}"]`)
)

// ============================ FILL FORM INPUTS ===============================

Cypress.Commands.add('fillInput', (label, text, opts = {}) => {
    cy.get(opts.parent || 'body').contains('label', label)
        .invoke('attr', 'for')
        .then((id) =>
            cy.get('#' + id)
                .then($input => opts.clearExistingText ? cy.wrap($input).clear({force: true}).type(text) : cy.wrap($input).type(text))
        )
})

Cypress.Commands.add('fillInputGroup', (values, opts = {parent: 'body'}) => {
    Object.entries(values).forEach(([label, text]) => cy.fillInput(label, text, opts))
})


// ============================ RADIO BUTTONS ===============================

Cypress.Commands.add('selectRadio', (groupLabel, value, opts = {}) => {
    cy.get(opts.parent || 'body').contains('legend', groupLabel)
        .parent('fieldset')
        .then($fieldset => {
            if (opts.findByValue) {
                cy
                    .wrap($fieldset)
                    .find(`[value=${value}]`)
                    .invoke('attr', 'id')
                    .then(id => cy.get(`[for="${id}"]`).click())
            } else {
                cy.wrap($fieldset).contains('label', value).click()
            }
        })

})


// ============================ DROPDOWN / AUTOCOMPLETE ===============================

// TODO - look up ID using from recall object using passed text
Cypress.Commands.add('selectFromAutocomplete', (label, text, opts = {parent: 'body'}) => {
    cy.fillInput(label, text.substr(0, 3), opts)
    cy.get(opts.parent).contains(text).click({force: true})
})

Cypress.Commands.add('selectFromDropdown', (label, option, opts = {parent: 'body'}) => {
    cy.get(opts.parent).contains('label', label)
        .invoke('attr', 'for')
        .then((id) => {
            cy.get('#' + id).select(option)
        })
})


// ================================== UPLOAD / DOWNLOAD ===============================

Cypress.Commands.add('downloadPdf', (linkText) => {
    return cy.contains('a', linkText)
        .then($link => {
            $link[0].setAttribute('download', 'download')
            cy.wrap($link).click()
            const url = $link.attr('href')
            return cy.request({
                url, encoding: 'base64',
            })
        })
        .then((response) =>
            cy.task('readPdf', response.body).then(pdf => pdf.text)
        )
})

Cypress.Commands.add('suggestedCategoryFor', (fileName) => {
    return cy.contains('label', `Select a category for ${fileName}`)
        .invoke('attr', 'for')
        .then((id) =>
            cy.get('#' + id).invoke('val')
        )
})

Cypress.Commands.add('uploadFile', ({field, file, encoding}) => {
    cy.get(`[name="${field}"]`).attachFile({filePath: `../uploads/${file}`, encoding})
})

Cypress.Commands.add('uploadPDF', ({field, file}) => {
    cy.uploadFile({field, file, encoding: 'base64'})
})

// =================================== DATES ================================

Cypress.Commands.add('enterDateTimeFromRecall', (propertyName) => {
    cy.enterDateTime(recall[propertyName], {parent: `#${propertyName}`})
})

Cypress.Commands.add('enterDateTime', (isoDateTime, opts = {parent: 'body'}) => {
    const {day, month, year, hour, minute} = splitIsoDateToParts(isoDateTime)
    cy.fillInput('Day', day, opts)
    cy.fillInput('Month', month, opts)
    cy.fillInput('Year', year, opts)
    if (isoDateTime.length > 10) {
        cy.fillInput('Hour', hour, opts)
        cy.fillInput('Minute', minute, opts)
    }
})

// ================================ RECALL INFO ================================
Cypress.Commands.add('recallInfo', (label, opts = {}) =>
    cy
        .get(opts.parent || 'body')
        .find('.govuk-summary-list__key')
        .contains(label)
        .next('.govuk-summary-list__value')
        .invoke('text')
        .then(text => text.trim())
)