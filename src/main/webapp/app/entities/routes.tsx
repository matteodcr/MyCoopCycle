import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Commercant from './commercant';
import Livreur from './livreur';
import Cooperative from './cooperative';
import Client from './client';
import Produit from './produit';
import CategorieProduit from './categorie-produit';
import Commande from './commande';
import Item from './item';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="commercant/*" element={<Commercant />} />
        <Route path="livreur/*" element={<Livreur />} />
        <Route path="cooperative/*" element={<Cooperative />} />
        <Route path="client/*" element={<Client />} />
        <Route path="produit/*" element={<Produit />} />
        <Route path="categorie-produit/*" element={<CategorieProduit />} />
        <Route path="commande/*" element={<Commande />} />
        <Route path="item/*" element={<Item />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
