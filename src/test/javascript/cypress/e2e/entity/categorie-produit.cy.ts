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

describe('CategorieProduit e2e test', () => {
  const categorieProduitPageUrl = '/categorie-produit';
  const categorieProduitPageUrlPattern = new RegExp('/categorie-produit(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const categorieProduitSample = { name: 'integrated' };

  let categorieProduit;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/categorie-produits+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/categorie-produits').as('postEntityRequest');
    cy.intercept('DELETE', '/api/categorie-produits/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (categorieProduit) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/categorie-produits/${categorieProduit.id}`,
      }).then(() => {
        categorieProduit = undefined;
      });
    }
  });

  it('CategorieProduits menu should load CategorieProduits page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('categorie-produit');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CategorieProduit').should('exist');
    cy.url().should('match', categorieProduitPageUrlPattern);
  });

  describe('CategorieProduit page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(categorieProduitPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CategorieProduit page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/categorie-produit/new$'));
        cy.getEntityCreateUpdateHeading('CategorieProduit');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', categorieProduitPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/categorie-produits',
          body: categorieProduitSample,
        }).then(({ body }) => {
          categorieProduit = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/categorie-produits+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [categorieProduit],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(categorieProduitPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CategorieProduit page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('categorieProduit');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', categorieProduitPageUrlPattern);
      });

      it('edit button click should load edit CategorieProduit page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CategorieProduit');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', categorieProduitPageUrlPattern);
      });

      it('edit button click should load edit CategorieProduit page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CategorieProduit');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', categorieProduitPageUrlPattern);
      });

      it('last delete button click should delete instance of CategorieProduit', () => {
        cy.intercept('GET', '/api/categorie-produits/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('categorieProduit').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', categorieProduitPageUrlPattern);

        categorieProduit = undefined;
      });
    });
  });

  describe('new CategorieProduit page', () => {
    beforeEach(() => {
      cy.visit(`${categorieProduitPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CategorieProduit');
    });

    it('should create an instance of CategorieProduit', () => {
      cy.get(`[data-cy="name"]`).type('payment bl').should('have.value', 'payment bl');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        categorieProduit = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', categorieProduitPageUrlPattern);
    });
  });
});
