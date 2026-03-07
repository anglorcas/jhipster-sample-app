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
  await page.getByRole('link', { name: 'Label' }).click();
  await page.getByRole('button', { name: 'Create a new Label' }).click();
  await page.getByRole('textbox', { name: 'Label' }).click();
  await page.getByRole('textbox', { name: 'Label' }).fill('new');
  await page.getByRole('button', { name: 'Save' }).click();
  await page.getByText('A new Label is created with').click();
});
