export * from './recall'
export * from './caseworker'

export const nomsNumber = Cypress.env('NOMS_NUMBER') || 'A1234AA'
export const nomsNumberNoMiddleName = Cypress.env('NOMS_NUMBER_NO_MIDDLE_NAME') || 'Z9876ZZ'