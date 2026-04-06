import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByRole('link', { name: 'Account', exact: true }).click();
  await page.locator('#login').click();
  await page.getByRole('textbox', { name: 'Username' }).click();
  await page.getByRole('textbox', { name: 'Username' }).fill('user');
  await page.getByRole('textbox', { name: 'Password' }).click();
  await page.getByRole('textbox', { name: 'Password' }).fill('user');
  await page.getByRole('button', { name: 'Sign in' }).click();
  await page.getByRole('link', { name: 'Entities' }).click();
  await page.getByRole('link', { name: 'Bank Account' }).click();
  await page.getByText('Bank Accounts').click();
  await page.getByRole('button', { name: 'Create a new Bank Account' }).click();
  await page.getByRole('heading', { name: 'Create or edit a Bank Account' }).click();
});
