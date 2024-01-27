# Stage 1: Build the Angular application
FROM node:18 as build-stage

# Set the working directory to /app
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install Angular CLI globally
RUN npm install -g @angular/cli

# Install app dependencies
RUN npm install

# Bundle app source
COPY . .

# Build the Angular application in production mode
RUN ng build --configuration production

# Stage 2: Serve the application using Nginx
FROM nginx:alpine

# Copy the build output from the build stage
COPY --from=build-stage /app/dist/sepr-group-phase /usr/share/nginx/html

# Expose port 80
EXPOSE 80
