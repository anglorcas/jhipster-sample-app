import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByRole('link', { name: 'Account', exact: true }).click();
  await page.locator('#login').click();
  await page.getByRole('textbox', { name: 'Username' }).fill('user');
  await page.getByRole('textbox', { name: 'Password' }).click();
  await page.getByRole('textbox', { name: 'Password' }).fill('user');
  await page.getByRole('button', { name: 'Sign in' }).click();
  await page.getByRole('link', { name: 'Entities' }).click();
  await page.getByRole('link', { name: 'Bank Account' }).click();
  await page.getByRole('button', { name: 'Create a new Bank Account' }).click();
  await page.getByRole('textbox', { name: 'Name' }).click();
  await page.getByRole('textbox', { name: 'Name' }).fill('new');
  await page.getByRole('spinbutton', { name: 'Balance' }).click();
  await page.getByRole('spinbutton', { name: 'Balance' }).fill('0.0');
  await page.getByLabel('User').selectOption('2: Object');
  await page.getByRole('button', { name: 'Save' }).click();
  await page.getByText('A new Bank Account is created').click();
});
