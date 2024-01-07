FROM oven/bun:latest as builder
WORKDIR /app
COPY package.json .
RUN bun install
COPY . .
RUN bun run build

FROM nginx:1.19.0-alpine
COPY --from=builder /app/dist /var/www/html
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
