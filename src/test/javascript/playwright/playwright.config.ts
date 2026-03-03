import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './src/test/javascript/playwright/tests', // your test folder
  timeout: 30 * 1000, // 30 seconds per test
  retries: process.env.CI ? 2 : 0, // retry failed tests 2x on CI
  use: {
    baseURL: 'http://localhost:8080', // adjust if your app runs on another port
    headless: true, // run headless in CI
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
    trace: 'retain-on-failure',
  },
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'firefox',
      use: { ...devices['Desktop Firefox'] },
    },
    {
      name: 'webkit',
      use: { ...devices['Desktop Safari'] },
    },
  ],
  reporter: [
    ['list'],
    ['html', { open: 'never', outputFolder: 'playwright-report' }],
    ['junit', { outputFile: 'playwright-results/results.xml' }],
  ],
  fullyParallel: true, // run tests in parallel
  forbidOnly: !!process.env.CI, // fail if `test.only` is left in code
});
