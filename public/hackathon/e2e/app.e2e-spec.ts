import { HackathonPage } from './app.po';

describe('hackathon App', () => {
  let page: HackathonPage;

  beforeEach(() => {
    page = new HackathonPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
