import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './tests', // FIXED: relative to the config file
  timeout: 60 * 1000,
  retries: process.env.CI ? 2 : 0,
  use: {
    baseURL: 'http://localhost:8080',
    headless: true,
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
    trace: 'retain-on-failure',
  },
  projects: [
    { name: 'chromium', use: { ...devices['Desktop Chrome'] } },
    { name: 'firefox', use: { ...devices['Desktop Firefox'] } },
  ],
  reporter: [
    ['list'],
    ['html', { open: 'never', outputFolder: 'playwright-report' }],
    ['junit', { outputFile: 'playwright-results/results.xml' }],
  ],
  fullyParallel: true,
  forbidOnly: !!process.env.CI,
});
