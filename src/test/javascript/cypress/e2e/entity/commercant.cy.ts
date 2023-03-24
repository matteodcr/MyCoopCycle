import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Commercant e2e test', () => {
  const commercantPageUrl = '/commercant';
  const commercantPageUrlPattern = new RegExp('/commercant(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const commercantSample = { name: 'Orchestrator digital' };

  let commercant;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/commercants+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/commercants').as('postEntityRequest');
    cy.intercept('DELETE', '/api/commercants/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (commercant) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/commercants/${commercant.id}`,
      }).then(() => {
        commercant = undefined;
      });
    }
  });

  it('Commercants menu should load Commercants page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('commercant');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Commercant').should('exist');
    cy.url().should('match', commercantPageUrlPattern);
  });

  describe('Commercant page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(commercantPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Commercant page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/commercant/new$'));
        cy.getEntityCreateUpdateHeading('Commercant');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', commercantPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/commercants',
          body: commercantSample,
        }).then(({ body }) => {
          commercant = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/commercants+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [commercant],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(commercantPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Commercant page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('commercant');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', commercantPageUrlPattern);
      });

      it('edit button click should load edit Commercant page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Commercant');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', commercantPageUrlPattern);
      });

      it('edit button click should load edit Commercant page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Commercant');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', commercantPageUrlPattern);
      });

      it('last delete button click should delete instance of Commercant', () => {
        cy.intercept('GET', '/api/commercants/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('commercant').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', commercantPageUrlPattern);

        commercant = undefined;
      });
    });
  });

  describe('new Commercant page', () => {
    beforeEach(() => {
      cy.visit(`${commercantPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Commercant');
    });

    it('should create an instance of Commercant', () => {
      cy.get(`[data-cy="name"]`).type('Wisconsin Chicken Dy').should('have.value', 'Wisconsin Chicken Dy');

      cy.get(`[data-cy="location"]`).type('Devolved Granite').should('have.value', 'Devolved Granite');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        commercant = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', commercantPageUrlPattern);
    });
  });
});
