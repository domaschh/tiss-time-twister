# Use an official Node runtime as a parent image
FROM node:18

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

# Expose port 4200
EXPOSE 4200

# Start the Angular application
CMD ["ng", "serve", "--host", "0.0.0.0", "--disable-host-check"]
