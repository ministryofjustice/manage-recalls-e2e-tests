const cucumber = require('cypress-cucumber-preprocessor').default
const pdf = require('pdf-parse');

module.exports = (on) => {
    on('file:preprocessor', cucumber())
    on('task', {
        readPdf (base64) {
            const buffer = Buffer.from(base64, 'base64')
            return pdf(buffer).catch(err => err)
        }
    })
}