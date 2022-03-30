'use strict'

const express = require('express')
const jwt = require('jsonwebtoken')
const morganBody = require('morgan-body')
const bodyParser = require('body-parser')
const fs = require('fs')
const app = express()

const manageRecallsUiUrl = process.env.MANAGE_RECALLS_UI_URL || 'http://localhost:3000'
const port = process.env.PORT || 9090
const privateKey = fs.readFileSync('jwtRS256.key')
const publicKeyModulus = fs.readFileSync('jwtRS256.key.modulus')

app.use(bodyParser.json())
morganBody(app, {logAllReqHeader:true, maxBodyLength:5000})

function createToken() {
  const payload = {
    name: "PPUD User",
    user_name: 'PPUD_USER',
    scope: ['read', 'write'],
    auth_source: 'external',
    authorities: ['MANAGE_RECALLS', 'ROLE_MANAGE_RECALLS'],
    jti: '83b50a10-cca6-41db-985f-e87efb303ddb',
    client_id: 'clientid',
  }

  return jwt.sign(payload, privateKey, { algorithm: 'RS256', expiresIn: '1h' })
}

app.get('/', (req, res) => {
  res.send('fake-hmpps-auth up and running')
})

app.get('/auth/health*', (req, res) => {
  res.send('OK')
})

app.get('/auth/oauth/authorize*', (req, res) => {
  res
    .redirect(`${manageRecallsUiUrl}/login/callback?code=codexxxx&state=${req.query['state']}`)
})

app.get('/auth/logout*', (req, res) => {
  res.contentType('text/html').send('<html><body>Login page<h1>Sign in</h1></body></html>')
})

app.post('/auth/oauth/token', (req, res) => {
  res
    .contentType('application/json')
    .header('Location', `${manageRecallsUiUrl}/login/callback?code=codexxxx&state=${req.query['state']}`)
    .send(
      JSON.stringify({
        access_token: createToken(),
        token_type: 'bearer',
        name: 'PPUD User',
        user_name: 'PPUD_USER',
        expires_in: 599,
        scope: 'read',
        internalUser: true,
      })
    )
})

app.get('/auth/.well-known/jwks.json', (req, res) => {
  res
    .contentType('application/json')
    .send(
      JSON.stringify({
        keys: [
          {
            kty: 'RSA',
            e: 'AQAB',
            use: 'sig',
            kid: 'jwtRS256',
            alg: 'RS256',
            n: 'p73fsljItGs7fvCwDzYMdiaDPQMeJSTBWsHmTknQScwxzFeWcm7n7gk26b12B0oA4H3YN00D3JSlk38kgYikN0q-73V0XmcDFQ-VnI1THshOA8S_gy-K-CISLgnkdfuVinbUm8HDTx3sH9wyJLZH6iWKRZRyJ978PmVayLKL7uqACfvMyteU8pBdaXr4wFmt-2s1SxFSLgc9B6sPJC_q1D7_Z1NkTemEzCePGLoLP8GEDnhtHKkD62RwX1zkbpfvxr_NA3ibFL9WnzbDjbr52p9imWy4d5t9Cdt71DTVrqVBeKpR-mVNyjEm2KkB3S55Sy9Z2ctMKJQ2oCtMXI6HPQ'
            // n: publicKeyModulus.toString().slice(0, -1),
          }
        ]
      })
    )
})

app.get('/auth/api/user/me', (req, res) => {
  res.contentType('application/json').send(
    JSON.stringify({
      staffId: 231232,
      username: 'PPUD_USER',
      active: true,
      name: 'PPUD User',
      uuid: '1223',
    })
  )
})

app.get('/auth/api/user/me/roles', (req, res) => {
  res.contentType('application/json').send(JSON.stringify([{ roleId: 'SOME_USER_ROLE' }]))
})

app.get('/favicon.ico', (req, res) => {
  res.send()
})

app.listen(port, () => {
  console.log(`fake-hmpps-auth listening on port ${port}!`)
})
