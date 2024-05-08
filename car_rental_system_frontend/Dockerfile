# Stage 1: Build the Angular app
FROM node:18-alpine AS angular

WORKDIR /app


# Copy the rest of the application
COPY . .

# Install dependencies
RUN npm install

# Build the Angular app for production
RUN npm run build

# Stage 2: Serve the Angular app using Nginx
FROM httpd:alpine3.15

WORKDIR /usr/local/apache2/htdocs
# Copy the built Angular app from the previous stage to the Nginx public directory
COPY --from=angular app/dist/car_rental_system_frontend/browser .
COPY --from=angular app/dist/car_rental_system_frontend/server .
COPY --from=angular app/dist/car_rental_system_frontend/3rdpartylicenses.txt .
COPY --from=angular app/dist/car_rental_system_frontend/prerendered-routes.json .

# Copy the Nginx configuration file
COPY default.conf /etc/nginx/conf.d/default.conf