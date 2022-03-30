FROM node:16-alpine

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm ci --only=production

# RUN openssl genrsa -out jwtRS256.key 2048 \
#   && openssl rsa -in jwtRS256.key -pubout -out jwtRS256.key.pub \
#   && openssl rsa -pubin -modulus -noout < jwtRS256.key.pub | base64 > jwtRS256.key.modulus

COPY . .

ENV PORT=8080
EXPOSE 8080

CMD [ "node", "index.js" ]
