const cucumber = require('cypress-cucumber-preprocessor').default
const pdf = require('pdf-parse');

module.exports = (on) => {
    on('before:browser:launch', (browser = {}, launchOptions) => {
        if (browser.family === 'chromium' && browser.name !== 'electron') {
            launchOptions.args.push('--no-sandbox')
            launchOptions.args.push('--disable-dev-shm-usage')
        }
        return launchOptions
    })
    on('file:preprocessor', cucumber())
    on('task', {
        readPdf (base64) {
            const buffer = Buffer.from(base64, 'base64')
            return pdf(buffer).catch(err => err)
        }
    })
}