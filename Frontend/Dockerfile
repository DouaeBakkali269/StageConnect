FROM node:18-alpine
WORKDIR /app
ENV NODE_ENV=production

# Copy the pre-built artifacts from GitLab CI
COPY .next/standalone ./
COPY .next/static ./.next/static
COPY public ./public

# Create non-root user for security
RUN addgroup -g 1001 -S nodejs
RUN adduser -S nextjs -u 1001
USER nextjs

EXPOSE 3000
CMD ["node", "server.js"]